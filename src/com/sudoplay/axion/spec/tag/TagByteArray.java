package com.sudoplay.axion.spec.tag;

import com.sudoplay.axion.tag.Tag;

import java.util.Arrays;

/**
 * @author Jason Taylor
 * @tag.type 7
 * @tag.name <code>TAG_Byte_Array</code>
 * @tag.payload * <code>TAG_Int</code> length<br> * An array of bytes of unspecified format. The length of this array is
 * <code>length</code> bytes.
 */
public class TagByteArray extends Tag {

  private byte[] data;

  /**
   * Creates a new {@link TagByteArray} with the given name.
   *
   * @param newName the {@link Tag} name
   */
  public TagByteArray(final String newName) {
    this(newName, new byte[0]);
  }

  /**
   * Creates a new {@link TagByteArray} with no name and the given value.
   *
   * @param newByteArray the {@link Byte} array value
   */
  public TagByteArray(final byte[] newByteArray) {
    this(null, newByteArray);
  }

  /**
   * Creates a new {@link TagByteArray} with the given name and value.
   *
   * @param newName      the {@link Tag} name
   * @param newByteArray the {@link Byte} array value
   */
  public TagByteArray(final String newName, final byte[] newByteArray) {
    super(newName);
    set(newByteArray);
  }

  /**
   * Sets the value of this {@link TagByteArray} to a copy of the given value.
   *
   * @param newByteArray the {@link Byte} array value
   */
  public void set(final byte[] newByteArray) {
    if (newByteArray == null) {
      throw new IllegalArgumentException(this.toString() + " doesn't support null payload");
    }
    data = newByteArray.clone();
  }

  /**
   * Returns a copy of the value of this {@link TagByteArray}.
   *
   * @return a copy of the value of this {@link TagByteArray}
   */
  public byte[] get() {
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
    TagByteArray other = (TagByteArray) obj;
    return Arrays.equals(data, other.data);
  }

  @Override
  public String toString() {
    return super.toString() + ": [" + data.length + " bytes]";
  }

  @Override
  public TagByteArray clone() {
    return new TagByteArray(getName(), data);
  }

}
