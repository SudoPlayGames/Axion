package com.sudoplay.axion.tag;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.sudoplay.axion.helper.TagHelper;

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
public class TagList extends Tag {

  public static final byte TAG_ID = (byte) 9;
  public static final String TAG_NAME = "TAG_List";

  private List<Tag> data;

  /**
   * Store type id for tags in this list; all tags must be of the same type.
   */
  private final byte type;

  public TagList(final Class<? extends Tag> tagClass) {
    this(tagClass, null);
  }

  public TagList(final Class<? extends Tag> tagClass, final String newName) {
    super(newName);
    data = new ArrayList<Tag>();
    type = TagHelper.getId(tagClass);
  }

  /**
   * Add tag to the end of the list. If no tag type has been assigned to the
   * list, assign the type of the tag to be added. If a tag type has been
   * assigned to the list and the tag to be added does not match this type, an
   * exception is thrown. Cannot add <code>TAG_End</code> to the list.
   * 
   * @param tag
   */
  public void add(final Tag tag) {
    if (tag.hasParent()) {
      throw new IllegalStateException("Tag can not be added to more than one collection tag");
    } else if (tag.getTagId() == TagEnd.TAG_ID) {
      throw new InvalidParameterException("Can not add a TAG_End to a TAG_List");
    } else if (type == tag.getTagId()) {
      tag.setName(null);
      tag.setParent(this);
      data.add(tag);
    } else {
      throw new InvalidParameterException("Can not add multiple tag types to a list tag");
    }
  }

  public void remove(final Tag tag) {
    if (data.remove(tag)) {
      tag.setParent(null);
    }
  }

  public Tag remove(final int index) {
    return data.remove(index);
  }

  public void addByte(final byte newByte) {
    add(new TagByte(null, newByte));
  }

  public void addByteArray(final byte[] newByteArray) {
    add(new TagByteArray(null, newByteArray));
  }

  public void addCompound(final TagCompound newTagCompound) {
    add(newTagCompound);
  }

  public void addDouble(final double newDouble) {
    add(new TagDouble(null, newDouble));
  }

  public void addFloat(final float newFloat) {
    add(new TagFloat(null, newFloat));
  }

  public void addInt(final int newInt) {
    add(new TagInt(null, newInt));
  }

  public void addIntArray(final int[] newIntArray) {
    add(new TagIntArray(null, newIntArray));
  }

  public void addList(final TagList newTagList) {
    add(newTagList);
  }

  public void addLong(final long newLong) {
    add(new TagLong(null, newLong));
  }

  public void addShort(final short newShort) {
    add(new TagShort(null, newShort));
  }

  public void addString(final String newString) {
    add(new TagString(null, newString));
  }

  public byte getType() {
    return type;
  }

  public List<Tag> getAsList() {
    return Collections.unmodifiableList(data);
  }

  public Tag get(final int index) {
    return data.get(index);
  }

  public int size() {
    return data.size();
  }

  public void clear() {
    data.clear();
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

  @Override
  protected void onNameChange(final String oldName, final String newName) {
    if (newName != null && !newName.isEmpty()) {
      throw new IllegalStateException("Tag belongs to a " + TagList.TAG_NAME + " and can not be named");
    }
  }

}
