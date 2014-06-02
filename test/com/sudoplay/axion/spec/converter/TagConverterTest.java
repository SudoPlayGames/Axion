package com.sudoplay.axion.spec.converter;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Assert;
import org.junit.Test;

import com.sudoplay.axion.TestUtil;
import com.sudoplay.axion.registry.TagAdapter;
import com.sudoplay.axion.registry.TagConverter;
import com.sudoplay.axion.registry.TagRegistry;
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

  private static final TagRegistry REGISTRY = new TagRegistry() {
    {
      registerBaseTagAdapter(TagAdapter.Spec.BASE);
      register(1, TagByte.class, Byte.class, TagAdapter.Spec.BYTE, TagConverter.Spec.BYTE);
      register(2, TagShort.class, Short.class, TagAdapter.Spec.SHORT, TagConverter.Spec.SHORT);
      register(3, TagInt.class, Integer.class, TagAdapter.Spec.INT, TagConverter.Spec.INT);
      register(4, TagLong.class, Long.class, TagAdapter.Spec.LONG, TagConverter.Spec.LONG);
      register(5, TagFloat.class, Float.class, TagAdapter.Spec.FLOAT, TagConverter.Spec.FLOAT);
      register(6, TagDouble.class, Double.class, TagAdapter.Spec.DOUBLE, TagConverter.Spec.DOUBLE);
      register(7, TagByteArray.class, byte[].class, TagAdapter.Spec.BYTE_ARRAY, TagConverter.Spec.BYTE_ARRAY);
      register(8, TagString.class, String.class, TagAdapter.Spec.STRING, TagConverter.Spec.STRING);
      register(9, TagList.class, List.class, TagAdapter.Spec.LIST, TagConverter.Spec.LIST);
      register(10, TagCompound.class, Map.class, TagAdapter.Spec.COMPOUND, TagConverter.Spec.COMPOUND);
      register(11, TagIntArray.class, int[].class, TagAdapter.Spec.INT_ARRAY, TagConverter.Spec.INT_ARRAY);
    }
  };

  @Test
  public void test_TagByteArrayConverter() {
    byte[] value = new byte[] { 0, 1, 2, 3 };
    TagByteArray tag = new TagByteArray("name", value);
    Assert.assertArrayEquals(value, (byte[]) TagConverter.Spec.BYTE_ARRAY.convert(tag));
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
    assertEquals(tag, REGISTRY.getConverterForTag(TagCompound.class).convert("name", TestUtil.getMap()));

    Map expected = TestUtil.getMap();
    Map actual = (Map) REGISTRY.getConverterForTag(TagCompound.class).convert(tag);

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
    double value = (double) 12.2568;
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
    assertArrayEquals(value, (int[]) TagConverter.Spec.INT_ARRAY.convert(tag));
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
    assertEquals(value, REGISTRY.getConverterForTag(TagList.class).convert(tag));
    assertEquals(tag, REGISTRY.getConverterForTag(TagList.class).convert("name", value));
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
