package com.sudoplay.axion.tag.impl;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import com.sudoplay.axion.tag.Abstract_Tag;

/**
 * @tag.type 8
 * 
 * @tag.name <code>TAG_String</code>
 * 
 * @tag.payload * <code>TAG_Short</code> length<br>
 *              * An array of bytes defining a string in UTF-8 format. The
 *              length of this array is <code>length</code> bytes.
 * 
 * @author Jason Taylor
 * 
 */
public class TagString extends Abstract_Tag {

  public static final byte TAG_ID = (byte) 8;
  public static final String TAG_NAME = "TAG_String";
  public static final String EMPTY = "";

  private String data;

  public TagString(final String newName) {
    super(newName);
  }

  public TagString(final String newName, final String newString) {
    super(newName);
    set(newString);
  }

  public void set(final String newString) {
    if (newString == null) {
      throw new IllegalArgumentException("TAG_String does not support a null payload");
    }
    data = newString;
  }

  public String get() {
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
    data = dataInput.readUTF();
  }

  @Override
  public void write(DataOutput dataOutput) throws IOException {
    if (data == null) {
      throw new IllegalArgumentException("TAG_String does not support a null payload");
    }
    dataOutput.writeUTF(data);
  }

}
