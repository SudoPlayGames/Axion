package com.sudoplay.axion.tag.definition;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.sudoplay.axion.tag.impl.TagByte;

public class NBTByteTest {

  @Test
  public void hasTagIdOne() {
    assertTrue(new TagByte("tagName").getTagId() == 1);
  }

  @Test
  public void hasTagNameTAG_Byte() {
    assertTrue("TAG_Byte".equals(new TagByte("tagName").getTagName()));
  }

  @Test
  public void hasName() {
    TagByte nbt = new TagByte("tagName");
    assertTrue("tagName".equals(nbt.getName()));
  }
  
  @Test
  public void hasData() {
    TagByte nbt = new TagByte("tagName", (byte) 16);
    assertTrue(16 == nbt.get());
  }

}
