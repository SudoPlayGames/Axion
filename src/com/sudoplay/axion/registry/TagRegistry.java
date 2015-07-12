package com.sudoplay.axion.registry;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sudoplay.axion.AxionInstanceException;
import com.sudoplay.axion.tag.Tag;
import com.sudoplay.axion.util.TypeResolver;

/**
 * The {@link TagRegistry} class is responsible for maintaining relationships
 * between integer ids, {@link Tag} classes, {@link Tag} value classes,
 * {@link TagAdapter}s, and {@link TagConverter}s and providing lookup methods
 * to access the data via its relationships.
 * 
 * @author Jason Taylor
 */
public class TagRegistry implements Cloneable {

  private static final Logger LOG = LoggerFactory.getLogger(TagRegistry.class);

  /**
   * Maps {@link Tag} classes to their respective integer ids.
   */
  private final Map<Class<? extends Tag>, Integer> classToId = new HashMap<Class<? extends Tag>, Integer>();

  /**
   * Maps integer ids to their respective {@link Tag} classes.
   */
  private final Map<Integer, Class<? extends Tag>> idToClass = new HashMap<Integer, Class<? extends Tag>>();

  /**
   * Maps {@link Tag} classes the their respective {@link TagAdapter}s.
   */
  private final Map<Class<? extends Tag>, TagAdapter<? extends Tag>> classToAdapter = new HashMap<Class<? extends Tag>, TagAdapter<? extends Tag>>();

  /**
   * Maps integer ids to their respective {@link TagAdapter}s.
   */
  private final Map<Integer, TagAdapter<? extends Tag>> idToAdapter = new HashMap<Integer, TagAdapter<? extends Tag>>();

  /**
   * Maps {@link Tag} classes to their respective {@link TagConverter}s.
   */
  private final Map<Class<? extends Tag>, TagConverter<? extends Tag, ?>> classToConverter = new HashMap<Class<? extends Tag>, TagConverter<? extends Tag, ?>>();

  /**
   * Maps value classes to their respective {@link TagConverter}s.
   */
  private final Map<Class<?>, TagConverter<? extends Tag, ?>> typeToConverter = new HashMap<Class<?>, TagConverter<? extends Tag, ?>>();

  /**
   * The base {@link TagAdapter}; used as an entry point when reading and
   * writing a {@link Tag} hierarchy.
   */
  private TagAdapter<Tag> baseTagAdapter;

  /**
   * Creates a new, empty {@link TagRegistry}.
   */
  public TagRegistry() {
    LOG.debug("Created empty TagRegistry [{}]", this);
  }

  /**
   * Creates a new {@link TagRegistry} by copying the registry given.
   * <p>
   * All {@link TagAdapter}s and {@link TagConverter}s are duplicated and
   * assigned a reference to this registry.
   * 
   * @param toCopy
   * @throws AxionInstanceException
   */
  protected TagRegistry(final TagRegistry toCopy) throws AxionInstanceException {
    LOG.debug("Entering TagRegistry(toCopy=[{}])", toCopy);
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
    LOG.debug("Leaving TagRegistry(): [{}]", this);
  }

  /**
   * Registers a {@link TagAdapter} as the base tag adapter. The base tag
   * adapter is the entry point for reading and writing a tag hierarchy.
   * 
   * @param newBaseTagAdapter
   * @throws AxionInstanceException
   */
  public void registerBaseTagAdapter(final TagAdapter<Tag> newBaseTagAdapter) throws AxionInstanceException {
    LOG.debug("Entering registerBaseTagAdapter(newBaseTagAdapter=[{}])", newBaseTagAdapter);
    baseTagAdapter = newBaseTagAdapter.newInstance(this);
    LOG.debug("Leaving registerBaseTagAdapter()");
  }

