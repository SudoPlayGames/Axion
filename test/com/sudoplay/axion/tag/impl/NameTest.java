package com.sudoplay.axion.tag.impl;

import static org.junit.Assert.*;

import org.junit.Test;

import com.sudoplay.axion.tag.Abstract_Tag;

public class NameTest {

  @Test
  public void test() {
    Abstract_Tag tag;

    tag = new TagEnd();
    tag.setName("name");
    assertEquals(tag.getClass().getSimpleName(), "", tag.getName());

    check(new TagByte("name"));
    check(new TagShort("name"));
    check(new TagInt("name"));
    check(new TagLong("name"));
    check(new TagFloat("name"));
    check(new TagDouble("name"));
    check(new TagByteArray("name"));
    check(new TagString("name"));
    check(new TagList("name"));
    check(new TagCompound("name"));
    check(new TagIntArray("name"));

  }

  private void check(Abstract_Tag tag) {
    assertEquals(tag.getClass().getSimpleName(), "name", tag.getName());
  }

}
