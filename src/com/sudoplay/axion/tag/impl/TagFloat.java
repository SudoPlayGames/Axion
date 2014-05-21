package com.sudoplay.axion.tag.impl;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.tag.Abstract_Tag;

/**
 * @tag.type 5
 * 
 * @tag.name <code>TAG_Float</code>
 * 
 * @tag.payload * A floating point value (32 bits, big endian, IEEE 754-2008,
 *              binary32).
 * 
 * @author Jason Taylor
 * 
 */
public class TagFloat extends Abstract_Tag {

  public static final byte TAG_ID = (byte) 5;
  public static final String TAG_NAME = "TAG_Float";

  private float data;

  public TagFloat(final String newName) {
    super(newName);
  }

  public TagFloat(final String newName, final float newFloat) {
    super(newName);
    data = newFloat;
  }

  public void set(final float newFloat) {
    data = newFloat;
  }

  public float get() {
    return data;
  }

  @Override
  public byte getTagId() {
    return TAG_ID;
  }

  @Override
  public String getTagName() {
    return TAG_NAME;
  }

  @Override
  public void read(Axion axion, DataInput input) throws IOException {
    data = input.readFloat();
  }

  @Override
  public void write(Axion axion, DataOutput output) throws IOException {
    output.writeFloat(data);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + Float.floatToIntBits(data);
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
    TagFloat other = (TagFloat) obj;
    if (Float.floatToIntBits(data) != Float.floatToIntBits(other.data))
      return false;
    return true;
  }

}
