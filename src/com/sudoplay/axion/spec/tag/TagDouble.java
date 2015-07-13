package com.sudoplay.axion.spec.tag;

import com.sudoplay.axion.tag.Tag;

/**
 * @author Jason Taylor
 * @tag.type 6
 * @tag.name <code>TAG_Double</code>
 * @tag.payload * A floating point value (64 bits, big endian, IEEE 754-2008, binary64).
 */
public class TagDouble extends Tag {

  private double data;

  /**
   * Creates a new {@link TagDouble} with the given name.
   *
   * @param newName the {@link Tag} name
   */
  public TagDouble(final String newName) {
    super(newName);
  }

  /**
   * Creates a new {@link TagDouble} with no name and the given value.
   *
   * @param newDouble the {@link Double} value
   */
  public TagDouble(final double newDouble) {
    this(null, newDouble);
  }

  /**
   * Creates the {@link TagDouble} with the given name and value.
   *
   * @param newName   the {@link Tag} name
   * @param newDouble the {@link Double} value
   */
  public TagDouble(final String newName, final double newDouble) {
    super(newName);
    data = newDouble;
  }

  /**
   * Sets the value of this {@link TagDouble} to the given value.
   *
   * @param newDouble the {@link Double} value
   */
  public void set(final double newDouble) {
    data = newDouble;
  }

  /**
   * Returns the value of this {@link TagDouble}.
   *
   * @return the value of this {@link TagDouble}
   */
  public double get() {
    return data;
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
    return Double.doubleToLongBits(data) == Double.doubleToLongBits(other.data);
  }

  @Override
  public String toString() {
    return super.toString() + ": " + data;
  }

  @Override
  public TagDouble clone() {
    return new TagDouble(getName(), data);
  }

}
