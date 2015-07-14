package com.sudoplay.axion;

import com.sudoplay.axion.api.AxionReader;
import com.sudoplay.axion.api.AxionWritable;
import com.sudoplay.axion.api.AxionWriter;
import com.sudoplay.axion.ext.tag.TagBoolean;
import com.sudoplay.axion.mapper.NBTObjectMapper;
import com.sudoplay.axion.spec.tag.TagCompound;
import com.sudoplay.axion.spec.tag.TagInt;
import com.sudoplay.axion.spec.tag.TagList;
import com.sudoplay.axion.spec.tag.TagLong;
import com.sudoplay.axion.tag.Tag;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class AxionTest {

  private static Axion axion;

  @BeforeClass
  public static void before() {
    if ((axion = Axion.getInstance("test")) == null) {
      axion = Axion.createInstanceFrom(Axion.getExtInstance(), "test");
    }
    axion.registerNBTObjectMapper(Vector.class, new VectorMapper());
  }

  @Test
  public void test_hasConverterForValueReturnsTrue_whenTrue() {
    Long value = 42L;
    assertTrue(axion.hasConverterFor(value));
  }

  @Test
  public void test_hasConverterForValueReturnsFalse_whenFalse() {
    Vector value = new Vector();
    assertFalse(axion.hasConverterFor(value));
  }

  @Test
  public void test_hasConverterForTagReturnsTrue_whenTrue() {
    TagLong tag = new TagLong(42L);
    assertTrue(axion.hasConverterFor(tag));
  }

  @Test
  public void test_hasConverterForTagReturnsFalse_whenFalse() {
    Tag tag = new Tag("tag") {
      @SuppressWarnings("CloneDoesntCallSuperClone")
      @Override
      public Tag clone() {
        return this;
      }
    };
    assertFalse(axion.hasConverterFor(tag));
  }

  @Test
  public void test_hasMapperForReturnsTrue_whenTrue() {
    assertTrue(axion.hasMapperFor(Vector.class));
  }

  @Test
  public void test_hasMapperForReturnsFalse_whenFalse() {
    assertFalse(axion.hasMapperFor(Date.class));
  }

  @Test
  public void test_writeObject_writesImplementationsOfAxionWritable() {
    TestClassWithNullaryConstructor testClass = new TestClassWithNullaryConstructor();
    testClass.aLong = 42;

    TagCompound tagCompound = axion.createTagFrom("testClass", testClass);
    long actual = tagCompound.getValue("aLong", axion);
    assertEquals(42L, actual);
  }

  @Test
  public void test_createTagFrom_readsImplementationOfAxionWritableWithNullaryConstructor() {
    TestClassWithNullaryConstructor testClass = new TestClassWithNullaryConstructor();
    testClass.aLong = 42;

    TagCompound tagCompound = axion.createTagFrom("testClass", testClass);
    TestClassWithNullaryConstructor newTestClass = axion.createFromTag(tagCompound, TestClassWithNullaryConstructor
        .class);
    assertEquals(testClass.aLong, newTestClass.aLong);
  }

  @Test
  public void test_createTagFrom_writesConvertibleObjects() {
    TagBoolean tag = axion.createTagFrom("aBoolean", true);
    assertEquals(true, tag.get());
  }

  @Test
  public void test_createTagFrom_readsConvertibleObjects() {
    TagBoolean tag = axion.createTagFrom("aBoolean", true);
    boolean b = axion.createFromTag(tag, boolean.class);
    assertEquals(true, b);
  }

  @Test
  public void test_createTagFrom_writesMappableObjects() {
    Vector v = new Vector();
    v.x = 42;
    v.y = 73;
    v.z = 31415;

    TagList list = axion.createTagFrom(v);
    assertEquals(42, ((TagInt) list.get(0)).get());
    assertEquals(73, ((TagInt) list.get(1)).get());
    assertEquals(31415, ((TagInt) list.get(2)).get());
  }

  @Test
  public void test_createTagFrom_readsMappableObjects() {
    Vector v = new Vector();
    v.x = 42;
    v.y = 73;
    v.z = 31415;

    TagList list = axion.createTagFrom(v);
    Vector newV = axion.createFromTag(list, Vector.class);
    assertEquals(v.x, newV.x);
    assertEquals(v.y, newV.y);
    assertEquals(v.z, newV.z);
  }

  public static class VectorMapper implements NBTObjectMapper<TagList, Vector> {
    @Override
    public TagList createTagFrom(String name, Vector object, Axion axion) {
      TagList out = new TagList(TagInt.class);
      out.add(new TagInt(object.x));
      out.add(new TagInt(object.y));
      out.add(new TagInt(object.z));
      return out;
    }

    @Override
    public Vector createObjectFrom(TagList tag, Axion axion) {
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

  public static class TestClassWithNullaryConstructor implements AxionWritable {

    public boolean aBoolean;
    public long aLong;

    public TestClassWithNullaryConstructor() {
      // must have nullary constructor or (Tag, Axion) constructor
    }

    @Override
    public void write(AxionWriter out) {
      out.write("aBoolean", aBoolean);
      out.write("aLong", aLong);
    }

    @Override
    public void read(AxionReader in) {
      aBoolean = in.read("aBoolean");
      aLong = in.read("aLong");
    }
  }

  private String getTestString() {
    return "TagCompound(\"Level\"): 11 entries\n{\n  TagShort(\"shortTest\"): 32767\n  TagLong(\"longTest\"): " +
        "9223372036854775807\n  TagFloat(\"floatTest\"): 0.49823147\n  TagString(\"stringTest\"): HELLO WORLD THIS IS" +
        " A TEST STRING ÅÄÖ!\n  TagInt(\"intTest\"): 2147483647\n  TagCompound(\"nested compound test\"): 2 entries\n" +
        "  {\n    TagCompound(\"ham\"): 2 entries\n    {\n      TagString(\"name\"): Hampus\n      TagFloat" +
        "(\"value\"): 0.75\n    }\n    TagCompound(\"egg\"): 2 entries\n    {\n      TagString(\"name\"): Eggbert\n  " +
        "    TagFloat(\"value\"): 0.5\n    }\n  }\n  TagList(\"listTest (long)\"): 5 entries of type TagLong\n  {\n  " +
        "  TagLong: 11\n    TagLong: 12\n    TagLong: 13\n    TagLong: 14\n    TagLong: 15\n  }\n  TagByte" +
        "(\"byteTest\"): 127\n  TagList(\"listTest (compound)\"): 2 entries of type TagCompound\n  {\n    " +
        "TagCompound: 2 entries\n    {\n      TagString(\"name\"): Compound tag #0\n      TagLong(\"created-on\"): " +
        "1264099775885\n    }\n    TagCompound: 2 entries\n    {\n      TagString(\"name\"): Compound tag #1\n      " +
        "TagLong(\"created-on\"): 1264099775885\n    }\n  }\n  TagByteArray(\"byteArrayTest (the first 1000 values of" +
        " (n*n*255+n*7)%100, starting with n=0 (0, 62, 34, 16, 8, ...))\"): [1000 bytes]\n  TagDouble(\"doubleTest\")" +
        ": 0.4931287132182315\n}\n";
  }

}
