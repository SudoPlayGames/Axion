package com.sudoplay.axion.ext.tag;

import java.util.Arrays;

import com.sudoplay.axion.tag.Tag;

/**
 * @tag.type 86
 * 
 * @tag.name <code>TAG_Boolean_Array</code>
 * 
 * @tag.payload * <code>TAG_Int</code> length<br>
 *              * An array of booleans. The length of this array is
 *              <code>length</code> / 8 booleans. Each boolean is stored as a
 *              bit.
 * 
 * @author Jason Taylor
 * 
 */
public class TagBooleanArray extends Tag {

  private boolean[] data;

  public TagBooleanArray(final String newName) {
    this(newName, new boolean[0]);
  }

  public TagBooleanArray(final String newName, final boolean[] newBooleanArray) {
    super(newName);
    set(newBooleanArray);
  }

  public void set(final boolean[] newBooleanArray) {
    if (newBooleanArray == null) {
      throw new IllegalArgumentException(this.toString() + " doesn't support null payload");
    }
    data = newBooleanArray.clone();
  }

  public boolean[] get() {
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
    TagBooleanArray other = (TagBooleanArray) obj;
    if (!Arrays.equals(data, other.data))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return super.toString() + ": [" + data.length + " booleans]";
  }

  @Override
  public Tag clone() {
    return new TagBooleanArray(getName(), data);
  }

}
