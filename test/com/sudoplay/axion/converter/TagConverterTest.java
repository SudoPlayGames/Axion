package com.sudoplay.axion.converter;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.TestUtil;
import com.sudoplay.axion.ext.tag.TagBoolean;
import com.sudoplay.axion.spec.tag.Tag;
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

  /*
   * Spec Tag Converters
   */

  @Test
  public void testTagByteArrayConverter() {
    byte[] value = new byte[] { 0, 1, 2, 3 };
    TagByteArray tag = new TagByteArray("name", value);
    assertArrayEquals(value, (byte[]) Axion.getDefault().convertToValue(tag));
    assertEquals(tag, Axion.getDefault().convertToTag("name", value));
  }

  @Test
  public void testTagByteConverter() {
    byte value = (byte) 12;
    TagByte tag = new TagByte("name", value);
    assertEquals(value, Axion.getDefault().convertToValue(tag));
    assertEquals(tag, Axion.getDefault().convertToTag("name", value));
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Test
  public void testTagCompoundConverter() {
    TagCompound tag = TestUtil.getTagCompound();
    assertEquals(tag, Axion.getDefault().convertToTag("name", TestUtil.getMap()));

    Map expected = TestUtil.getMap();
    Map actual = Axion.getDefault().convertToValue(tag);

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
  public void testTagDoubleConverter() {
    double value = (double) 12.2568;
    Tag tag = new TagDouble("name", value);
    assertEquals(value, Axion.getDefault().convertToValue(tag));
    assertEquals(tag, Axion.getDefault().convertToTag("name", value));
  }

  @Test
  public void testTagFloatConverter() {
    float value = 12.168f;
    Tag tag = new TagFloat("name", value);
    assertEquals(value, Axion.getDefault().convertToValue(tag));
    assertEquals(tag, Axion.getDefault().convertToTag("name", value));
  }

  @Test
  public void testTagIntArrayConverter() {
    int[] value = new int[] { 0, 1, 2, 3 };
    Tag tag = new TagIntArray("name", value);
    assertArrayEquals(value, (int[]) Axion.getDefault().convertToValue(tag));
    assertEquals(tag, Axion.getDefault().convertToTag("name", value));
  }

  @Test
  public void testTagIntConverter() {
    int value = 42;
    Tag tag = new TagInt("name", value);
    assertEquals(value, Axion.getDefault().convertToValue(tag));
    assertEquals(tag, Axion.getDefault().convertToTag("name", value));
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Test
  public void testTagListConverter() {
    List value = new ArrayList();
    value.add(648);
    value.add(9813);
    value.add(72138);
    TagList tag = new TagList(TagInt.class, "name");
    tag.addInt(648);
    tag.addInt(9813);
    tag.addInt(72138);
    assertEquals(value, Axion.getDefault().convertToValue(tag));
    assertEquals(tag, Axion.getDefault().convertToTag("name", value));
  }

  @Test
  public void testTagLongConverter() {
    long value = 4243563456L;
    Tag tag = new TagLong("name", value);
    assertEquals(value, Axion.getDefault().convertToValue(tag));
    assertEquals(tag, Axion.getDefault().convertToTag("name", value));
  }

  @Test
  public void testTagShortConverter() {
    short value = 4234;
    Tag tag = new TagShort("name", value);
    assertEquals(value, Axion.getDefault().convertToValue(tag));
    assertEquals(tag, Axion.getDefault().convertToTag("name", value));
  }

  @Test
  public void testTagStringConverter() {
    String value = "forty-two";
    Tag tag = new TagString("name", value);
    assertEquals(value, Axion.getDefault().convertToValue(tag));
    assertEquals(tag, Axion.getDefault().convertToTag("name", value));
  }

  /*
   * Extended Tag Converters
   */

  @Test
  public void testTagBooleanConverter() {
    boolean value = true;
    TagBoolean tag = new TagBoolean("name", value);
    assertEquals(value, Axion.getDefault().convertToValue(tag));
    assertEquals(tag, Axion.getDefault().convertToTag("name", value));
  }

}
