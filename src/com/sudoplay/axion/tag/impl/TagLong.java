package com.sudoplay.axion.tag.impl;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.tag.Abstract_Tag;

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
public class TagLong extends Abstract_Tag {

  public static final byte TAG_ID = (byte) 4;
  public static final String TAG_NAME = "TAG_Long";

  private long data;

  public TagLong(final String newName) {
    super(newName);
  }

  public TagLong(final String newName, final long newLong) {
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
  public void read(Axion axion, DataInput input) throws IOException {
    data = input.readLong();
  }

  @Override
  public void write(Axion axion, DataOutput output) throws IOException {
    output.writeLong(data);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + (int) (data ^ (data >>> 32));
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!super.equals(obj))
      return false;
    if (getClass() != obj.getClass())
      return false;
    TagLong other = (TagLong) obj;
    if (data != other.data)
      return false;
    return true;
  }

  @Override
  public String toString() {
    return TAG_NAME + super.toString() + ": " + data;
  }

}
