package com.sudoplay.axion.tag;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.sudoplay.axion.ext.tag.TagBoolean;
import com.sudoplay.axion.ext.tag.TagBooleanArray;
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

public class ToStringTest {

  @Test
  public void testSpec() {

    assertEquals("TagByte(\"name\"): 16", new TagByte("name", (byte) 16).toString());
    assertEquals("TagShort(\"name\"): 16", new TagShort("name", (short) 16).toString());
    assertEquals("TagInt(\"name\"): 16", new TagInt("name", 16).toString());
    assertEquals("TagLong(\"name\"): 16", new TagLong("name", (long) 16).toString());
    assertEquals("TagFloat(\"name\"): 16.0", new TagFloat("name", (float) 16).toString());
    assertEquals("TagDouble(\"name\"): 16.0", new TagDouble("name", (double) 16).toString());
    assertEquals("TagByteArray(\"name\"): [4 bytes]", new TagByteArray("name", new byte[] { 0, 1, 2, 3 }).toString());
    assertEquals("TagString(\"name\"): string", new TagString("name", "string").toString());
    assertEquals("TagIntArray(\"name\"): [4 ints]", new TagIntArray("name", new int[] { 0, 1, 2, 3 }).toString());

    TagList list = new TagList(TagInt.class, "name");
    list.add(new TagInt("name", 56));
    list.add(new TagInt("name", 56));
    list.add(new TagInt("name", 56));
    list.add(new TagInt("name", 56));
    assertEquals("TagList(\"name\"): 4 entries of type TagInt", list.toString());

    TagCompound compound = new TagCompound("name");
    compound.put(new TagFloat("float", 6.5f));
    assertEquals("TagCompound(\"name\"): 1 entries", compound.toString());
  }

  @Test
  public void testExt() {
    assertEquals("TagBoolean(\"name\"): true", new TagBoolean("name", true).toString());
    assertEquals("TagDoubleArray(\"name\"): [4 doubles]", new TagDoubleArray("name", new double[] { 0, 1, 2, 3 }).toString());
    assertEquals("TagFloatArray(\"name\"): [4 floats]", new TagFloatArray("name", new float[] { 0, 1, 2, 3 }).toString());
    assertEquals("TagLongArray(\"name\"): [4 longs]", new TagLongArray("name", new long[] { 0, 1, 2, 3 }).toString());
    assertEquals("TagShortArray(\"name\"): [4 shorts]", new TagShortArray("name", new short[] { 0, 1, 2, 3 }).toString());
    assertEquals("TagStringArray(\"name\"): [4 strings]", new TagStringArray("name", new String[] { "0", "1", "2", "3" }).toString());
    assertEquals("TagBooleanArray(\"name\"): [4 booleans]", new TagBooleanArray("name", new boolean[] { true, false, false, true }).toString());
  }

}
