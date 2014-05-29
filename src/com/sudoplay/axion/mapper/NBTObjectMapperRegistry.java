package com.sudoplay.axion.mapper;

import java.util.HashMap;
import java.util.Map;

import com.sudoplay.axion.tag.Tag;
import com.sudoplay.axion.util.TypeResolver;

/**
 * The {@link NBTObjectMapperRegistry} is responsible for storing the object
 * type to {@link NBTObjectMapper} relationship and providing a lookup method to
 * retrieve the mapper from its type.
 * 
 * @author Jason Taylor
 */
public class NBTObjectMapperRegistry implements Cloneable {

  private Map<Class<?>, NBTObjectMapper<? extends Tag, ?>> typeToMapper = new HashMap<Class<?>, NBTObjectMapper<? extends Tag, ?>>();

  /**
   * Creates a new {@link NBTObjectMapperRegistry}.
   */
  public NBTObjectMapperRegistry() {
    //
  }

  /**
   * Creates a new {@link NBTObjectMapper} by duplicating the registry given.
   * <p>
   * Note that this duplicates the backing map, but doesn't actually duplicate
   * the mappers.
   * 
   * @param toCopy
   */
  protected NBTObjectMapperRegistry(final NBTObjectMapperRegistry toCopy) {
    typeToMapper.putAll(toCopy.typeToMapper);
  }

  /**
   * Register a new {@link NBTObjectMapper} for the type given.
   * <p>
   * If a mapper has already been registered for the type given, an exception is
   * thrown.
   * 
   * @param type
   *          the class of the object to map
   * @param mapper
   *          the {@link NBTObjectMapper} to register
   * @throws AxionMapperRegistrationException
   */
  public <T extends Tag, O> void register(final Class<O> type, final NBTObjectMapper<T, O> mapper) throws AxionMapperRegistrationException {
    if (typeToMapper.containsKey(type)) {
      throw new AxionMapperRegistrationException("Mapper already registered for type: " + type.getSimpleName());
    }
    typeToMapper.put(type, mapper);
  }

  /**
   * Returns the {@link NBTObjectMapper} registered for the type given.
   * <p>
   * If no mapper is found, an exception is thrown.
   * 
   * @param type
   *          the type to get the {@link NBTObjectMapper} for
   * @return the {@link NBTObjectMapper} registered for the type given
   * @throws AxionMapperRegistrationException
   */
  @SuppressWarnings("unchecked")
  public <T extends Tag, O> NBTObjectMapper<T, O> getMapperFor(final Class<O> type) throws AxionMapperRegistrationException {
    if (type == null) {
      return null;
    }
    NBTObjectMapper<T, O> converter = (NBTObjectMapper<T, O>) typeToMapper.get(type);
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
      throw new AxionMapperRegistrationException("No mapper registered for type: " + type.getSimpleName());
    }
    return converter;
  }

  /**
   * Duplicates this {@link NBTObjectMapperRegistry} by using a copy
   * constructor.
   * 
   * @see #NBTObjectMapperRegistry(NBTObjectMapperRegistry)
   */
  @Override
  public NBTObjectMapperRegistry clone() {
    return new NBTObjectMapperRegistry(this);
  }

}
