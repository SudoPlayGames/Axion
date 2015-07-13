package com.sudoplay.axion.spec.tag;

import com.sudoplay.axion.tag.Tag;

/**
 * @author Jason Taylor
 * @tag.type 2
 * @tag.name <code>TAG_Short</code>
 * @tag.payload * A signed short (16 bits, big endian).
 */
public class TagShort extends Tag {

  private short data;

  /**
   * Creates a new {@link TagShort} with the given name.
   *
   * @param newName the {@link Tag} name
   */
  public TagShort(final String newName) {
    super(newName);
  }

  /**
   * Creates a new {@link TagShort} with no name and the given value.
   *
   * @param newShort the {@link Short} value
   */
  public TagShort(final short newShort) {
    this(null, newShort);
  }

  /**
   * Creates a new {@link TagShort} with the given name and value.
   *
   * @param newName  the {@link Tag} name
   * @param newShort the {@link Short} value
   */
  public TagShort(final String newName, final short newShort) {
    super(newName);
    data = newShort;
  }

  /**
   * Sets the value of this {@link TagShort} to the given value.
   *
   * @param newShort the {@link Short} value
   */
  public void set(final short newShort) {
    data = newShort;
  }

  /**
   * Returns the value of this {@link TagShort}.
   *
   * @return the value of this {@link TagShort}
   */
  public short get() {
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
    TagShort other = (TagShort) obj;
    return data == other.data;
  }

  @Override
  public String toString() {
    return super.toString() + ": " + data;
  }

  @Override
  public TagShort clone() {
    return new TagShort(getName(), data);
  }

}
