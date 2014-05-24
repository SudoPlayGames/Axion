package com.sudoplay.axion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sudoplay.axion.spec.tag.TagByte;
import com.sudoplay.axion.spec.tag.TagCompound;
import com.sudoplay.axion.spec.tag.TagList;

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
    tag.put("byte", (byte) 16);
    tag.put("byteArray", new byte[] { 0, 1, 2, 3 });
    tag.put("double", 67.394857);
    tag.put("float", 6.453f);
    tag.put("int", 16);
    tag.put("intArray", new int[] { 0, 1, 2, 3 });
    tag.put("long", 79L);
    tag.put("short", (short) 947);
    tag.put("string", "somestring");
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
