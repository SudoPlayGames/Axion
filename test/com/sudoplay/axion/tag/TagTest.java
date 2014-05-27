package com.sudoplay.axion.tag;

import org.junit.Assert;
import org.junit.Test;

import com.sudoplay.axion.TestUtil.AbstractContainerTagTestClass;
import com.sudoplay.axion.TestUtil.AbstractTagTestClass;

public class TagTest {

  /**
   * Should return name set in constructor.
   */
  @Test
  public void test_getNameWhenNotNull() {
    AbstractTagTestClass tag = new AbstractTagTestClass("newName");
    Assert.assertEquals("newName", tag.getName());
  }

  /**
   * Should return an empty string.
   */
  @Test
  public void test_getNameWhenNull() {
    AbstractTagTestClass tag = new AbstractTagTestClass(null);
    Assert.assertEquals("", tag.getName());
  }

  /**
   * Should set a new name for the tag.
   */
  @Test
  public void test_setNameWhenNoParent() {
    AbstractTagTestClass tag = new AbstractTagTestClass(null);
    tag.setName("newName");
    Assert.assertEquals("newName", tag.getName());
  }

  /**
   * A tag should call its parent's
   * {@link Tag#onChildNameChange(String, String)} method when its name is
   * chanaged.
   */
  @Test
  public void test_setNameWhenParent() {
    AbstractTagTestClass tag = new AbstractTagTestClass(null);
    tag.addTo(new AbstractContainerTagTestClass("parent"));
    try {
      tag.setName("newName");
      Assert.fail("Expected RuntimeException");
    } catch (RuntimeException e) {
      // Expected RuntimeException
    }

  }

  /**
   * Two simple abstract tags with the same name should be equal regardless of
   * parent.
   */
  @Test
  public void test_equals() {
    AbstractTagTestClass tag1 = new AbstractTagTestClass("newName");
    AbstractTagTestClass tag2 = new AbstractTagTestClass("newName");
    Assert.assertEquals(tag1, tag2); // equal without parent
    tag1.addTo(new AbstractContainerTagTestClass("parent"));
    Assert.assertEquals(tag1, tag2); // equal with parent mismatch
  }

  /**
   * Should set tag's parent and add to container tag's collection.
   */
  @Test
  public void test_addTo() {
    AbstractTagTestClass tag = new AbstractTagTestClass("newName");
    AbstractContainerTagTestClass parent = new AbstractContainerTagTestClass("parent");
    tag.addTo(parent);
    Assert.assertEquals(1, parent.size());
    Assert.assertTrue(tag.hasParent());
    Assert.assertTrue(tag.getParent() == parent);

    // should throw if has parent
    try {
      tag.addTo(new AbstractContainerTagTestClass("newParent"));
      Assert.fail("Expected AxionIllegalTagStateException");
    } catch (AxionIllegalTagStateException e) {
      // Expected AxionIllegalTagStateException
    }

    // should throw if null
    try {
      tag = new AbstractTagTestClass("newName");
      tag.addTo(null);
      Assert.fail("Expected AxionInvalidTagException");
    } catch (AxionInvalidTagException e) {
      // Expected AxionInvalidTagException
    }
  }

  /**
   * Should return parent or null if no container tag.
   */
  @Test
  public void test_getParent() {
    AbstractTagTestClass tag = new AbstractTagTestClass("newName");
    Assert.assertTrue(tag.getParent() == null);
    AbstractContainerTagTestClass parent = new AbstractContainerTagTestClass("parent");
    tag.addTo(parent);
    Assert.assertTrue(tag.getParent() == parent);
  }

  /**
   * Should return true if tag has parent.
   */
  @Test
  public void hasParent() {
    AbstractTagTestClass tag = new AbstractTagTestClass("newName");
    tag.addTo(new AbstractContainerTagTestClass("parent"));
    Assert.assertTrue(tag.hasParent());
  }

  /**
   * A child should call its parent's {@link Tag#onChildRemoval(Tag)} method
   * when its {@link Tag#removeFromParent()} method is called.
   */
  @Test
  public void test_removeFromParent() {
    AbstractTagTestClass tag = new AbstractTagTestClass("newName");
    AbstractContainerTagTestClass parent = new AbstractContainerTagTestClass("parent");
    tag.addTo(parent);
    Assert.assertTrue(parent.size() == 1);
    tag.removeFromParent();
    Assert.assertTrue(parent.size() == 0);
  }

  /**
   * Should return a string consisting of the tag class' simple name and the
   * tag's reference name.
   */
  @Test
  public void test_toString() {
    AbstractTagTestClass tag = new AbstractTagTestClass("newName");
    Assert.assertEquals("AbstractTagTestClass(\"newName\")", tag.toString());
  }

}
