package com.sudoplay.axion.ext.tag;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import com.sudoplay.axion.TestUtil.AbstractContainerTagTestClass;

public class TagStringArrayTest {

  /**
   * Should have two constructors: a constructor to set name and a constructor
   * to set name and value.
   */
  @Test
  public void test_constructors() {
    TagStringArray tag = new TagStringArray("newName");
    Assert.assertEquals("newName", tag.getName());
    tag = new TagStringArray("newName", new String[] { "351", "32002", "3215", "8496" });
    Assert.assertEquals("newName", tag.getName());
    Assert.assertTrue(Arrays.equals(new String[] { "351", "32002", "3215", "8496" }, tag.get()));
  }

  /**
   * Should set the tag's value.
   */
  @Test
  public void test_set() {
    TagStringArray tag = new TagStringArray("newName");
    tag.set(new String[] { "351", "32002", "3215", "8496" });
    Assert.assertTrue(Arrays.equals(new String[] { "351", "32002", "3215", "8496" }, tag.get()));
  }

  /**
   * Should return a copy of the tag's value.
   */
  @Test
  public void test_get() {
    TagStringArray tag = new TagStringArray("newName");
    String[] data = new String[] { "351", "32002", "3215", "8496" };
    tag.set(data);
    Assert.assertTrue(data != tag.get());
    Assert.assertTrue(Arrays.equals(data, tag.get()));
  }

  /**
   * Two tags with the same name and value should be equal.
   */
  @Test
  public void test_equals() {
    TagStringArray tag1 = new TagStringArray("newName", new String[] { "351", "32002", "3215", "8496" });
    TagStringArray tag2 = new TagStringArray("newName", new String[] { "351", "32002", "3215", "8496" });
    Assert.assertEquals(tag1, tag2);
  }

  /**
   * Should return a string that is a combination of the tag class' simple name,
   * reference name, and value.
   */
  @Test
  public void test_toString() {
    TagStringArray tag = new TagStringArray("newName", new String[] { "351", "32002", "3215", "8496" });
    Assert.assertEquals("TagStringArray(\"newName\"): [4 strings]", tag.toString());
  }

  /**
   * Should return a new instance of the tag that is equal but not ==.
   */
  @Test
  public void test_cloneWithoutParent() {
    TagStringArray tag1 = new TagStringArray("newName", new String[] { "351", "32002", "3215", "8496" });
    TagStringArray tag2 = tag1.clone();
    Assert.assertEquals(tag1, tag2);
    Assert.assertTrue(tag1 != tag2);
  }

  /**
   * Clone should not clone parent.
   */
  @Test
  public void test_cloneWithParent() {
    TagStringArray tag1 = new TagStringArray("newName", new String[] { "351", "32002", "3215", "8496" });
    AbstractContainerTagTestClass parent = new AbstractContainerTagTestClass("parent");
    tag1.addTo(parent);
    TagStringArray clone = tag1.clone();
    Assert.assertEquals(tag1, clone);
    Assert.assertTrue(false == clone.hasParent());
  }

}
