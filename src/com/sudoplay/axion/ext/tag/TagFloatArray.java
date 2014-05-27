package com.sudoplay.axion.ext.tag;

import java.util.Arrays;

import com.sudoplay.axion.tag.Tag;

/**
 * @tag.type 82
 * 
 * @tag.name <code>TAG_Float_Array</code>
 * 
 * @tag.payload * <code>TAG_Int</code> length<br>
 *              * An array of floats. The length of this array is
 *              <code>length</code> floats.
 * 
 * @author Jason Taylor
 * 
 */
public class TagFloatArray extends Tag {

  private float[] data;

  public TagFloatArray(final String newName) {
    this(newName, new float[0]);
  }

  public TagFloatArray(final String newName, final float[] newFloatArray) {
    super(newName);
    set(newFloatArray);
  }

  public void set(final float[] newFloatArray) {
    if (newFloatArray == null) {
      throw new IllegalArgumentException(this.toString() + " doesn't support null payload");
    }
    data = newFloatArray.clone();
  }

  public float[] get() {
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
    TagFloatArray other = (TagFloatArray) obj;
    if (!Arrays.equals(data, other.data))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return super.toString() + ": [" + data.length + " floats]";
  }

  @Override
  public Tag clone() {
    return new TagFloatArray(getName(), data);
  }

}
