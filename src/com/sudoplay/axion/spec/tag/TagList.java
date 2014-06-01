package com.sudoplay.axion.spec.tag;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.sudoplay.axion.tag.AxionIllegalTagNameException;
import com.sudoplay.axion.tag.AxionInvalidTagException;
import com.sudoplay.axion.tag.ContainerTag;
import com.sudoplay.axion.tag.Tag;

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
public class TagList extends ContainerTag {

  private final List<Tag> data;

  /**
   * Stores the type for tags in this list; all tags must be of the same type.
   */
  private final Class<? extends Tag> type;

  /**
   * Creates a new {@link TagList} of the given type with no name and an empty
   * backing list.
   * 
   * @param tagClass
   *          the list type
   */
  public TagList(final Class<? extends Tag> tagClass) {
    this(tagClass, null, new ArrayList<Tag>());
  }

  /**
   * Creates a new {@link TagList} of the given type with no name and a copy of
   * the given list as the backing list.
   * 
   * @param tagClass
   *          the list type
   * @param newList
   *          the list
   */
  public TagList(final Class<? extends Tag> tagClass, final List<Tag> newList) {
    this(tagClass, null, newList);
  }

  /**
   * Creates a new {@link TagList} of the given type with the given name and an
   * empty backing list.
   * 
   * @param tagClass
   *          the list type
   * @param newName
   *          the {@link Tag} name
   */
  public TagList(final Class<? extends Tag> tagClass, final String newName) {
    this(tagClass, newName, new ArrayList<Tag>());
  }

  /**
   * Creates a new {@link TagList} of the given type with the given name and a
   * copy of the given list as the backing list.
   * 
   * @param tagClass
   *          the list type
   * @param newName
   *          the {@link Tag} name
   * @param newList
   *          the list
   */
  public TagList(final Class<? extends Tag> tagClass, final String newName, final List<Tag> newList) {
    super(newName);
    type = tagClass;
    if (newList == null || newList.isEmpty()) {
      data = new ArrayList<Tag>();
    } else {
      data = new ArrayList<Tag>(newList);
      for (Tag tag : data) {
        assertValid(tag);
        tag.setName("");
      }
    }
  }

  /**
   * Adds tag to the end of the list. If the tag to be added does not match this
   * list's type, an exception is thrown.
   * 
   * @param tag
   *          the {@link Tag} to add
   */
  public void add(final Tag tag) {
    assertValid(tag).addTo(this);
  }

  /**
   * Removes a {@link Tag} from this list.
   * 
   * @param tag
   *          the {@link Tag} to remove
   * @return true if the given {@link Tag} was found and removed
   */
  public boolean remove(final Tag tag) {
    if (contains(tag)) {
      tag.removeFromParent();
      return true;
    }
    return false;
  }

  /**
   * Removes a {@link Tag} from this list via its index.
   * 
   * @param index
   *          the index of the {@link Tag} to remove
   * @return the {@link Tag} removed
   */
  public Tag remove(final int index) {
    Tag removed = data.get(index);
    removed.removeFromParent();
    return removed;
  }

  /**
   * Checks if this {@link TagList} contains the {@link Tag} passed in.
   * <p>
   * Since names are stripped of tags in lists, this method compares values
   * only; tags with different names and identical values will match.
   * 
   * @param tag
   *          tag whose presence in this list is to be tested
   * 
   * @return true if this list contains a tag with a matching value
   */
  @Override
  public boolean contains(final Tag tag) {
    if (tag == null || !tag.getClass().isAssignableFrom(type)) {
      return false;
    }
    /*
     * If we don't strip off its name here, it will not satisfy equals(). We
     * also clone it because we don't want to alter the original.
     */
    return data.contains(tag.clone().setName(""));
  }

  @Override
  public Iterator<Tag> iterator() {
    return Collections.unmodifiableList(data).iterator();
  }

  @Override
  public int size() {
    return data.size();
  }

  @Override
  public void clear() {
    List<Tag> toRemove = new ArrayList<Tag>(data);
    for (Tag child : toRemove) {
      child.removeFromParent();
    }
  }

  /**
   * Returns the type of this {@link TagList}.
   * 
   * @return the type of this {@link TagList}
   */
  public Class<? extends Tag> getType() {
    return type;
  }

  /**
   * Returns an unmodifiable view of the backing list.
   * 
   * @return an unmodifiable view of the backing list
   */
  public List<Tag> getAsList() {
    return Collections.unmodifiableList(data);
  }

  /**
   * Returns a {@link Tag} from the backing list at the index given.
   * 
   * @param index
   *          the index of the {@link Tag} to get
   * @return a {@link Tag} from the backing list at the index given
   */
  @SuppressWarnings("unchecked")
  public <T extends Tag> T get(final int index) {
    return (T) data.get(index);
  }

  @Override
  protected void onChildAddition(Tag tag) {
    tag.setName(null);
    data.add(tag);
  }

  @Override
  protected void onChildRemoval(Tag tag) {
    data.remove(tag);
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
  protected void onChildNameChange(final String oldName, final String newName) throws AxionIllegalTagNameException {
    if (newName != null && !newName.isEmpty()) {
      throw new AxionIllegalTagNameException("Tag belongs to a " + TagList.class.getSimpleName() + " and can not be named");
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

  /**
   * Checks if the {@link Tag} given is not null and of this list's type.
   * 
   * @param tag
   *          the {@link Tag} to check
   * @return the {@link Tag} given
   * @throws AxionInvalidTagException
   */
  protected Tag assertValid(final Tag tag) throws AxionInvalidTagException {
    if (tag == null) {
      throw new AxionInvalidTagException(this.toString() + " can't contain null tags");
    } else if (type != tag.getClass()) {
      throw new AxionInvalidTagException("Can't add tag of type [" + tag.getClass().getSimpleName() + "] to " + this.toString());
    }
    return tag;
  }

}
