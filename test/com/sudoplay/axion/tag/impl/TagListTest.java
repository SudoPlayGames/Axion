package com.sudoplay.axion.tag.impl;

import static org.junit.Assert.*;

import java.security.InvalidParameterException;

import org.junit.Test;

public class TagListTest {

  @Test
  public void testChildNameChange() {
    TagList list = new TagList("newList") {
      {
        add(new TagByte("name", (byte) 14));
      }
    };
    assertTrue(list.size() == 1);
    try {
      list.get(0).setName("shouldNotHaveAName");
      fail("Should throw IllegalStateException");
    } catch (IllegalStateException e) {
      //
    }
    assertEquals("", list.get(0).getName());
  }

  @Test
  public void testMixedTypes() {
    try {
      TagList list = new TagList("newList") {
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
