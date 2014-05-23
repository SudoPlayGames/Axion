package com.sudoplay.axion.tag.impl;

import static org.junit.Assert.*;

import org.junit.Test;

public class TagNameTest {

  @Test
  public void test() {
    assertTrue(new TagEnd().getTagName() == "TAG_End");
    assertTrue(new TagByte("name").getTagName() == "TAG_Byte");
    assertTrue(new TagShort("name").getTagName() == "TAG_Short");
    assertTrue(new TagInt("name").getTagName() == "TAG_Int");
    assertTrue(new TagLong("name").getTagName() == "TAG_Long");
    assertTrue(new TagFloat("name").getTagName() == "TAG_Float");
    assertTrue(new TagDouble("name").getTagName() == "TAG_Double");
    assertTrue(new TagByteArray("name").getTagName() == "TAG_Byte_Array");
    assertTrue(new TagString("name").getTagName() == "TAG_String");
    assertTrue(new TagList(TagByte.class).getTagName() == "TAG_List");
    assertTrue(new TagCompound().getTagName() == "TAG_Compound");
    assertTrue(new TagIntArray("name").getTagName() == "TAG_Int_Array");
  }

}
