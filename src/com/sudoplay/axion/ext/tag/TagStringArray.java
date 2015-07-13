package com.sudoplay.axion.ext.tag;

import com.sudoplay.axion.tag.Tag;

import java.util.Arrays;

/**
 * @author Jason Taylor
 * @tag.type 85
 * @tag.name <code>TAG_String_Array</code>
 * @tag.payload * <code>TAG_Int</code> length<br> * An array of Strings. The length of this array is <code>length</code>
 * Strings.
 */
public class TagStringArray extends Tag {

  private String[] data;

  /**
   * Creates a new {@link TagStringArray} with the given name.
   *
   * @param newName the {@link Tag} name
   */
  public TagStringArray(final String newName) {
    this(newName, new String[0]);
  }

  /**
   * Creates a new {@link TagStringArray} with no name and the given value.
   *
   * @param newStringArray the {@link String} array value
   */
  public TagStringArray(final String[] newStringArray) {
    this(null, newStringArray);
  }

  /**
   * Creates a new {@link TagStringArray} with the given name and value.
   *
   * @param newName        the {@link Tag} name
   * @param newStringArray the {@link String} array value
   */
  public TagStringArray(final String newName, final String[] newStringArray) {
    super(newName);
    set(newStringArray);
  }

  /**
   * Sets the value of this {@link TagStringArray} to the given value.
   *
   * @param newStringArray the {@link String} array value
   */
  public void set(final String[] newStringArray) {
    if (newStringArray == null) {
      throw new IllegalArgumentException(this.toString() + " doesn't support null payload");
    }
    data = newStringArray.clone();
  }

  /**
   * Returns the value of this {@link TagStringArray}.
   *
   * @return the value of this {@link TagStringArray}
   */
  public String[] get() {
    return data.clone();
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + Arrays.hashCode(data);
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
    TagStringArray other = (TagStringArray) obj;
    return Arrays.equals(data, other.data);
  }

  @Override
  public String toString() {
    return super.toString() + ": [" + data.length + " strings]";
  }

  @Override
  public TagStringArray clone() {
    return new TagStringArray(getName(), data);
  }

}
