package com.sudoplay.axion.tag;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.spec.tag.Tag;
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
    assertEquals(tag.getClass().getSimpleName(), Axion.getDefault().getIdFor(tag.getClass()), id);
  }

}
