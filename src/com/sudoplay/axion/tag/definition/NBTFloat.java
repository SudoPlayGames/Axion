package com.sudoplay.axion.tag.definition;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import com.sudoplay.axion.tag.Abstract_NBT;

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
public class NBTFloat extends Abstract_NBT {

  public static final byte TAG_ID = (byte) 5;
  public static final String TAG_NAME = "TAG_Float";
  
  private float data;
  
  public NBTFloat(final String newName) {
    super(newName);
  }

  public NBTFloat(final String newName, final float newFloat) {
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
  public void read(DataInput dataInput) throws IOException {
    data = dataInput.readFloat();
  }

  @Override
  public void write(DataOutput dataOutput) throws IOException {
    dataOutput.writeFloat(data);
  }

}
