package com.sudoplay.axion.tag.standard;

import com.sudoplay.axion.Axion;

/**
 * @tag.type 1
 * 
 * @tag.name <code>TAG_Byte</code>
 * 
 * @tag.payload * A single signed byte (8 bits)
 * 
 * @author Jason Taylor
 * 
 */
public class TagByte extends Tag {

  private byte data;

  public TagByte(final String newName) {
    super(newName);
  }

  public TagByte(final String newName, final byte newByte) {
    super(newName);
    data = newByte;
  }

  public void set(final byte newByte) {
    data = newByte;
  }

  public byte get() {
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
    TagByte other = (TagByte) obj;
    if (data != other.data)
      return false;
    return true;
  }

  @Override
  public String toString() {
    return Axion.getNameFor(this) + super.toString() + ": " + data;
  }

  @Override
  public TagByte clone() {
    return new TagByte(getName(), data);
  }

}
