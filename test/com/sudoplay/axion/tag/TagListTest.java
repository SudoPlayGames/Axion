package com.sudoplay.axion.tag;

import static org.junit.Assert.*;

import java.security.InvalidParameterException;

import org.junit.Test;

import com.sudoplay.axion.tag.TagByte;
import com.sudoplay.axion.tag.TagList;
import com.sudoplay.axion.tag.TagLong;

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
      fail("Should throw InvalidParameterException");
    } catch (InvalidParameterException e) {
      //
    }
  }

}
