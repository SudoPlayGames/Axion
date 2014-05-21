package com.sudoplay.axion;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import com.sudoplay.axion.tag.Abstract_Tag;
import com.sudoplay.axion.tag.impl.TagByte;
import com.sudoplay.axion.tag.impl.TagByteArray;
import com.sudoplay.axion.tag.impl.TagCompound;
import com.sudoplay.axion.tag.impl.TagDouble;
import com.sudoplay.axion.tag.impl.TagEnd;
import com.sudoplay.axion.tag.impl.TagFloat;
import com.sudoplay.axion.tag.impl.TagInt;
import com.sudoplay.axion.tag.impl.TagIntArray;
import com.sudoplay.axion.tag.impl.TagList;
import com.sudoplay.axion.tag.impl.TagLong;
import com.sudoplay.axion.tag.impl.TagShort;
import com.sudoplay.axion.tag.impl.TagString;

public class Axion {

  public Abstract_Tag createTag(final byte id, final String newName) {

    switch (id) {

    case TagEnd.TAG_ID:
      return new TagEnd();
    case TagByte.TAG_ID:
      return new TagByte(newName);
    case TagShort.TAG_ID:
      return new TagShort(newName);
    case TagInt.TAG_ID:
      return new TagInt(newName);
    case TagLong.TAG_ID:
      return new TagLong(newName);
    case TagFloat.TAG_ID:
      return new TagFloat(newName);
    case TagDouble.TAG_ID:
      return new TagDouble(newName);
    case TagByteArray.TAG_ID:
      return new TagByteArray(newName);
    case TagString.TAG_ID:
      return new TagString(newName);
    case TagList.TAG_ID:
      return new TagList(newName);
    case TagCompound.TAG_ID:
      return new TagCompound(newName);
    case TagIntArray.TAG_ID:
      return new TagIntArray(newName);

    }

    return null;

  }

  public Abstract_Tag read(final DataInput dataInput) throws IOException {
    byte id = dataInput.readByte();
    if (id == TagEnd.TAG_ID) {
      return new TagEnd();
    } else {
      String name = dataInput.readUTF();
      Abstract_Tag nbt = createTag(id, name);
      nbt.read(this, dataInput);
      return nbt;
    }
  }

  public void write(final Abstract_Tag nbt, final DataOutput dataOutput) throws IOException {
    dataOutput.writeByte(nbt.getTagId());
    if (nbt.getTagId() != TagEnd.TAG_ID) {
      dataOutput.writeUTF(nbt.getName());
      nbt.write(this, dataOutput);
    }
  }

}
