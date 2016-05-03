package com.sudoplay.axion.util;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * A utility class to find all super classes and super interfaces of a given class.
 *
 * @author Jason Taylor
 */
public class TypeResolver {

  private TypeResolver() {
    //
  }

  /**
   * Returns a set of all classes that the given class implements and/or extends.
   *
   * @param clazz the class to get super interfaces and super classes for
   * @return a set of all classes that the given class implements and/or extends
   */
  public static Set<Class<?>> getAllClasses(Class<?> clazz) {
    Set<Class<?>> set = new LinkedHashSet<>();
    Class<?> c = clazz;
    while (c != null) {
      set.add(c);
      getAllSuperInterfaces(c, set);
      c = c.getSuperclass();
    }
    return set;
  }

  /**
   * Adds all super interfaces of the given class to the given set. Returns the set.
   *
   * @param clazz the class to find all super interfaces for
   * @param set   the set to add the found interfaces to
   * @return the set given
   */
  private static Set<Class<?>> getAllSuperInterfaces(Class<?> clazz, Set<Class<?>> set) {
    for (Class<?> c : clazz.getInterfaces()) {
      set.add(c);
      getAllSuperInterfaces(c, set);
    }
    return set;
  }

}
