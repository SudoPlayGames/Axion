package com.sudoplay.axion.tag.spec;

import com.sudoplay.axion.Axion;

/**
 * @tag.type 8
 * 
 * @tag.name <code>TAG_String</code>
 * 
 * @tag.payload * <code>TAG_Short</code> length<br>
 *              * An array of bytes defining a string in UTF-8 format. The
 *              length of this array is <code>length</code> bytes.
 * 
 * @author Jason Taylor
 * 
 */
public class TagString extends Tag {

  public static final String EMPTY = "";

  private String data = EMPTY;

  public TagString(final String newName) {
    super(newName);
  }

  public TagString(final String newName, final String newString) {
    super(newName);
    set(newString);
  }

  public void set(final String newString) {
    if (newString == null) {
      data = EMPTY;
    } else {
      data = newString;
    }
  }

  public String get() {
    return data;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((data == null) ? 0 : data.hashCode());
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
    TagString other = (TagString) obj;
    if (data == null) {
      if (other.data != null)
        return false;
    } else if (!data.equals(other.data))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return Axion.getNameFor(this) + super.toString() + ": " + data;
  }

  @Override
  public TagString clone() {
    return new TagString(getName(), data);
  }

}
