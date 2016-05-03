package com.sudoplay.axion.converter;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.registry.TypeConverter;
import com.sudoplay.axion.registry.TypeConverterFactory;
import com.sudoplay.axion.spec.tag.TagString;
import com.sudoplay.axion.tag.Tag;
import com.sudoplay.axion.util.AxionTypeToken;

import java.util.HashMap;
import java.util.Map;

/**
 * Enum type converter and factory
 * <p>
 * Created by Jason Taylor on 7/23/2015.
 */
public class EnumTypeConverterFactory implements TypeConverterFactory {
  @SuppressWarnings("unchecked")
  @Override
  public <T extends Tag, V> TypeConverter<T, V> create(Axion axion, AxionTypeToken<V> typeToken) {
    Class<? super V> rawType = typeToken.getRawType();
    if (!Enum.class.isAssignableFrom(rawType) || rawType == Enum.class) {
      return null;
    }
    if (!rawType.isEnum()) {
      rawType = rawType.getSuperclass(); // handle anonymous subclasses
    }
    return (TypeConverter<T, V>) new EnumTypeConverter(rawType);
  }

  @Override
  public TypeConverterFactory newInstance(Axion axion) {
    return new EnumTypeConverterFactory();
  }

  private class EnumTypeConverter<V extends Enum<V>> extends TypeConverter<TagString, V> {

    private Map<String, V> nameToConstant = new HashMap<>();
    private Map<V, String> constantToName = new HashMap<>();

    public EnumTypeConverter(Class<V> vClass) {
      for (V constant : vClass.getEnumConstants()) {
        String name = constant.name();
        nameToConstant.put(name, constant);
        constantToName.put(constant, name);
      }
    }

    @Override
    public V convert(TagString tag) {
      return nameToConstant.get(tag.get());
    }

    @Override
    public TagString convert(String name, V value) {
      return new TagString(name, constantToName.get(value));
    }
  }

}
