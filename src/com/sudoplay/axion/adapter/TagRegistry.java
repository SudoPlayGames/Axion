package com.sudoplay.axion.adapter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.sudoplay.axion.tag.Tag;
import com.sudoplay.axion.util.TypeResolver;

public class TagRegistry implements Cloneable {

  private final Map<Class<? extends Tag>, Integer> classToId = new HashMap<Class<? extends Tag>, Integer>();
  private final Map<Integer, Class<? extends Tag>> idToClass = new HashMap<Integer, Class<? extends Tag>>();
  private final Map<Class<? extends Tag>, TagAdapter<? extends Tag>> classToAdapter = new HashMap<Class<? extends Tag>, TagAdapter<? extends Tag>>();
  private final Map<Integer, TagAdapter<? extends Tag>> idToAdapter = new HashMap<Integer, TagAdapter<? extends Tag>>();
  private final Map<Class<? extends Tag>, TagConverter<? extends Tag, ?>> classToConverter = new HashMap<Class<? extends Tag>, TagConverter<? extends Tag, ?>>();
  private final Map<Class<?>, TagConverter<? extends Tag, ?>> typeToConverter = new HashMap<Class<?>, TagConverter<? extends Tag, ?>>();
  private TagAdapter<Tag> baseTagAdapter;

  public TagRegistry() {
    //
  }

  protected TagRegistry(final TagRegistry toCopy) {
    classToId.putAll(toCopy.classToId);
    idToClass.putAll(toCopy.idToClass);
    Iterator<Entry<Class<? extends Tag>, TagAdapter<? extends Tag>>> it1 = toCopy.classToAdapter.entrySet().iterator();
    while (it1.hasNext()) {
      Entry<Class<? extends Tag>, TagAdapter<? extends Tag>> entry = it1.next();
      classToAdapter.put(entry.getKey(), entry.getValue().newInstance(this));
    }
    Iterator<Entry<Integer, TagAdapter<? extends Tag>>> it2 = toCopy.idToAdapter.entrySet().iterator();
    while (it2.hasNext()) {
      Entry<Integer, TagAdapter<? extends Tag>> entry = it2.next();
      idToAdapter.put(entry.getKey(), entry.getValue().newInstance(this));
    }
    Iterator<Entry<Class<? extends Tag>, TagConverter<? extends Tag, ?>>> it3 = toCopy.classToConverter.entrySet().iterator();
    while (it3.hasNext()) {
      Entry<Class<? extends Tag>, TagConverter<? extends Tag, ?>> entry = it3.next();
      classToConverter.put(entry.getKey(), entry.getValue().newInstance(this));
    }
    Iterator<Entry<Class<?>, TagConverter<? extends Tag, ?>>> it4 = toCopy.typeToConverter.entrySet().iterator();
    while (it4.hasNext()) {
      Entry<Class<?>, TagConverter<? extends Tag, ?>> entry = it4.next();
      typeToConverter.put(entry.getKey(), entry.getValue().newInstance(this));
    }
    baseTagAdapter = toCopy.baseTagAdapter.newInstance(this);
  }

  public void registerBaseTagAdapter(final TagAdapter<Tag> newBaseTagAdapter) {
    baseTagAdapter = newBaseTagAdapter.newInstance(this);
  }

  public <T extends Tag, V> void register(final int id, final Class<T> tagClass, final Class<V> type, final TagAdapter<T> adapter,
      final TagConverter<T, V> converter) throws AxionTagRegistrationException {

    if (classToAdapter.containsKey(tagClass) || classToId.containsKey(tagClass)) {
      throw new AxionTagRegistrationException("Tag class already registered: " + tagClass.getSimpleName());
    } else if (idToAdapter.containsKey(id) || idToClass.containsKey(id)) {
      throw new AxionTagRegistrationException("Tag id already registered: " + id);
    } else if (classToConverter.containsKey(tagClass)) {
      throw new AxionTagRegistrationException("Converter already registered for class: " + tagClass.getSimpleName());
    } else if (typeToConverter.containsKey(type)) {
      throw new AxionTagRegistrationException("Converter already registered for type: " + type.getSimpleName());
    }

    classToId.put(tagClass, id);
    idToClass.put(id, tagClass);

    TagAdapter<T> newAdapterInstance = adapter.newInstance(this);
    classToAdapter.put(tagClass, newAdapterInstance);
    idToAdapter.put(id, newAdapterInstance);

    TagConverter<T, V> newConverterInstance = converter.newInstance(this);
    classToConverter.put(tagClass, newConverterInstance);
    typeToConverter.put(type, newConverterInstance);
  }

  public TagAdapter<Tag> getBaseTagAdapter() {
    return baseTagAdapter;
  }

  @SuppressWarnings("unchecked")
  public <T extends Tag> TagAdapter<T> getAdapterFor(final int id) throws AxionTagRegistrationException {
    TagAdapter<T> result;
    if ((result = (TagAdapter<T>) idToAdapter.get(id)) == null) {
      throw new AxionTagRegistrationException("No adapter registered for id: " + id);
    }
    return result;
  }

  @SuppressWarnings("unchecked")
  public <T extends Tag> TagAdapter<T> getAdapterFor(final Class<T> tagClass) throws AxionTagRegistrationException {
    TagAdapter<T> result;
    if ((result = (TagAdapter<T>) classToAdapter.get(tagClass)) == null) {
      throw new AxionTagRegistrationException("No adapter registered for class: " + tagClass.getSimpleName());
    }
    return result;
  }

  public Integer getIdFor(final Class<? extends Tag> tagClass) throws AxionTagRegistrationException {
    Integer result;
    if ((result = classToId.get(tagClass)) == null) {
      throw new AxionTagRegistrationException("No id registered for tag class: " + tagClass.getSimpleName());
    }
    return result;
  }

  public Class<? extends Tag> getClassFor(final int id) throws AxionTagRegistrationException {
    Class<? extends Tag> result;
    if ((result = idToClass.get(id)) == null) {
      throw new AxionTagRegistrationException("No class registered for tag id: " + id);
    }
    return result;
  }

  @SuppressWarnings("unchecked")
  public <T extends Tag, V> TagConverter<T, V> getConverterForTag(final Class<T> tagClass) {
    if (tagClass == null) {
      return null;
    } else if (!classToConverter.containsKey(tagClass)) {
      throw new AxionTagRegistrationException("No converter registered for tag class: " + tagClass);
    }
    return (TagConverter<T, V>) classToConverter.get(tagClass);
  }

  @SuppressWarnings("unchecked")
  public <T extends Tag, V> TagConverter<T, V> getConverterForValue(final Class<V> valueClass) {
    if (valueClass == null) {
      return null;
    }
    TagConverter<T, V> converter = (TagConverter<T, V>) typeToConverter.get(valueClass);
    if (converter == null) {
      for (Class<?> c : TypeResolver.getAllClasses(valueClass)) {
        if (typeToConverter.containsKey(c)) {
          try {
            converter = (TagConverter<T, V>) typeToConverter.get(c);
            break;
          } catch (ClassCastException e) {
            //
          }
        }
      }
    }
    if (converter == null) {
      throw new AxionTagRegistrationException("No converter registered for type: " + valueClass.getSimpleName());
    }
    return converter;
  }

  @Override
  public TagRegistry clone() {
    return new TagRegistry(this);
  }

}
