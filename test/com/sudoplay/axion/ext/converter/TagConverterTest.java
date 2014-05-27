package com.sudoplay.axion.ext.converter;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import com.sudoplay.axion.TestUtil;
import com.sudoplay.axion.adapter.TagConverter;
import com.sudoplay.axion.ext.tag.TagBoolean;
import com.sudoplay.axion.ext.tag.TagBooleanArray;
import com.sudoplay.axion.ext.tag.TagDoubleArray;
import com.sudoplay.axion.ext.tag.TagFloatArray;
import com.sudoplay.axion.ext.tag.TagLongArray;
import com.sudoplay.axion.ext.tag.TagShortArray;
import com.sudoplay.axion.ext.tag.TagStringArray;

public class TagConverterTest {

  @Test
  public void test_TagBooleanArrayConverter() {
    boolean[] value = new boolean[] { true, false, false, true };
    TagBooleanArray tag = new TagBooleanArray("name", value);
    Assert.assertTrue(Arrays.equals(value, (boolean[]) TagConverter.Ext.BOOLEAN_ARRAY.convert(tag)));
    Assert.assertEquals(tag, TagConverter.Ext.BOOLEAN_ARRAY.convert("name", value));
  }

  @Test
  public void test_TagBooleanConverter() {
    boolean value = true;
    TagBoolean tag = new TagBoolean("name", value);
    assertEquals(value, TagConverter.Ext.BOOLEAN.convert(tag));
    assertEquals(tag, TagConverter.Ext.BOOLEAN.convert("name", true));
  }

  @Test
  public void test_TagDoubleArrayConverter() {
    double[] value = new double[] { 0.235, 1.1894, 2.3218, 3.1415 };
    TagDoubleArray tag = new TagDoubleArray("name", value);
    Assert.assertArrayEquals(value, (double[]) TagConverter.Ext.DOUBLE_ARRAY.convert(tag), TestUtil.DOUBLE_DELTA);
    Assert.assertEquals(tag, TagConverter.Ext.DOUBLE_ARRAY.convert("name", value));
  }

  @Test
  public void test_TagFloatArrayConverter() {
    float[] value = new float[] { 0.235f, 1.1894f, 2.3218f, 3.1415f };
    TagFloatArray tag = new TagFloatArray("name", value);
    Assert.assertArrayEquals(value, (float[]) TagConverter.Ext.FLOAT_ARRAY.convert(tag), TestUtil.FLOAT_DELTA);
    Assert.assertEquals(tag, TagConverter.Ext.FLOAT_ARRAY.convert("name", value));
  }

  @Test
  public void test_TagLongArrayConverter() {
    long[] value = new long[] { 48521388956L, 874789213511L, 78471220214L, 98569913654L };
    TagLongArray tag = new TagLongArray("name", value);
    Assert.assertArrayEquals(value, (long[]) TagConverter.Ext.LONG_ARRAY.convert(tag));
    Assert.assertEquals(tag, TagConverter.Ext.LONG_ARRAY.convert("name", value));
  }

  @Test
  public void test_TagShortArrayConverter() {
    short[] value = new short[] { 128, 485, 10, 25641 };
    TagShortArray tag = new TagShortArray("name", value);
    Assert.assertArrayEquals(value, (short[]) TagConverter.Ext.SHORT_ARRAY.convert(tag));
    Assert.assertEquals(tag, TagConverter.Ext.SHORT_ARRAY.convert("name", value));
  }

  @Test
  public void test_TagStringArrayConverter() {
    String[] value = new String[] { "128", "485", "10", "25641" };
    TagStringArray tag = new TagStringArray("name", value);
    Assert.assertArrayEquals(value, (String[]) TagConverter.Ext.STRING_ARRAY.convert(tag));
    Assert.assertEquals(tag, TagConverter.Ext.STRING_ARRAY.convert("name", value));
  }

}
