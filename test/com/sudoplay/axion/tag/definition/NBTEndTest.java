package com.sudoplay.axion.tag.definition;

import static org.junit.Assert.*;

import org.junit.Test;

import com.sudoplay.axion.tag.impl.TagEnd;

public class NBTEndTest {

  @Test
  public void hasTagIdZero() {
    TagEnd nbtEnd = new TagEnd();
    assertTrue(nbtEnd.getTagId() == 0);
  }

  @Test
  public void hasTagNameTAG_End() {
    TagEnd nbtEnd = new TagEnd();
    assertTrue("TAG_End".equals(nbtEnd.getTagName()));
  }
  
  @Test
  public void hasNoName() {
    TagEnd nbtEnd = new TagEnd();
    nbtEnd.setName("shouldhavenoname");
    assertTrue("".equals(nbtEnd.getName()));
  }
  
  @Test
  public void canNotRead() {
    try {
      new TagEnd().read(null);
    } catch (UnsupportedOperationException e) {
      assert true;
    }
    assert false;
  }
  
  @Test
  public void canNotWrite() {
    try {
      new TagEnd().write(null);
    } catch (UnsupportedOperationException e) {
      assert true;
    }
    assert false;
  }

}
