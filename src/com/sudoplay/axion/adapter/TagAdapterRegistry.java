package com.sudoplay.axion.adapter;

import java.util.HashMap;
import java.util.Map;

import com.sudoplay.axion.spec.tag.Tag;

public class TagAdapterRegistry implements Cloneable {

  private final Map<Class<? extends Tag>, Integer> classToId = new HashMap<Class<? extends Tag>, Integer>();
  private final Map<Integer, Class<? extends Tag>> idToClass = new HashMap<Integer, Class<? extends Tag>>();
  private final Map<Class<? extends Tag>, TagAdapter<? extends Tag>> classToAdapter = new HashMap<Class<? extends Tag>, TagAdapter<? extends Tag>>();
  private final Map<Integer, TagAdapter<? extends Tag>> idToAdapter = new HashMap<Integer, TagAdapter<? extends Tag>>();

  public TagAdapterRegistry() {
    //
  }

  protected TagAdapterRegistry(final TagAdapterRegistry toCopy) {
    classToId.putAll(toCopy.classToId);
    idToClass.putAll(toCopy.idToClass);
    classToAdapter.putAll(toCopy.classToAdapter);
    idToAdapter.putAll(toCopy.idToAdapter);
  }

  public <T extends Tag> void register(final int id, final Class<T> tagClass, final TagAdapter<T> adapter) throws AxionAdapterRegistrationException {
    if (classToAdapter.containsKey(tagClass) || classToId.containsKey(tagClass)) {
      throw new AxionAdapterRegistrationException("Tag class already registered: " + tagClass.getSimpleName());
    } else if (idToAdapter.containsKey(id) || idToClass.containsKey(id)) {
      throw new AxionAdapterRegistrationException("Tag id already registered: " + id);
    }
    classToAdapter.put(tagClass, adapter);
    idToAdapter.put(id, adapter);
    classToId.put(tagClass, id);
    idToClass.put(id, tagClass);
  }

  @SuppressWarnings("unchecked")
  public <T extends Tag> TagAdapter<T> getAdapterFor(final int id) throws AxionAdapterRegistrationException {
    TagAdapter<T> result;
    if ((result = (TagAdapter<T>) idToAdapter.get(id)) == null) {
      throw new AxionAdapterRegistrationException("No adapter registered for id: " + id);
    }
    return result;
  }

  @SuppressWarnings("unchecked")
  public <T extends Tag> TagAdapter<T> getAdapterFor(final Class<T> tagClass) throws AxionAdapterRegistrationException {
    TagAdapter<T> result;
    if ((result = (TagAdapter<T>) classToAdapter.get(tagClass)) == null) {
      throw new AxionAdapterRegistrationException("No adapter registered for class: " + tagClass.getSimpleName());
    }
    return result;
  }

  public Integer getIdFor(final Class<? extends Tag> tagClass) throws AxionAdapterRegistrationException {
    Integer result;
    if ((result = classToId.get(tagClass)) == null) {
      throw new AxionAdapterRegistrationException("No id registered for tag class: " + tagClass.getSimpleName());
    }
    return result;
  }

  public Class<? extends Tag> getClassFor(final int id) throws AxionAdapterRegistrationException {
    Class<? extends Tag> result;
    if ((result = idToClass.get(id)) == null) {
      throw new AxionAdapterRegistrationException("No class registered for tag id: " + id);
    }
    return result;
  }

  @Override
  public TagAdapterRegistry clone() {
    return new TagAdapterRegistry(this);
  }

}
