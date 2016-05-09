package com.sudoplay.axion.ext.tag;

import com.sudoplay.axion.tag.Tag;

import java.util.Arrays;

/**
 * tag.type 83<br> tag.name <code>TAG_Long_Array</code><br> tag.payload * <code>TAG_Int</code> length<br> An array of
 * longs. The length of this array is <code>length</code> longs.
 *
 * @author Jason Taylor
 */
public class TagLongArray extends Tag {

  private long[] data;

  /**
   * Creates a new {@link TagLongArray} with the given name.
   *
   * @param newName the {@link Tag} name
   */
  public TagLongArray(final String newName) {
    this(newName, new long[0]);
  }

  /**
   * Creates a new {@link TagLongArray} with no name and the given value.
   *
   * @param newLongArray the {@link Long} array value
   */
  public TagLongArray(final long[] newLongArray) {
    this(null, newLongArray);
  }

  /**
   * Creates a new {@link TagLongArray} with the given name and value.
   *
   * @param newName      the {@link Tag} name
   * @param newLongArray the {@link Long} array value
   */
  public TagLongArray(final String newName, final long[] newLongArray) {
    super(newName);
    set(newLongArray);
  }

  /**
   * Sets the value of this {@link TagLongArray} to the given value.
   *
   * @param newLongArray the {@link Long} array value
   */
  public void set(final long[] newLongArray) {
    if (newLongArray == null) {
      throw new IllegalArgumentException(this.toString() + " doesn't support null payload");
    }
    data = newLongArray.clone();
  }

  /**
   * Returns the value of this {@link TagLongArray}.
   *
   * @return the value of this {@link TagLongArray}
   */
  public long[] get() {
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
    TagLongArray other = (TagLongArray) obj;
    return Arrays.equals(data, other.data);
  }

  @Override
  public String toString() {
    return super.toString() + ": [" + data.length + " longs]";
  }

  @Override
  public TagLongArray clone() {
    return new TagLongArray(getName(), data);
  }

}
