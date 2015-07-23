/*
 * Copyright (C) 2013 Jason Taylor.
 * Released as open-source under the Apache License, Version 2.0.
 * 
 * ============================================================================
 * | Juple
 * ============================================================================
 * 
 * Copyright (C) 2013 Jason Taylor
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * ============================================================================
 * | Gson
 * | --------------------------------------------------------------------------
 * | Juple is a derivative work based on Google's Gson library:
 * | https://code.google.com/p/google-gson/
 * ============================================================================
 * 
 * Copyright (C) 2008 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sudoplay.axion.system;

import com.sudoplay.axion.util.AxionTypeToken;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * Returns a function that can construct an instance of a requested type.
 */
public final class ConstructorConstructor {
  private final Map<Type, InstanceCreator<?>> instanceCreators;

  public ConstructorConstructor(
      Map<Type, InstanceCreator<?>> instanceCreators
  ) {
    this.instanceCreators = instanceCreators;
  }

  public ConstructorConstructor() {
    this(Collections.<Type, InstanceCreator<?>>emptyMap());
  }

  public void registerInstanceCreator(Type type, InstanceCreator<?> instanceCreator) {
    instanceCreators.put(type, instanceCreator);
  }

  public <T> ObjectConstructor<T> get(
      AxionTypeToken<T> typeToken
  ) {
    final Type type = typeToken.getType();
    final Class<? super T> rawType = typeToken.getRawType();

    // first try an instance creator

    @SuppressWarnings("unchecked")
    // types must agree
    final InstanceCreator<T> creator = (InstanceCreator<T>) instanceCreators.get(type);
    if (creator != null) {
      return () -> creator.createInstance(type);
    }

    // Next try raw type match for instance creators
    @SuppressWarnings("unchecked")
    // types must agree
    final InstanceCreator<T> rawTypeCreator = (InstanceCreator<T>) instanceCreators.get(rawType);
    if (rawTypeCreator != null) {
      return () -> rawTypeCreator.createInstance(type);
    }

    ObjectConstructor<T> defaultConstructor = newDefaultConstructor(rawType);
    if (defaultConstructor != null) {
      return defaultConstructor;
    }

    // finally try unsafe
    return newUnsafeAllocator(type, rawType);
  }

  @SuppressWarnings("unchecked")
  private <T> ObjectConstructor<T> newDefaultConstructor(
      Class<? super T> rawType
  ) {
    try {
      final Constructor<? super T> constructor = rawType
          .getDeclaredConstructor();
      if (!constructor.isAccessible()) {
        constructor.setAccessible(true);
      }
      return () -> {
        try {
          return (T) constructor.newInstance((Object[]) null);
        } catch (InstantiationException e) {
          // TODO: JsonParseException ?
          throw new RuntimeException("Failed to invoke " + constructor
              + " with no args", e);
        } catch (InvocationTargetException e) {
          // TODO: don't wrap if cause is unchecked!
          // TODO: JsonParseException ?
          throw new RuntimeException("Failed to invoke " + constructor
              + " with no args", e.getTargetException());
        } catch (IllegalAccessException e) {
          throw new AssertionError(e);
        }
      };
    } catch (NoSuchMethodException e) {
      return null;
    }
  }

  /**
   * Constructors for common interface types like Map and List and their subytpes.
   */
  @SuppressWarnings("unchecked")
  // use runtime checks to guarantee that 'T' is what it is
  private <T> ObjectConstructor<T> newDefaultImplementationConstructor(
      final Type type, Class<? super T> rawType) {

    if (Collection.class.isAssignableFrom(rawType)) {

      if (SortedSet.class.isAssignableFrom(rawType)) {
        return () -> (T) new TreeSet<>();

      } else if (EnumSet.class.isAssignableFrom(rawType)) {
        return () -> {
          if (type instanceof ParameterizedType) {
            Type elementType = ((ParameterizedType) type).getActualTypeArguments()[0];
            if (elementType instanceof Class) {
              return (T) EnumSet.noneOf((Class) elementType);
            } else {
              throw new IllegalArgumentException("Invalid EnumSet type: " + type.toString());
            }
          } else {
            throw new IllegalArgumentException("Invalid EnumSet type: " + type.toString());
          }
        };

      } else if (Set.class.isAssignableFrom(rawType)) {
        return () -> (T) new LinkedHashSet<>();

      } else if (Queue.class.isAssignableFrom(rawType)) {
        return () -> (T) new LinkedList<>();

      } else {
        return () -> (T) new ArrayList<>();
      }
    }

    if (Map.class.isAssignableFrom(rawType)) {

      if (SortedMap.class.isAssignableFrom(rawType)) {
        return () -> (T) new TreeMap<>();

      } else if (type instanceof ParameterizedType) {
        Type[] types = ((ParameterizedType) type).getActualTypeArguments();
        AxionTypeToken typeToken = AxionTypeToken.get(types[0]);
        if (!(String.class.isAssignableFrom(typeToken.getRawType()))) {
          return () -> (T) new LinkedHashMap<>();
        }

      }
    }

    return null;
  }

  private <T> ObjectConstructor<T> newUnsafeAllocator(
      final Type type,
      final Class<? super T> rawType
  ) {
    return new ObjectConstructor<T>() {
      private final UnsafeAllocator unsafeAllocator = UnsafeAllocator.create();

      @SuppressWarnings("unchecked")
      public T construct() {
        try {
          Object newInstance = unsafeAllocator.newInstance(rawType);
          return (T) newInstance;
        } catch (Exception e) {
          throw new RuntimeException(
              "Unable to invoke no-args constructor for " + type
                  + ". Registering a TMLInstanceCreator for this"
                  + " type may fix this problem.",
              e
          );
        }
      }
    };
  }

  @Override
  public String toString() {
    return instanceCreators.toString();
  }
}