package com.sudoplay.axion.tag.impl;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.sudoplay.axion.tag.Abstract_Tag;

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
      tag.setName(null);
      tag.setParent(this);
      data.add(tag);
    } else if (type == tag.getTagId()) {
      tag.setName(null);
      tag.setParent(this);
      data.add(tag);
    } else {
      throw new InvalidParameterException("Cannot add multiple tag types to a list tag.");
    }
  }

  public void overrideType(byte newType) {
    type = newType;
  }

  public byte getType() {
    return type;
  }

  public List<Abstract_Tag> getAsList() {
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
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((data == null) ? 0 : data.hashCode());
    result = prime * result + type;
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!super.equals(obj))
      return false;
    if (getClass() != obj.getClass())
      return false;
    TagList other = (TagList) obj;
    if (data == null) {
      if (other.data != null)
        return false;
    } else if (!data.equals(other.data))
      return false;
    if (type != other.type)
      return false;
    return true;
  }

  @Override
  public String toString() {
    return TAG_NAME + super.toString() + ": " + data.size() + " entries of type " + type;
  }

}
