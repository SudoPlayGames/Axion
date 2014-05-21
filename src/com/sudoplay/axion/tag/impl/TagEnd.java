package com.sudoplay.axion.tag.impl;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.tag.Abstract_Tag;

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
public class TagEnd extends Abstract_Tag {

  public static final byte TAG_ID = (byte) 0;
  public static final String TAG_NAME = "TAG_End";
  public static final TagEnd INSTANCE = new TagEnd();

  public TagEnd() {
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
  public void read(Axion axion, DataInput input) throws IOException {
    throw new UnsupportedOperationException(TAG_NAME + " does not support reading");
  }

  @Override
  public void write(Axion axion, DataOutput output) throws IOException {
    throw new UnsupportedOperationException(TAG_NAME + " does not support writing");
  }

}
