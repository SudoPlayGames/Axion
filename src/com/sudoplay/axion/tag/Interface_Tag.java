package com.sudoplay.axion.tag;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import com.sudoplay.axion.Axion;

public interface Interface_Tag {

  public byte getTagId();

  public String getTagName();

  public void read(Axion axion, DataInput input) throws IOException;

  public void write(Axion axion, DataOutput output) throws IOException;

}
