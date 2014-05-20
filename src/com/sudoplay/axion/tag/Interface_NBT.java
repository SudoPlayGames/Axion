package com.sudoplay.axion.tag;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public interface Interface_NBT {

  public byte getTagId();

  public String getTagName();

  public void read(DataInput dataInput) throws IOException;

  public void write(DataOutput dataOutput) throws IOException;

}
