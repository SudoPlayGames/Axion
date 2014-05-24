package com.sudoplay.axion.tag;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import com.sudoplay.axion.spec.tag.Tag;

public class TagRegistry {

  private static final Map<Class<? extends Tag>, Integer> classToId = new HashMap<Class<? extends Tag>, Integer>();
  private static final Map<Integer, Class<? extends Tag>> idToClass = new HashMap<Integer, Class<? extends Tag>>();

  private TagRegistry() {
    //
  }

  public static void register(final int id, final Class<? extends Tag> tagClass) {
    if (classToId.containsKey(tagClass)) {
      throw new IllegalArgumentException("Tag class already registered: " + tagClass.getSimpleName());
    } else if (idToClass.containsKey(id)) {
      throw new IllegalArgumentException("Tag id already registered: " + id);
    }
    classToId.put(tagClass, id);
    idToClass.put(id, tagClass);
  }

  public static int getIdFor(final Class<? extends Tag> tagClass) {
    Integer result;
    if ((result = classToId.get(tagClass)) == null) {
      throw new IllegalArgumentException("No id registered for tag class: " + tagClass.getSimpleName());
    }
    return result;
  }

  public static Class<? extends Tag> getClassFor(final int id) {
    Class<? extends Tag> result;
    if ((result = idToClass.get(id)) == null) {
      throw new IllegalArgumentException("No class registered for tag id: " + id);
    }
    return result;
  }

  public static Tag createInstance(final int id, final String newName) {
    return createInstance(getClassFor(id), newName);
  }

  public static Tag createInstance(final Class<? extends Tag> tagClass, final String newName) {
    try {
      Constructor<? extends Tag> constructor = tagClass.getDeclaredConstructor(String.class);
      constructor.setAccessible(true);
      return constructor.newInstance(newName);
    } catch (Exception e) {
      throw new RuntimeException("Failed to create instance for tag " + tagClass.getSimpleName(), e);
    }
  }

}
