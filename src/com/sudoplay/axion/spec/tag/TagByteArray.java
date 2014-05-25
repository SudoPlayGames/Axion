package com.sudoplay.axion.spec.tag;

import java.util.Arrays;

import com.sudoplay.axion.Axion;

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
public class TagByteArray extends Tag {

  private byte[] data;

  public TagByteArray(final String newName) {
    super(newName);
  }

  public TagByteArray(final String newName, final byte[] newByteArray) {
    super(newName);
    set(newByteArray);
  }

  public void set(final byte[] newByteArray) {
    data = newByteArray.clone();
  }

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
    if (!Arrays.equals(data, other.data))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return Axion.getNameFor(this) + super.toString() + ": [" + data.length + " bytes]";
  }

  @Override
  public TagByteArray clone() {
    return new TagByteArray(getName(), data.clone());
  }

}