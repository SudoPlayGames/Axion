package com.sudoplay.axion.spec.converter;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.TestUtil;
import com.sudoplay.axion.registry.TypeConverter;
import com.sudoplay.axion.spec.tag.*;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class TypeConverterTest {

  private static Axion axion;

  @BeforeClass
  public static void before() {
    axion = Axion.getExtInstance();
  }

  @Test
  public void test_TagByteArrayConverter() {
    byte[] value = new byte[]{0, 1, 2, 3};
    TagByteArray tag = new TagByteArray("name", value);
    Assert.assertArrayEquals(value, TypeConverter.Spec.BYTE_ARRAY.convert(tag));
    Assert.assertEquals(tag, TypeConverter.Spec.BYTE_ARRAY.convert("name", value));
  }

  @Test
  public void test_TagByteConverter() {
    byte value = (byte) 42;
    TagByte tag = new TagByte("name", value);
    Assert.assertEquals(value, (byte) TypeConverter.Spec.BYTE.convert(tag));
    Assert.assertEquals(tag, TypeConverter.Spec.BYTE.convert("name", value));
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  @Test
  public void test_TagCompoundConverter() {
    TagCompound tag = TestUtil.getTagCompound();

    /*
     The integration of mappers and converters, and the addition of the
     MapTypeConverter, has created conflict with map conversion and it
     has been removed for now.
     */
    // should throw UnsupportedOperationException if trying to convert a map
    try {
      TypeConverter<?, ? super Map> converter = axion.getConverter(TagCompound.class);
      converter.convert("name", TestUtil.getMap());
      fail();
    } catch (UnsupportedOperationException e) {
      // expected
    }

    Map expected = TestUtil.getMap();
    Map actual = (Map) axion.getConverter(TagCompound.class).convert(tag);

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
    assertEquals(value, TypeConverter.Spec.DOUBLE.convert(tag), TestUtil.DOUBLE_DELTA);
    assertEquals(tag, TypeConverter.Spec.DOUBLE.convert("name", value));
  }

  @Test
  public void test_TagFloatConverter() {
    float value = 12.168f;
    TagFloat tag = new TagFloat("name", value);
    assertEquals(value, TypeConverter.Spec.FLOAT.convert(tag), TestUtil.DOUBLE_DELTA);
    assertEquals(tag, TypeConverter.Spec.FLOAT.convert("name", value));
  }

  @Test
  public void test_TagIntArrayConverter() {
    int[] value = new int[]{0, 1, 2, 3};
    TagIntArray tag = new TagIntArray("name", value);
    assertArrayEquals(value, TypeConverter.Spec.INT_ARRAY.convert(tag));
    assertEquals(tag, TypeConverter.Spec.INT_ARRAY.convert("name", value));
  }

  @Test
  public void test_TagIntConverter() {
    int value = 42;
    TagInt tag = new TagInt("name", value);
    assertEquals(value, (int) TypeConverter.Spec.INT.convert(tag));
    assertEquals(tag, TypeConverter.Spec.INT.convert("name", value));
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
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
    assertEquals(value, axion.getConverter(TagList.class).convert(tag));
    assertEquals(tag, axion.getConverter(TagList.class).convert("name", value));
  }

  @Test
  public void test_TagLongConverter() {
    long value = 4243563456L;
    TagLong tag = new TagLong("name", value);
    assertEquals(value, (long) TypeConverter.Spec.LONG.convert(tag));
    assertEquals(tag, TypeConverter.Spec.LONG.convert("name", value));
  }

  @Test
  public void test_TagShortConverter() {
    short value = 4234;
    TagShort tag = new TagShort("name", value);
    assertEquals(value, (short) TypeConverter.Spec.SHORT.convert(tag));
    assertEquals(tag, TypeConverter.Spec.SHORT.convert("name", value));
  }

  @Test
  public void test_TagStringConverter() {
    String value = "forty-two";
    TagString tag = new TagString("name", value);
    assertEquals(value, TypeConverter.Spec.STRING.convert(tag));
    assertEquals(tag, TypeConverter.Spec.STRING.convert("name", value));
  }

}
