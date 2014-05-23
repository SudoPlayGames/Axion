package com.sudoplay.axion.tag;

import java.util.HashMap;
import java.util.Map;

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

public class TagHelper {

  private static final Map<Class<? extends Abstract_Tag>, Byte> classToIdMap;

  static {
    classToIdMap = new HashMap<Class<? extends Abstract_Tag>, Byte>();
    classToIdMap.put(TagEnd.class, TagEnd.TAG_ID);
    classToIdMap.put(TagByte.class, TagByte.TAG_ID);
    classToIdMap.put(TagShort.class, TagShort.TAG_ID);
    classToIdMap.put(TagInt.class, TagInt.TAG_ID);
    classToIdMap.put(TagLong.class, TagLong.TAG_ID);
    classToIdMap.put(TagFloat.class, TagFloat.TAG_ID);
    classToIdMap.put(TagDouble.class, TagDouble.TAG_ID);
    classToIdMap.put(TagByteArray.class, TagByteArray.TAG_ID);
    classToIdMap.put(TagString.class, TagString.TAG_ID);
    classToIdMap.put(TagList.class, TagList.TAG_ID);
    classToIdMap.put(TagCompound.class, TagCompound.TAG_ID);
    classToIdMap.put(TagIntArray.class, TagIntArray.TAG_ID);
  }

  public static byte getId(final Class<? extends Abstract_Tag> tagClass) {
    return classToIdMap.get(tagClass);
  }

  public static String getName(final byte id) {

    switch (id) {

    case TagEnd.TAG_ID:
      return TagEnd.TAG_NAME;
    case TagByte.TAG_ID:
      return TagByte.TAG_NAME;
    case TagShort.TAG_ID:
      return TagShort.TAG_NAME;
    case TagInt.TAG_ID:
      return TagInt.TAG_NAME;
    case TagLong.TAG_ID:
      return TagLong.TAG_NAME;
    case TagFloat.TAG_ID:
      return TagFloat.TAG_NAME;
    case TagDouble.TAG_ID:
      return TagDouble.TAG_NAME;
    case TagByteArray.TAG_ID:
      return TagByteArray.TAG_NAME;
    case TagString.TAG_ID:
      return TagString.TAG_NAME;
    case TagList.TAG_ID:
      return TagList.TAG_NAME;
    case TagCompound.TAG_ID:
      return TagCompound.TAG_NAME;
    case TagIntArray.TAG_ID:
      return TagIntArray.TAG_NAME;

    }

    return null;
  }

  public static Abstract_Tag create(final byte id, final String newName) {

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

}
