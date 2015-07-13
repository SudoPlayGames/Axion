package com.sudoplay.axion.spec.tag;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.sudoplay.axion.TestUtil;
import com.sudoplay.axion.TestUtil.AbstractContainerTagTestClass;
import com.sudoplay.axion.tag.AxionInvalidTagException;
import com.sudoplay.axion.tag.Tag;

public class TagCompoundTest {

  /**
   * Should have two constructors: a constructor to set name and a constructor
   * to set name and value.
   */
  @Test
  public void test_constructors() {
    TagCompound tag = new TagCompound("newName");
    Assert.assertEquals("newName", tag.getName());

    // allows string/tag map in constructor
    TagInt tagInt = new TagInt("tagInt", 42);
    Map<String, Tag> map = new HashMap<String, Tag>();
    map.put("newTag", tagInt);
    tag = new TagCompound("newName", map);
    Assert.assertEquals("newName", tag.getName());
    Assert.assertEquals(map, tag.getAsMap());

    // should not allow unnamed tags in constructor map
    map.put("another", new TagInt("", 38));
    try {
      tag = new TagCompound("newName", map);
      Assert.fail("Expected AxionInvalidTagException");
    } catch (AxionInvalidTagException e) {
      // Expected AxionInvalidTagException
    }

    // null map parameter should result in empty backing map
    tag = new TagCompound("newName", null);
    Assert.assertTrue(tag.getAsMap().isEmpty());
  }

  /**
   * Should return unmodifiable map.
   */
  @Test
  public void test_getAsMap() {
    TagCompound tag = new TagCompound();
    TagInt tagInt = new TagInt("newName", 42);
    tag.put(tagInt);
    try {
      tag.getAsMap().clear();
      Assert.fail("Expected UnsupportedOperationException");
    } catch (UnsupportedOperationException e) {
      // Expected UnsupportedOperationException
    }
    Assert.assertEquals(42, ((TagInt) tag.getAsMap().get("newName")).get());
  }

  /**
   * Should return an Iterator<Tag> of an unmodifiable set of the backing map's
   * values.
   */
  @Test
  public void test_iterator() {
    TagCompound tag = new TagCompound();
    TagInt tagInt = new TagInt("newName", 42);
    tag.put(tagInt);
    Iterator<Tag> it = tag.iterator();
    while (it.hasNext()) {
      Tag next = it.next();
      Assert.assertEquals(tagInt, next);
      try {
        it.remove();
        Assert.fail("Expected UnsupportedOperationException");
      } catch (UnsupportedOperationException e) {
        // Expected UnsupportedOperationException
      }
    }
  }

  /**
   * Should remove all tags from the compound and remove all of the parent
   * references in the tags removed.
   */
  @Test
  public void test_clear() {
    TagCompound tag = new TagCompound();
    TagInt tagInt = new TagInt("newName", 42);
    tag.put(tagInt);
    Assert.assertEquals(1, tag.size());
    tag.clear();
    Assert.assertEquals(0, tag.size());
    Assert.assertTrue(false == tagInt.hasParent());
  }

  /**
   * Should return the number of tags in the backing map.
   */
  @Test
  public void test_size() {
    TagCompound tag = new TagCompound();
    TagInt tagInt = new TagInt("newName", 42);
    tag.put(tagInt);
    Assert.assertEquals(1, tag.size());
    tagInt.removeFromParent();
    Assert.assertEquals(0, tag.size());
  }

  /**
   * Should return true if the backing map contains the tag passed.
   */
  @Test
  public void test_contains() {
    TagCompound tag = new TagCompound();
    TagInt tagInt = new TagInt("newName", 42);
    tag.put(tagInt);
    Assert.assertTrue(tag.contains(tagInt));

    // true even if tag has no parent or different parent
    TagInt tagInt2 = new TagInt("newName", 42);
    Assert.assertTrue(tag.contains(tagInt2));
  }

  /**
   * Should return true if the backing map contains the key passed.
   */
  @Test
  public void test_containsKey() {
    TagCompound tag = new TagCompound();
    TagInt tagInt = new TagInt("newName", 42);
    tag.put(tagInt);
    Assert.assertTrue(tag.containsKey("newName"));
  }

  /**
   * Should remove and return the tag mapped to the key passed.
   */
  @Test
  public void test_remove() {
    TagCompound tag = new TagCompound();
    TagInt tagInt = new TagInt("newName", 42);
    tag.put(tagInt);
    Assert.assertEquals(1, tag.size());
    TagInt removed = (TagInt) tag.remove("newName");
    Assert.assertEquals(0, tag.size());
    Assert.assertTrue(tagInt == removed);
  }

  /**
   * Should return tag mapped to key or null if no tag mapped to key.
   */
  @Test
  public void test_get() {
    TagCompound tag = new TagCompound();
    TagInt tagInt = new TagInt("newName", 42);
    tag.put(tagInt);
    Assert.assertEquals(tagInt, tag.get("newName"));
    Assert.assertEquals(null, tag.get("null"));
  }

