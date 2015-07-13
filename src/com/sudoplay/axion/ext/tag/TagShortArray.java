package com.sudoplay.axion.ext.tag;

import com.sudoplay.axion.tag.Tag;

import java.util.Arrays;

/**
 * @author Jason Taylor
 * @tag.type 84
 * @tag.name <code>TAG_Short_Array</code>
 * @tag.payload * <code>TAG_Int</code> length<br> * An array of shorts. The length of this array is <code>length</code>
 * shorts.
 */
public class TagShortArray extends Tag {

  private short[] data;

  /**
   * Creates a new {@link TagShortArray} with the given name.
   *
   * @param newName the {@link Tag} name
   */
  public TagShortArray(final String newName) {
    this(newName, new short[0]);
  }

  /**
   * Creates a new {@link TagShortArray} with no name and the given value.
   *
   * @param newShortArray the {@link Short} array value
   */
  public TagShortArray(final short[] newShortArray) {
    this(null, newShortArray);
  }

  /**
   * Creates a new {@link TagShortArray} with the given name and value.
   *
   * @param newName       the {@link Tag} name
   * @param newShortArray the {@link Short} array value
   */
  public TagShortArray(final String newName, final short[] newShortArray) {
    super(newName);
    set(newShortArray);
  }

  /**
   * Sets the value of this {@link TagShortArray} to the given value.
   *
   * @param newShortArray the {@link Short} array value
   */
  public void set(final short[] newShortArray) {
    if (newShortArray == null) {
      throw new IllegalArgumentException(this.toString() + " doesn't support null payload");
    }
    data = newShortArray.clone();
  }

  /**
   * Returns the value of this {@link TagShortArray}.
   *
   * @return the value of this {@link TagShortArray}
   */
  public short[] get() {
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
    TagShortArray other = (TagShortArray) obj;
    return Arrays.equals(data, other.data);
  }

  @Override
  public String toString() {
    return super.toString() + ": [" + data.length + " shorts]";
  }

  @Override
  public TagShortArray clone() {
    return new TagShortArray(getName(), data);
  }

}
