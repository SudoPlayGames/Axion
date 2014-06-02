package com.sudoplay.axion.ext.tag;

import java.util.Arrays;

import com.sudoplay.axion.tag.Tag;

/**
 * @tag.type 86
 * 
 * @tag.name <code>TAG_Boolean_Array</code>
 * 
 * @tag.payload * <code>TAG_Int</code> length<br>
 *              * An array of booleans. The length of this array is
 *              <code>length</code> / 8 booleans. Each boolean is stored as a
 *              bit.
 * 
 * @author Jason Taylor
 * 
 */
public class TagBooleanArray extends Tag {

  private boolean[] data;

  /**
   * Creates a new {@link TagBooleanArray} with the given name.
   * 
   * @param newName
   *          the {@link Tag} name
   */
  public TagBooleanArray(final String newName) {
    this(newName, new boolean[0]);
  }

  /**
   * Creates a new {@link TagBooleanArray} with no name and the given value.
   * 
   * @param newBooleanArray
   *          the {@link Boolean} array value
   */
  public TagBooleanArray(final boolean[] newBooleanArray) {
    this(null, newBooleanArray);
  }

  /**
   * Creates a new {@link TagBooleanArray} with the given name and value.
   * 
   * @param newName
   *          the {@link Tag} name
   * @param newBooleanArray
   *          the {@link Boolean} array value
   */
  public TagBooleanArray(final String newName, final boolean[] newBooleanArray) {
    super(newName);
    set(newBooleanArray);
  }

  /**
   * Sets the value of this {@link TagBooleanArray} to the given value.
   * 
   * @param newBooleanArray
   *          the {@link Boolean} array value
   */
  public void set(final boolean[] newBooleanArray) {
    if (newBooleanArray == null) {
      throw new IllegalArgumentException(this.toString() + " doesn't support null payload");
    }
    data = newBooleanArray.clone();
  }

  /**
   * Returns the value of this {@link TagBooleanArray}.
   * 
   * @return the value of this {@link TagBooleanArray}
   */
  public boolean[] get() {
    return data.clone();
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + Arrays.hashCode(data);
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
    TagBooleanArray other = (TagBooleanArray) obj;
    if (!Arrays.equals(data, other.data))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return super.toString() + ": [" + data.length + " booleans]";
  }

  @Override
  public TagBooleanArray clone() {
    return new TagBooleanArray(getName(), data);
  }

}
