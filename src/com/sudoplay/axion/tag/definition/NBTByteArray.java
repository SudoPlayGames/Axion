package com.sudoplay.axion.tag.definition;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import com.sudoplay.axion.tag.Abstract_NBT;

/**
 * @tag.type 7
 * 
 * @tag.name <code>TAG_Byte_Array</code>
 * 
 * @tag.payload * <code>TAG_Int</code> length<br>
 *              * An array of bytes of unspecified format. The length of this
 *              array is <code>length</code> bytes.
 * 
 * @author Jason Taylor
 * 
 */
public class NBTByteArray extends Abstract_NBT {

  public static final byte TAG_ID = (byte) 7;
  public static final String TAG_NAME = "TAG_Byte_Array";

  private byte[] data;

  public NBTByteArray(final String newName) {
    super(newName);
  }

  public NBTByteArray(final String newName, final byte[] newByteArray) {
    super(newName);
    data = newByteArray;
  }

  @Override
  public byte getTagId() {
    return TAG_ID;
  }

  @Override
  public String getTagName() {
    return TAG_NAME;
  }

  public void set(final byte[] newByteArray) {
    data = newByteArray;
  }

  public byte[] get() {
    return data;
  }

  @Override
  public void read(DataInput dataInput) throws IOException {
    int len = dataInput.readInt();
    data = new byte[len];
    dataInput.readFully(data);
  }

  @Override
  public void write(DataOutput dataOutput) throws IOException {
    dataOutput.writeInt(data.length);
    dataOutput.write(data);
  }

}
