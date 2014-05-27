package com.sudoplay.axion.ext.tag;

import org.junit.Assert;
import org.junit.Test;

import com.sudoplay.axion.TestUtil.AbstractContainerTagTestClass;

public class TagBooleanTest {

  /**
   * Should have two constructors: a constructor to set name and a constructor
   * to set name and value.
   */
  @Test
  public void test_constructors() {
    TagBoolean tag = new TagBoolean("newName");
    Assert.assertEquals("newName", tag.getName());
    tag = new TagBoolean("newName", true);
    Assert.assertEquals("newName", tag.getName());
    Assert.assertEquals(true, tag.get());
  }

  /**
   * Should set the tag's value.
   */
  @Test
  public void test_set() {
    TagBoolean tag = new TagBoolean("newName");
    tag.set(true);
    Assert.assertEquals(true, tag.get());
  }

  /**
   * Should return tag's value.
   */
  @Test
  public void test_get() {
    TagBoolean tag = new TagBoolean("newName");
    tag.set(true);
    Assert.assertEquals(true, tag.get());
  }

  /**
   * Two tags with the same name and value should be equal.
   */
  @Test
  public void test_equals() {
    TagBoolean tag1 = new TagBoolean("newName", true);
    TagBoolean tag2 = new TagBoolean("newName", true);
    Assert.assertEquals(tag1, tag2);
  }

  /**
   * Should return a string that is a combination of the tag class' simple name,
   * reference name, and value.
   */
  @Test
  public void test_toString() {
    TagBoolean tag = new TagBoolean("newName", true);
    Assert.assertEquals("TagBoolean(\"newName\"): true", tag.toString());
  }

  /**
   * Should return a new instance of the tag that is equal but not ==.
   */
  @Test
  public void test_cloneWithoutParent() {
    TagBoolean tag1 = new TagBoolean("newName", true);
    TagBoolean tag2 = tag1.clone();
    Assert.assertEquals(tag1, tag2);
    Assert.assertTrue(tag1 != tag2);
  }

  /**
   * Clone should not clone parent.
   */
  @Test
  public void test_cloneWithParent() {
    TagBoolean tag1 = new TagBoolean("newName", true);
    AbstractContainerTagTestClass parent = new AbstractContainerTagTestClass("parent");
    tag1.addTo(parent);
    TagBoolean clone = tag1.clone();
    Assert.assertEquals(tag1, clone);
    Assert.assertTrue(false == clone.hasParent());
  }

}