  /**
   * Should add the tag to the collection, set the tag's parent, and throw an
   * exception on null tags and unnamed tags. Putting a tag with the same name
   * as an existing tag will overwrite the existing tag.
   */
  @Test
  public void test_put() {
    TagCompound tag = new TagCompound();
    TagInt tagInt = new TagInt("newName", 42);
    tag.put(tagInt);
    Assert.assertEquals(1, tag.size());
    Assert.assertEquals(tagInt, tag.get("newName"));
    Assert.assertTrue(tag == tagInt.getParent());
    tag.put(new TagInt("newName", 72));
    Assert.assertEquals(72, ((TagInt) tag.get("newName")).get());
    try {
      tag.put(null);
      Assert.fail("Expected AxionInvalidTagException");
    } catch (AxionInvalidTagException e) {
      // Expected AxionInvalidTagException
    }
    try {
      tag.put(new TagInt(""));
      Assert.fail("Expected AxionInvalidTagException");
    } catch (AxionInvalidTagException e) {
      // Expected AxionInvalidTagException
    }

  }

  /**
   * Should add the tag to the backing map, but not alter the tag's parent.
   */
  @Test
  public void test_onChildAddition() {
    TagCompound tag = new TagCompound();
    TagInt tagInt = new TagInt("newName", 42);
    tag.onChildAddition(tagInt);
    Assert.assertEquals(tagInt, tag.get("newName"));
    Assert.assertFalse(tagInt.hasParent());
  }

  /**
   * Should remove the tag from the backing map, but not alter the tag's parent.
   */
  @Test
  public void test_onChildRemoval() {
    TagCompound tag = new TagCompound();
    TagInt tagInt = new TagInt("newName", 42);
    tag.put(tagInt);
    tag.onChildRemoval(tagInt);
    Assert.assertEquals(null, tag.get("newName"));
    Assert.assertTrue(tagInt.hasParent());
  }

  /**
   * Should throw on unnamed tag or null tag.
   */
  @Test
  public void test_assertValid() {
    TagCompound tag = new TagCompound();
    try {
      tag.assertValid(new TagInt(""));
      Assert.fail("Expected AxionInvalidTagException");
    } catch (AxionInvalidTagException e) {
      // Expected AxionInvalidTagException
    }
    try {
      tag.assertValid(null);
      Assert.fail("Expected AxionInvalidTagException");
    } catch (AxionInvalidTagException e) {
      // Expected AxionInvalidTagException
    }
  }

  /**
   * Two tags with the same name and values should be equal.
   */
  @Test
  public void test_equals() {
    TagCompound tag1 = new TagCompound();
    tag1.put(new TagInt("newName", 42));
    TagCompound tag2 = new TagCompound();
    tag2.put(new TagInt("newName", 42));
    Assert.assertEquals(tag2, tag2);
  }

  /**
   * Should return a string that is a combination of the tag class' simple name,
   * reference name, and value.
   */
  @Test
  public void test_toString() {
    TagCompound tag = new TagCompound("newCompound");
    tag.put(new TagInt("newName1", 42));
    tag.put(new TagInt("newName2", 42));
    tag.put(new TagInt("newName3", 42));
    tag.put(new TagInt("newName4", 42));
    Assert.assertEquals("TagCompound(\"newCompound\"): 4 entries", tag.toString());
  }

  /**
   * Should return a new instance of the tag that is equal but not ==.
   */
  @Test
  public void test_cloneWithoutParent() {
    TagCompound tag1 = TestUtil.getTagCompound();
    TagCompound tag2 = tag1.clone();
    Assert.assertEquals(tag1, tag2);
    Assert.assertTrue(tag1 != tag2);
    Assert.assertTrue(tag1.get("int") != tag2.get("int"));
  }

  /**
   * Clone should not clone parent.
   */
  @Test
  public void test_cloneWithParent() {
    TagCompound tag1 = TestUtil.getTagCompound();
    AbstractContainerTagTestClass parent = new AbstractContainerTagTestClass("parent");
    tag1.addTo(parent);
    TagCompound clone = tag1.clone();
    Assert.assertEquals(tag1, clone);
    Assert.assertTrue(false == clone.hasParent());
  }

  /**
   * When a compound tag's child has its name changed, the change should be
   * reflected in the backing map.
   */
  @Test
  public void test_nameChange() {
    TagCompound compound = new TagCompound("compound");
    compound.put(new TagByte("tagByte", (byte) 16));
    TagByte tagByte = compound.get("tagByte");
    assertEquals("tagByte", tagByte.getName());
    tagByte.setName("newName");
    assertEquals("newName", tagByte.getName());
    assertEquals(tagByte, compound.get("newName"));
  }

  @Test
  public void test_stream() {
    TagCompound compound = new TagCompound();
    compound.put("first", new TagString("first"));
    compound.put("second", new TagLong(42L));
    compound.put("third", new TagLong(73L));
    compound.put("last", new TagFloat(3.1415f));

    assertEquals(1, compound.stream().filter(e -> "second".equals(e.getKey())).count());
    assertEquals(2, compound.stream().filter(e -> e.getValue() instanceof TagLong).count());
  }

}
