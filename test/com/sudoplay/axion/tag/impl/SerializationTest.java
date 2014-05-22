package com.sudoplay.axion.tag.impl;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.junit.Test;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.tag.Abstract_Tag;

public class SerializationTest {

  private Axion axion = new Axion();

  @Test
  public void test_TagEnd() throws IOException {
    assertEquals(TagEnd.class.getSimpleName(), new TagEnd(), serialize(new TagEnd()));
  }

  @Test
  public void test_TagByte() throws IOException {
    assertEquals(TagByte.class.getSimpleName(), new TagByte("name", Byte.MAX_VALUE), serialize(new TagByte("name", Byte.MAX_VALUE)));
  }

  @Test
  public void test_TagShort() throws IOException {
    assertEquals(TagShort.class.getSimpleName(), new TagShort("name", Short.MAX_VALUE), serialize(new TagShort("name", Short.MAX_VALUE)));
  }

  @Test
  public void test_TagInt() throws IOException {
    assertEquals(TagInt.class.getSimpleName(), new TagInt("name", Integer.MAX_VALUE), serialize(new TagInt("name", Integer.MAX_VALUE)));
  }

  @Test
  public void test_TagLong() throws IOException {
    assertEquals(TagLong.class.getSimpleName(), new TagLong("name", Long.MAX_VALUE), serialize(new TagLong("name", Long.MAX_VALUE)));
  }

  @Test
  public void test_TagFloat() throws IOException {
    assertEquals(TagFloat.class.getSimpleName(), new TagFloat("name", Float.MAX_VALUE), serialize(new TagFloat("name", Float.MAX_VALUE)));
  }

  @Test
  public void test_TagDouble() throws IOException {
    assertEquals(TagDouble.class.getSimpleName(), new TagDouble("name", Double.MAX_VALUE), serialize(new TagDouble("name", Double.MAX_VALUE)));
  }

  @Test
  public void test_TagByteArray() throws IOException {
    assertEquals(TagByteArray.class.getSimpleName(), new TagByteArray("name", new byte[] { 0, 1, 2, 3 }), serialize(new TagByteArray("name", new byte[] { 0, 1,
        2, 3 })));
  }

  @Test
  public void test_TagString() throws IOException {
    assertEquals(TagString.class.getSimpleName(), new TagString("name", "string"), serialize(new TagString("name", "string")));
  }

  @Test
  public void test_TagList() throws IOException {
    TagList listA = new TagList("name");
    listA.add(new TagByte("tagA", (byte) 16));
    listA.add(new TagByte("tagB", (byte) 8));
    listA.add(new TagByte("tagC", (byte) 4));
    listA.add(new TagByte("tagD", (byte) 2));

    TagList listB = new TagList("name");
    listB.add(new TagByte("tagA", (byte) 16));
    listB.add(new TagByte("tagB", (byte) 8));
    listB.add(new TagByte("tagC", (byte) 4));
    listB.add(new TagByte("tagD", (byte) 2));

    assertEquals(TagList.class.getSimpleName(), listA, serialize(listB));
  }

  @Test
  public void test_TagCompound() throws IOException {
    TagCompound compoundA = new TagCompound("name");
    TagList listA = new TagList("name");
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
    TagList listB = new TagList("name");
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

    assertEquals(TagCompound.class.getSimpleName(), compoundA, serialize(compoundB));
  }

  @Test
  public void test_TagIntArray() throws IOException {
    assertEquals(TagIntArray.class.getSimpleName(), new TagIntArray("name", new int[] { 0, 1, 2, 3 }), serialize(new TagIntArray("name",
        new int[] { 0, 1, 2, 3 })));
  }

  private Abstract_Tag serialize(Abstract_Tag start) throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    DataOutputStream out = new DataOutputStream(baos);
    axion.writeRaw(start, out);

    ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
    DataInputStream in = new DataInputStream(bais);
    return axion.readRaw(in);
  }

}
