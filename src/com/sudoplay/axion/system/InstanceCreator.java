package com.sudoplay.axion.system;

import java.lang.reflect.Type;

/**
 * Created by Jason Taylor on 7/17/2015.
 */
public interface InstanceCreator<V> {

  V createInstance(Type type);

}
