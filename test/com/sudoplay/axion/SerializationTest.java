package com.sudoplay.axion;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.Test;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.AxionConfiguration.CharacterEncodingType;
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
import com.sudoplay.axion.stream.AxionInputStream;
import com.sudoplay.axion.stream.AxionOutputStream;
import com.sudoplay.axion.stream.CharacterEncoderFactory;
import com.sudoplay.axion.tag.Tag;

public class SerializationTest {

  @Test
  public void testSpec() throws IOException {
    assertEquals(TagByte.class.getSimpleName(), new TagByte("name", Byte.MAX_VALUE), serialize(new TagByte("name", Byte.MAX_VALUE)));
    assertEquals(TagShort.class.getSimpleName(), new TagShort("name", Short.MAX_VALUE), serialize(new TagShort("name", Short.MAX_VALUE)));
    assertEquals(TagInt.class.getSimpleName(), new TagInt("name", Integer.MAX_VALUE), serialize(new TagInt("name", Integer.MAX_VALUE)));
    assertEquals(TagLong.class.getSimpleName(), new TagLong("name", Long.MAX_VALUE), serialize(new TagLong("name", Long.MAX_VALUE)));
    assertEquals(TagFloat.class.getSimpleName(), new TagFloat("name", Float.MAX_VALUE), serialize(new TagFloat("name", Float.MAX_VALUE)));
    assertEquals(TagDouble.class.getSimpleName(), new TagDouble("name", Double.MAX_VALUE), serialize(new TagDouble("name", Double.MAX_VALUE)));
    assertEquals(TagByteArray.class.getSimpleName(), new TagByteArray("name", new byte[] { 0, 1, 2, 3 }), serialize(new TagByteArray("name", new byte[] { 0, 1,
        2, 3 })));
    assertEquals(TagString.class.getSimpleName(), new TagString("name", "string"), serialize(new TagString("name", "string")));
    assertEquals(TagIntArray.class.getSimpleName(), new TagIntArray("name", new int[] { 0, 1, 2, 3 }), serialize(new TagIntArray("name",
        new int[] { 0, 1, 2, 3 })));

    TagList listA = new TagList(TagByte.class, "name");
    listA.add(new TagByte("tagA", (byte) 16));
    listA.add(new TagByte("tagB", (byte) 8));
    listA.add(new TagByte("tagC", (byte) 4));
    listA.add(new TagByte("tagD", (byte) 2));
    TagList listB = new TagList(TagByte.class, "name");
    listB.add(new TagByte("tagA", (byte) 16));
    listB.add(new TagByte("tagB", (byte) 8));
    listB.add(new TagByte("tagC", (byte) 4));
    listB.add(new TagByte("tagD", (byte) 2));
    assertEquals(TagList.class.getSimpleName(), listA, serialize(listB));

    TagCompound compoundA = TestUtil.getTagCompound();
    TagCompound compoundB = TestUtil.getTagCompound();
    assertEquals(TagCompound.class.getSimpleName(), compoundA, serialize(compoundB));
  }

  @Test
  public void testExt() throws IOException {
    assertEquals(TestUtil.getTagBoolean(), serialize(TestUtil.getTagBoolean()));
    assertEquals(TestUtil.getTagDoubleArray(), serialize(TestUtil.getTagDoubleArray()));
    assertEquals(TestUtil.getTagFloatArray(), serialize(TestUtil.getTagFloatArray()));
    assertEquals(TestUtil.getTagLongArray(), serialize(TestUtil.getTagLongArray()));
    assertEquals(TestUtil.getTagShortArray(), serialize(TestUtil.getTagShortArray()));
    assertEquals(TestUtil.getTagStringArray(), serialize(TestUtil.getTagStringArray()));
    assertEquals(TestUtil.getTagBooleanArray(), serialize(TestUtil.getTagBooleanArray()));
  }

  private Tag serialize(Tag start) throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    AxionOutputStream out = new AxionOutputStream(baos, CharacterEncoderFactory.create(CharacterEncodingType.MODIFIED_UTF_8));
    Axion.getExt().writeTag(start, out);

    ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
    AxionInputStream in = new AxionInputStream(bais, CharacterEncoderFactory.create(CharacterEncodingType.MODIFIED_UTF_8));
    return Axion.getExt().readTag(null, in);
  }

}
