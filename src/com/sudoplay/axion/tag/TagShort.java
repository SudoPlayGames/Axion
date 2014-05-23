package com.sudoplay.axion.tag;


/**
 * @tag.type 2
 * 
 * @tag.name <code>TAG_Short</code>
 * 
 * @tag.payload * A signed short (16 bits, big endian).
 * 
 * @author Jason Taylor
 * 
 */
public class TagShort extends Tag {

  public static final byte TAG_ID = (byte) 2;
  public static final String TAG_NAME = "TAG_Short";

  private short data;

  public TagShort(final String newName) {
    super(newName);
  }

  public TagShort(final String newName, final short newShort) {
    super(newName);
    data = newShort;
  }

  public void set(final short newShort) {
    data = newShort;
  }

  public short get() {
    return data;
  }

  @Override
  public byte getTagId() {
    return TAG_ID;
  }

  @Override
  public String getTagName() {
    return TAG_NAME;
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
    TagShort other = (TagShort) obj;
    if (data != other.data)
      return false;
    return true;
  }

  @Override
  public String toString() {
    return TAG_NAME + super.toString() + ": " + data;
  }

}
