package com.sudoplay.axion.ext.tag;

import java.util.Arrays;

import com.sudoplay.axion.tag.Tag;

/**
 * @tag.type 83
 * 
 * @tag.name <code>TAG_Long_Array</code>
 * 
 * @tag.payload * <code>TAG_Int</code> length<br>
 *              * An array of longs. The length of this array is
 *              <code>length</code> longs.
 * 
 * @author Jason Taylor
 * 
 */
public class TagLongArray extends Tag {

  private long[] data;

  public TagLongArray(final String newName) {
    this(newName, new long[0]);
  }

  public TagLongArray(final String newName, final long[] newLongArray) {
    super(newName);
    set(newLongArray);
  }

  public void set(final long[] newLongArray) {
    if (newLongArray == null) {
      throw new IllegalArgumentException(this.toString() + " doesn't support null payload");
    }
    data = newLongArray.clone();
  }

  public long[] get() {
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
    TagLongArray other = (TagLongArray) obj;
    if (!Arrays.equals(data, other.data))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return super.toString() + ": [" + data.length + " longs]";
  }

  @Override
  public TagLongArray clone() {
    return new TagLongArray(getName(), data);
  }

}
