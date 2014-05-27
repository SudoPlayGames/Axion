package com.sudoplay.axion.spec.tag;

import org.junit.Assert;
import org.junit.Test;

import com.sudoplay.axion.TestUtil;
import com.sudoplay.axion.TestUtil.AbstractContainerTagTestClass;

public class TagFloatTest {

  /**
   * Should have two constructors: a constructor to set name and a constructor
   * to set name and value.
   */
  @Test
  public void test_constructors() {
    TagFloat tag = new TagFloat("newName");
    Assert.assertEquals("newName", tag.getName());
    tag = new TagFloat("newName", 42.056f);
    Assert.assertEquals("newName", tag.getName());
    Assert.assertEquals(42.056f, tag.get(), TestUtil.DOUBLE_DELTA);
  }

  /**
   * Should set the tag's value.
   */
  @Test
  public void test_set() {
    TagFloat tag = new TagFloat("newName");
    tag.set(42.056f);
    Assert.assertEquals(42.056f, tag.get(), TestUtil.DOUBLE_DELTA);
  }

  /**
   * Should return tag's value.
   */
  @Test
  public void test_get() {
    TagFloat tag = new TagFloat("newName");
    tag.set(42.056f);
    Assert.assertEquals(42.056f, tag.get(), TestUtil.DOUBLE_DELTA);
  }

  /**
   * Two tags with the same name and value should be equal.
   */
  @Test
  public void test_equals() {
    TagFloat tag1 = new TagFloat("newName", 42.056f);
    TagFloat tag2 = new TagFloat("newName", 42.056f);
    Assert.assertEquals(tag1, tag2);
  }

  /**
   * Should return a string that is a combination of the tag class' simple name,
   * reference name, and value.
   */
  @Test
  public void test_toString() {
    TagFloat tag = new TagFloat("newName", 42.056f);
    Assert.assertEquals("TagFloat(\"newName\"): 42.056", tag.toString());
  }

  /**
   * Should return a new instance of the tag that is equal but not ==.
   */
  @Test
  public void test_cloneWithoutParent() {
    TagFloat tag1 = new TagFloat("newName", 42.056f);
    TagFloat tag2 = tag1.clone();
    Assert.assertEquals(tag1, tag2);
    Assert.assertTrue(tag1 != tag2);
  }

  /**
   * Clone should not clone parent.
   */
  @Test
  public void test_cloneWithParent() {
    TagFloat tag1 = new TagFloat("newName", 42.056f);
    AbstractContainerTagTestClass parent = new AbstractContainerTagTestClass("parent");
    tag1.addTo(parent);
    TagFloat clone = tag1.clone();
    Assert.assertEquals(tag1, clone);
    Assert.assertTrue(false == clone.hasParent());
  }

}
