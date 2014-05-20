package com.sudoplay.axion.serializer;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import com.sudoplay.axion.tag.Abstract_NBT;

public interface Interface_NBTSerializer {

  public Abstract_NBT read(final DataInput dataInput) throws IOException;

  public void write(final Abstract_NBT nbt, final DataOutput dataOutput) throws IOException;

}
