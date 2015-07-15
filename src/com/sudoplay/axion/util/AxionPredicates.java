package com.sudoplay.axion.util;

import java.util.function.Predicate;

/**
 * Common predicates.
 * <p>
 * Created by Jason Taylor on 7/13/2015.
 */
public class AxionPredicates {

  public static <O> Predicate<O> notNull() {
    return o -> o != null;
  }

  private AxionPredicates() {
    //
  }

}
