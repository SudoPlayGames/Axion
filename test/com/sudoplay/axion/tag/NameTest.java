package com.sudoplay.axion.tag;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.sudoplay.axion.tag.spec.Tag;
import com.sudoplay.axion.tag.spec.TagByte;
import com.sudoplay.axion.tag.spec.TagByteArray;
import com.sudoplay.axion.tag.spec.TagCompound;
import com.sudoplay.axion.tag.spec.TagDouble;
import com.sudoplay.axion.tag.spec.TagFloat;
import com.sudoplay.axion.tag.spec.TagInt;
import com.sudoplay.axion.tag.spec.TagIntArray;
import com.sudoplay.axion.tag.spec.TagList;
import com.sudoplay.axion.tag.spec.TagLong;
import com.sudoplay.axion.tag.spec.TagShort;
import com.sudoplay.axion.tag.spec.TagString;

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
