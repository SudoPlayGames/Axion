package com.sudoplay.axion.spec.tag;

import org.junit.Assert;
import org.junit.Test;

import com.sudoplay.axion.TestUtil.AbstractContainerTagTestClass;

public class TagByteTest {

  /**
   * Should have two constructors: a constructor to set name and a constructor
   * to set name and value.
   */
  @Test
  public void test_constructors() {
    TagByte tag = new TagByte("newName");
    Assert.assertEquals("newName", tag.getName());
    tag = new TagByte("newName", (byte) 42);
    Assert.assertEquals("newName", tag.getName());
    Assert.assertEquals((byte) 42, tag.get());
  }

  /**
   * Should set the tag's value.
   */
  @Test
  public void test_set() {
    TagByte tag = new TagByte("newName");
    tag.set((byte) 42);
    Assert.assertEquals((byte) 42, tag.get());
  }

  /**
   * Should return tag's value.
   */
  @Test
  public void test_get() {
    TagByte tag = new TagByte("newName");
    tag.set((byte) 42);
    Assert.assertEquals((byte) 42, tag.get());
  }

  /**
   * Two tags with the same name and value should be equal.
   */
  @Test
  public void test_equals() {
    TagByte tag1 = new TagByte("newName", (byte) 42);
    TagByte tag2 = new TagByte("newName", (byte) 42);
    Assert.assertEquals(tag1, tag2);
  }

  /**
   * Should return a string that is a combination of the tag class' simple name,
   * reference name, and value.
   */
  @Test
  public void test_toString() {
    TagByte tag = new TagByte("newName", (byte) 42);
    Assert.assertEquals("TagByte(\"newName\"): 42", tag.toString());
  }

  /**
   * Should return a new instance of the tag that is equal but not ==.
   */
  @Test
  public void test_cloneWithoutParent() {
    TagByte tag1 = new TagByte("newName", (byte) 42);
    TagByte tag2 = tag1.clone();
    Assert.assertEquals(tag1, tag2);
    Assert.assertTrue(tag1 != tag2);
  }
  
  /**
   * Clone should not clone parent.
   */
  @Test
  public void test_cloneWithParent() {
    TagByte tag1 = new TagByte("newName", (byte) 42);
    AbstractContainerTagTestClass parent = new AbstractContainerTagTestClass("parent");
    tag1.addTo(parent);
    TagByte clone = tag1.clone();
    Assert.assertEquals(tag1, clone);
    Assert.assertTrue(false == clone.hasParent());
  }

}
