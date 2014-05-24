package com.sudoplay.axion.tag;

import static org.junit.Assert.*;

import org.junit.Test;

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
}
