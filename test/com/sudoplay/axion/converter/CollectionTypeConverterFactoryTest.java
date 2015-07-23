package com.sudoplay.axion.converter;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.tag.Tag;
import com.sudoplay.axion.util.AxionTypeToken;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Jason Taylor on 7/18/2015.
 */
public class CollectionTypeConverterFactoryTest {

  private static Axion axion;

  @BeforeClass
  public static void before() {
    axion = Axion.createInstanceFrom(Axion.getExtInstance(), "CollectionTypeConverterFactoryTest");

  }

  private List<Integer> getIntegerList() {
    List<Integer> list = new LinkedList<>();
    list.add(1);
    list.add(1);
    list.add(2);
    list.add(3);
    list.add(5);
    return list;
  }

  private List<List<Integer>> getNestedIntegerList() {
    List<List<Integer>> list = new ArrayList<>();
    for (int i = 0; i < 5; ++i) {
      list.add(getIntegerList());
    }
    return list;
  }

  @Test
  public void test_nested_lists() {
    Tag tag = axion.toTag(getNestedIntegerList());
    Type type = new AxionTypeToken<ArrayList<ArrayList<Integer>>>(){}.getType();
    List<List<Integer>> list = axion.fromTag(tag, type);
    assertEquals(5, list.size());
    list.forEach(integers -> assertEquals(5, integers.size()));
  }

}
