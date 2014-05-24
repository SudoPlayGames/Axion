package com.sudoplay.axion.adapter;

import java.util.HashMap;
import java.util.Map;

import com.sudoplay.axion.spec.tag.Tag;

public class TagAdapterRegistry {

  private static final Map<Class<? extends Tag>, TagAdapter> classToAdapter = new HashMap<Class<? extends Tag>, TagAdapter>();
  private static final Map<Integer, TagAdapter> idToAdapter = new HashMap<Integer, TagAdapter>();

  private TagAdapterRegistry() {
    //
  }

  public static void register(final int id, final Class<? extends Tag> tagClass, final TagAdapter adapter) {
    if (classToAdapter.containsKey(tagClass)) {
      throw new IllegalArgumentException("Tag class already registered: " + tagClass.getSimpleName());
    } else if (idToAdapter.containsKey(id)) {
      throw new IllegalArgumentException("Tag id already registered: " + id);
    }
    classToAdapter.put(tagClass, adapter);
    idToAdapter.put(id, adapter);
  }

  public static TagAdapter getAdapterFor(final int id) {
    TagAdapter result;
    if ((result = idToAdapter.get(id)) == null) {
      throw new IllegalArgumentException("No adapter registered for id: " + id);
    }
    return result;
  }

  public static TagAdapter getAdapterFor(final Class<? extends Tag> tagClass) {
    TagAdapter result;
    if ((result = classToAdapter.get(tagClass)) == null) {
      throw new IllegalArgumentException("No adapter registered for class: " + tagClass.getSimpleName());
    }
    return result;
  }

}
