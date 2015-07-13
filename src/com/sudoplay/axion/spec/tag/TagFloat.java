package com.sudoplay.axion.spec.tag;

import com.sudoplay.axion.tag.Tag;

/**
 * @author Jason Taylor
 * @tag.type 5
 * @tag.name <code>TAG_Float</code>
 * @tag.payload * A floating point value (32 bits, big endian, IEEE 754-2008, binary32).
 */
public class TagFloat extends Tag {

  private float data;

  /**
   * Creates a new {@link TagFloat} with the given name.
   *
   * @param newName the {@link Tag} name
   */
  public TagFloat(final String newName) {
    super(newName);
  }

  /**
   * Creates a new {@link TagFloat} with no name and the given value.
   *
   * @param newFloat the {@link Float} value
   */
  public TagFloat(final float newFloat) {
    this(null, newFloat);
  }

  /**
   * Creates a new {@link TagFloat} with the given name and value.
   *
   * @param newName  the {@link Tag} name
   * @param newFloat the {@link Float} value
   */
  public TagFloat(final String newName, final float newFloat) {
    super(newName);
    data = newFloat;
  }

  /**
   * Sets the value of this {@link TagFloat} to the given value.
   *
   * @param newFloat the {@link Float} value
   */
  public void set(final float newFloat) {
    data = newFloat;
  }

  /**
   * Returns the value of this {@link TagFloat}.
   *
   * @return the value of this {@link TagFloat}
   */
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
    return Float.floatToIntBits(data) == Float.floatToIntBits(other.data);
  }

  @Override
  public String toString() {
    return super.toString() + ": " + data;
  }

  @Override
  public TagFloat clone() {
    return new TagFloat(getName(), data);
  }

}
