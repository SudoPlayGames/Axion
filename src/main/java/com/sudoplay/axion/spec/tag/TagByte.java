package com.sudoplay.axion.spec.tag;

import com.sudoplay.axion.tag.Tag;

/**
 * tag.type 1<br> tag.name <code>TAG_Byte</code><br> tag.payload * A single signed byte (8 bits)
 *
 * @author Jason Taylor
 */
public class TagByte extends Tag {

  private byte data;

  /**
   * Creates a new {@link TagByte} with the given name.
   *
   * @param newName the {@link Tag} name
   */
  public TagByte(final String newName) {
    super(newName);
  }

  /**
   * Creates a new {@link TagByte} with no name and the given value.
   *
   * @param newByte the {@link Byte} value
   */
  public TagByte(final byte newByte) {
    this(null, newByte);
  }

  /**
   * Creates a new {@link TagByte} with the given name and value.
   *
   * @param newName the {@link Tag} name
   * @param newByte the {@link Byte} value
   */
  public TagByte(final String newName, final byte newByte) {
    super(newName);
    data = newByte;
  }

  /**
   * Sets the value of this {@link TagByte} to the given value.
   *
   * @param newByte the {@link Byte} value
   */
  public void set(final byte newByte) {
    data = newByte;
  }

  /**
   * Returns the value of this {@link TagByte}.
   *
   * @return the value of this {@link TagByte}
   */
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
    return data == other.data;
  }

  @Override
  public String toString() {
    return super.toString() + ": " + data;
  }

  @SuppressWarnings("CloneDoesntCallSuperClone")
  @Override
  public TagByte clone() {
    return new TagByte(getName(), data);
  }

}
