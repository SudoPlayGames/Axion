package com.sudoplay.axion.ext.tag;

import com.sudoplay.axion.tag.Tag;

/**
 * @author Jason Taylor
 * @tag.type 80
 * @tag.name <code>TAG_Boolean</code>
 * @tag.payload * True is recorded as a byte value of 1 and false is a byte value of 0.
 */
public class TagBoolean extends Tag {

  private boolean data;

  /**
   * Creates a new {@link TagBoolean} with the given name.
   *
   * @param newName the {@link Tag} name
   */
  public TagBoolean(final String newName) {
    super(newName);
  }

  /**
   * Creates a new {@link TagBoolean} with no name and the given value.
   *
   * @param newBoolean the {@link Boolean} value
   */
  public TagBoolean(final boolean newBoolean) {
    this(null, newBoolean);
  }

  /**
   * Creates a new {@link TagBoolean} with the given name and value.
   *
   * @param newName    the {@link Tag} name
   * @param newBoolean the {@link Boolean} value
   */
  public TagBoolean(final String newName, final boolean newBoolean) {
    super(newName);
    data = newBoolean;
  }

  /**
   * Sets the value of this {@link TagBoolean} to the given value.
   *
   * @param newBoolean the {@link Boolean} value
   */
  public void set(final boolean newBoolean) {
    data = newBoolean;
  }

  /**
   * Returns the value of this {@link TagBoolean}.
   *
   * @return the value of this {@link TagBoolean}
   */
  public boolean get() {
    return data;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + (data ? 1231 : 1237);
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
    TagBoolean other = (TagBoolean) obj;
    return data == other.data;
  }

  @Override
  public String toString() {
    return super.toString() + ": " + data;
  }

  @Override
  public TagBoolean clone() {
    return new TagBoolean(getName(), data);
  }

}
