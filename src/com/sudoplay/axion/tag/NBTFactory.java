package com.sudoplay.axion.tag;

import com.sudoplay.axion.tag.definition.NBTByte;
import com.sudoplay.axion.tag.definition.NBTByteArray;
import com.sudoplay.axion.tag.definition.NBTCompound;
import com.sudoplay.axion.tag.definition.NBTDouble;
import com.sudoplay.axion.tag.definition.NBTEnd;
import com.sudoplay.axion.tag.definition.NBTFloat;
import com.sudoplay.axion.tag.definition.NBTInt;
import com.sudoplay.axion.tag.definition.NBTIntArray;
import com.sudoplay.axion.tag.definition.NBTList;
import com.sudoplay.axion.tag.definition.NBTLong;
import com.sudoplay.axion.tag.definition.NBTShort;
import com.sudoplay.axion.tag.definition.NBTString;

public class NBTFactory {

  public static Abstract_NBT create(final byte id, final String newName) {

    switch (id) {

    case NBTEnd.TAG_ID:
      return new NBTEnd();
    case NBTByte.TAG_ID:
      return new NBTByte(newName);
    case NBTShort.TAG_ID:
      return new NBTShort(newName);
    case NBTInt.TAG_ID:
      return new NBTInt(newName);
    case NBTLong.TAG_ID:
      return new NBTLong(newName);
    case NBTFloat.TAG_ID:
      return new NBTFloat(newName);
    case NBTDouble.TAG_ID:
      return new NBTDouble(newName);
    case NBTByteArray.TAG_ID:
      return new NBTByteArray(newName);
    case NBTString.TAG_ID:
      return new NBTString(newName);
    case NBTList.TAG_ID:
      return new NBTList(newName);
    case NBTCompound.TAG_ID:
      return new NBTCompound(newName);
    case NBTIntArray.TAG_ID:
      return new NBTIntArray(newName);

    }

    return null;

  }

}
