package com.sudoplay.axion.spec.tag;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.sudoplay.axion.tag.AxionIllegalNameChangeException;
import com.sudoplay.axion.tag.AxionInvalidTagException;

/**
 * @tag.type 9
 * 
 * @tag.name <code>TAG_List</code>
 * 
 * @tag.payload * <code>TAG_Byte</code> tagId<br>
 *              * <code>TAG_Int</code> length<br>
 *              * A sequential list of Tags (not Named Tags), of type
 *              <code>typeId</code>. The length of this array is
 *              <code>length</code> Tags.
 * 
 * @tag.note All tags share the same type.
 * 
 * @author Jason Taylor
 * 
 */
public class TagList extends Tag implements Iterable<Tag> {

  private final List<Tag> data;

  /**
   * Stores the type for tags in this list; all tags must be of the same type.
   */
  private final Class<? extends Tag> type;

  public TagList(final Class<? extends Tag> tagClass) {
    this(tagClass, "", new ArrayList<Tag>());
  }

  public TagList(final Class<? extends Tag> tagClass, final String newName) {
    this(tagClass, newName, new ArrayList<Tag>());
  }

  public TagList(final Class<? extends Tag> tagClass, final String newName, final List<Tag> newList) {
    super(newName);
    type = tagClass;
    if (newList == null || newList.isEmpty()) {
      data = new ArrayList<Tag>();
    } else {
      data = new ArrayList<Tag>(newList);
      for (Tag tag : data) {
        assertValidTag(tag);
      }
    }
  }

  private void assertValidTag(final Tag tag) throws AxionInvalidTagException {
    if (tag == null) {
      throw new AxionInvalidTagException(this.toString() + " can't contain null tags");
    } else if (type != tag.getClass()) {
      throw new AxionInvalidTagException("Can't add tag of type [" + tag.getClass().getSimpleName() + "] to " + this.toString());
    } else if (tag.hasParent()) {
      throw new AxionInvalidTagException("Tag can't be added to more than one collection tag");
    }
  }

  /**
   * Add tag to the end of the list. If the tag to be added does not match this
   * list's type, an exception is thrown.
   * 
   * @param tag
   */
  public void add(final Tag tag) {
    assertValidTag(tag);
    tag.setName(null);
    tag.setParent(this);
    data.add(tag);
  }

  public boolean remove(final Tag tag) {
    if (data.remove(tag)) {
      tag.setParent(null);
      return true;
    }
    return false;
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

  public Class<? extends Tag> getType() {
    return type;
  }

  public List<Tag> getAsList() {
    return Collections.unmodifiableList(data);
  }

  @SuppressWarnings("unchecked")
  public <T extends Tag> T get(final int index) {
    return (T) data.get(index);
  }

  @Override
  public Iterator<Tag> iterator() {
    return Collections.unmodifiableList(data).iterator();
  }

  public int size() {
    return data.size();
  }

  public void clear() {
    data.clear();
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((data == null) ? 0 : data.hashCode());
    result = prime * result + ((type == null) ? 0 : type.hashCode());
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
    if (type == null) {
      if (other.type != null)
        return false;
    } else if (!type.equals(other.type))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return super.toString() + ": " + data.size() + " entries of type " + type.getSimpleName();
  }

  @Override
  protected void onChildNameChange(final String oldName, final String newName) throws AxionIllegalNameChangeException {
    if (newName != null && !newName.isEmpty()) {
      throw new AxionIllegalNameChangeException("Tag belongs to a " + TagList.class.getSimpleName() + " and can not be named");
    }
  }

  @Override
  public TagList clone() {
    if (data.isEmpty()) {
      return new TagList(type, getName(), new ArrayList<Tag>());
    } else {
      List<Tag> newList = new ArrayList<Tag>(data.size());
      for (Tag tag : data) {
        newList.add(tag.clone());
      }
      return new TagList(type, getName(), newList);
    }
  }

}
