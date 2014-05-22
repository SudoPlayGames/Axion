package com.sudoplay.axion.adapter;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import com.sudoplay.axion.tag.Abstract_Tag;

public interface Interface_Adapter {

  public Abstract_Tag read(final Abstract_Tag parent, final DataInput in) throws IOException;

  public void write(final Abstract_Tag tag, final DataOutput out) throws IOException;

}
