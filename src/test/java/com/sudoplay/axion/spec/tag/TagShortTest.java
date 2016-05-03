package com.sudoplay.axion.spec.tag;

import org.junit.Assert;
import org.junit.Test;

import com.sudoplay.axion.TestUtil.AbstractContainerTagTestClass;

public class TagShortTest {

  /**
   * Should have two constructors: a constructor to set name and a constructor
   * to set name and value.
   */
  @Test
  public void test_constructors() {
    TagShort tag = new TagShort("newName");
    Assert.assertEquals("newName", tag.getName());
    tag = new TagShort("newName", (short) 32128);
    Assert.assertEquals("newName", tag.getName());
    Assert.assertEquals((short) 32128, tag.get());
  }

  /**
   * Should set the tag's value.
   */
  @Test
  public void test_set() {
    TagShort tag = new TagShort("newName");
    tag.set((short) 32128);
    Assert.assertEquals((short) 32128, tag.get());
  }

  /**
   * Should return tag's value.
   */
  @Test
  public void test_get() {
    TagShort tag = new TagShort("newName");
    tag.set((short) 32128);
    Assert.assertEquals((short) 32128, tag.get());
  }

  /**
   * Two tags with the same name and value should be equal.
   */
  @Test
  public void test_equals() {
    TagShort tag1 = new TagShort("newName", (short) 32128);
    TagShort tag2 = new TagShort("newName", (short) 32128);
    Assert.assertEquals(tag1, tag2);
  }

  /**
   * Should return a string that is a combination of the tag class' simple name,
   * reference name, and value.
   */
  @Test
  public void test_toString() {
    TagShort tag = new TagShort("newName", (short) 32128);
    Assert.assertEquals("TagShort(\"newName\"): 32128", tag.toString());
  }

  /**
   * Should return a new instance of the tag that is equal but not ==.
   */
  @Test
  public void test_cloneWithoutParent() {
    TagShort tag1 = new TagShort("newName", (short) 32128);
    TagShort tag2 = tag1.clone();
    Assert.assertEquals(tag1, tag2);
    Assert.assertTrue(tag1 != tag2);
  }

  /**
   * Clone should not clone parent.
   */
  @Test
  public void test_cloneWithParent() {
    TagShort tag1 = new TagShort("newName", (short) 32128);
    AbstractContainerTagTestClass parent = new AbstractContainerTagTestClass("parent");
    tag1.addTo(parent);
    TagShort clone = tag1.clone();
    Assert.assertEquals(tag1, clone);
    Assert.assertTrue(false == clone.hasParent());
  }

}
