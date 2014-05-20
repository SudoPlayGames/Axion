package com.sudoplay.axion.tag.definition;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import com.sudoplay.axion.tag.Abstract_NBT;

/**
 * @tag.type 1
 * 
 * @tag.name <code>TAG_Byte</code>
 * 
 * @tag.payload * A single signed byte (8 bits)
 * 
 * @author Jason Taylor
 * 
 */
public class NBTByte extends Abstract_NBT {

  public static final byte TAG_ID = (byte) 1;
  public static final String TAG_NAME = "TAG_Byte";

  private byte data;

  public NBTByte(final String newName) {
    super(newName);
  }

  public NBTByte(final String newName, final byte newByte) {
    super(newName);
    data = newByte;
  }
  
  public void set(final byte newByte) {
    data = newByte;
  }
  
  public byte get() {
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
  public void read(final DataInput dataInput) throws IOException {
    data = dataInput.readByte();
  }

  @Override
  public void write(final DataOutput dataOutput) throws IOException {
    dataOutput.write(data);
  }

}
