package com.sudoplay.axion.tag;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.sudoplay.axion.TestUtil;
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

public class EqualsTest {

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
    TagCompound compoundA = TestUtil.getTagCompound();
    TagCompound compoundB = TestUtil.getTagCompound();
    assertEquals(TagCompound.class.getSimpleName(), compoundA, compoundB);
  }

  @Test
  public void test_TagIntArray() {
    assertEquals(TagIntArray.class.getSimpleName(), new TagIntArray("name", new int[] { 0, 1, 2, 3 }), new TagIntArray("name", new int[] { 0, 1, 2, 3 }));
  }

}
