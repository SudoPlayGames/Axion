package com.sudoplay.axion.tag.impl;

import java.util.Arrays;

import com.sudoplay.axion.tag.Abstract_Tag;

/**
 * @tag.type 7
 * 
 * @tag.name <code>TAG_Byte_Array</code>
 * 
 * @tag.payload * <code>TAG_Int</code> length<br>
 *              * An array of bytes of unspecified format. The length of this
 *              array is <code>length</code> bytes.
 * 
 * @author Jason Taylor
 * 
 */
public class TagByteArray extends Abstract_Tag {

  public static final byte TAG_ID = (byte) 7;
  public static final String TAG_NAME = "TAG_Byte_Array";

  private byte[] data;

  public TagByteArray(final String newName) {
    super(newName);
  }

  public TagByteArray(final String newName, final byte[] newByteArray) {
    super(newName);
    data = newByteArray;
  }

  @Override
  public byte getTagId() {
    return TAG_ID;
  }

  @Override
  public String getTagName() {
    return TAG_NAME;
  }

  public void set(final byte[] newByteArray) {
    data = newByteArray;
  }

  public byte[] get() {
    return data;
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
    if (!Arrays.equals(data, other.data))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return TAG_NAME + super.toString() + ": [" + data.length + " bytes]";
  }

}
