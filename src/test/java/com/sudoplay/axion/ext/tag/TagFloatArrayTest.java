package com.sudoplay.axion.ext.tag;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import com.sudoplay.axion.TestUtil.AbstractContainerTagTestClass;

public class TagFloatArrayTest {

  /**
   * Should have two constructors: a constructor to set name and a constructor
   * to set name and value.
   */
  @Test
  public void test_constructors() {
    TagFloatArray tag = new TagFloatArray("newName");
    Assert.assertEquals("newName", tag.getName());
    tag = new TagFloatArray("newName", new float[] { 0.23f, 1.2584f, 2.2398f, 3.1415f });
    Assert.assertEquals("newName", tag.getName());
    Assert.assertTrue(Arrays.equals(new float[] { 0.23f, 1.2584f, 2.2398f, 3.1415f }, tag.get()));
  }

  /**
   * Should set the tag's value.
   */
  @Test
  public void test_set() {
    TagFloatArray tag = new TagFloatArray("newName");
    tag.set(new float[] { 0.23f, 1.2584f, 2.2398f, 3.1415f });
    Assert.assertTrue(Arrays.equals(new float[] { 0.23f, 1.2584f, 2.2398f, 3.1415f }, tag.get()));
  }

  /**
   * Should return a copy of the tag's value.
   */
  @Test
  public void test_get() {
    TagFloatArray tag = new TagFloatArray("newName");
    float[] data = new float[] { 0.23f, 1.2584f, 2.2398f, 3.1415f };
    tag.set(data);
    Assert.assertTrue(data != tag.get());
    Assert.assertTrue(Arrays.equals(data, tag.get()));
  }

  /**
   * Two tags with the same name and value should be equal.
   */
  @Test
  public void test_equals() {
    TagFloatArray tag1 = new TagFloatArray("newName", new float[] { 0.23f, 1.2584f, 2.2398f, 3.1415f });
    TagFloatArray tag2 = new TagFloatArray("newName", new float[] { 0.23f, 1.2584f, 2.2398f, 3.1415f });
    Assert.assertEquals(tag1, tag2);
  }

  /**
   * Should return a string that is a combination of the tag class' simple name,
   * reference name, and value.
   */
  @Test
  public void test_toString() {
    TagFloatArray tag = new TagFloatArray("newName", new float[] { 0.23f, 1.2584f, 2.2398f, 3.1415f });
    Assert.assertEquals("TagFloatArray(\"newName\"): [4 floats]", tag.toString());
  }

  /**
   * Should return a new instance of the tag that is equal but not ==.
   */
  @Test
  public void test_cloneWithoutParent() {
    TagFloatArray tag1 = new TagFloatArray("newName", new float[] { 0.23f, 1.2584f, 2.2398f, 3.1415f });
    TagFloatArray tag2 = tag1.clone();
    Assert.assertEquals(tag1, tag2);
    Assert.assertTrue(tag1 != tag2);
  }

  /**
   * Clone should not clone parent.
   */
  @Test
  public void test_cloneWithParent() {
    TagFloatArray tag1 = new TagFloatArray("newName", new float[] { 0.23f, 1.2584f, 2.2398f, 3.1415f });
    AbstractContainerTagTestClass parent = new AbstractContainerTagTestClass("parent");
    tag1.addTo(parent);
    TagFloatArray clone = tag1.clone();
    Assert.assertEquals(tag1, clone);
    Assert.assertTrue(false == clone.hasParent());
  }

}
