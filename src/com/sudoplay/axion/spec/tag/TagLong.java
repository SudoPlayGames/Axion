package com.sudoplay.axion.spec.tag;

import com.sudoplay.axion.tag.Tag;

/**
 * @author Jason Taylor
 * @tag.type 4
 * @tag.name <code>TAG_Long</code>
 * @tag.payload * A signed long (64 bits, big endian).
 */
public class TagLong extends Tag {

  private long data;

  /**
   * Creates a new {@link TagLong} with the given name.
   *
   * @param newName the {@link Tag} name
   */
  public TagLong(final String newName) {
    super(newName);
  }

  /**
   * Creates a new {@link TagLong} with no name and the given value.
   *
   * @param newLong the {@link Long} value
   */
  public TagLong(final long newLong) {
    this(null, newLong);
  }

  /**
   * Creates a new {@link TagLong} with the given name and value.
   *
   * @param newName the {@link Tag} name
   * @param newLong the {@link Long} value
   */
  public TagLong(final String newName, final long newLong) {
    super(newName);
    data = newLong;
  }

  /**
   * Sets the value of this {@link TagLong} to the given value.
   *
   * @param newLong the {@link Long} value
   */
  public void set(final long newLong) {
    data = newLong;
  }

  /**
   * Returns the value of this {@link TagLong}.
   *
   * @return the value of this {@link TagLong}
   */
  public long get() {
    return data;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + (int) (data ^ (data >>> 32));
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
    TagLong other = (TagLong) obj;
    return data == other.data;
  }

  @Override
  public String toString() {
    return super.toString() + ": " + data;
  }

  @Override
  public TagLong clone() {
    return new TagLong(getName(), data);
  }

}
