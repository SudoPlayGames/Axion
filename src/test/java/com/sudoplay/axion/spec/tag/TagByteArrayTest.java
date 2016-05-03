package com.sudoplay.axion.spec.tag;

import org.junit.Assert;
import org.junit.Test;

import com.sudoplay.axion.TestUtil.AbstractContainerTagTestClass;

public class TagByteArrayTest {

  /**
   * Should have two constructors: a constructor to set name and a constructor
   * to set name and value.
   */
  @Test
  public void test_constructors() {
    TagByteArray tag = new TagByteArray("newName");
    Assert.assertEquals("newName", tag.getName());
    tag = new TagByteArray("newName", new byte[] { 0, 1, 2, 3 });
    Assert.assertEquals("newName", tag.getName());
    Assert.assertArrayEquals(new byte[] { 0, 1, 2, 3 }, tag.get());
  }

  /**
   * Should set the tag's value.
   */
  @Test
  public void test_set() {
    TagByteArray tag = new TagByteArray("newName");
    tag.set(new byte[] { 0, 1, 2, 3 });
    Assert.assertArrayEquals(new byte[] { 0, 1, 2, 3 }, tag.get());
  }

  /**
   * Should return a copy of the tag's value.
   */
  @Test
  public void test_get() {
    TagByteArray tag = new TagByteArray("newName");
    byte[] data = new byte[] { 0, 1, 2, 3 };
    tag.set(data);
    Assert.assertTrue(data != tag.get());
    Assert.assertArrayEquals(data, tag.get());
  }

  /**
   * Two tags with the same name and value should be equal.
   */
  @Test
  public void test_equals() {
    TagByteArray tag1 = new TagByteArray("newName", new byte[] { 0, 1, 2, 3 });
    TagByteArray tag2 = new TagByteArray("newName", new byte[] { 0, 1, 2, 3 });
    Assert.assertEquals(tag1, tag2);
  }

  /**
   * Should return a string that is a combination of the tag class' simple name,
   * reference name, and value.
   */
  @Test
  public void test_toString() {
    TagByteArray tag = new TagByteArray("newName", new byte[] { 0, 1, 2, 3 });
    Assert.assertEquals("TagByteArray(\"newName\"): [4 bytes]", tag.toString());
  }

  /**
   * Should return a new instance of the tag that is equal but not ==.
   */
  @Test
  public void test_cloneWithoutParent() {
    TagByteArray tag1 = new TagByteArray("newName", new byte[] { 0, 1, 2, 3 });
    TagByteArray tag2 = tag1.clone();
    Assert.assertEquals(tag1, tag2);
    Assert.assertTrue(tag1 != tag2);
  }

  /**
   * Clone should not clone parent.
   */
  @Test
  public void test_cloneWithParent() {
    TagByteArray tag1 = new TagByteArray("newName", new byte[] { 0, 1, 2, 3 });
    AbstractContainerTagTestClass parent = new AbstractContainerTagTestClass("parent");
    tag1.addTo(parent);
    TagByteArray clone = tag1.clone();
    Assert.assertEquals(tag1, clone);
    Assert.assertTrue(false == clone.hasParent());
  }

}
