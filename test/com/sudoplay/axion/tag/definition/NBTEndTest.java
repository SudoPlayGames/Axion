package com.sudoplay.axion.tag.definition;

import static org.junit.Assert.*;

import org.junit.Test;

public class NBTEndTest {

  @Test
  public void hasTagIdZero() {
    NBTEnd nbtEnd = new NBTEnd();
    assertTrue(nbtEnd.getTagId() == 0);
  }

  @Test
  public void hasTagNameTAG_End() {
    NBTEnd nbtEnd = new NBTEnd();
    assertTrue("TAG_End".equals(nbtEnd.getTagName()));
  }
  
  @Test
  public void hasNoName() {
    NBTEnd nbtEnd = new NBTEnd();
    nbtEnd.setName("shouldhavenoname");
    assertTrue("".equals(nbtEnd.getName()));
  }
  
  @Test
  public void canNotRead() {
    try {
      new NBTEnd().read(null);
    } catch (UnsupportedOperationException e) {
      assert true;
    }
    assert false;
  }
  
  @Test
  public void canNotWrite() {
    try {
      new NBTEnd().write(null);
    } catch (UnsupportedOperationException e) {
      assert true;
    }
    assert false;
  }

}
