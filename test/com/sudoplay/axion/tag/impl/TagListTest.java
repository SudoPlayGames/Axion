package com.sudoplay.axion.tag.impl;

import static org.junit.Assert.*;

import java.security.InvalidParameterException;

import org.junit.Test;

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
