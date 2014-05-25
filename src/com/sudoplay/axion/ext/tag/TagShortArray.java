package com.sudoplay.axion.ext.tag;

import java.util.Arrays;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.spec.tag.Tag;

/**
 * @tag.type 84
 * 
 * @tag.name <code>TAG_Short_Array</code>
 * 
 * @tag.payload * <code>TAG_Int</code> length<br>
 *              * An array of shorts. The length of this array is
 *              <code>length</code> shorts.
 * 
 * @author Jason Taylor
 * 
 */
public class TagShortArray extends Tag {

  private short[] data;

  public TagShortArray(final String newName) {
    this(newName, new short[0]);
  }

  public TagShortArray(final String newName, final short[] newShortArray) {
    super(newName);
    set(newShortArray);
  }

  public void set(final short[] newShortArray) {
    if (newShortArray == null) {
      throw new IllegalArgumentException(Axion.getNameFor(this) + " doesn't support null payload");
    }
    data = newShortArray.clone();
  }

  public short[] get() {
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
    TagShortArray other = (TagShortArray) obj;
    if (!Arrays.equals(data, other.data))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return Axion.getNameFor(this) + super.toString() + ": [" + data.length + " shorts]";
  }

  @Override
  public Tag clone() {
    return new TagShortArray(getName(), data);
  }

}
