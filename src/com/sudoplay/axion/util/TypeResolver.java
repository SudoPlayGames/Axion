package com.sudoplay.axion.util;

import java.util.LinkedHashSet;
import java.util.Set;

public class TypeResolver {

  private TypeResolver() {
    //
  }

  public static Set<Class<?>> getAllClasses(Class<?> clazz) {
    Set<Class<?>> set = new LinkedHashSet<Class<?>>();
    Class<?> c = clazz;
    while (c != null) {
      set.add(c);
      getAllSuperInterfaces(c, set);
      c = c.getSuperclass();
    }
    return set;
  }

  private static Set<Class<?>> getAllSuperInterfaces(Class<?> clazz, Set<Class<?>> set) {
    for (Class<?> c : clazz.getInterfaces()) {
      set.add(c);
      getAllSuperInterfaces(c, set);
    }
    return set;
  }

}
