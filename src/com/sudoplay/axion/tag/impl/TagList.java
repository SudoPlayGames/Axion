package com.sudoplay.axion.tag.impl;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.sudoplay.axion.tag.Abstract_Tag;
import com.sudoplay.axion.tag.TagFactory;

/**
 * @tag.type 9
 * 
 * @tag.name <code>TAG_List</code>
 * 
 * @tag.payload * <code>TAG_Byte</code> tagId<br>
 *              * <code>TAG_Int</code> length<br>
 *              * A sequential list of Tags (not Named Tags), of type
 *              <code>typeId</code>. The length of this array is
 *              <code>length</code> Tags
 * 
 * @tag.note All tags share the same type.
 * 
 * @author Jason Taylor
 * 
 */
public class TagList extends Abstract_Tag {

  public static final byte TAG_ID = (byte) 9;
  public static final String TAG_NAME = "TAG_List";

  private List<Abstract_Tag> data;

  /**
   * Used to store type id for tags in this list; all tags must be of the same
   * type.
   */
  private byte type = TagEnd.TAG_ID;

  public TagList() {
    super(null);
    data = new ArrayList<Abstract_Tag>();
  }

  public TagList(final String newName) {
    super(newName);
    data = new ArrayList<Abstract_Tag>();
  }

  /**
   * Add tag to the end of the list. If no tag type has been assigned to the
   * list, assign the type of the tag to be added. If a tag type has been
   * assigned to the list and the tag to be added does not match this type, an
   * exception is thrown. Cannot add <code>TAG_End</code> to the list.
   * 
   * @param tag
   */
  public void add(Abstract_Tag tag) {
    if (tag.getTagId() == TagEnd.TAG_ID) {
      throw new InvalidParameterException("Cannot add a TAG_End to a TAG_List.");
    } else if (type == TagEnd.TAG_ID) {
      type = tag.getTagId();
      data.add(tag);
    } else if (type == tag.getTagId()) {
      data.add(tag);
    } else {
      throw new InvalidParameterException("Cannot add multiple tag types to a list tag.");
    }
  }

  public List<Abstract_Tag> getAsList() {
    return data;
  }

  public List<Abstract_Tag> getAsUnmodifiableList() {
    return Collections.unmodifiableList(data);
  }

  public Abstract_Tag get(final int index) {
    return data.get(index);
  }

  public int size() {
    return data.size();
  }

  public void clear() {
    data.clear();
    type = TagEnd.TAG_ID;
  }

  @Override
  public byte getTagId() {
    return TAG_ID;
  }

  @Override
  public String getTagName() {
    return TAG_NAME;
  }

  @Override
  public void read(DataInput dataInput) throws IOException {
    type = dataInput.readByte();
    int len = dataInput.readInt();
    data = new ArrayList<Abstract_Tag>();
    for (int i = 0; i < len; i++) {
      Abstract_Tag tag = TagFactory.create(type, null);
      tag.read(dataInput);
      data.add(tag);
    }
  }

  @Override
  public void write(DataOutput dataOutput) throws IOException {
    if (data.size() == 0) {
      type = TagByte.TAG_ID;
    }
    dataOutput.writeByte(type);
    dataOutput.writeInt(data.size());
    for (int i = 0; i < data.size(); i++) {
      data.get(i).write(dataOutput);
    }
  }

}
