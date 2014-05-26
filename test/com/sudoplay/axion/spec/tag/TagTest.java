package com.sudoplay.axion.spec.tag;

import org.junit.Assert;
import org.junit.Test;

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
  public void test_setnameWhenParent() {
    AbstractTagTestClass tag = new AbstractTagTestClass(null);
    tag.setParent(new AbstractTagTestClass("parent"));
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
    tag1.setParent(tag2);
    Assert.assertEquals(tag1, tag2); // equal with parent mismatch
  }

  /**
   * Should return parent.
   */
  @Test
  public void test_getParent() {
    AbstractTagTestClass tag1 = new AbstractTagTestClass("newName");
    AbstractTagTestClass tag2 = new AbstractTagTestClass("newName");
    tag1.setParent(tag2);
    Assert.assertTrue(tag1.getParent() == tag2);
  }

  /**
   * Should return true if tag has parent.
   */
  @Test
  public void test_hasParent() {
    AbstractTagTestClass tag1 = new AbstractTagTestClass("newName");
    AbstractTagTestClass tag2 = new AbstractTagTestClass("newName");
    tag1.setParent(tag2);
    Assert.assertTrue(tag1.hasParent());
  }

  /**
   * A child should call its parent's {@link Tag#onChildRemoval(Tag)} method
   * when its {@link Tag#removeFromParent()} method is called.
   */
  @Test
  public void test_removeFromParent() {
    AbstractTagTestClass tag1 = new AbstractTagTestClass("newName");
    AbstractTagTestClass tag2 = new AbstractTagTestClass("newName");
    tag1.setParent(tag2);
    try {
      tag1.removeFromParent();
      Assert.fail("Expected RuntimeException");
    } catch (RuntimeException e) {
      // Expected RuntimeException
    }
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

  class AbstractTagTestClass extends Tag {

    public AbstractTagTestClass(String newName) {
      super(newName);
    }

    @Override
    public AbstractTagTestClass clone() {
      return new AbstractTagTestClass(getName());
    }

    @Override
    protected void onChildNameChange(String oldName, String newName) {
      throw new RuntimeException("To test if this method is called on child's namechange");
    }

    @Override
    protected void onChildRemoval(Tag tag) {
      throw new RuntimeException("To test if this method is called on child removal");
    }

  }

}
