package com.sudoplay.axion.util;

import java.lang.reflect.Type;

/**
 * This class contains static methods for asserting various conditions and throwing various exceptions.
 * <p>
 * Created by Jason Taylor on 7/16/2015.
 *
 * @author Jason Taylor
 */
public class AxionContract {

  /**
   * Throws a {@link NullPointerException} if the supplied object is null.
   *
   * @param o object
   * @return the supplied object
   */
  public static <O> O assertNotNull(O o, String message) {
    if (o == null) throw new NullPointerException(message);
    return o;
  }

  /**
   * Throws an {@link IllegalArgumentException} if the supplied condition is false.
   *
   * @param condition condition
   */
  public static void assertArgument(boolean condition, String message) {
    if (!condition) throw new IllegalArgumentException(message);
  }

  /**
   * Throws an {@link IllegalArgumentException} if the supplied predicate is false.
   *
   * @param o object
   */
  public static <O> void assertArgumentNotNull(O o, String message) {
    if (o == null) throw new IllegalArgumentException("Argument can't be null: " + message);
  }

  /**
   * Throws an {@link IllegalStateException} if the supplied condition is false.
   *
   * @param condition condition
   */
  public static void assertState(boolean condition, String message) {
    if (!condition) throw new IllegalStateException(message);
  }

  /**
   * Asserts that the supplied type is not a primitive type.
   *
   * @param type type
   */
  public static void checkNotPrimitive(Type type, String message) {
    assertArgument(!(type instanceof Class<?>) || !((Class<?>) type).isPrimitive(), message);
  }

  private AxionContract() {
    //
  }

}