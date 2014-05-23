package com.sudoplay.axion.tag;

import static org.junit.Assert.*;

import org.junit.Test;

public class CloneTest {

  @Test
  public void testGeneral() {
    check(new TagByte("tagByte", (byte) 16));
    check(new TagByteArray("tagByteArray", new byte[] { 0, 1, 2, 3 }));
    check(new TagDouble("tagDouble", 34.5234));
    check(new TagFloat("tagFloat", 45.43f));
    check(new TagInt("tagInt", 42));
    check(new TagIntArray("tagIntArray", new int[] { 0, 1, 2, 3 }));
    check(new TagLong("tagLong", 45L));
    check(new TagShort("tagShort", (short) 23434));
    check(new TagString("tagString", "someString"));
  }

  @Test
  public void testTagList() {
    TagList tag = new TagList(TagByte.class);
    tag.addByte((byte) 23);
    tag.addByte((byte) 26);
    tag.addByte((byte) 98);
    tag.addByte((byte) 3);

    TagList clone = tag.clone();
    assertTrue(tag != clone);
    assertEquals(tag, clone);

    for (int i = 0; i < tag.size(); i++) {
      assertTrue(tag.get(i) != clone.get(i));
      assertEquals(tag.get(i), clone.get(i));
    }
  }

  @Test
  public void testTagCompound() {
    TagCompound tag = new TagCompound("tagCompound");
    tag.putBoolean("boolean", true);
    tag.putIntArray("intArray", new int[] { 0, 1, 2, 3 });
    check(tag);
  }

  @Test
  public void testTagEnd() {
    TagEnd tag = TagEnd.INSTANCE;
    TagEnd clone = tag.clone();
    assertTrue(tag == clone);
    assertEquals(tag, clone);
  }

  private void check(Tag tag) {
    Tag clone = tag.clone();
    assertTrue(tag != clone);
    assertEquals(tag, clone);
  }

}
