package com.sudoplay.axion.spec.converter;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.sudoplay.axion.Axion;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.sudoplay.axion.TestUtil;
import com.sudoplay.axion.registry.TagAdapter;
import com.sudoplay.axion.registry.TagConverter;
import com.sudoplay.axion.registry.TagAdapterRegistry;
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

public class TagConverterTest {

  private static Axion axion;

  @BeforeClass
  public static void before() {
    axion = Axion.getExtInstance();
  }

  @Test
  public void test_TagByteArrayConverter() {
    byte[] value = new byte[] { 0, 1, 2, 3 };
    TagByteArray tag = new TagByteArray("name", value);
    Assert.assertArrayEquals(value, TagConverter.Spec.BYTE_ARRAY.convert(tag));
    Assert.assertEquals(tag, TagConverter.Spec.BYTE_ARRAY.convert("name", value));
  }

  @Test
  public void test_TagByteConverter() {
    byte value = (byte) 42;
    TagByte tag = new TagByte("name", value);
    Assert.assertEquals(value, (byte) TagConverter.Spec.BYTE.convert(tag));
    Assert.assertEquals(tag, TagConverter.Spec.BYTE.convert("name", value));
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Test
  public void test_TagCompoundConverter() {
    TagCompound tag = TestUtil.getTagCompound();
    assertEquals(tag, axion.getConverterForTag(TagCompound.class).convert("name", TestUtil.getMap()));

    Map expected = TestUtil.getMap();
    Map actual = (Map) axion.getConverterForTag(TagCompound.class).convert(tag);

    assertEquals(expected.size(), actual.size());

    Iterator<Entry<String, Object>> it = expected.entrySet().iterator();
    while (it.hasNext()) {
      Entry<String, Object> entry = it.next();
      Object one = entry.getValue();
      Object two = actual.get(entry.getKey());
      if (one.getClass().isArray()) {
        // TODO test arrays
      } else {
        assertEquals(one, two);
      }
    }
  }

  @Test
  public void test_TagDoubleConverter() {
    double value = 12.2568;
    TagDouble tag = new TagDouble("name", value);
    assertEquals(value, TagConverter.Spec.DOUBLE.convert(tag), TestUtil.DOUBLE_DELTA);
    assertEquals(tag, TagConverter.Spec.DOUBLE.convert("name", value));
  }

  @Test
  public void test_TagFloatConverter() {
    float value = 12.168f;
    TagFloat tag = new TagFloat("name", value);
    assertEquals(value, TagConverter.Spec.FLOAT.convert(tag), TestUtil.DOUBLE_DELTA);
    assertEquals(tag, TagConverter.Spec.FLOAT.convert("name", value));
  }

  @Test
  public void test_TagIntArrayConverter() {
    int[] value = new int[] { 0, 1, 2, 3 };
    TagIntArray tag = new TagIntArray("name", value);
    assertArrayEquals(value, TagConverter.Spec.INT_ARRAY.convert(tag));
    assertEquals(tag, TagConverter.Spec.INT_ARRAY.convert("name", value));
  }

  @Test
  public void test_TagIntConverter() {
    int value = 42;
    TagInt tag = new TagInt("name", value);
    assertEquals(value, (int) TagConverter.Spec.INT.convert(tag));
    assertEquals(tag, TagConverter.Spec.INT.convert("name", value));
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Test
  public void test_TagListConverter() {
    List value = new ArrayList();
    value.add(648);
    value.add(9813);
    value.add(72138);
    TagList tag = new TagList(TagInt.class, "name");
    tag.add(new TagInt("", 648));
    tag.add(new TagInt("", 9813));
    tag.add(new TagInt("", 72138));
    assertEquals(value, axion.getConverterForTag(TagList.class).convert(tag));
    assertEquals(tag, axion.getConverterForTag(TagList.class).convert("name", value));
  }

  @Test
  public void test_TagLongConverter() {
    long value = 4243563456L;
    TagLong tag = new TagLong("name", value);
    assertEquals(value, (long) TagConverter.Spec.LONG.convert(tag));
    assertEquals(tag, TagConverter.Spec.LONG.convert("name", value));
  }

  @Test
  public void test_TagShortConverter() {
    short value = 4234;
    TagShort tag = new TagShort("name", value);
    assertEquals(value, (short) TagConverter.Spec.SHORT.convert(tag));
    assertEquals(tag, TagConverter.Spec.SHORT.convert("name", value));
  }

  @Test
  public void test_TagStringConverter() {
    String value = "forty-two";
    TagString tag = new TagString("name", value);
    assertEquals(value, TagConverter.Spec.STRING.convert(tag));
    assertEquals(tag, TagConverter.Spec.STRING.convert("name", value));
  }

}
