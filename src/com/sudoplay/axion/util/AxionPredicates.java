package com.sudoplay.axion.util;

import java.util.function.Predicate;

/**
 * Common predicates.
 * <p>
 * Created by Jason Taylor on 7/13/2015.
 */
public class AxionPredicates {

  public static <O> Predicate<O> isNotNull() {
    return o -> o != null;
  }

  public static <O> Predicate<O> alwaysFalse() {
    return o -> false;
  }

  public static <O> Predicate<O> alwaysTrue() {
    return o -> true;
  }

  public static <O> Predicate<O> isTrue(boolean b) {
    return o -> b;
  }

  public static <O> Predicate<O> isFalse(boolean b) {
    return o -> !b;
  }

  private AxionPredicates() {
    //
  }

}
