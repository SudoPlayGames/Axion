package com.sudoplay.axion.tag;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.sudoplay.axion.tag.spec.TagByte;
import com.sudoplay.axion.tag.spec.TagByteArray;
import com.sudoplay.axion.tag.spec.TagCompound;
import com.sudoplay.axion.tag.spec.TagDouble;
import com.sudoplay.axion.tag.spec.TagFloat;
import com.sudoplay.axion.tag.spec.TagInt;
import com.sudoplay.axion.tag.spec.TagIntArray;
import com.sudoplay.axion.tag.spec.TagList;
import com.sudoplay.axion.tag.spec.TagLong;
import com.sudoplay.axion.tag.spec.TagShort;
import com.sudoplay.axion.tag.spec.TagString;

public class ToStringTest {

  @Test
  public void test() {

    assertEquals("TagByte(\"name\"): 16", new TagByte("name", (byte) 16).toString());
    assertEquals("TagShort(\"name\"): 16", new TagShort("name", (short) 16).toString());
    assertEquals("TagInt(\"name\"): 16", new TagInt("name", 16).toString());
    assertEquals("TagLong(\"name\"): 16", new TagLong("name", (long) 16).toString());
    assertEquals("TagFloat(\"name\"): 16.0", new TagFloat("name", (float) 16).toString());
    assertEquals("TagDouble(\"name\"): 16.0", new TagDouble("name", (double) 16).toString());
    assertEquals("TagByteArray(\"name\"): [4 bytes]", new TagByteArray("name", new byte[] { 0, 1, 2, 3 }).toString());
    assertEquals("TagString(\"name\"): string", new TagString("name", "string").toString());

    TagList list = new TagList(TagInt.class, "name");
    list.add(new TagInt("name", 56));
    list.add(new TagInt("name", 56));
    list.add(new TagInt("name", 56));
    list.add(new TagInt("name", 56));
    assertEquals("TagList(\"name\"): 4 entries of type 3", list.toString());

    TagCompound compound = new TagCompound("name");
    compound.putFloat("float", 6.5f);
    compound.putBoolean("booean", true);
    assertEquals("TagCompound(\"name\"): 2 entries", compound.toString());

    assertEquals("TagIntArray(\"name\"): [4 ints]", new TagIntArray("name", new int[] { 0, 1, 2, 3 }).toString());

  }

}
