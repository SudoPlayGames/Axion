package com.sudoplay.axion.spec.tag;

import org.junit.Assert;
import org.junit.Test;

import com.sudoplay.axion.TestUtil;
import com.sudoplay.axion.TestUtil.AbstractContainerTagTestClass;

public class TagDoubleTest {

  /**
   * Should have two constructors: a constructor to set name and a constructor
   * to set name and value.
   */
  @Test
  public void test_constructors() {
    TagDouble tag = new TagDouble("newName");
    Assert.assertEquals("newName", tag.getName());
    tag = new TagDouble("newName", 42.056);
    Assert.assertEquals("newName", tag.getName());
    Assert.assertEquals(42.056, tag.get(), TestUtil.DOUBLE_DELTA);
  }

  /**
   * Should set the tag's value.
   */
  @Test
  public void test_set() {
    TagDouble tag = new TagDouble("newName");
    tag.set(42.056);
    Assert.assertEquals(42.056, tag.get(), TestUtil.DOUBLE_DELTA);
  }

  /**
   * Should return tag's value.
   */
  @Test
  public void test_get() {
    TagDouble tag = new TagDouble("newName");
    tag.set(42.056);
    Assert.assertEquals(42.056, tag.get(), TestUtil.DOUBLE_DELTA);
  }

  /**
   * Two tags with the same name and value should be equal.
   */
  @Test
  public void test_equals() {
    TagDouble tag1 = new TagDouble("newName", 42.056);
    TagDouble tag2 = new TagDouble("newName", 42.056);
    Assert.assertEquals(tag1, tag2);
  }

  /**
   * Should return a string that is a combination of the tag class' simple name,
   * reference name, and value.
   */
  @Test
  public void test_toString() {
    TagDouble tag = new TagDouble("newName", 42.056);
    Assert.assertEquals("TagDouble(\"newName\"): 42.056", tag.toString());
  }

  /**
   * Should return a new instance of the tag that is equal but not ==.
   */
  @Test
  public void test_cloneWithoutParent() {
    TagDouble tag1 = new TagDouble("newName", 42.056);
    TagDouble tag2 = tag1.clone();
    Assert.assertEquals(tag1, tag2);
    Assert.assertTrue(tag1 != tag2);
  }

  /**
   * Clone should not clone parent.
   */
  @Test
  public void test_cloneWithParent() {
    TagDouble tag1 = new TagDouble("newName", 42.056);
    AbstractContainerTagTestClass parent = new AbstractContainerTagTestClass("parent");
    tag1.addTo(parent);
    TagDouble clone = tag1.clone();
    Assert.assertEquals(tag1, clone);
    Assert.assertTrue(false == clone.hasParent());
  }

}