  /**
   * Registers a {@link TagAdapter} and {@link TagConverter} with an id, tag
   * class, and value class. Tags must be registered to be recognized by the
   * system.
   * <p>
   * An exception is thrown if a duplicate id, tag class, or value class is
   * registered.
   * 
   * @param id
   *          the int id of the {@link Tag}
   * @param tagClass
   *          the class of the {@link Tag}
   * @param type
   *          the class of the value represented by the tag
   * @param adapter
   *          the {@link TagAdapter} for the {@link Tag}
   * @param converter
   *          the {@link TagConverter} for the {@link Tag}
   * @throws AxionTagRegistrationException
   */
  public <T extends Tag, V> void register(final int id, final Class<T> tagClass, final Class<V> type, final TagAdapter<T> adapter,
      final TagConverter<T, V> converter) throws AxionTagRegistrationException, AxionInstanceException {

    LOG.debug("Entering register(id=[{}], tagClass=[{}], type=[{}], adapter=[{}], converter=[{}])", id, tagClass, type, adapter, converter);

    if (tagClass == null) {
      LOG.error("Can't register a null tag class");
      throw new AxionTagRegistrationException("Can't register a null tag class");
    } else if (type == null) {
      LOG.error("Can't register a null tag type");
      throw new AxionTagRegistrationException("Can't register a null tag type");
    } else if (adapter == null) {
      LOG.error("Can't register a null tag adapter");
      throw new AxionTagRegistrationException("Can't register a null tag adapter");
    } else if (converter == null) {
      LOG.error("Can't register a null tag converter");
      throw new AxionTagRegistrationException("Can't register a null tag converter");
    } else if (classToAdapter.containsKey(tagClass) || classToId.containsKey(tagClass)) {
      LOG.error("Tag class [{}] already registered", tagClass);
      throw new AxionTagRegistrationException("Tag class already registered: " + tagClass);
    } else if (idToAdapter.containsKey(id) || idToClass.containsKey(id)) {
      LOG.error("Tag id [{}] already registered", id);
      throw new AxionTagRegistrationException("Tag id already registered: " + id);
    } else if (classToConverter.containsKey(tagClass)) {
      LOG.error("Converter already registered for class [{}]", tagClass);
      throw new AxionTagRegistrationException("Converter already registered for class: " + tagClass);
    } else if (typeToConverter.containsKey(type)) {
      LOG.error("Converter already registered for type [{}]", type);
      throw new AxionTagRegistrationException("Converter already registered for type: " + type);
    }

    classToId.put(tagClass, id);
    idToClass.put(id, tagClass);

    TagAdapter<T> newAdapterInstance = adapter.newInstance(this);
    classToAdapter.put(tagClass, newAdapterInstance);
    idToAdapter.put(id, newAdapterInstance);

    TagConverter<T, V> newConverterInstance = converter.newInstance(this);
    classToConverter.put(tagClass, newConverterInstance);
    typeToConverter.put(type, newConverterInstance);

    LOG.debug("Leaving register()");
  }

  /**
   * Returns the {@link TagAdapter} registered as the base tag adapter.
   * <p>
   * If no base tag adapter has been registered, an exception is thrown.
   * 
   * @return the {@link TagAdapter} registered as the base tag adapter
   */
  public TagAdapter<Tag> getBaseTagAdapter() throws AxionTagRegistrationException {
    if (baseTagAdapter == null) {
      LOG.error("No base tag adapter registered; use registerBaseTagAdapter() to register an adapter as the base tag adapater");
      throw new AxionTagRegistrationException("No base tag adapter registered; use registerBaseTagAdapter() to register an adapter as the base tag adapater");
    }
    return baseTagAdapter;
  }

  /**
   * Returns the {@link TagAdapter} registered for the int id given.
   * <p>
   * If no adapter is found, an exception is thrown.
   * 
   * @param id
   *          the int id to get the {@link TagAdapter} for
   * @return the {@link TagAdapter} registered for the int id given
   * @throws AxionTagRegistrationException
   */
  @SuppressWarnings("unchecked")
  public <T extends Tag> TagAdapter<T> getAdapterFor(final int id) throws AxionTagRegistrationException {
    LOG.trace("Entering getAdapterFor(id=[{}])", id);
    TagAdapter<T> result;
    if ((result = (TagAdapter<T>) idToAdapter.get(id)) == null) {
      LOG.error("No adapter registered for id [{}]", id);
      throw new AxionTagRegistrationException("No adapter registered for id: " + id);
    }
    LOG.trace("Leaving getAdapterFor(): [{}]", result);
    return result;
  }

  /**
   * Returns the {@link TagAdapter} registered for the {@link Tag} class given.
   * <p>
   * If no adapter is found, an exception is thrown.
   * 
   * @param tagClass
   *          the {@link Tag} class to get the {@link TagAdapter} for
   * @return the {@link TagAdapter} registered for the {@link Tag} class given
   * @throws AxionTagRegistrationException
   */
  @SuppressWarnings("unchecked")
  public <T extends Tag> TagAdapter<T> getAdapterFor(final Class<T> tagClass) throws AxionTagRegistrationException {
    LOG.trace("Entering getAdapterFor(tagClass=[{}])", tagClass);
    TagAdapter<T> result;
    if ((result = (TagAdapter<T>) classToAdapter.get(tagClass)) == null) {
      LOG.error("No adapter registered for class [{}]", tagClass);
      throw new AxionTagRegistrationException("No adapter registered for class: " + tagClass);
    }
    LOG.trace("Leaving getAdapterFor(): [{}]", result);
    return result;
  }

