package com.sudoplay.axion.api;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.AxionWriteException;
import com.sudoplay.axion.api.impl.DefaultAxionWriter;
import com.sudoplay.axion.ext.tag.TagBoolean;
import com.sudoplay.axion.registry.TypeConverter;
import com.sudoplay.axion.spec.tag.TagCompound;
import com.sudoplay.axion.spec.tag.TagInt;
import com.sudoplay.axion.spec.tag.TagList;
import com.sudoplay.axion.spec.tag.TagString;
import com.sudoplay.axion.tag.Tag;
import com.sudoplay.axion.util.AxionPredicates;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class AxionWriterTest {

  private static Axion axion;

  @BeforeClass
  public static void before() {
    if ((axion = Axion.getInstance("test")) == null) {
      axion = Axion.createInstanceFrom(Axion.getExtInstance(), "test");
    }
    axion.registerConverter(Vector.class, new VectorConverter());
  }

  @Test
  public void test_write_name_tag() {
    AxionWriter out = getTestWriter();

    // should add tag to backing compound
    out.write("test", new TagInt(42));
    Tag tag = out.getTagCompound().get("test");
    assertEquals(42, (int) axion.convertTag(tag));

    // should throw IllegalArgumentException on null tag parameter
    try {
      out.write("test", (Tag) null);
      fail();
    } catch (IllegalArgumentException e) {
      // expected
    }

    // should throw IllegalArgumentException on null name parameter
    try {
      out.write(null, new TagInt(42));
      fail();
    } catch (IllegalArgumentException e) {
      // expected
    }
  }

  @Test
  public void test_write_name_axionWritable() {
    AxionWriter out = getTestWriter();

    // should write AxionWritable implementation
    out.write("test", getTestWritableVector());
    TagCompound vector = out.getTagCompound().get("test");
    Tag tag = vector.get("y");
    int actual = axion.convertTag(tag);
    assertEquals(1, actual);

    // should throw IllegalArgumentException on null writable parameter
    try {
      out.write("test", (AxionWritable) null);
      fail();
    } catch (IllegalArgumentException e) {
      // expected
    }

    // should throw IllegalArgumentException on null name parameter
    try {
      out.write(null, new WritableVector());
      fail();
    } catch (IllegalArgumentException e) {
      // expected
    }
  }

  @Test
  public void test_write_name_object() {
    AxionWriter out = getTestWriter();

    // should write mappable
    out.write("test", getTestVector());
    TagList vector = out.getTagCompound().get("test");
    int actual = axion.convertTag(vector.get(1));
    assertEquals(1, actual);

    // should write convertible
    out.write("test", 42);
    TagInt tagInt = out.getTagCompound().get("test");
    actual = tagInt.get();
    assertEquals(42, actual);

    // should throw IllegalArgumentException on null object parameter
    try {
      out.write("test", (Object) null);
      fail();
    } catch (IllegalArgumentException e) {
      // expected
    }

    // should throw IllegalArgumentException on null name parameter
    try {
      out.write(null, new WritableVector());
      fail();
    } catch (IllegalArgumentException e) {
      // expected
    }
  }

  @Test
  public void test_writeIf_name_tag_predicate() {
    AxionWriter out = getTestWriter();

    // should write if predicate passes
    out.writeIf("pass", new TagInt(42), AxionPredicates.alwaysTrue());
    assertNotNull(out.getTagCompound().get("pass"));

    // should not write if predicate fails
    out.writeIf("fail", new TagInt(73), AxionPredicates.alwaysFalse());
    assertNull(out.getTagCompound().get("fail"));

    // should not throw on null tag if predicate prevents writing
    out.writeIf("test", (Tag) null, AxionPredicates.alwaysFalse());

    // should throw IllegalArgumentException on null tag if predicate passes
    try {
      out.writeIf("test", (Tag) null, AxionPredicates.alwaysTrue());
      fail();
    } catch (IllegalArgumentException e) {
      // expected
    }

    // should throw IllegalArgumentException on null name parameter if predicate passes
    try {
      out.writeIf(null, new TagInt(42), AxionPredicates.alwaysTrue());
      fail();
    } catch (IllegalArgumentException e) {
      // expected
    }

    // should throw IllegalArgumentException on null name parameter if predicate passes
    try {
      out.writeIf(null, new TagInt(42), AxionPredicates.alwaysFalse());
      fail();
    } catch (IllegalArgumentException e) {
      // expected
    }

    // should throw IllegalArgumentException on null predicate parameter if predicate passes
    try {
      out.writeIf("test", new TagInt(42), null);
      fail();
    } catch (IllegalArgumentException e) {
      // expected
    }
  }

  @Test
  public void test_writeIf_name_axionWritable_predicate() {
    AxionWriter out = getTestWriter();

    // should write if predicate passes
    out.writeIf("pass", new WritableArgsVector(3, 1, 4), AxionPredicates.alwaysTrue());
    assertNotNull(out.getTagCompound().get("pass"));

    // should not write if predicate fails
    out.writeIf("fail", new WritableArgsVector(3, 1, 4), AxionPredicates.alwaysFalse());
    assertNull(out.getTagCompound().get("fail"));

    // should not throw on null AxionWritable if predicate prevents writing
    out.writeIf("test", (AxionWritable) null, AxionPredicates.alwaysFalse());

    // should throw IllegalArgumentException on null AxionWritable if predicate allows writing
    try {
      out.writeIf("test", (AxionWritable) null, AxionPredicates.alwaysTrue());
      fail();
    } catch (IllegalArgumentException e) {
      // expected
    }

    // should throw IllegalArgumentException on null name parameter if predicate allows
    try {
      out.writeIf(null, new WritableArgsVector(3, 1, 4), AxionPredicates.alwaysTrue());
      fail();
    } catch (IllegalArgumentException e) {
      // expected
    }

    // should throw IllegalArgumentException on null name parameter if predicate denies
    try {
      out.writeIf(null, new WritableArgsVector(3, 1, 4), AxionPredicates.alwaysFalse());
      fail();
    } catch (IllegalArgumentException e) {
      // expected
    }
  }

  @Test
  public void test_writeIf_name_object_predicate() {
    AxionWriter out = getTestWriter();

    // should write mappable object if predicate passes
    out.writeIf("pass1", getTestVector(), AxionPredicates.alwaysTrue());
    assertNotNull(out.getTagCompound().get("pass1"));

    // should not write mappable object if predicate fails
    out.writeIf("fail1", getTestVector(), AxionPredicates.alwaysFalse());
    assertNull(out.getTagCompound().get("fail1"));

    // should write convertible object if predicate passes
    out.writeIf("pass2", 42, AxionPredicates.alwaysTrue());
    assertNotNull(out.getTagCompound().get("pass2"));

    // should not write convertible object if predicate fails
    out.writeIf("fail2", 42, AxionPredicates.alwaysFalse());
    assertNull(out.getTagCompound().get("fail2"));

    // should not throw on null object if predicate prevents writing
    out.writeIf("test", (Object) null, AxionPredicates.alwaysFalse());

    // should throw IllegalArgumentException on null object if predicate allows writing
    try {
      out.writeIf("test", (Object) null, AxionPredicates.alwaysTrue());
      fail();
    } catch (IllegalArgumentException e) {
      // expected
    }

    // should throw IllegalArgumentException on null name parameter if predicate allows
    try {
      out.writeIf(null, getTestVector(), AxionPredicates.alwaysTrue());
      fail();
    } catch (IllegalArgumentException e) {
      // expected
    }

    // should throw IllegalArgumentException on null name parameter if predicate denies
    try {
      out.writeIf(null, getTestVector(), AxionPredicates.alwaysFalse());
      fail();
    } catch (IllegalArgumentException e) {
      // expected
    }
  }

  @Test
  public void test_writeIfNotNull_name_tag() {
    AxionWriter out = getTestWriter();

    // should write if not null
    out.writeIfNotNull("test1", new TagInt(42));
    assertNotNull(out.getTagCompound().get("test1"));

    // should not write if null
    out.writeIfNotNull("test2", (Tag) null);
    assertNull(out.getTagCompound().get("test2"));

    // should throw IllegalArgumentException on null name parameter
    try {
      out.writeIfNotNull(null, getTestVector());
      fail();
    } catch (IllegalArgumentException e) {
      // expected
    }
  }

  @Test
  public void test_writeIfNotNull_name_axionWritable() {
    AxionWriter out = getTestWriter();

    // should write if not null
    out.writeIfNotNull("test1", getTestWritableVector());
    assertNotNull(out.getTagCompound().get("test1"));

    // should not write if null
    out.writeIfNotNull("test2", (AxionWritable) null);
    assertNull(out.getTagCompound().get("test2"));

    // should throw IllegalArgumentException on null name parameter
    try {
      out.writeIfNotNull(null, getTestWritableVector());
      fail();
    } catch (IllegalArgumentException e) {
      // expected
    }
  }

  @Test
  public void test_writeIfNotNull_name_object() {
    AxionWriter out = getTestWriter();

    // should write mappable if not null
    out.writeIfNotNull("test1", getTestVector());
    assertNotNull(out.getTagCompound().get("test1"));

    // should not write mappable if null
    out.writeIfNotNull("test2", (Object) null);
    assertNull(out.getTagCompound().get("test2"));

    // should write if not null
    out.writeIfNotNull("test3", 42);
    assertNotNull(out.getTagCompound().get("test3"));

    // should not write if null
    out.writeIfNotNull("test4", (Object) null);
    assertNull(out.getTagCompound().get("test4"));

    // should throw IllegalArgumentException on null name parameter
    try {
      out.writeIfNotNull(null, getTestVector());
      fail();
    } catch (IllegalArgumentException e) {
      // expected
    }
  }

  @Test
  public void test_write_name_map() {
    AxionWriter out = getTestWriter();

    { // should keep order and write convertible objects as keys and values
      LinkedHashMap<String, Integer> linkedHashMap = new LinkedHashMap<>();
      linkedHashMap.put("first", 1);
      linkedHashMap.put("second", 2);
      linkedHashMap.put("third", 3);
      linkedHashMap.put("fourth", 4);
      linkedHashMap.put("last", 5);
      out.write("test", linkedHashMap);
      TagList tagList = out.getTagCompound().get("test");
      TagList valueList = tagList.get(1);
      assertEquals(1, ((TagInt) valueList.get(0)).get());
      assertEquals(5, ((TagInt) valueList.get(4)).get());
    }

    { // should write mappable as value
      LinkedHashMap<String, Vector> linkedHashMap = new LinkedHashMap<>();
      Vector vector = getTestVector();
      linkedHashMap.put("vector", vector);
      out.write("test", linkedHashMap);
      TagList tagList = out.getTagCompound().get("test");
      TagList valueList = tagList.get(1);
      assertEquals(3, ((TagInt) ((TagList) valueList.get(0)).get(0)).get());
    }

    { // should write mappable as key
      LinkedHashMap<Vector, String> linkedHashMap = new LinkedHashMap<>();
      Vector vector = getTestVector();
      linkedHashMap.put(vector, "vector");
      out.write("test", linkedHashMap);
      TagList tagList = out.getTagCompound().get("test");
      TagList valueList = tagList.get(0);
      assertEquals(3, ((TagInt) ((TagList) valueList.get(0)).get(0)).get());
    }

    { // should write AxionWritable as value
      LinkedHashMap<String, WritableVector> linkedHashMap = new LinkedHashMap<>();
      WritableVector vector = getTestWritableVector();
      linkedHashMap.put("vector", vector);
      out.write("test", linkedHashMap);
      TagList tagList = out.getTagCompound().get("test");
      TagList valueList = tagList.get(1);
      assertEquals(3, ((TagInt) ((TagCompound) valueList.get(0)).get("x")).get());
    }

    { // should write AxionWritable as key
      LinkedHashMap<WritableVector, String> linkedHashMap = new LinkedHashMap<>();
      WritableVector vector = getTestWritableVector();
      linkedHashMap.put(vector, "vector");
      out.write("test", linkedHashMap);
      TagList tagList = out.getTagCompound().get("test");
      TagList valueList = tagList.get(0);
      assertEquals(3, ((TagInt) ((TagCompound) valueList.get(0)).get("x")).get());
    }

    // should throw AxionWriteException if any key or value is null
    try {
      HashMap<String, Integer> map = new HashMap<>();
      map.put("yes", 42);
      map.put("yess", 73);
      map.put("no", null);
      out.write("test", map);
      fail();
    } catch (AxionWriteException e) {
      // expected
    }

    // should throw IllegalArgumentException on null name parameter
    try {
      out.write(null, new HashMap<>());
      fail();
    } catch (IllegalArgumentException e) {
      // expected
    }

    // should throw IllegalArgumentException on null map parameter
    try {
      out.write("test", (Map) null);
      fail();
    } catch (IllegalArgumentException e) {
      // expected
    }
  }

  @Test
  public void test_write_name_collection() {
    AxionWriter out = getTestWriter();

    { // should keep order
      Collection<Integer> collection = new ArrayList<>();
      for (int i = 0; i < 20; ++i) {
        collection.add(i);
      }
      out.write("test1", collection);
      TagList list = out.getTagCompound().get("test1");
      assertEquals(0, ((TagInt) list.get(0)).get());
      assertEquals(19, ((TagInt) list.get(19)).get());
    }

    { // should write axionWritable
      Collection<WritableVector> collection = new ArrayList<>();
      collection.add(getTestWritableVector());
      out.write("test2", collection);
      TagList list = out.getTagCompound().get("test2");
      TagCompound vector = list.get(0);
      assertEquals(3, ((TagInt) vector.get("x")).get());
      assertEquals(1, ((TagInt) vector.get("y")).get());
      assertEquals(4, ((TagInt) vector.get("z")).get());
    }

    { // should write mappable
      Collection<Vector> collection = new ArrayList<>();
      collection.add(getTestVector());
      out.write("test3", collection);
      TagList list = out.getTagCompound().get("test3");
      TagList vector = list.get(0);
      assertEquals(3, ((TagInt) vector.get(0)).get());
      assertEquals(1, ((TagInt) vector.get(1)).get());
      assertEquals(4, ((TagInt) vector.get(2)).get());
    }

    // should throw IllegalArgumentException on null name parameter
    try {
      out.write(null, new ArrayList<>());
      fail();
    } catch (IllegalArgumentException e) {
      // expected
    }

    // should throw IllegalArgumentException on null map parameter
    try {
      out.write("test", (Collection) null);
      fail();
    } catch (IllegalArgumentException e) {
      // expected
    }
  }

  public static class VectorConverter extends TypeConverter<TagList, Vector> {
    @Override
    public TagList convert(String name, Vector object) {
      TagList out = new TagList(TagInt.class, name);
      out.add(new TagInt(object.x));
      out.add(new TagInt(object.y));
      out.add(new TagInt(object.z));
      return out;
    }

    @Override
    public Vector convert(TagList tag) {
      Vector object = new Vector();
      object.x = ((TagInt) tag.get(0)).get();
      object.y = ((TagInt) tag.get(1)).get();
      object.z = ((TagInt) tag.get(2)).get();
      return object;
    }
  }

  public static class Vector {
    public int x, y, z;
  }

  public static class ArgsVector {
    public int x, y, z;

    public ArgsVector(int x, int y, int z) {
      this.x = x;
      this.y = y;
      this.z = z;
    }
  }

  public static class WritableArgsVector extends ArgsVector implements AxionWritable {
    public int x, y, z;

    public WritableArgsVector(int x, int y, int z) {
      super(x, y, z);
    }

    @Override
    public void write(AxionWriter out) {
      out.write("x", x).write("y", y).write("z", z);
    }

    @Override
    public void read(AxionReader in) {
      x = in.read("x");
      y = in.read("y");
      z = in.read("z");
    }
  }

  public static class WritableVector extends Vector implements AxionWritable {
    @Override
    public void write(AxionWriter out) {
      out.write("x", x).write("y", y).write("z", z);
    }

    @Override
    public void read(AxionReader in) {
      x = in.read("x");
      y = in.read("y");
      z = in.read("z");
    }
  }

  private TagList getTestMapTagList() {
    TagList map;
    TagList keyList, valueList;

    map = new TagList(TagList.class);
    keyList = new TagList(TagString.class);
    valueList = new TagList(TagInt.class);

    keyList.add(new TagString(null, "first"));
    keyList.add(new TagString(null, "second"));
    keyList.add(new TagString(null, "third"));
    keyList.add(new TagString(null, "fourth"));
    keyList.add(new TagString(null, "last"));

    valueList.add(new TagInt(0));
    valueList.add(new TagInt(1));
    valueList.add(new TagInt(2));
    valueList.add(new TagInt(3));
    valueList.add(new TagInt(4));

    map.add(keyList);
    map.add(valueList);

    return map;
  }

  private Vector getTestVector() {
    Vector vector = new Vector();
    vector.x = 3;
    vector.y = 1;
    vector.z = 4;
    return vector;
  }

  private TagList getTestTagList() {
    TagList t = new TagList(TagInt.class);
    Random random = new Random(42);
    for (int i = 0; i < 100; ++i) {
      t.add(new TagInt(random.nextInt(100) + 1));
    }
    return t;
  }

  private WritableVector getTestWritableVector() {
    WritableVector v = new WritableVector();
    v.x = 3;
    v.y = 1;
    v.z = 4;
    return v;
  }

  private TagCompound getTestTagCompound() {
    TagCompound t = new TagCompound();
    t.put("boolean", new TagBoolean(false));
    t.put("string", new TagString("someString"));
    t.put("int", new TagInt(42));
    t.put("vector", axion.convertValue(getTestVector()));
    t.put("list", getTestTagList());
    t.put("compound", getNestedTestTagCompound());
    t.put("map", getTestMapTagList());

    TagCompound out = new TagCompound();
    getTestWritableVector().write(axion.newWriter(out));
    t.put("writableVector", out);
    t.put("writableArgsVector", axion.convertValue(new WritableArgsVector(3, 1, 4)));
    return t;
  }

  private TagCompound getNestedTestTagCompound() {
    TagCompound t = new TagCompound();
    t.put("string", new TagString("someString"));
    t.put("int", new TagInt(42));
    t.put("vector", axion.convertValue(getTestVector()));
    t.put("list", getTestTagList());
    return t;
  }

  private AxionWriter getTestWriter() {
    return new DefaultAxionWriter(axion);
  }

}
