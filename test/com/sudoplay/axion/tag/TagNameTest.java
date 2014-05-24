package com.sudoplay.axion.tag;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.sudoplay.axion.tag.standard.TagByte;
import com.sudoplay.axion.tag.standard.TagByteArray;
import com.sudoplay.axion.tag.standard.TagCompound;
import com.sudoplay.axion.tag.standard.TagDouble;
import com.sudoplay.axion.tag.standard.TagFloat;
import com.sudoplay.axion.tag.standard.TagInt;
import com.sudoplay.axion.tag.standard.TagIntArray;
import com.sudoplay.axion.tag.standard.TagList;
import com.sudoplay.axion.tag.standard.TagLong;
import com.sudoplay.axion.tag.standard.TagShort;
import com.sudoplay.axion.tag.standard.TagString;

public class TagNameTest {

  @Test
  public void test() {
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
