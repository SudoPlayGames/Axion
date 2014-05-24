package com.sudoplay.axion.tag;

import static org.junit.Assert.assertEquals;

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

public class ToStringTest {

  @Test
  public void test() {

    assertEquals("TAG_Byte(\"name\"): 16", new TagByte("name", (byte) 16).toString());
    assertEquals("TAG_Short(\"name\"): 16", new TagShort("name", (short) 16).toString());
    assertEquals("TAG_Int(\"name\"): 16", new TagInt("name", 16).toString());
    assertEquals("TAG_Long(\"name\"): 16", new TagLong("name", (long) 16).toString());
    assertEquals("TAG_Float(\"name\"): 16.0", new TagFloat("name", (float) 16).toString());
    assertEquals("TAG_Double(\"name\"): 16.0", new TagDouble("name", (double) 16).toString());
    assertEquals("TAG_Byte_Array(\"name\"): [4 bytes]", new TagByteArray("name", new byte[] { 0, 1, 2, 3 }).toString());
    assertEquals("TAG_String(\"name\"): string", new TagString("name", "string").toString());

    TagList list = new TagList(TagInt.class, "name");
    list.add(new TagInt("name", 56));
    list.add(new TagInt("name", 56));
    list.add(new TagInt("name", 56));
    list.add(new TagInt("name", 56));
    assertEquals("TAG_List(\"name\"): 4 entries of type 3", list.toString());

    TagCompound compound = new TagCompound("name");
    compound.putFloat("float", 6.5f);
    compound.putBoolean("booean", true);
    assertEquals("TAG_Compound(\"name\"): 2 entries", compound.toString());

    assertEquals("TAG_Int_Array(\"name\"): [4 ints]", new TagIntArray("name", new int[] { 0, 1, 2, 3 }).toString());

  }

}
