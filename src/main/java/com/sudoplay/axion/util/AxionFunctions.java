package com.sudoplay.axion.util;

import java.util.function.Function;

/**
 * Common functions.
 * <p>
 * Created by Jason Taylor on 7/13/2015.
 */
public class AxionFunctions {

  public static <V> Function<V, V> ifNullChangeTo(V defaultValue) {
    return v -> (v == null) ? defaultValue : v;
  }

  private AxionFunctions() {
    //
  }

}
