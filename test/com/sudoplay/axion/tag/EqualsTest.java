package com.sudoplay.axion.tag;

import static org.junit.Assert.*;

import org.junit.Test;

import com.sudoplay.axion.tag.TagByte;
import com.sudoplay.axion.tag.TagByteArray;
import com.sudoplay.axion.tag.TagCompound;
import com.sudoplay.axion.tag.TagDouble;
import com.sudoplay.axion.tag.TagEnd;
import com.sudoplay.axion.tag.TagFloat;
import com.sudoplay.axion.tag.TagInt;
import com.sudoplay.axion.tag.TagIntArray;
import com.sudoplay.axion.tag.TagList;
import com.sudoplay.axion.tag.TagLong;
import com.sudoplay.axion.tag.TagShort;
import com.sudoplay.axion.tag.TagString;

public class EqualsTest {

  @Test
  public void test_TagEnd() {
    assertEquals(TagEnd.class.getSimpleName(), new TagEnd(), new TagEnd());
  }

  @Test
  public void test_TagByte() {
    assertEquals(TagByte.class.getSimpleName(), new TagByte("name", Byte.MAX_VALUE), new TagByte("name", Byte.MAX_VALUE));
  }

  @Test
  public void test_TagShort() {
    assertEquals(TagShort.class.getSimpleName(), new TagShort("name", Short.MAX_VALUE), new TagShort("name", Short.MAX_VALUE));
  }

  @Test
  public void test_TagInt() {
    assertEquals(TagInt.class.getSimpleName(), new TagInt("name", Integer.MAX_VALUE), new TagInt("name", Integer.MAX_VALUE));
  }

  @Test
  public void test_TagLong() {
    assertEquals(TagLong.class.getSimpleName(), new TagLong("name", Long.MAX_VALUE), new TagLong("name", Long.MAX_VALUE));
  }

  @Test
  public void test_TagFloat() {
    assertEquals(TagFloat.class.getSimpleName(), new TagFloat("name", Float.MAX_VALUE), new TagFloat("name", Float.MAX_VALUE));
  }

  @Test
  public void test_TagDouble() {
    assertEquals(TagDouble.class.getSimpleName(), new TagDouble("name", Double.MAX_VALUE), new TagDouble("name", Double.MAX_VALUE));
  }

  @Test
  public void test_TagByteArray() {
    assertEquals(TagByteArray.class.getSimpleName(), new TagByteArray("name", new byte[] { 0, 1, 2, 3 }), new TagByteArray("name", new byte[] { 0, 1, 2, 3 }));
  }

  @Test
  public void test_TagString() {
    assertEquals(TagString.class.getSimpleName(), new TagString("name", "string"), new TagString("name", "string"));
  }

  @Test
  public void test_TagList() {
    TagList listA = new TagList(TagByte.class, "name");
    listA.add(new TagByte("tagA", (byte) 16));
    listA.add(new TagByte("tagB", (byte) 8));
    listA.add(new TagByte("tagC", (byte) 4));
    listA.add(new TagByte("tagD", (byte) 2));

    TagList listB = new TagList(TagByte.class, "name");
    listB.add(new TagByte("tagA", (byte) 16));
    listB.add(new TagByte("tagB", (byte) 8));
    listB.add(new TagByte("tagC", (byte) 4));
    listB.add(new TagByte("tagD", (byte) 2));

    assertEquals(TagList.class.getSimpleName(), listA, listB);
  }

  @Test
  public void test_TagCompound() {
    TagCompound compoundA = new TagCompound("name");
    TagList listA = new TagList(TagByte.class, "name");
    listA.add(new TagByte("tagA", (byte) 16));
    listA.add(new TagByte("tagB", (byte) 8));
    listA.add(new TagByte("tagC", (byte) 4));
    listA.add(new TagByte("tagD", (byte) 2));
    compoundA.putList("list", listA);
    compoundA.putBoolean("boolean", true);
    compoundA.putByte("byte", (byte) 16);
    compoundA.putByteArray("byteArray", new byte[] { 0, 1, 2, 3 });
    compoundA.putDouble("double", 67.394857);
    compoundA.putFloat("float", 6.453f);
    compoundA.putInt("int", 16);
    compoundA.putIntArray("intArray", new int[] { 0, 1, 2, 3 });
    compoundA.putLong("long", 79L);
    compoundA.putShort("short", (short) 947);
    compoundA.putString("string", "somestring");

    TagCompound compoundB = new TagCompound("name");
    TagList listB = new TagList(TagByte.class, "name");
    listB.add(new TagByte("tagA", (byte) 16));
    listB.add(new TagByte("tagB", (byte) 8));
    listB.add(new TagByte("tagC", (byte) 4));
    listB.add(new TagByte("tagD", (byte) 2));
    compoundB.putList("list", listB);
    compoundB.putBoolean("boolean", true);
    compoundB.putByte("byte", (byte) 16);
    compoundB.putByteArray("byteArray", new byte[] { 0, 1, 2, 3 });
    compoundB.putDouble("double", 67.394857);
    compoundB.putFloat("float", 6.453f);
    compoundB.putInt("int", 16);
    compoundB.putIntArray("intArray", new int[] { 0, 1, 2, 3 });
    compoundB.putLong("long", 79L);
    compoundB.putShort("short", (short) 947);
    compoundB.putString("string", "somestring");

    assertEquals(TagCompound.class.getSimpleName(), compoundA, compoundB);
  }

  @Test
  public void test_TagIntArray() {
    assertEquals(TagIntArray.class.getSimpleName(), new TagIntArray("name", new int[] { 0, 1, 2, 3 }), new TagIntArray("name", new int[] { 0, 1, 2, 3 }));
  }

}
