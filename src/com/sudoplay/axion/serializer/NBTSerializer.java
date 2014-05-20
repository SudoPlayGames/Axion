package com.sudoplay.axion.serializer;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import com.sudoplay.axion.tag.Abstract_NBT;
import com.sudoplay.axion.tag.NBTFactory;
import com.sudoplay.axion.tag.definition.NBTEnd;

public class NBTSerializer implements Interface_NBTSerializer {
  
  public static final NBTSerializer INSTANCE = new NBTSerializer();

  @Override
  public Abstract_NBT read(final DataInput dataInput) throws IOException {
    byte id = dataInput.readByte();
    if (id == NBTEnd.TAG_ID) {
      return new NBTEnd();
    } else {
      String name = dataInput.readUTF();
      Abstract_NBT nbt = NBTFactory.create(id, name);
      nbt.read(dataInput);
      return nbt;
    }
  }

  @Override
  public void write(final Abstract_NBT nbt, final DataOutput dataOutput) throws IOException {
    dataOutput.writeByte(nbt.getTagId());
    if (nbt.getTagId() != NBTEnd.TAG_ID) {
      dataOutput.writeUTF(nbt.getName());
      nbt.write(dataOutput);
    }
  }

}
