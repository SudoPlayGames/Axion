package com.sudoplay.axion.util;

import java.util.HashMap;
import java.util.Map;

import com.sudoplay.axion.tag.Tag;
import com.sudoplay.axion.tag.TagByte;
import com.sudoplay.axion.tag.TagByteArray;
import com.sudoplay.axion.tag.TagCompound;
import com.sudoplay.axion.tag.TagDouble;
import com.sudoplay.axion.tag.TagFloat;
import com.sudoplay.axion.tag.TagInt;
import com.sudoplay.axion.tag.TagIntArray;
import com.sudoplay.axion.tag.TagList;
import com.sudoplay.axion.tag.TagLong;
import com.sudoplay.axion.tag.TagShort;
import com.sudoplay.axion.tag.TagString;

public class TagUtil {

  private static final Map<Class<? extends Tag>, Byte> classToIdMap;

  static {
    classToIdMap = new HashMap<Class<? extends Tag>, Byte>();
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

  public static byte getId(final Class<? extends Tag> tagClass) {
    return classToIdMap.get(tagClass);
  }

  public static Class<? extends Tag> getTagClass(final byte id) {

    switch (id) {

    case TagByte.TAG_ID:
      return TagByte.class;
    case TagShort.TAG_ID:
      return TagShort.class;
    case TagInt.TAG_ID:
      return TagInt.class;
    case TagLong.TAG_ID:
      return TagLong.class;
    case TagFloat.TAG_ID:
      return TagFloat.class;
    case TagDouble.TAG_ID:
      return TagDouble.class;
    case TagByteArray.TAG_ID:
      return TagByteArray.class;
    case TagString.TAG_ID:
      return TagString.class;
    case TagList.TAG_ID:
      return TagList.class;
    case TagCompound.TAG_ID:
      return TagCompound.class;
    case TagIntArray.TAG_ID:
      return TagIntArray.class;

    }

    return null;

  }

  public static String getName(final byte id) {

    switch (id) {

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

}
