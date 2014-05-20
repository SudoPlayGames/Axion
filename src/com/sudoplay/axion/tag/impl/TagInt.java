package com.sudoplay.axion.tag.impl;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import com.sudoplay.axion.tag.Abstract_Tag;

/**
 * @tag.type 3
 * 
 * @tag.name <code>TAG_Int</code>
 * 
 * @tag.payload * A signed short (32 bits, big endian).
 * 
 * @author Jason Taylor
 * 
 */
public class TagInt extends Abstract_Tag {

  public static final byte TAG_ID = (byte) 3;
  public static final String TAG_NAME = "TAG_Int";
  
  private int data;
  
  public TagInt(final String newName) {
    super(newName);
  }

  public TagInt(final String newName, final int newInt) {
    super(newName);
    data = newInt;
  }
  
  public void set(final int newInt) {
    data = newInt;
  }
  
  public int get() {
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
    data = dataInput.readInt();
  }

  @Override
  public void write(DataOutput dataOutput) throws IOException {
    dataOutput.writeInt(data);
  }

}
