package com.sudoplay.axion.util;

import java.util.function.Consumer;

/**
 * Created by Jason Taylor on 7/14/2015.
 */
public class AxionConsumers {

  public static <V> Consumer<V> nullConsumer() {
    return v -> {
      // do nothing
    };
  }

}
