package com.sudoplay.axion.tag.impl;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import com.sudoplay.axion.tag.Abstract_Tag;

/**
 * @tag.type 11
 * 
 * @tag.name <code>TAG_Int_Array</code>
 * 
 * @tag.payload * <code>TAG_Int</code> length<br>
 *              * An array of ints. The length of this array is
 *              <code>length</code> ints.
 * 
 * @author Jason Taylor
 * 
 */
public class TagIntArray extends Abstract_Tag {

  public static final byte TAG_ID = (byte) 11;
  public static final String TAG_NAME = "TAG_Int_Array";

  private int[] data;

  public TagIntArray(final String newName) {
    super(newName);
  }

  public TagIntArray(final String newName, final int[] newIntArray) {
    super(newName);
    data = newIntArray;
  }

  public void set(final int[] newIntArray) {
    data = newIntArray;
  }

  public int[] get() {
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
    int len = dataInput.readInt();
    data = new int[len];
    for (int i = 0; i < len; i++) {
      data[i] = dataInput.readInt();
    }
  }

  @Override
  public void write(DataOutput dataOutput) throws IOException {
    dataOutput.writeInt(data.length);
    for (int i = 0; i < data.length; i++) {
      dataOutput.writeInt(data[i]);
    }
  }

}
