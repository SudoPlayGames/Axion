package com.sudoplay.axion.tag;

/**
 * @tag.type 6
 * 
 * @tag.name <code>TAG_Double</code>
 * 
 * @tag.payload * A floating point value (64 bits, big endian, IEEE 754-2008,
 *              binary64).
 * 
 * @author Jason Taylor
 * 
 */
public class TagDouble extends Tag {

  public static final byte TAG_ID = (byte) 6;
  public static final String TAG_NAME = "TAG_Double";

  private double data;

  public TagDouble(final String newName) {
    super(newName);
  }

  public TagDouble(final String newName, final double newDouble) {
    super(newName);
    data = newDouble;
  }

  public void set(final double newDouble) {
    data = newDouble;
  }

  public double get() {
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
    long temp;
    temp = Double.doubleToLongBits(data);
    result = prime * result + (int) (temp ^ (temp >>> 32));
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
    TagDouble other = (TagDouble) obj;
    if (Double.doubleToLongBits(data) != Double.doubleToLongBits(other.data))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return TAG_NAME + super.toString() + ": " + data;
  }

  @Override
  public TagDouble clone() {
    return new TagDouble(getName(), data);
  }

}
