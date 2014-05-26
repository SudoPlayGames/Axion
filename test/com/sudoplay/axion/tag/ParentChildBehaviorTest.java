package com.sudoplay.axion.tag;

import static org.junit.Assert.*;

import org.junit.Test;

import com.sudoplay.axion.spec.tag.TagByte;
import com.sudoplay.axion.spec.tag.TagCompound;
import com.sudoplay.axion.spec.tag.TagList;

public class ParentChildBehaviorTest {

  /**
   * When a tag that belongs to one list is added to another list, an
   * {@link AxionInvalidTagException} should be thrown.
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
      fail("Expected AxionInvalidTagException");
    } catch (AxionInvalidTagException e) {
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
   * {@link AxionInvalidTagException} should be thrown.
   */
  @Test
  public void testListToCompoundParentChange() {
    TagList list = new TagList(TagByte.class, "list");
    TagCompound compound = new TagCompound("compound");

    list.addByte((byte) 16);
    assertEquals(1, list.size());
    try {
      compound.put(list.get(0));
      fail("Expected AxionInvalidTagException");
    } catch (AxionInvalidTagException e) {
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
      fail("Should throw AxionIllegalNameChangeException");
    } catch (AxionIllegalNameChangeException e) {
      //
    }
    assertEquals("", list.get(0).getName());
  }

  @Test
  public void testCompoundChildNameChange() {
    TagCompound compound = new TagCompound("compound");
    compound.put(new TagByte("tagByte", (byte) 16));
    TagByte tagByte = compound.get("tagByte");
    assertEquals("tagByte", tagByte.getName());
    tagByte.setName("newName");
    assertEquals("newName", tagByte.getName());
    assertEquals(tagByte, compound.get("newName"));
  }

}
