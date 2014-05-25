package com.sudoplay.axion.adapter;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import com.sudoplay.axion.spec.tag.Tag;

public class TagAdapterRegistry {

  private final Map<Class<? extends Tag>, Integer> classToId = new HashMap<Class<? extends Tag>, Integer>();
  private final Map<Integer, Class<? extends Tag>> idToClass = new HashMap<Integer, Class<? extends Tag>>();
  private final Map<Class<? extends Tag>, TagAdapter<? extends Tag>> classToAdapter = new HashMap<Class<? extends Tag>, TagAdapter<? extends Tag>>();
  private final Map<Integer, TagAdapter<? extends Tag>> idToAdapter = new HashMap<Integer, TagAdapter<? extends Tag>>();

  public <T extends Tag> void register(final int id, final Class<T> tagClass, final TagAdapter<T> adapter) {
    if (classToAdapter.containsKey(tagClass) || classToId.containsKey(tagClass)) {
      throw new IllegalArgumentException("Tag class already registered: " + tagClass.getSimpleName());
    } else if (idToAdapter.containsKey(id) || idToClass.containsKey(id)) {
      throw new IllegalArgumentException("Tag id already registered: " + id);
    }
    classToAdapter.put(tagClass, adapter);
    idToAdapter.put(id, adapter);
    classToId.put(tagClass, id);
    idToClass.put(id, tagClass);
  }

  @SuppressWarnings("unchecked")
  public <T extends Tag> TagAdapter<T> getAdapterFor(final int id) {
    TagAdapter<T> result;
    if ((result = (TagAdapter<T>) idToAdapter.get(id)) == null) {
      throw new IllegalArgumentException("No adapter registered for id: " + id);
    }
    return result;
  }

  @SuppressWarnings("unchecked")
  public <T extends Tag> TagAdapter<T> getAdapterFor(final Class<T> tagClass) {
    TagAdapter<T> result;
    if ((result = (TagAdapter<T>) classToAdapter.get(tagClass)) == null) {
      throw new IllegalArgumentException("No adapter registered for class: " + tagClass.getSimpleName());
    }
    return result;
  }

  public Integer getIdFor(final Class<? extends Tag> tagClass) {
    Integer result;
    if ((result = classToId.get(tagClass)) == null) {
      throw new IllegalArgumentException("No id registered for tag class: " + tagClass.getSimpleName());
    }
    return result;
  }

  public Class<? extends Tag> getClassFor(final int id) {
    Class<? extends Tag> result;
    if ((result = idToClass.get(id)) == null) {
      throw new IllegalArgumentException("No class registered for tag id: " + id);
    }
    return result;
  }

  public Tag createInstance(final int id, final String newName) {
    return createInstance(getClassFor(id), newName);
  }

  public <T extends Tag> T createInstance(final Class<T> tagClass, final String newName) {
    try {
      Constructor<T> constructor = tagClass.getDeclaredConstructor(String.class);
      constructor.setAccessible(true);
      return (T) constructor.newInstance(newName);
    } catch (Exception e) {
      throw new RuntimeException("Failed to create instance for tag " + tagClass.getSimpleName(), e);
    }
  }

}
