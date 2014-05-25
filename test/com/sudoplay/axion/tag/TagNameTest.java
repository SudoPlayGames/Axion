package com.sudoplay.axion.tag;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.ext.tag.TagBoolean;
import com.sudoplay.axion.ext.tag.TagDoubleArray;
import com.sudoplay.axion.ext.tag.TagFloatArray;
import com.sudoplay.axion.ext.tag.TagLongArray;
import com.sudoplay.axion.ext.tag.TagShortArray;
import com.sudoplay.axion.ext.tag.TagStringArray;
import com.sudoplay.axion.spec.tag.TagByte;
import com.sudoplay.axion.spec.tag.TagByteArray;
import com.sudoplay.axion.spec.tag.TagCompound;
import com.sudoplay.axion.spec.tag.TagDouble;
import com.sudoplay.axion.spec.tag.TagFloat;
import com.sudoplay.axion.spec.tag.TagInt;
import com.sudoplay.axion.spec.tag.TagIntArray;
import com.sudoplay.axion.spec.tag.TagList;
import com.sudoplay.axion.spec.tag.TagLong;
import com.sudoplay.axion.spec.tag.TagShort;
import com.sudoplay.axion.spec.tag.TagString;

public class TagNameTest {

  @Test
  public void testSpec() {
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

  @Test
  public void testExt() {
    assertEquals(Axion.getNameFor(new TagBoolean("name")), "TagBoolean");
    assertEquals(Axion.getNameFor(new TagDoubleArray("name")), "TagDoubleArray");
    assertEquals(Axion.getNameFor(new TagFloatArray("name")), "TagFloatArray");
    assertEquals(Axion.getNameFor(new TagLongArray("name")), "TagLongArray");
    assertEquals(Axion.getNameFor(new TagShortArray("name")), "TagShortArray");
    assertEquals(Axion.getNameFor(new TagStringArray("name")), "TagStringArray");
  }

}
