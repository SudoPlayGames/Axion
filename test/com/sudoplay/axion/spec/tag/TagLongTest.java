package com.sudoplay.axion.spec.tag;

import org.junit.Assert;
import org.junit.Test;

import com.sudoplay.axion.TestUtil.AbstractContainerTagTestClass;

public class TagLongTest {

  /**
   * Should have two constructors: a constructor to set name and a constructor
   * to set name and value.
   */
  @Test
  public void test_constructors() {
    TagLong tag = new TagLong("newName");
    Assert.assertEquals("newName", tag.getName());
    tag = new TagLong("newName", 63457564564334567L);
    Assert.assertEquals("newName", tag.getName());
    Assert.assertEquals(63457564564334567L, tag.get());
  }

  /**
   * Should set the tag's value.
   */
  @Test
  public void test_set() {
    TagLong tag = new TagLong("newName");
    tag.set(63457564564334567L);
    Assert.assertEquals(63457564564334567L, tag.get());
  }

  /**
   * Should return tag's value.
   */
  @Test
  public void test_get() {
    TagLong tag = new TagLong("newName");
    tag.set(63457564564334567L);
    Assert.assertEquals(63457564564334567L, tag.get());
  }

  /**
   * Two tags with the same name and value should be equal.
   */
  @Test
  public void test_equals() {
    TagLong tag1 = new TagLong("newName", 63457564564334567L);
    TagLong tag2 = new TagLong("newName", 63457564564334567L);
    Assert.assertEquals(tag1, tag2);
  }

  /**
   * Should return a string that is a combination of the tag class' simple name,
   * reference name, and value.
   */
  @Test
  public void test_toString() {
    TagLong tag = new TagLong("newName", 63457564564334567L);
    Assert.assertEquals("TagLong(\"newName\"): 63457564564334567", tag.toString());
  }

  /**
   * Should return a new instance of the tag that is equal but not ==.
   */
  @Test
  public void test_cloneWithoutParent() {
    TagLong tag1 = new TagLong("newName", 63457564564334567L);
    TagLong tag2 = tag1.clone();
    Assert.assertEquals(tag1, tag2);
    Assert.assertTrue(tag1 != tag2);
  }

  /**
   * Clone should not clone parent.
   */
  @Test
  public void test_cloneWithParent() {
    TagLong tag1 = new TagLong("newName", 63457564564334567L);
    AbstractContainerTagTestClass parent = new AbstractContainerTagTestClass("parent");
    tag1.addTo(parent);
    TagLong clone = tag1.clone();
    Assert.assertEquals(tag1, clone);
    Assert.assertTrue(false == clone.hasParent());
  }

}
