package com.sudoplay.axion.tag;

import static org.junit.Assert.*;

import org.junit.Test;

import com.sudoplay.axion.tag.Tag;
import com.sudoplay.axion.tag.TagByte;
import com.sudoplay.axion.tag.TagByteArray;
import com.sudoplay.axion.tag.TagCompound;
import com.sudoplay.axion.tag.TagDouble;
import com.sudoplay.axion.tag.TagEnd;
import com.sudoplay.axion.tag.TagFloat;
import com.sudoplay.axion.tag.TagInt;
import com.sudoplay.axion.tag.TagIntArray;
import com.sudoplay.axion.tag.TagList;
import com.sudoplay.axion.tag.TagLong;
import com.sudoplay.axion.tag.TagShort;
import com.sudoplay.axion.tag.TagString;

public class NameTest {

  @Test
  public void test() {
    Tag tag;

    tag = TagEnd.INSTANCE;
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
    check(new TagList(TagByte.class, "name"));
    check(new TagCompound("name"));
    check(new TagIntArray("name"));

  }

  private void check(Tag tag) {
    assertEquals(tag.getClass().getSimpleName(), "name", tag.getName());
  }

}