  /**
   * Returns the int id registered for the {@link Tag} class given.
   * <p>
   * If no id is found, an exception is thrown.
   * 
   * @param tagClass
   *          the {@link Tag} class to get the int id for
   * @return the int id registered for the {@link Tag} class given
   * @throws AxionTagRegistrationException
   */
  public int getIdFor(final Class<? extends Tag> tagClass) throws AxionTagRegistrationException {
    LOG.trace("Entering getIdFor(tagClass=[{}])", tagClass);
    Integer result;
    if ((result = classToId.get(tagClass)) == null) {
      throw new AxionTagRegistrationException("No id registered for tag class: " + tagClass.getSimpleName());
    }
    LOG.trace("Leaving getIdFor(): [{}]", result);
    return result;
  }

  /**
   * Returns the {@link Tag} class registered for the int id given.
   * <p>
   * If no class is found, an exception is thrown.
   * 
   * @param id
   *          the int id to get the {@link Tag} class for
   * @return the {@link Tag} class registered for the int id given
   * @throws AxionTagRegistrationException
   */
  public Class<? extends Tag> getClassFor(final int id) throws AxionTagRegistrationException {
    LOG.trace("Entering getClassFor(id=[{}])", id);
    Class<? extends Tag> result;
    if ((result = idToClass.get(id)) == null) {
      LOG.error("No class registered for tag id [{}]", id);
      throw new AxionTagRegistrationException("No class registered for tag id: " + id);
    }
    LOG.trace("Leaving getClassFor(): [{}]", result);
    return result;
  }

  /**
   * Returns the {@link TagConverter} registered to handle the {@link Tag} class
   * given.
   * <p>
   * If no converter is found, an exception is thrown.
   * 
   * @param tagClass
   *          the class of the tag to get the converter for
   * @return the {@link TagConverter} registered to handle the tag class given
   * @throws AxionTagRegistrationException
   */
  @SuppressWarnings("unchecked")
  public <T extends Tag, V> TagConverter<T, V> getConverterForTag(final Class<T> tagClass) throws AxionTagRegistrationException {
    LOG.trace("Entering getConverterForTag(tagClass=[{}])", tagClass);
    if (tagClass == null) {
      return null;
    } else if (!classToConverter.containsKey(tagClass)) {
      throw new AxionTagRegistrationException("No converter registered for tag class: " + tagClass);
    }
    TagConverter<T, V> result = (TagConverter<T, V>) classToConverter.get(tagClass);
    LOG.trace("Leaving getConverterForTag(): [{}]", result);
    return result;
  }

  /**
   * Returns the {@link TagConverter} registered to handle the value class
   * given.
   * <p>
   * If no converter is found, an exception is thrown.
   * 
   * @param valueClass
   *          the class of the value to get the converter for
   * @return the {@link TagConverter} registered to handle the value class given
   * @throws AxionTagRegistrationException
   */
  @SuppressWarnings("unchecked")
  public <T extends Tag, V> TagConverter<T, V> getConverterForValue(final Class<V> valueClass) throws AxionTagRegistrationException {
    LOG.trace("Entering getConverterForValue(valueClass=[{}])", valueClass);
    TagConverter<T, V> converter = null;
    if (valueClass != null) {
      converter = (TagConverter<T, V>) typeToConverter.get(valueClass);
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
        LOG.error("No converter registered for type [{}]", valueClass);
        throw new AxionTagRegistrationException("No converter registered for type: " + valueClass);
      }
    }
    LOG.trace("Leaving getConverterForValue(): [{}]", converter);
    return converter;
  }

  /**
   * Returns true if the given class has a converter registered.
   *
   * @param type class
   * @return true if the given class has a converter registered
   */
  public <V> boolean hasConverterForValue(Class<V> type) {
    return typeToConverter.containsKey(type);
  }

  /**
   * Returns true if the given class has a converter registered.
   * @param tagClass class
   * @return true if the given class has a converter registered
   */
  public boolean hasConverterForTag(Class<? extends Tag> tagClass) {
    return classToConverter.containsKey(tagClass);
  }

  /**
   * Creates a duplicate of this {@link TagRegistry} via the copy constructor
   * {@link #TagRegistry(TagRegistry)}.
   */
  @Override
  public TagRegistry clone() {
    LOG.debug("Entering clone()");
    TagRegistry clone = new TagRegistry(this);
    LOG.debug("Leaving clone(): [{}]", clone);
    return clone;
  }

}
