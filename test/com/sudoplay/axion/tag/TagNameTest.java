package com.sudoplay.axion.tag;

import static org.junit.Assert.*;

import org.junit.Test;

import com.sudoplay.axion.Axion;
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
    assertEquals(Axion.getNameFor(new TagByte("name")), "TagByte");
    assertEquals(Axion.getNameFor(new TagShort("name")), "TagShort");
    assertEquals(Axion.getNameFor(new TagInt("name")), "TagInt");
    assertEquals(Axion.getNameFor(new TagLong("name")), "TagLong");
    assertEquals(Axion.getNameFor(new TagFloat("name")), "TagFloat");
    assertEquals(Axion.getNameFor(new TagDouble("name")), "TagDouble");
    assertEquals(Axion.getNameFor(new TagByteArray("name")), "TagByteArray");
    assertEquals(Axion.getNameFor(new TagString("name")), "TagString");
    assertEquals(Axion.getNameFor(new TagList(TagByte.class)), "TagList");
    assertEquals(Axion.getNameFor(new TagCompound()), "TagCompound");
    assertEquals(Axion.getNameFor(new TagIntArray("name")), "TagIntArray");
  }

}
