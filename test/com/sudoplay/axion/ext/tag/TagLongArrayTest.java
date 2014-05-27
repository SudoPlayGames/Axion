package com.sudoplay.axion.ext.tag;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import com.sudoplay.axion.TestUtil.AbstractContainerTagTestClass;

public class TagLongArrayTest {

  /**
   * Should have two constructors: a constructor to set name and a constructor
   * to set name and value.
   */
  @Test
  public void test_constructors() {
    TagLongArray tag = new TagLongArray("newName");
    Assert.assertEquals("newName", tag.getName());
    tag = new TagLongArray("newName", new long[] { 512489635351856L, 8646358436843L, 6846843685454L, 684853436854L });
    Assert.assertEquals("newName", tag.getName());
    Assert.assertTrue(Arrays.equals(new long[] { 512489635351856L, 8646358436843L, 6846843685454L, 684853436854L }, tag.get()));
  }

  /**
   * Should set the tag's value.
   */
  @Test
  public void test_set() {
    TagLongArray tag = new TagLongArray("newName");
    tag.set(new long[] { 512489635351856L, 8646358436843L, 6846843685454L, 684853436854L });
    Assert.assertTrue(Arrays.equals(new long[] { 512489635351856L, 8646358436843L, 6846843685454L, 684853436854L }, tag.get()));
  }

  /**
   * Should return a copy of the tag's value.
   */
  @Test
  public void test_get() {
    TagLongArray tag = new TagLongArray("newName");
    long[] data = new long[] { 512489635351856L, 8646358436843L, 6846843685454L, 684853436854L };
    tag.set(data);
    Assert.assertTrue(data != tag.get());
    Assert.assertTrue(Arrays.equals(data, tag.get()));
  }

  /**
   * Two tags with the same name and value should be equal.
   */
  @Test
  public void test_equals() {
    TagLongArray tag1 = new TagLongArray("newName", new long[] { 512489635351856L, 8646358436843L, 6846843685454L, 684853436854L });
    TagLongArray tag2 = new TagLongArray("newName", new long[] { 512489635351856L, 8646358436843L, 6846843685454L, 684853436854L });
    Assert.assertEquals(tag1, tag2);
  }

  /**
   * Should return a string that is a combination of the tag class' simple name,
   * reference name, and value.
   */
  @Test
  public void test_toString() {
    TagLongArray tag = new TagLongArray("newName", new long[] { 512489635351856L, 8646358436843L, 6846843685454L, 684853436854L });
    Assert.assertEquals("TagLongArray(\"newName\"): [4 longs]", tag.toString());
  }

  /**
   * Should return a new instance of the tag that is equal but not ==.
   */
  @Test
  public void test_cloneWithoutParent() {
    TagLongArray tag1 = new TagLongArray("newName", new long[] { 512489635351856L, 8646358436843L, 6846843685454L, 684853436854L });
    TagLongArray tag2 = tag1.clone();
    Assert.assertEquals(tag1, tag2);
    Assert.assertTrue(tag1 != tag2);
  }

  /**
   * Clone should not clone parent.
   */
  @Test
  public void test_cloneWithParent() {
    TagLongArray tag1 = new TagLongArray("newName", new long[] { 512489635351856L, 8646358436843L, 6846843685454L, 684853436854L });
    AbstractContainerTagTestClass parent = new AbstractContainerTagTestClass("parent");
    tag1.addTo(parent);
    TagLongArray clone = tag1.clone();
    Assert.assertEquals(tag1, clone);
    Assert.assertTrue(false == clone.hasParent());
  }

}
