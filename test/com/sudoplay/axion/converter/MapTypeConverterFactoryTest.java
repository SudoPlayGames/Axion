package com.sudoplay.axion.converter;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.api.AxionReader;
import com.sudoplay.axion.api.AxionWritable;
import com.sudoplay.axion.api.AxionWriter;
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

  private Map<Writable, List<Writable>> map = new HashMap<>();

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

    map.put(new Writable("numbers"), listA);
    map.put(new Writable("letters"), listB);

    AxionWriter out = axion.newWriter();

    out.write("map", map);

    AxionReader in = axion.newReader(out.getTagCompound());

    Type type = new AxionTypeToken<HashMap<Writable, LinkedList<Writable>>>() {
    }.getType();
    Map<Writable, List<Writable>> newMap = in.read("map", type);

    assertEquals(map, newMap);
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
