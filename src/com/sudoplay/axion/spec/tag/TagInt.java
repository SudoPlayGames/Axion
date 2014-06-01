package com.sudoplay.axion.spec.tag;

import com.sudoplay.axion.tag.Tag;

/**
 * @tag.type 3
 * 
 * @tag.name <code>TAG_Int</code>
 * 
 * @tag.payload * A signed short (32 bits, big endian).
 * 
 * @author Jason Taylor
 * 
 */
public class TagInt extends Tag {

  private int data;

  /**
   * Creates a new {@link TagInt} with the given name.
   * 
   * @param newName
   *          the {@link Tag} name
   */
  public TagInt(final String newName) {
    super(newName);
  }

  /**
   * Creates a new {@link TagInt} with no name and the given value.
   * 
   * @param newInt
   *          the {@link Integer} value
   */
  public TagInt(final int newInt) {
    this(null, newInt);
  }

  /**
   * Creates a new {@link TagInt} with the given name and value.
   * 
   * @param newName
   *          the {@link Tag} name
   * @param newInt
   *          the {@link Integer} value
   */
  public TagInt(final String newName, final int newInt) {
    super(newName);
    data = newInt;
  }

  /**
   * Sets the value of this {@link TagInt} to the given value.
   * 
   * @param newInt
   *          the {@link Integer} value
   */
  public void set(final int newInt) {
    data = newInt;
  }

  /**
   * Returns the value of this {@link TagInt}.
   * 
   * @return the value of this {@link TagInt}
   */
  public int get() {
    return data;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + data;
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
    TagInt other = (TagInt) obj;
    if (data != other.data)
      return false;
    return true;
  }

  @Override
  public String toString() {
    return super.toString() + ": " + data;
  }

  @Override
  public TagInt clone() {
    return new TagInt(getName(), data);
  }

}
