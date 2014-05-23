package com.sudoplay.axion.tag;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.sudoplay.axion.util.TagUtil;

public class TagUtilTest {

  @Test
  public void testClassToId() {
    assertEquals(TagByte.TAG_ID, TagUtil.getId(TagByte.class));
    assertEquals(TagShort.TAG_ID, TagUtil.getId(TagShort.class));
    assertEquals(TagInt.TAG_ID, TagUtil.getId(TagInt.class));
    assertEquals(TagLong.TAG_ID, TagUtil.getId(TagLong.class));
    assertEquals(TagFloat.TAG_ID, TagUtil.getId(TagFloat.class));
    assertEquals(TagDouble.TAG_ID, TagUtil.getId(TagDouble.class));
    assertEquals(TagByteArray.TAG_ID, TagUtil.getId(TagByteArray.class));
    assertEquals(TagString.TAG_ID, TagUtil.getId(TagString.class));
    assertEquals(TagList.TAG_ID, TagUtil.getId(TagList.class));
    assertEquals(TagCompound.TAG_ID, TagUtil.getId(TagCompound.class));
    assertEquals(TagIntArray.TAG_ID, TagUtil.getId(TagIntArray.class));
  }

  @Test
  public void testIdToClass() {
    assertEquals(TagByte.class, TagUtil.getTagClass(TagByte.TAG_ID));
    assertEquals(TagShort.class, TagUtil.getTagClass(TagShort.TAG_ID));
    assertEquals(TagInt.class, TagUtil.getTagClass(TagInt.TAG_ID));
    assertEquals(TagLong.class, TagUtil.getTagClass(TagLong.TAG_ID));
    assertEquals(TagFloat.class, TagUtil.getTagClass(TagFloat.TAG_ID));
    assertEquals(TagDouble.class, TagUtil.getTagClass(TagDouble.TAG_ID));
    assertEquals(TagByteArray.class, TagUtil.getTagClass(TagByteArray.TAG_ID));
    assertEquals(TagString.class, TagUtil.getTagClass(TagString.TAG_ID));
    assertEquals(TagList.class, TagUtil.getTagClass(TagList.TAG_ID));
    assertEquals(TagCompound.class, TagUtil.getTagClass(TagCompound.TAG_ID));
    assertEquals(TagIntArray.class, TagUtil.getTagClass(TagIntArray.TAG_ID));
  }

}
