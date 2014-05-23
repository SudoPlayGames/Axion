package com.sudoplay.axion.tag;

import static org.junit.Assert.*;

import org.junit.Test;

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

public class TagHelperTest {

  @Test
  public void testClassToId() {
    assertEquals(TagEnd.TAG_ID, TagHelper.getId(TagEnd.class));
    assertEquals(TagByte.TAG_ID, TagHelper.getId(TagByte.class));
    assertEquals(TagShort.TAG_ID, TagHelper.getId(TagShort.class));
    assertEquals(TagInt.TAG_ID, TagHelper.getId(TagInt.class));
    assertEquals(TagLong.TAG_ID, TagHelper.getId(TagLong.class));
    assertEquals(TagFloat.TAG_ID, TagHelper.getId(TagFloat.class));
    assertEquals(TagDouble.TAG_ID, TagHelper.getId(TagDouble.class));
    assertEquals(TagByteArray.TAG_ID, TagHelper.getId(TagByteArray.class));
    assertEquals(TagString.TAG_ID, TagHelper.getId(TagString.class));
    assertEquals(TagList.TAG_ID, TagHelper.getId(TagList.class));
    assertEquals(TagCompound.TAG_ID, TagHelper.getId(TagCompound.class));
    assertEquals(TagIntArray.TAG_ID, TagHelper.getId(TagIntArray.class));
  }

  @Test
  public void testIdToClass() {
    assertEquals(TagEnd.class, TagHelper.getTagClass(TagEnd.TAG_ID));
    assertEquals(TagByte.class, TagHelper.getTagClass(TagByte.TAG_ID));
    assertEquals(TagShort.class, TagHelper.getTagClass(TagShort.TAG_ID));
    assertEquals(TagInt.class, TagHelper.getTagClass(TagInt.TAG_ID));
    assertEquals(TagLong.class, TagHelper.getTagClass(TagLong.TAG_ID));
    assertEquals(TagFloat.class, TagHelper.getTagClass(TagFloat.TAG_ID));
    assertEquals(TagDouble.class, TagHelper.getTagClass(TagDouble.TAG_ID));
    assertEquals(TagByteArray.class, TagHelper.getTagClass(TagByteArray.TAG_ID));
    assertEquals(TagString.class, TagHelper.getTagClass(TagString.TAG_ID));
    assertEquals(TagList.class, TagHelper.getTagClass(TagList.TAG_ID));
    assertEquals(TagCompound.class, TagHelper.getTagClass(TagCompound.TAG_ID));
    assertEquals(TagIntArray.class, TagHelper.getTagClass(TagIntArray.TAG_ID));
  }

}
