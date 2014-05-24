package com.sudoplay.axion.tag.spec;

import com.sudoplay.axion.Axion;

/**
 * @tag.type 5
 * 
 * @tag.name <code>TAG_Float</code>
 * 
 * @tag.payload * A floating point value (32 bits, big endian, IEEE 754-2008,
 *              binary32).
 * 
 * @author Jason Taylor
 * 
 */
public class TagFloat extends Tag {

  private float data;

  public TagFloat(final String newName) {
    super(newName);
  }

  public TagFloat(final String newName, final float newFloat) {
    super(newName);
    data = newFloat;
  }

  public void set(final float newFloat) {
    data = newFloat;
  }

  public float get() {
    return data;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + Float.floatToIntBits(data);
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
    TagFloat other = (TagFloat) obj;
    if (Float.floatToIntBits(data) != Float.floatToIntBits(other.data))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return Axion.getNameFor(this) + super.toString() + ": " + data;
  }

  @Override
  public TagFloat clone() {
    return new TagFloat(getName(), data);
  }

}
