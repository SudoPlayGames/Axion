package com.sudoplay.axion.tag;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class NameTest {

  @Test
  public void test() {
    check(new TagByte("name"));
    check(new TagShort("name"));
    check(new TagInt("name"));
    check(new TagLong("name"));
    check(new TagFloat("name"));
    check(new TagDouble("name"));
    check(new TagByteArray("name"));
    check(new TagString("name"));
    check(new TagList(TagByte.class, "name"));
    check(new TagCompound("name"));
    check(new TagIntArray("name"));
  }

  private void check(Tag tag) {
    assertEquals(tag.getClass().getSimpleName(), "name", tag.getName());
  }

}
