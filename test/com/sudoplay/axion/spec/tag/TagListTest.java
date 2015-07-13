package com.sudoplay.axion.spec.tag;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.TestUtil;
import com.sudoplay.axion.TestUtil.AbstractContainerTagTestClass;
import com.sudoplay.axion.tag.AxionIllegalTagNameException;
import com.sudoplay.axion.tag.AxionInvalidTagException;
import com.sudoplay.axion.tag.Tag;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class TagListTest {

  /**
   * Should have two constructors: a constructor to set name and a constructor to set name and value.
   */
  @Test
  public void test_constructors() {
    TagList tag = new TagList(TagInt.class, "newName");
    Assert.assertEquals("newName", tag.getName());

    // allows tag list in constructor
    TagInt tagInt = new TagInt("tagInt", 42);
    List<Tag> map = new ArrayList<Tag>();
    map.add(tagInt);
    tag = new TagList(TagInt.class, "newName", map);
    Assert.assertEquals("newName", tag.getName());
    Assert.assertEquals(map, tag.getAsList());

    // on insertion, tags should have name set to empty string
    Assert.assertEquals("", tag.get(0).getName());

    // should not allow null tags in constructor list
    map.add(null);
    try {
      tag = new TagList(TagInt.class, "newName", map);
      Assert.fail("Expected AxionInvalidTagException");
    } catch (AxionInvalidTagException e) {
      // Expected AxionInvalidTagException
    }

    // null map parameter should result in empty backing list
    tag = new TagList(TagInt.class, "newName", null);
    Assert.assertTrue(tag.getAsList().isEmpty());
  }

  /**
   * Should return unmodifiable list.
   */
  @Test
  public void test_getAsList() {
    TagList tag = new TagList(TagInt.class);
    TagInt tagInt = new TagInt("newName", 42);
    tag.add(tagInt);
    try {
      tag.getAsList().clear();
      Assert.fail("Expected UnsupportedOperationException");
    } catch (UnsupportedOperationException e) {
      // Expected UnsupportedOperationException
    }
    Assert.assertEquals(42, ((TagInt) tag.getAsList().get(0)).get());
  }

  /**
   * Should return an Iterator<Tag> of an unmodifiable view of the backing list.
   */
  @Test
  public void test_iterator() {
    TagList tag = new TagList(TagInt.class);
    TagInt tagInt = new TagInt("newName", 42);
    tag.add(tagInt);
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
   * Should remove all tags from the list and remove all of the parent references in the tags removed.
   */
  @Test
  public void test_clear() {
    TagList tag = new TagList(TagInt.class);
    TagInt tagInt = new TagInt("newName", 42);
    tag.add(tagInt);
    Assert.assertEquals(1, tag.size());
    tag.clear();
    Assert.assertEquals(0, tag.size());
    Assert.assertTrue(false == tagInt.hasParent());
  }

  /**
   * Should return the number of tags in the backing list.
   */
  @Test
  public void test_size() {
    TagList tag = new TagList(TagInt.class);
    TagInt tagInt = new TagInt("newName", 42);
    tag.add(tagInt);
    Assert.assertEquals(1, tag.size());
    tagInt.removeFromParent();
    Assert.assertEquals(0, tag.size());
  }

  /**
   * Should return true if the backing list contains the tag passed.
   */
  @Test
  public void test_contains() {
    TagList tag = new TagList(TagInt.class);
    TagInt tagInt = new TagInt("newName", 42);
    tag.add(tagInt);
    Assert.assertTrue(tag.contains(tagInt));

    // true even if tag has no parent, different parent, or different name
    TagInt tagInt2 = new TagInt("differentName", 42);
    Assert.assertTrue(tag.contains(tagInt2));

    TagLong tagLong = new TagLong("newLong", 5L);
    Assert.assertFalse(tag.contains(tagLong));

    Assert.assertFalse(tag.contains(null));
  }

  /**
   * Should remove tag passed and return true if removed, false if not found.
   */
  @Test
  public void test_removeByTag() {
    TagList tag = new TagList(TagInt.class);
    TagInt tagInt = new TagInt("newName", 42);
    tag.add(tagInt);
    Assert.assertEquals(1, tag.size());
    Assert.assertTrue(tag.remove(tagInt));
    Assert.assertEquals(0, tag.size());
    Assert.assertFalse(tag.remove(tagInt));
    Assert.assertFalse(tag.remove(null));
  }

  /**
   * Should remove and return tag by index.
   */
  @Test
  public void test_removeByIndex() {
    TagList tag = new TagList(TagInt.class);
    TagInt tagInt = new TagInt("newName", 42);
    tag.add(tagInt);
    Assert.assertEquals(1, tag.size());
    Assert.assertTrue(tagInt == tag.remove(0));
    Assert.assertEquals(0, tag.size());
  }

  /**
   * Should return tag by index.
   */
  @Test
  public void test_get() {
    TagList tag = new TagList(TagInt.class);
    TagInt tagInt = new TagInt("newName", 42);
    tag.add(tagInt);
    Assert.assertEquals(tagInt, tag.get(0));
    Assert.assertTrue(tagInt == tag.get(0));
  }

  /**
   * Should add the tag to the collection, set the tag's name to empty, set the tag's parent, and throw an exception on
   * null tags.
   */
  @Test
  public void test_add() {
    TagList tag = new TagList(TagInt.class);
    TagInt tagInt = new TagInt("newName", 42);
    tag.add(tagInt);
    Assert.assertEquals("", tagInt.getName());
    Assert.assertEquals(1, tag.size());
    Assert.assertEquals(tagInt, tag.get(0));
    Assert.assertTrue(tag == tagInt.getParent());
    tag.add(new TagInt("newName", 72));
    Assert.assertEquals(72, ((TagInt) tag.get(1)).get());
    try {
      tag.add(null);
      Assert.fail("Expected AxionInvalidTagException");
    } catch (AxionInvalidTagException e) {
      // Expected AxionInvalidTagException
    }
  }

  /**
   * Should add the tag to the backing list, but not alter the tag's parent.
   */
  @Test
  public void test_onChildAddition() {
    TagList tag = new TagList(TagInt.class);
    TagInt tagInt = new TagInt("newName", 42);
    tag.onChildAddition(tagInt);
    Assert.assertEquals(tagInt, tag.get(0));
    Assert.assertFalse(tagInt.hasParent());
  }

  /**
   * Should remove the tag from the backing list, but not alter the tag's parent.
   */
  @Test
  public void test_onChildRemoval() {
    TagList tag = new TagList(TagInt.class);
    TagInt tagInt = new TagInt("newName", 42);
    tag.add(tagInt);
    tag.onChildRemoval(tagInt);
    Assert.assertEquals(0, tag.size());
    Assert.assertTrue(tagInt.hasParent());
  }

  /**
   * Should throw on tag of wrong type or null tag.
   */
  @Test
  public void test_assertValid() {
    TagList tag = new TagList(TagInt.class);
    try {
      tag.assertValid(null);
      Assert.fail("Expected AxionInvalidTagException");
    } catch (AxionInvalidTagException e) {
      // Expected AxionInvalidTagException
    }
    try {
      tag.assertValid(new TagLong(""));
      Assert.fail("Expected AxionInvalidTagException");
    } catch (AxionInvalidTagException e) {
      // Expected AxionInvalidTagException
    }
  }

  /**
   * Two list tags with the same values should be equal.
   */
  @Test
  public void test_equals() {
    TagList tag1 = new TagList(TagInt.class);
    tag1.add(new TagInt("newName", 42));
    TagList tag2 = new TagList(TagInt.class);
    tag2.add(new TagInt("differentName", 42));
    Assert.assertEquals(tag2, tag2);
  }

  /**
   * Should return a string that is a combination of the tag class' simple name, reference name, value and type.
   */
  @Test
  public void test_toString() {
    TagList tag = new TagList(TagInt.class, "newList");
    tag.add(new TagInt("newName1", 42));
    tag.add(new TagInt("newName2", 42));
    tag.add(new TagInt("newName3", 42));
    tag.add(new TagInt("newName4", 42));
    Assert.assertEquals("TagList(\"newList\"): 4 entries of type TagInt", tag.toString());
  }

  /**
   * Should return a new instance of the tag that is equal but not ==.
   */
  @Test
  public void test_cloneWithoutParent() {
    TagList tag1 = TestUtil.getTagList();
    TagList tag2 = tag1.clone();
    Assert.assertEquals(tag1, tag2);
    Assert.assertTrue(tag1 != tag2);
    Assert.assertTrue(tag1.get(0) != tag2.get(0));
  }

  /**
   * Clone should not clone parent.
   */
  @Test
  public void test_cloneWithParent() {
    TagList tag1 = TestUtil.getTagList();
    AbstractContainerTagTestClass parent = new AbstractContainerTagTestClass("parent");
    tag1.addTo(parent);
    TagList clone = tag1.clone();
    Assert.assertEquals(tag1, clone);
    Assert.assertTrue(false == clone.hasParent());
  }

  /**
   * When a list child's name is changed, an exception is thrown.
   */
  @Test
  public void test_nameChange() {
    TagList list = new TagList(TagByte.class, "newList") {
      {
        add(new TagByte("name", (byte) 14));
      }
    };
    assertTrue(list.size() == 1);
    try {
      list.get(0).setName("shouldNotHaveAName");
      fail("Should throw AxionIllegalNameChangeException");
    } catch (AxionIllegalTagNameException e) {
      //
    }
    assertEquals("", list.get(0).getName());
  }

  @Test
  public void test_stream() {
    TagList list = new TagList(TagInt.class);
    list.add(new TagInt(0));
    list.add(new TagInt(1));
    list.add(new TagInt(2));
    assertEquals(3, list.stream(TagInt.class).count());
    assertEquals(true, list.stream(TagInt.class).anyMatch(tag -> tag.get() > 1));

    //noinspection unchecked
    list
        .stream(TagInt.class)
        .filter(tagInt -> tagInt.get() < 2)
        .map(TagInt::get)
        .collect(Collectors.toCollection((Supplier<ArrayList>) ArrayList::new))
        .forEach(System.out::println);

    list.valueStream(
        TagInt.class,
        tag -> tag.get() < 2,
        TagInt::get,
        ArrayList::new
    ).forEach(System.out::println);
  }

  @Test
  public void test_valueStream() {
    TagList list = new TagList(TagInt.class);
    list.add(new TagInt(0));
    list.add(new TagInt(1));
    list.add(new TagInt(2));

    //noinspection unchecked
    list
        .stream(TagInt.class)
        .filter(tagInt -> tagInt.get() < 2)
        .map(TagInt::get)
        .collect(Collectors.toCollection((Supplier<ArrayList>) ArrayList::new))
        .forEach(value -> assertTrue((int) value < 2));

    list
        .valueStream(
            TagInt.class,
            tag -> tag.get() < 2,
            TagInt::get,
            ArrayList::new
        ).forEach(value -> assertTrue(value < 2));

    list
        .valueStream(
            TagInt.class,
            int.class,
            Axion.getSpecInstance()
        ).forEach(value -> assertTrue(value < 3));

    assertEquals(
        3,
        list
            .valueStream(
                TagInt.class,
                int.class,
                Axion.getSpecInstance()
            ).count()
    );
  }

}
