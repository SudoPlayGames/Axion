package com.sudoplay.axion.serializer;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import com.sudoplay.axion.tag.Abstract_Tag;
import com.sudoplay.axion.tag.TagFactory;
import com.sudoplay.axion.tag.impl.TagEnd;

public class TagSerializer implements Interface_TagSerializer {
  
  public static final TagSerializer INSTANCE = new TagSerializer();

  @Override
  public Abstract_Tag read(final DataInput dataInput) throws IOException {
    byte id = dataInput.readByte();
    if (id == TagEnd.TAG_ID) {
      return new TagEnd();
    } else {
      String name = dataInput.readUTF();
      Abstract_Tag nbt = TagFactory.create(id, name);
      nbt.read(dataInput);
      return nbt;
    }
  }

  @Override
  public void write(final Abstract_Tag nbt, final DataOutput dataOutput) throws IOException {
    dataOutput.writeByte(nbt.getTagId());
    if (nbt.getTagId() != TagEnd.TAG_ID) {
      dataOutput.writeUTF(nbt.getName());
      nbt.write(dataOutput);
    }
  }

}
