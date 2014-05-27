package com.sudoplay.axion.tag;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.sudoplay.axion.Axion;
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

public class TagIdTest {

  @Test
  public void testSpec() {
    check(new TagByte("name"), 1);
    check(new TagShort("name"), 2);
    check(new TagInt("name"), 3);
    check(new TagLong("name"), 4);
    check(new TagFloat("name"), 5);
    check(new TagDouble("name"), 6);
    check(new TagByteArray("name"), 7);
    check(new TagString("name"), 8);
    check(new TagList(TagByte.class), 9);
    check(new TagCompound(), 10);
    check(new TagIntArray("name"), 11);
  }
  
  @Test
  public void testExt() {
    check(TestUtil.getTagBoolean(), 80);
    check(TestUtil.getTagDoubleArray(), 81);
    check(TestUtil.getTagFloatArray(), 82);
    check(TestUtil.getTagLongArray(), 83);
    check(TestUtil.getTagShortArray(), 84);
    check(TestUtil.getTagStringArray(), 85);
    check(TestUtil.getTagBooleanArray(), 86);
  }

  private void check(Tag tag, int id) {
    assertEquals(tag.getClass().getSimpleName(), Axion.getExtInstance().getIdFor(tag.getClass()), id);
  }

}
