package com.sudoplay.axion.tag.impl;

import static org.junit.Assert.*;

import org.junit.Test;

import com.sudoplay.axion.tag.Abstract_Tag;

public class TagIdTest {

  @Test
  public void test() {
    check(new TagEnd(), 0);
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

  private void check(Abstract_Tag tag, int id) {
    assertEquals(tag.getClass().getSimpleName(), tag.getTagId(), id);
  }

}
