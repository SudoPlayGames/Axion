package com.sudoplay.axion.tag;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.sudoplay.axion.tag.standard.Tag;
import com.sudoplay.axion.tag.standard.TagByte;
import com.sudoplay.axion.tag.standard.TagByteArray;
import com.sudoplay.axion.tag.standard.TagCompound;
import com.sudoplay.axion.tag.standard.TagDouble;
import com.sudoplay.axion.tag.standard.TagFloat;
import com.sudoplay.axion.tag.standard.TagInt;
import com.sudoplay.axion.tag.standard.TagIntArray;
import com.sudoplay.axion.tag.standard.TagList;
import com.sudoplay.axion.tag.standard.TagLong;
import com.sudoplay.axion.tag.standard.TagShort;
import com.sudoplay.axion.tag.standard.TagString;

public class TagIdTest {

  @Test
  public void test() {
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

  private void check(Tag tag, int id) {
    assertEquals(tag.getClass().getSimpleName(), tag.getTagId(), id);
  }

}
