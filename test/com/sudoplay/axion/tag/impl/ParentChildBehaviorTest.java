package com.sudoplay.axion.tag.impl;

import static org.junit.Assert.*;

import org.junit.Test;

public class ParentChildBehaviorTest {

  /**
   * When a tag that belongs to one list is added to another list, an
   * {@link IllegalStateException} should be thrown.
   */
  @Test
  public void testListToListParentChange() {
    TagList list = new TagList(TagByte.class, "list") {
      {
        add(new TagByte("name", (byte) 14));
      }
    };
    assertEquals(1, list.size());
    TagList newList = new TagList(TagByte.class, "newList");
    try {
      newList.add(list.get(0));
      fail("Expected IllegalStateException");
    } catch (IllegalStateException e) {
      //
    }
    TagByte tagByte = (TagByte) list.get(0);
    tagByte.removeFromParent();
    assertEquals(0, list.size());
    newList.add(tagByte);
    assertEquals(1, newList.size());
  }

  /**
   * When a tag that belongs to a list is added to a compound, or vice-versa, an
   * {@link IllegalStateException} should be thrown.
   */
  @Test
  public void testListToCompoundParentChange() {
    TagList list = new TagList(TagByte.class, "list");
    TagCompound compound = new TagCompound("compound");

    list.addByte((byte) 16);
    assertEquals(1, list.size());
    try {
      compound.put("byteTag", list.get(0));
      fail("Expected IllegalStateException");
    } catch (IllegalStateException e) {
      //
    }

  }

  @Test
  public void testListChildNameChange() {
    TagList list = new TagList(TagByte.class, "newList") {
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
  public void testCompoundChildNameChange() {
    TagCompound compound = new TagCompound("compound");
    compound.putByte("tagByte", (byte) 16);
    TagByte tagByte = (TagByte) compound.get("tagByte");
    assertEquals("tagByte", tagByte.getName());
    tagByte.setName("newName");
    assertEquals("newName", tagByte.getName());
    assertEquals(tagByte, compound.get("newName"));
  }

}
