package com.sudoplay.axion.tag;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.junit.Test;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.TestUtil;
import com.sudoplay.axion.tag.spec.Tag;
import com.sudoplay.axion.tag.spec.TagByte;
import com.sudoplay.axion.tag.spec.TagByteArray;
import com.sudoplay.axion.tag.spec.TagCompound;
import com.sudoplay.axion.tag.spec.TagDouble;
import com.sudoplay.axion.tag.spec.TagFloat;
import com.sudoplay.axion.tag.spec.TagInt;
import com.sudoplay.axion.tag.spec.TagIntArray;
import com.sudoplay.axion.tag.spec.TagList;
import com.sudoplay.axion.tag.spec.TagLong;
import com.sudoplay.axion.tag.spec.TagShort;
import com.sudoplay.axion.tag.spec.TagString;

public class SerializationTest {

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

    assertEquals(TagList.class.getSimpleName(), listA, serialize(listB));
  }

  @Test
  public void test_TagCompound() throws IOException {
    TagCompound compoundA = TestUtil.getTagCompound();
    TagCompound compoundB = TestUtil.getTagCompound();
    assertEquals(TagCompound.class.getSimpleName(), compoundA, serialize(compoundB));
  }

  @Test
  public void test_TagIntArray() throws IOException {
    assertEquals(TagIntArray.class.getSimpleName(), new TagIntArray("name", new int[] { 0, 1, 2, 3 }), serialize(new TagIntArray("name",
        new int[] { 0, 1, 2, 3 })));
  }

  private Tag serialize(Tag start) throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    DataOutputStream out = new DataOutputStream(baos);
    Axion.write(start, out);

    ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
    DataInputStream in = new DataInputStream(bais);
    return Axion.read(in);
  }

}
