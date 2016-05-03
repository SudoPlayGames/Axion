package com.sudoplay.axion.ext.tag;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import com.sudoplay.axion.TestUtil.AbstractContainerTagTestClass;

public class TagBooleanArrayTest {

  /**
   * Should have two constructors: a constructor to set name and a constructor
   * to set name and value.
   */
  @Test
  public void test_constructors() {
    TagBooleanArray tag = new TagBooleanArray("newName");
    Assert.assertEquals("newName", tag.getName());
    tag = new TagBooleanArray("newName", new boolean[] { true, false, false, true });
    Assert.assertEquals("newName", tag.getName());
    Assert.assertTrue(Arrays.equals(new boolean[] { true, false, false, true }, tag.get()));
  }

  /**
   * Should set the tag's value.
   */
  @Test
  public void test_set() {
    TagBooleanArray tag = new TagBooleanArray("newName");
    tag.set(new boolean[] { true, false, false, true });
    Assert.assertTrue(Arrays.equals(new boolean[] { true, false, false, true }, tag.get()));
  }

  /**
   * Should return a copy of the tag's value.
   */
  @Test
  public void test_get() {
    TagBooleanArray tag = new TagBooleanArray("newName");
    boolean[] data = new boolean[] { true, false, false, true };
    tag.set(data);
    Assert.assertTrue(data != tag.get());
    Assert.assertTrue(Arrays.equals(data, tag.get()));
  }

  /**
   * Two tags with the same name and value should be equal.
   */
  @Test
  public void test_equals() {
    TagBooleanArray tag1 = new TagBooleanArray("newName", new boolean[] { true, false, false, true });
    TagBooleanArray tag2 = new TagBooleanArray("newName", new boolean[] { true, false, false, true });
    Assert.assertEquals(tag1, tag2);
  }

  /**
   * Should return a string that is a combination of the tag class' simple name,
   * reference name, and value.
   */
  @Test
  public void test_toString() {
    TagBooleanArray tag = new TagBooleanArray("newName", new boolean[] { true, false, false, true });
    Assert.assertEquals("TagBooleanArray(\"newName\"): [4 booleans]", tag.toString());
  }

  /**
   * Should return a new instance of the tag that is equal but not ==.
   */
  @Test
  public void test_cloneWithoutParent() {
    TagBooleanArray tag1 = new TagBooleanArray("newName", new boolean[] { true, false, false, true });
    TagBooleanArray tag2 = tag1.clone();
    Assert.assertEquals(tag1, tag2);
    Assert.assertTrue(tag1 != tag2);
  }

  /**
   * Clone should not clone parent.
   */
  @Test
  public void test_cloneWithParent() {
    TagBooleanArray tag1 = new TagBooleanArray("newName", new boolean[] { true, false, false, true });
    AbstractContainerTagTestClass parent = new AbstractContainerTagTestClass("parent");
    tag1.addTo(parent);
    TagBooleanArray clone = tag1.clone();
    Assert.assertEquals(tag1, clone);
    Assert.assertTrue(false == clone.hasParent());
  }

}
