package com.sudoplay.axion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sudoplay.axion.spec.tag.TagByte;
import com.sudoplay.axion.spec.tag.TagByteArray;
import com.sudoplay.axion.spec.tag.TagCompound;
import com.sudoplay.axion.spec.tag.TagDouble;
import com.sudoplay.axion.spec.tag.TagFloat;
import com.sudoplay.axion.spec.tag.TagInt;
import com.sudoplay.axion.spec.tag.TagIntArray;
import com.sudoplay.axion.spec.tag.TagList;
import com.sudoplay.axion.spec.tag.TagLong;
import com.sudoplay.axion.spec.tag.TagShort;
import com.sudoplay.axion.spec.tag.TagString;

public class TestUtil {

  private TestUtil() {
    //
  }

  public static TagCompound getTagCompound() {
    TagCompound tag = new TagCompound("name");
    TagList listA = new TagList(TagByte.class, "list");
    listA.add(new TagByte("tagA", (byte) 16));
    listA.add(new TagByte("tagB", (byte) 8));
    listA.add(new TagByte("tagC", (byte) 4));
    listA.add(new TagByte("tagD", (byte) 2));
    tag.put(listA);
    tag.put(new TagByte("byte", (byte) 16));
    tag.put(new TagByteArray("byteArray", new byte[] { 0, 1, 2, 3 }));
    tag.put(new TagDouble("double", 67.394857));
    tag.put(new TagFloat("float", 6.453f));
    tag.put(new TagInt("int", 16));
    tag.put(new TagIntArray("intArray", new int[] { 0, 1, 2, 3 }));
    tag.put(new TagLong("long", 79L));
    tag.put(new TagShort("short", (short) 947));
    tag.put(new TagString("string", "somestring"));
    return tag;
  }

  public static Map<String, Object> getMap() {
    List<Byte> list = new ArrayList<Byte>();
    list.add((byte) 16);
    list.add((byte) 8);
    list.add((byte) 4);
    list.add((byte) 2);

    Map<String, Object> map = new HashMap<String, Object>();
    map.put("list", list);
    map.put("byte", (byte) 16);
    map.put("byteArray", new byte[] { 0, 1, 2, 3 });
    map.put("double", 67.394857);
    map.put("float", 6.453f);
    map.put("int", 16);
    map.put("intArray", new int[] { 0, 1, 2, 3 });
    map.put("long", 79L);
    map.put("short", (short) 947);
    map.put("string", "somestring");

    return map;
  }

}
