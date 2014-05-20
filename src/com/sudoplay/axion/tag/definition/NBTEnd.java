package com.sudoplay.axion.tag.definition;

import java.io.DataInput;
import java.io.DataOutput;

import com.sudoplay.axion.tag.Abstract_NBT;

/**
 * @tag.type 0
 * 
 * @tag.name <code>TAG_End</code>
 * 
 * @tag.payload * None.
 * 
 * @tag.note This tag is used to mark the end of a list. Cannot be named! If
 *           type 0 appears where a Named Tag is expected, the name is assumed
 *           to be "". (In other words, this Tag is always just a single 0 byte
 *           when named, and nothing in all other cases).
 * 
 * @author Jason Taylor
 * 
 */
public class NBTEnd extends Abstract_NBT {

  public static final byte TAG_ID = (byte) 0;
  public static final String TAG_NAME = "TAG_End";
  public static final NBTEnd INSTANCE = new NBTEnd();

  public NBTEnd() {
    super(null);
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
  public void setName(final String newName) {
    super.setName(null);
  }

  @Override
  public void read(final DataInput dataInput) {
    throw new UnsupportedOperationException(TAG_NAME + " does not support reading");
  }

  @Override
  public void write(final DataOutput dataOutput) {
    throw new UnsupportedOperationException(TAG_NAME + " does not support writing");
  }

}
