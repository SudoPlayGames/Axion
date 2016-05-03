package com.sudoplay.axion.registry;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.AxionInstanceException;
import com.sudoplay.axion.tag.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * The {@link TagAdapterRegistry} class is responsible for maintaining relationships between integer ids, {@link Tag}
 * classes, {@link Tag} value classes, {@link TagAdapter}s, and {@link TypeConverter}s and providing lookup methods to
 * access the data via its relationships.
 *
 * @author Jason Taylor
 */
public class TagAdapterRegistry implements Cloneable {

  private static final Logger LOG = LoggerFactory.getLogger(TagAdapterRegistry.class);

  /**
   * Maps {@link Tag} classes to their respective integer ids.
   */
  private final Map<Class<? extends Tag>, Integer> classToId = new HashMap<>();

  /**
   * Maps integer ids to their respective {@link Tag} classes.
   */
  private final Map<Integer, Class<? extends Tag>> idToClass = new HashMap<>();

  /**
   * Maps {@link Tag} classes the their respective {@link TagAdapter}s.
   */
  private final Map<Class<? extends Tag>, TagAdapter<? extends Tag>> classToAdapter = new HashMap<>();

  /**
   * Maps integer ids to their respective {@link TagAdapter}s.
   */
  private final Map<Integer, TagAdapter<? extends Tag>> idToAdapter = new HashMap<>();

  /**
   * The base {@link TagAdapter}; used as an entry point when reading and writing a {@link Tag} hierarchy.
   */
  private TagAdapter<Tag> baseTagAdapter;

  /**
   * Creates a new, empty {@link TagAdapterRegistry}.
   */
  public TagAdapterRegistry() {
    LOG.debug("Created empty TagRegistry [{}]", this);
  }

  /**
   * Creates a new {@link TagAdapterRegistry} by copying the registry given.
   * <p>
   * All {@link TagAdapter}s and {@link TypeConverter}s are duplicated and assigned a reference to this registry.
   *
   * @param toCopy registry to copy
   * @throws AxionInstanceException
   */
  protected TagAdapterRegistry(
      final Axion axion,
      final TagAdapterRegistry toCopy
  ) throws AxionInstanceException {
    LOG.debug("Entering TagRegistry(toCopy=[{}])", toCopy);
    classToId.putAll(toCopy.classToId);
    idToClass.putAll(toCopy.idToClass);
    for (Entry<Class<? extends Tag>, TagAdapter<? extends Tag>> entry : toCopy.classToAdapter.entrySet()) {
      classToAdapter.put(entry.getKey(), entry.getValue().newInstance(axion));
    }
    for (Entry<Integer, TagAdapter<? extends Tag>> entry : toCopy.idToAdapter.entrySet()) {
      idToAdapter.put(entry.getKey(), entry.getValue().newInstance(axion));
    }
    baseTagAdapter = toCopy.baseTagAdapter.newInstance(axion);
    LOG.debug("Leaving TagRegistry(): [{}]", this);
  }

  /**
   * Registers a {@link TagAdapter} as the base tag adapter. The base tag adapter is the entry point for reading and
   * writing a tag hierarchy.
   *
   * @param newBaseTagAdapter new base tag adapter
   * @throws AxionInstanceException
   */
  public void registerBaseTagAdapter(
      final Axion axion,
      final TagAdapter<Tag> newBaseTagAdapter
  ) throws AxionInstanceException {
    LOG.debug("Entering registerBaseTagAdapter(newBaseTagAdapter=[{}])", newBaseTagAdapter);
    baseTagAdapter = newBaseTagAdapter.newInstance(axion);
    LOG.debug("Leaving registerBaseTagAdapter()");
  }

  /**
   * Registers a {@link TagAdapter} and {@link TypeConverter} with an id, tag class, and value class. Tags must be
   * registered to be recognized by the system.
   * <p>
   * An exception is thrown if a duplicate id, tag class, or value class is registered.
   *
   * @param id       the int id of the {@link Tag}
   * @param tagClass the class of the {@link Tag}
   * @param type     the class of the value represented by the tag
   * @param adapter  the {@link TagAdapter} for the {@link Tag}
   * @throws AxionTagRegistrationException
   */
  public <T extends Tag, V> void register(
      final Axion axion,
      final int id,
      final Class<T> tagClass,
      final Class<V> type,
      final TagAdapter<T> adapter
  ) throws AxionTagRegistrationException, AxionInstanceException {

    LOG.debug("Entering registerFactory(id=[{}], tagClass=[{}], type=[{}], adapter=[{}])", id, tagClass, type, adapter);

    if (tagClass == null) {
      LOG.error("Can't registerFactory a null tag class");
      throw new AxionTagRegistrationException("Can't registerFactory a null tag class");
    } else if (type == null) {
      LOG.error("Can't registerFactory a null tag type");
      throw new AxionTagRegistrationException("Can't registerFactory a null tag type");
    } else if (adapter == null) {
      LOG.error("Can't registerFactory a null tag adapter");
      throw new AxionTagRegistrationException("Can't registerFactory a null tag adapter");
    } else if (classToAdapter.containsKey(tagClass) || classToId.containsKey(tagClass)) {
      LOG.error("Tag class [{}] already registered", tagClass);
      throw new AxionTagRegistrationException("Tag class already registered: " + tagClass);
    } else if (idToAdapter.containsKey(id) || idToClass.containsKey(id)) {
      LOG.error("Tag id [{}] already registered", id);
      throw new AxionTagRegistrationException("Tag id already registered: " + id);
    }

    classToId.put(tagClass, id);
    idToClass.put(id, tagClass);

    TagAdapter<T> newAdapterInstance = adapter.newInstance(axion);
    classToAdapter.put(tagClass, newAdapterInstance);
    idToAdapter.put(id, newAdapterInstance);

    LOG.debug("Leaving registerFactory()");
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
      LOG.error("No base tag adapter registered; use registerBaseTagAdapter() to registerFactory an adapter as the " +
          "base tag " +
          "adapater");
      throw new AxionTagRegistrationException("No base tag adapter registered; use registerBaseTagAdapter() to " +
          "registerFactory an adapter as the base tag adapter");
    }
    return baseTagAdapter;
  }

  /**
   * Returns the {@link TagAdapter} registered for the int id given.
   * <p>
   * If no adapter is found, an exception is thrown.
   *
   * @param id the int id to get the {@link TagAdapter} for
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
   * @param tagClass the {@link Tag} class to get the {@link TagAdapter} for
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
   * @param tagClass the {@link Tag} class to get the int id for
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
   * @param id the int id to get the {@link Tag} class for
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
   * Creates a duplicate of this {@link TagAdapterRegistry} via the copy constructor {@link #TagAdapterRegistry(Axion,
   * TagAdapterRegistry)}.
   */
  public TagAdapterRegistry copy(Axion axion) {
    LOG.debug("Entering copy()");
    TagAdapterRegistry copy = new TagAdapterRegistry(axion, this);
    LOG.debug("Leaving copy(): [{}]", copy);
    return copy;
  }
}
