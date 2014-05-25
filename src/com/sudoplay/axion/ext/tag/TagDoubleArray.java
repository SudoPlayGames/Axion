package com.sudoplay.axion.ext.tag;

import java.util.Arrays;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.spec.tag.Tag;

/**
 * @tag.type 81
 * 
 * @tag.name <code>TAG_Double_Array</code>
 * 
 * @tag.payload * <code>TAG_Int</code> length<br>
 *              * An array of doubles. The length of this array is
 *              <code>length</code> doubles.
 * 
 * @author Jason Taylor
 * 
 */
public class TagDoubleArray extends Tag {

  private double[] data;

  public TagDoubleArray(final String newName) {
    this(newName, new double[0]);
  }

  public TagDoubleArray(final String newName, final double[] newDoubleArray) {
    super(newName);
    set(newDoubleArray);
  }

  public void set(final double[] newDoubleArray) {
    if (newDoubleArray == null) {
      throw new IllegalArgumentException(Axion.getNameFor(this) + " doesn't support null payload");
    }
    data = newDoubleArray.clone();
  }

  public double[] get() {
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
    TagDoubleArray other = (TagDoubleArray) obj;
    if (!Arrays.equals(data, other.data))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return Axion.getNameFor(this) + super.toString() + ": [" + data.length + " doubles]";
  }

  @Override
  public Tag clone() {
    return new TagDoubleArray(getName(), data);
  }

}
