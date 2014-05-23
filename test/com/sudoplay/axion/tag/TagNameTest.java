package com.sudoplay.axion.tag;

import static org.junit.Assert.*;

import org.junit.Test;

import com.sudoplay.axion.tag.TagByte;
import com.sudoplay.axion.tag.TagByteArray;
import com.sudoplay.axion.tag.TagCompound;
import com.sudoplay.axion.tag.TagDouble;
import com.sudoplay.axion.tag.TagEnd;
import com.sudoplay.axion.tag.TagFloat;
import com.sudoplay.axion.tag.TagInt;
import com.sudoplay.axion.tag.TagIntArray;
import com.sudoplay.axion.tag.TagList;
import com.sudoplay.axion.tag.TagLong;
import com.sudoplay.axion.tag.TagShort;
import com.sudoplay.axion.tag.TagString;

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
