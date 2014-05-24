package com.sudoplay.axion.tag;

import static org.junit.Assert.fail;

import org.junit.Test;

import com.sudoplay.axion.tag.spec.TagByte;
import com.sudoplay.axion.tag.spec.TagList;
import com.sudoplay.axion.tag.spec.TagLong;

public class TagListTest {

  @Test
  public void testMixedTypes() {
    try {
      new TagList(TagByte.class, "newList") {
        {
          add(new TagByte("name", (byte) 14));
          add(new TagLong("long", 45L));
        }
      };
      fail("Should throw IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      //
    }
  }

}
