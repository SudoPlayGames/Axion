package com.sudoplay.axion.converter;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.spec.tag.TagString;
import com.sudoplay.axion.tag.Tag;
import com.sudoplay.axion.util.AxionTypeToken;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Jason Taylor on 7/18/2015.
 */
public class EnumTypeConverterFactoryTest {

  private static Axion axion;

  @BeforeClass
  public static void before() {
    axion = Axion.createInstanceFrom(Axion.getExtInstance(), "EnumTypeConverterFactoryTest");
    axion.registerConverterFactory(new EnumTypeConverterFactory());
  }

  @Test
  public void test() {
    TestEnum te = TestEnum.A;

    assertEquals(new TagString(null, "A"), axion.toTag(te));

    TestEnum nte = axion.fromTag(new TagString(null, "A"), TestEnum.class);
    assertEquals(TestEnum.A, nte);

  }

  private enum TestEnum {
    A, B
  }

}
