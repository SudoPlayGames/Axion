package com.sudoplay.axion.serializer;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import com.sudoplay.axion.tag.Abstract_Tag;

public interface Interface_TagSerializer {

  public Abstract_Tag read(final DataInput dataInput) throws IOException;

  public void write(final Abstract_Tag nbt, final DataOutput dataOutput) throws IOException;

}
