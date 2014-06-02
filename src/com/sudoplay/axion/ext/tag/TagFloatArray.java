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

  /**
   * Creates a new {@link TagFloatArray} with the given name.
   * 
   * @param newName
   *          the {@link Tag} name
   */
  public TagFloatArray(final String newName) {
    this(newName, new float[0]);
  }

  /**
   * Creates a new {@link TagFloatArray} with no name and the given value.
   * 
   * @param newFloatArray
   *          the {@link Float} array value
   */
  public TagFloatArray(final float[] newFloatArray) {
    this(null, newFloatArray);
  }

  /**
   * Creates a new {@link TagFloatArray} with the given name and value.
   * 
   * @param newName
   *          the {@link Tag} name
   * @param newFloatArray
   *          the {@link Float} array value
   */
  public TagFloatArray(final String newName, final float[] newFloatArray) {
    super(newName);
    set(newFloatArray);
  }

  /**
   * Sets the value of this {@link TagFloatArray} to the given value.
   * 
   * @param newFloatArray
   *          the {@link Float} array value
   */
  public void set(final float[] newFloatArray) {
    if (newFloatArray == null) {
      throw new IllegalArgumentException(this.toString() + " doesn't support null payload");
    }
    data = newFloatArray.clone();
  }

  /**
   * Returns the value of this {@link TagFloatArray}.
   * 
   * @return the value of this {@link TagFloatArray}
   */
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
  public TagFloatArray clone() {
    return new TagFloatArray(getName(), data);
  }

}
