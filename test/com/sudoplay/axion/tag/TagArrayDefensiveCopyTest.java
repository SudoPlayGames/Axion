package com.sudoplay.axion.tag;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

import com.sudoplay.axion.ext.tag.TagBooleanArray;
import com.sudoplay.axion.ext.tag.TagDoubleArray;
import com.sudoplay.axion.ext.tag.TagFloatArray;
import com.sudoplay.axion.ext.tag.TagLongArray;
import com.sudoplay.axion.ext.tag.TagShortArray;
import com.sudoplay.axion.ext.tag.TagStringArray;
import com.sudoplay.axion.spec.tag.TagByteArray;
import com.sudoplay.axion.spec.tag.TagIntArray;

/**
 * Array tags should create a copy of the array passed in the constructor,
 * create a copy of the array passed in the set() method, and return a copy of
 * their contents.
 */
public class TagArrayDefensiveCopyTest {

  @Test
  public void testIntArray() {
    int[] array = new int[] { 0, 1, 2, 3 };
    TagIntArray tag = new TagIntArray("name", array);
    assertArrayEquals(array, tag.get());
    assertTrue(array != tag.get());
    array[0] = 5;
    assertTrue(array[0] != tag.get()[0]);
    tag.set(array);
    array[0] = 0;
    assertTrue(array[0] != tag.get()[0]);
  }

  @Test
  public void testByteArray() {
    byte[] array = new byte[] { 0, 1, 2, 3 };
    TagByteArray tag = new TagByteArray("name", array);
    assertArrayEquals(array, tag.get());
    assertTrue(array != tag.get());
    array[0] = 5;
    assertTrue(array[0] != tag.get()[0]);
    tag.set(array);
    array[0] = 0;
    assertTrue(array[0] != tag.get()[0]);
  }

  @Test
  public void testDoubleArray() {
    double[] array = new double[] { 0.2, 1.56, 2.3689, 323.21457 };
    TagDoubleArray tag = new TagDoubleArray("name", array);
    assertTrue(Arrays.equals(array, tag.get()));
    assertTrue(array != tag.get());
    array[0] = 5.23;
    assertTrue(array[0] != tag.get()[0]);
    tag.set(array);
    array[0] = 0.2;
    assertTrue(array[0] != tag.get()[0]);
  }

  @Test
  public void testFloatArray() {
    float[] array = new float[] { 0.2f, 1.56f, 2.3689f, 323.21457f };
    TagFloatArray tag = new TagFloatArray("name", array);
    assertTrue(Arrays.equals(array, tag.get()));
    assertTrue(array != tag.get());
    array[0] = 5.23f;
    assertTrue(array[0] != tag.get()[0]);
    tag.set(array);
    array[0] = 0.2f;
    assertTrue(array[0] != tag.get()[0]);
  }

  @Test
  public void testLongArray() {
    long[] array = new long[] { 153245684532L, 78457845789365L, 910210345875L, 54120121230215L };
    TagLongArray tag = new TagLongArray("name", array);
    assertTrue(Arrays.equals(array, tag.get()));
    assertTrue(array != tag.get());
    array[0] = 12473215652432021L;
    assertTrue(array[0] != tag.get()[0]);
    tag.set(array);
    array[0] = 153245684532L;
    assertTrue(array[0] != tag.get()[0]);
  }

  @Test
  public void testShortArray() {
    short[] array = new short[] { 1532, 7845, 9102, 5412 };
    TagShortArray tag = new TagShortArray("name", array);
    assertTrue(Arrays.equals(array, tag.get()));
    assertTrue(array != tag.get());
    array[0] = 1247;
    assertTrue(array[0] != tag.get()[0]);
    tag.set(array);
    array[0] = 1532;
    assertTrue(array[0] != tag.get()[0]);
  }

  @Test
  public void testStringArray() {
    String[] array = new String[] { "1532", "7845", "9102", "5412" };
    TagStringArray tag = new TagStringArray("name", array);
    assertTrue(Arrays.equals(array, tag.get()));
    assertTrue(array != tag.get());
    array[0] = "1247";
    assertTrue(array[0] != tag.get()[0]);
    tag.set(array);
    array[0] = "1532";
    assertTrue(array[0] != tag.get()[0]);
  }

  @Test
  public void testBooleanArray() {
    boolean[] array = new boolean[] { true, false, false, true };
    TagBooleanArray tag = new TagBooleanArray("name", array);
    assertTrue(Arrays.equals(array, tag.get()));
    assertTrue(array != tag.get());
    array[0] = false;
    assertTrue(array[0] != tag.get()[0]);
    tag.set(array);
    array[0] = true;
    assertTrue(array[0] != tag.get()[0]);
  }

}
