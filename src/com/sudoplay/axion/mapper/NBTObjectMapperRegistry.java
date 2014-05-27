package com.sudoplay.axion.mapper;

import java.util.HashMap;
import java.util.Map;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.tag.Tag;
import com.sudoplay.axion.util.TypeResolver;

public class NBTObjectMapperRegistry implements Cloneable {

  private Map<Class<?>, NBTObjectMapper<? extends Tag, ?>> typeToMapper = new HashMap<Class<?>, NBTObjectMapper<? extends Tag, ?>>();

  public NBTObjectMapperRegistry() {
    //
  }

  protected NBTObjectMapperRegistry(final NBTObjectMapperRegistry toCopy) {
    typeToMapper.putAll(toCopy.typeToMapper);
  }

  public <T extends Tag, O> void register(final Class<O> type, final NBTObjectMapper<T, O> mapper) throws AxionMapperRegistrationException {
    if (typeToMapper.containsKey(type)) {
      throw new AxionMapperRegistrationException("Mapper already registered for type: " + type.getSimpleName());
    }
    typeToMapper.put(type, mapper);
  }

  public <T extends Tag, O> O createObjectFrom(final T tag, final Class<O> type, final Axion axion) {
    if (tag == null) {
      return null;
    }
    return getMapperFor(type).createObjectFrom(tag, axion);
  }

  @SuppressWarnings("unchecked")
  public <T extends Tag, O> T createTagFrom(final String name, final O o, final Axion axion) {
    if (o == null) {
      return null;
    }
    NBTObjectMapper<T, O> mapper = (NBTObjectMapper<T, O>) getMapperFor(o.getClass());
    return mapper.createTagFrom(name, o, axion);
  }

  @SuppressWarnings("unchecked")
  protected <T extends Tag, O> NBTObjectMapper<T, O> getMapperFor(final Class<O> type) throws AxionMapperRegistrationException {
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

  @Override
  public NBTObjectMapperRegistry clone() {
    return new NBTObjectMapperRegistry(this);
  }

}
