package com.sudoplay.axion.tag.impl;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.tag.Abstract_Tag;

/**
 * @tag.type 2
 * 
 * @tag.name <code>TAG_Short</code>
 * 
 * @tag.payload * A signed short (16 bits, big endian).
 * 
 * @author Jason Taylor
 * 
 */
public class TagShort extends Abstract_Tag {

  public static final byte TAG_ID = (byte) 2;
  public static final String TAG_NAME = "TAG_Short";

  private short data;

  public TagShort(final String newName) {
    super(newName);
  }

  public TagShort(final String newName, final short newShort) {
    super(newName);
    data = newShort;
  }

  public void set(final short newShort) {
    data = newShort;
  }

  public short get() {
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
    data = input.readShort();
  }

  @Override
  public void write(Axion axion, DataOutput output) throws IOException {
    output.writeShort(data);
  }

}
