package com.sudoplay.axion.tag.standard;

import java.util.Arrays;

import com.sudoplay.axion.Axion;

/**
 * @tag.type 11
 * 
 * @tag.name <code>TAG_Int_Array</code>
 * 
 * @tag.payload * <code>TAG_Int</code> length<br>
 *              * An array of ints. The length of this array is
 *              <code>length</code> ints.
 * 
 * @author Jason Taylor
 * 
 */
public class TagIntArray extends Tag {

  private int[] data;

  public TagIntArray(final String newName) {
    super(newName);
  }

  public TagIntArray(final String newName, final int[] newIntArray) {
    super(newName);
    data = newIntArray;
  }

  public void set(final int[] newIntArray) {
    data = newIntArray;
  }

  public int[] get() {
    return data;
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
    TagIntArray other = (TagIntArray) obj;
    if (!Arrays.equals(data, other.data))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return Axion.getNameFor(this) + super.toString() + ": [" + data.length + " ints]";
  }

  @Override
  public TagIntArray clone() {
    return new TagIntArray(getName(), data.clone());
  }

}
