package com.sudoplay.axion.tag.definition;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import com.sudoplay.axion.tag.Abstract_NBT;

/**
 * @tag.type 4
 * 
 * @tag.name <code>TAG_Long</code>
 * 
 * @tag.payload * A signed long (64 bits, big endian).
 * 
 * @author Jason Taylor
 * 
 */
public class NBTLong extends Abstract_NBT {

  public static final byte TAG_ID = (byte) 4;
  public static final String TAG_NAME = "TAG_Long";

  private long data;

  public NBTLong(final String newName) {
    super(newName);
  }

  public NBTLong(final String newName, final long newLong) {
    super(newName);
    data = newLong;
  }

  public void set(final long newLong) {
    data = newLong;
  }

  public long get() {
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
    data = dataInput.readLong();
  }

  @Override
  public void write(DataOutput dataOutput) throws IOException {
    dataOutput.writeLong(data);
  }

}
