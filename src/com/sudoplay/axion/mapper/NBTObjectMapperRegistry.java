package com.sudoplay.axion.mapper;

import com.sudoplay.axion.tag.Tag;
import com.sudoplay.axion.util.TypeResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * The {@link NBTObjectMapperRegistry} is responsible for storing the object type to {@link NBTObjectMapper}
 * relationship and providing a lookup method to retrieve the mapper from its type.
 *
 * @author Jason Taylor
 */
public class NBTObjectMapperRegistry implements Cloneable {

  private static final Logger LOG = LoggerFactory.getLogger(NBTObjectMapperRegistry.class);

  private Map<Class<?>, NBTObjectMapper<? extends Tag, ?>> typeToMapper = new HashMap<>();

  /**
   * Creates a new {@link NBTObjectMapperRegistry}.
   */
  public NBTObjectMapperRegistry() {
    LOG.debug("Created empty NBTObjectMapperRegistry [{}]", this);
  }

  /**
   * Creates a new {@link NBTObjectMapper} by duplicating the registry given.
   * <p>
   * Note that this duplicates the backing map, but doesn't actually duplicate the mappers.
   *
   * @param toCopy registry to copy
   */
  protected NBTObjectMapperRegistry(final NBTObjectMapperRegistry toCopy) {
    LOG.debug("Entering NBTObjectMapperRegistry(toCopy=[{}])", toCopy);
    typeToMapper.putAll(toCopy.typeToMapper);
    LOG.debug("Leaving NBTObjectMapperRegistry(): [{}]", this);
  }

  /**
   * Register a new {@link NBTObjectMapper} for the type given.
   * <p>
   * If a mapper has already been registered for the type given, an exception is thrown.
   *
   * @param type   the class of the object to map
   * @param mapper the {@link NBTObjectMapper} to register
   * @throws AxionMapperRegistrationException
   */
  public <T extends Tag, O> void register(final Class<O> type, final NBTObjectMapper<T, O> mapper) throws
      AxionMapperRegistrationException {
    LOG.debug("Entering register(type=[{}], mapper=[{}])", type, mapper);
    if (typeToMapper.containsKey(type)) {
      LOG.error("Mapper already registered for type [{}]", type);
      throw new AxionMapperRegistrationException("Mapper already registered for type: " + type);
    }
    typeToMapper.put(type, mapper);
    LOG.debug("Leaving register()");
  }

  /**
   * Returns the {@link NBTObjectMapper} registered for the type given.
   * <p>
   * If no mapper is found, an exception is thrown.
   *
   * @param type the type to get the {@link NBTObjectMapper} for
   * @return the {@link NBTObjectMapper} registered for the type given
   * @throws AxionMapperRegistrationException
   */
  @SuppressWarnings("unchecked")
  public <T extends Tag, O> NBTObjectMapper<T, O> getMapperFor(final Class<O> type) throws
      AxionMapperRegistrationException {
    LOG.debug("Entering getMapperFor(type=[{}])", type);
    NBTObjectMapper<T, O> converter = null;
    if (type != null) {
      converter = (NBTObjectMapper<T, O>) typeToMapper.get(type);
      if (converter == null) {
        for (Class<?> c : TypeResolver.getAllClasses(type)) {
          if (typeToMapper.containsKey(c)) {
            try {
              converter = (NBTObjectMapper<T, O>) typeToMapper.get(c);
              break;
            } catch (ClassCastException e) {
              //
            }
          }
        }
      }
      if (converter == null) {
        LOG.error("No mapper registered for type [{}]", type);
        throw new AxionMapperRegistrationException("No mapper registered for type: " + type);
      }
    }
    LOG.debug("Leaving getMapperFor(): [{}]", converter);
    return converter;
  }

  /**
   * Duplicates this {@link NBTObjectMapperRegistry} by using a copy constructor.
   *
   * @see #NBTObjectMapperRegistry(NBTObjectMapperRegistry)
   */
  @SuppressWarnings("CloneDoesntCallSuperClone")
  @Override
  public NBTObjectMapperRegistry clone() {
    LOG.debug("Entering clone()");
    NBTObjectMapperRegistry clone = new NBTObjectMapperRegistry(this);
    LOG.debug("Leaving clone(): [{}]", clone);
    return clone;
  }

  /**
   * Returns true if the registry has a mapper for the given class.
   *
   * @param type class
   * @return true if the registry has a mapper for the given class
   */
  public boolean hasMapperFor(Class<?> type) {
    return typeToMapper.containsKey(type);
  }
}
