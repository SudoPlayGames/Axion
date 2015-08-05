package com.sudoplay.axion.converter;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.api.AxionReader;
import com.sudoplay.axion.api.AxionWritable;
import com.sudoplay.axion.api.AxionWriter;
import com.sudoplay.axion.spec.tag.TagList;
import com.sudoplay.axion.util.AxionTypeToken;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Type;
import java.util.*;

import static org.junit.Assert.assertEquals;

/**
 * Created by Jason Taylor on 7/20/2015.
 */
public class MapTypeConverterFactoryTest {

  private static Axion axion;

  @BeforeClass
  public static void before() {
    axion = Axion.createInstanceFrom(Axion.getExtInstance(), "MapTypeConverterFactoryTest");
  }

  @Test
  public void test() {

    List<Writable> listA = new ArrayList<>();
    listA.add(new Writable("1"));
    listA.add(new Writable("2"));

    List<Writable> listB = new ArrayList<>();
    listB.add(new Writable("A"));
    listB.add(new Writable("B"));

    Map<Writable, List<Writable>> map = new HashMap<>();
    map.put(new Writable("numbers"), listA);
    map.put(new Writable("letters"), listB);

    AxionWriter out = axion.newWriter();

    out.write("map", map);

    AxionReader in = axion.newReader(out.getTagCompound());

    Type type = new AxionTypeToken<HashMap<Writable, LinkedList<Writable>>>() {}.getType();
    Map<Writable, List<Writable>> newMap = in.read("map", type);

    // the maps should contain the same content
    assertEquals(map, newMap);

    // an ArrayList should go in ...
    assertEquals(ArrayList.class, map.get(new Writable("numbers")).getClass());

    // ... and a LinkedList should come out
    assertEquals(LinkedList.class, newMap.get(new Writable("numbers")).getClass());
  }

  @Test
  public void test_arrayValue() {

    Map<String, long[]> map = new HashMap<>();
    map.put("a", new long[]{3, 1, 4});
    map.put("b", new long[]{73, 21, 37, 12});

    AxionWriter out = axion.newWriter();

    out.write("map", map);

    Type type = new AxionTypeToken<HashMap<String, long[]>>() {}.getType();
    Map<String, long[]> newMap = axion.newReader(out.getTagCompound()).read("map", type);

    assertEquals(3, newMap.get("a")[0]);
    assertEquals(1, newMap.get("a")[1]);
    assertEquals(4, newMap.get("a")[2]);

    assertEquals(73, newMap.get("b")[0]);
    assertEquals(21, newMap.get("b")[1]);
    assertEquals(37, newMap.get("b")[2]);
    assertEquals(12, newMap.get("b")[3]);

    assertEquals(long[].class, newMap.get("a").getClass());
  }

  @Test
  public void test_enumMap() {

    Map<TestEnum, String> map = new EnumMap<>(TestEnum.class);
    map.put(TestEnum.A, "A");
    map.put(TestEnum.B, "B");

    TagList tagList = axion.toTag(map);

    Map<TestEnum, String> newMap = axion.fromTag(tagList, new AxionTypeToken<EnumMap<TestEnum, String>>() {}.getType());

    assertEquals(map, newMap);
  }

  public enum TestEnum {
    A, B
  }

  public static class Writable implements AxionWritable {

    private String name;

    public Writable(String name) {
      this.name = name;
    }

    @Override
    public void write(AxionWriter out) {
      out.write("name", name);
    }

    @Override
    public void read(AxionReader in) {
      name = in.read("name");
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof Writable)) return false;
      Writable writable = (Writable) o;
      return name.equals(writable.name);
    }

    @Override
    public int hashCode() {
      return name.hashCode();
    }

    @Override
    public String toString() {
      return "Writable{" +
          "name='" + name + '\'' +
          '}';
    }
  }

}
