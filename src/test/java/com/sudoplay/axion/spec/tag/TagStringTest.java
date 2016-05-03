package com.sudoplay.axion.spec.tag;

import org.junit.Assert;
import org.junit.Test;

import com.sudoplay.axion.TestUtil.AbstractContainerTagTestClass;

public class TagStringTest {

  /**
   * Should have two constructors: a constructor to set name and a constructor
   * to set name and value.
   */
  @Test
  public void test_constructors() {
    TagString tag = new TagString("newName");
    Assert.assertEquals("newName", tag.getName());
    tag = new TagString("newName", "Hello World!");
    Assert.assertEquals("newName", tag.getName());
    Assert.assertEquals("Hello World!", tag.get());
  }

  /**
   * Should set the tag's value.
   */
  @Test
  public void test_set() {
    TagString tag = new TagString("newName");
    tag.set("Hello World!");
    Assert.assertEquals("Hello World!", tag.get());
  }

  /**
   * Should return tag's value.
   */
  @Test
  public void test_get() {
    TagString tag = new TagString("newName");
    tag.set("Hello World!");
    Assert.assertEquals("Hello World!", tag.get());
  }

  /**
   * Two tags with the same name and value should be equal.
   */
  @Test
  public void test_equals() {
    TagString tag1 = new TagString("newName", "Hello World!");
    TagString tag2 = new TagString("newName", "Hello World!");
    Assert.assertEquals(tag1, tag2);
  }

  /**
   * Should return a string that is a combination of the tag class' simple name,
   * reference name, and value.
   */
  @Test
  public void test_toString() {
    TagString tag = new TagString("newName", "Hello World!");
    Assert.assertEquals("TagString(\"newName\"): Hello World!", tag.toString());
  }

  /**
   * Should return a new instance of the tag that is equal but not ==.
   */
  @Test
  public void test_cloneWithoutParent() {
    TagString tag1 = new TagString("newName", "Hello World!");
    TagString tag2 = tag1.clone();
    Assert.assertEquals(tag1, tag2);
    Assert.assertTrue(tag1 != tag2);
  }

  /**
   * Clone should not clone parent.
   */
  @Test
  public void test_cloneWithParent() {
    TagString tag1 = new TagString("newName", "Hello World!");
    AbstractContainerTagTestClass parent = new AbstractContainerTagTestClass("parent");
    tag1.addTo(parent);
    TagString clone = tag1.clone();
    Assert.assertEquals(tag1, clone);
    Assert.assertTrue(false == clone.hasParent());
  }

}
