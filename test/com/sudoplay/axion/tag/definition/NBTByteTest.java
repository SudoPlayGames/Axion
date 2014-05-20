package com.sudoplay.axion.tag.definition;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class NBTByteTest {

  @Test
  public void hasTagIdOne() {
    assertTrue(new NBTByte("tagName").getTagId() == 1);
  }

  @Test
  public void hasTagNameTAG_Byte() {
    assertTrue("TAG_Byte".equals(new NBTByte("tagName").getTagName()));
  }

  @Test
  public void hasName() {
    NBTByte nbt = new NBTByte("tagName");
    assertTrue("tagName".equals(nbt.getName()));
  }
  
  @Test
  public void hasData() {
    NBTByte nbt = new NBTByte("tagName", (byte) 16);
    assertTrue(16 == nbt.get());
  }

}
