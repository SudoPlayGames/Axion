package com.sudoplay.axion.tag.impl;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.tag.Abstract_Tag;

/**
 * @tag.type 6
 * 
 * @tag.name <code>TAG_Double</code>
 * 
 * @tag.payload * A floating point value (64 bits, big endian, IEEE 754-2008,
 *              binary64).
 * 
 * @author Jason Taylor
 * 
 */
public class TagDouble extends Abstract_Tag {

  public static final byte TAG_ID = (byte) 6;
  public static final String TAG_NAME = "TAG_Double";

  private double data;

  public TagDouble(final String newName) {
    super(newName);
  }

  public TagDouble(final String newName, final double newDouble) {
    super(newName);
    data = newDouble;
  }

  public void set(final double newDouble) {
    data = newDouble;
  }

  public double get() {
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
    data = input.readDouble();
  }

  @Override
  public void write(Axion axion, DataOutput output) throws IOException {
    output.writeDouble(data);
  }

}
