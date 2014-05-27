package com.sudoplay.axion.spec.tag;

import org.junit.Assert;
import org.junit.Test;

import com.sudoplay.axion.TestUtil.AbstractContainerTagTestClass;

public class TagIntTest {

  /**
   * Should have two constructors: a constructor to set name and a constructor
   * to set name and value.
   */
  @Test
  public void test_constructors() {
    TagInt tag = new TagInt("newName");
    Assert.assertEquals("newName", tag.getName());
    tag = new TagInt("newName", 42);
    Assert.assertEquals("newName", tag.getName());
    Assert.assertEquals(42, tag.get());
  }

  /**
   * Should set the tag's value.
   */
  @Test
  public void test_set() {
    TagInt tag = new TagInt("newName");
    tag.set(42);
    Assert.assertEquals(42, tag.get());
  }

  /**
   * Should return tag's value.
   */
  @Test
  public void test_get() {
    TagInt tag = new TagInt("newName");
    tag.set(42);
    Assert.assertEquals(42, tag.get());
  }

  /**
   * Two tags with the same name and value should be equal.
   */
  @Test
  public void test_equals() {
    TagInt tag1 = new TagInt("newName", 42);
    TagInt tag2 = new TagInt("newName", 42);
    Assert.assertEquals(tag1, tag2);
  }

  /**
   * Should return a string that is a combination of the tag class' simple name,
   * reference name, and value.
   */
  @Test
  public void test_toString() {
    TagInt tag = new TagInt("newName", 42);
    Assert.assertEquals("TagInt(\"newName\"): 42", tag.toString());
  }

  /**
   * Should return a new instance of the tag that is equal but not ==.
   */
  @Test
  public void test_cloneWithoutParent() {
    TagInt tag1 = new TagInt("newName", 42);
    TagInt tag2 = tag1.clone();
    Assert.assertEquals(tag1, tag2);
    Assert.assertTrue(tag1 != tag2);
  }

  /**
   * Clone should not clone parent.
   */
  @Test
  public void test_cloneWithParent() {
    TagInt tag1 = new TagInt("newName", 42);
    AbstractContainerTagTestClass parent = new AbstractContainerTagTestClass("parent");
    tag1.addTo(parent);
    TagInt clone = tag1.clone();
    Assert.assertEquals(tag1, clone);
    Assert.assertTrue(false == clone.hasParent());
  }

}
