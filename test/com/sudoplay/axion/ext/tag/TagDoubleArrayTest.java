package com.sudoplay.axion.ext.tag;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import com.sudoplay.axion.TestUtil.AbstractContainerTagTestClass;

public class TagDoubleArrayTest {

  /**
   * Should have two constructors: a constructor to set name and a constructor
   * to set name and value.
   */
  @Test
  public void test_constructors() {
    TagDoubleArray tag = new TagDoubleArray("newName");
    Assert.assertEquals("newName", tag.getName());
    tag = new TagDoubleArray("newName", new double[] { 0.23, 1.2584, 2.2398, 3.1415 });
    Assert.assertEquals("newName", tag.getName());
    Assert.assertTrue(Arrays.equals(new double[] { 0.23, 1.2584, 2.2398, 3.1415 }, tag.get()));
  }

  /**
   * Should set the tag's value.
   */
  @Test
  public void test_set() {
    TagDoubleArray tag = new TagDoubleArray("newName");
    tag.set(new double[] { 0.23, 1.2584, 2.2398, 3.1415 });
    Assert.assertTrue(Arrays.equals(new double[] { 0.23, 1.2584, 2.2398, 3.1415 }, tag.get()));
  }

  /**
   * Should return a copy of the tag's value.
   */
  @Test
  public void test_get() {
    TagDoubleArray tag = new TagDoubleArray("newName");
    double[] data = new double[] { 0.23, 1.2584, 2.2398, 3.1415 };
    tag.set(data);
    Assert.assertTrue(data != tag.get());
    Assert.assertTrue(Arrays.equals(data, tag.get()));
  }

  /**
   * Two tags with the same name and value should be equal.
   */
  @Test
  public void test_equals() {
    TagDoubleArray tag1 = new TagDoubleArray("newName", new double[] { 0.23, 1.2584, 2.2398, 3.1415 });
    TagDoubleArray tag2 = new TagDoubleArray("newName", new double[] { 0.23, 1.2584, 2.2398, 3.1415 });
    Assert.assertEquals(tag1, tag2);
  }

  /**
   * Should return a string that is a combination of the tag class' simple name,
   * reference name, and value.
   */
  @Test
  public void test_toString() {
    TagDoubleArray tag = new TagDoubleArray("newName", new double[] { 0.23, 1.2584, 2.2398, 3.1415 });
    Assert.assertEquals("TagDoubleArray(\"newName\"): [4 doubles]", tag.toString());
  }

  /**
   * Should return a new instance of the tag that is equal but not ==.
   */
  @Test
  public void test_cloneWithoutParent() {
    TagDoubleArray tag1 = new TagDoubleArray("newName", new double[] { 0.23, 1.2584, 2.2398, 3.1415 });
    TagDoubleArray tag2 = tag1.clone();
    Assert.assertEquals(tag1, tag2);
    Assert.assertTrue(tag1 != tag2);
  }

  /**
   * Clone should not clone parent.
   */
  @Test
  public void test_cloneWithParent() {
    TagDoubleArray tag1 = new TagDoubleArray("newName", new double[] { 0.23, 1.2584, 2.2398, 3.1415 });
    AbstractContainerTagTestClass parent = new AbstractContainerTagTestClass("parent");
    tag1.addTo(parent);
    TagDoubleArray clone = tag1.clone();
    Assert.assertEquals(tag1, clone);
    Assert.assertTrue(false == clone.hasParent());
  }

}
