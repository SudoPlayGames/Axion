package com.sudoplay.axion.spec.tag;

import com.sudoplay.axion.tag.Tag;

import java.util.Arrays;

/**
 * tag.type 11<br> tag.name <code>TAG_Int_Array</code><br> tag.payload * <code>TAG_Int</code> length<br> * An array of
 * ints. The length of this array is <code>length</code> ints.
 *
 * @author Jason Taylor
 */
public class TagIntArray extends Tag {

  private int[] data;

  /**
   * Creates a new {@link TagIntArray} with the given name.
   *
   * @param newName the {@link Tag} name
   */
  public TagIntArray(final String newName) {
    this(newName, new int[0]);
  }

  /**
   * Creates a new {@link TagIntArray} with no name and the given value.
   *
   * @param newIntArray the {@link Integer} array value
   */
  public TagIntArray(final int[] newIntArray) {
    this(null, newIntArray);
  }

  /**
   * Creates a new {@link TagIntArray} with the given name and value.
   *
   * @param newName     the {@link Tag} name
   * @param newIntArray the {@link Integer} array value
   */
  public TagIntArray(final String newName, final int[] newIntArray) {
    super(newName);
    set(newIntArray);
  }

  /**
   * Sets the value of this {@link TagIntArray} to the given value.
   *
   * @param newIntArray the {@link Integer} array value
   */
  public void set(final int[] newIntArray) {
    if (newIntArray == null) {
      throw new IllegalArgumentException(this.toString() + " doesn't support null payload");
    }
    data = newIntArray.clone();
  }

  /**
   * Returns the value of this {@link TagIntArray}.
   *
   * @return the value of this {@link TagIntArray}
   */
  public int[] get() {
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
    TagIntArray other = (TagIntArray) obj;
    return Arrays.equals(data, other.data);
  }

  @Override
  public String toString() {
    return super.toString() + ": [" + data.length + " ints]";
  }

  @Override
  public TagIntArray clone() {
    return new TagIntArray(getName(), data);
  }

}
