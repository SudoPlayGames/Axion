package com.sudoplay.axion.converter;

import java.util.HashMap;
import java.util.Map;

import com.sudoplay.axion.spec.tag.Tag;
import com.sudoplay.axion.util.TypeUtil;

public class TagConverterRegistry {

  private static final Map<Class<? extends Tag>, TagConverter<? extends Tag, ?>> classToConverter = new HashMap<Class<? extends Tag>, TagConverter<? extends Tag, ?>>();
  private static final Map<Class<?>, TagConverter<? extends Tag, ?>> typeToConverter = new HashMap<Class<?>, TagConverter<? extends Tag, ?>>();

  public static <T extends Tag, V> void registerTagConverter(final Class<T> tagClass, final Class<V> type, final TagConverter<T, V> converter) {
    if (classToConverter.containsKey(tagClass)) {
      throw new IllegalArgumentException("Converter already registered for class: " + tagClass.getSimpleName());
    } else if (typeToConverter.containsKey(type)) {
      throw new IllegalArgumentException("Converter already registered for type: " + type.getSimpleName());
    }
    classToConverter.put(tagClass, converter);
    typeToConverter.put(type, converter);
  }

  @SuppressWarnings("unchecked")
  public static <T extends Tag, V> V convertToValue(final T tag) {
    if (tag == null) {
      return null;
    } else if (!classToConverter.containsKey(tag.getClass())) {
      throw new IllegalArgumentException("No converter registered for tag class: " + tag.getClass());
    }
    TagConverter<T, ?> converter = (TagConverter<T, ?>) classToConverter.get(tag.getClass());
    return (V) converter.convert(tag);
  }

  @SuppressWarnings("unchecked")
  public static <V, T extends Tag> T convertToTag(final String name, final V value) {
    if (value == null) {
      return null;
    }
    TagConverter<T, V> converter = (TagConverter<T, V>) typeToConverter.get(value.getClass());
    if (converter == null) {
      for (Class<?> c : TypeUtil.getAllClasses(value.getClass())) {
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
      throw new IllegalArgumentException("No converter registered for type: " + value.getClass().getSimpleName());
    }
    return converter.convert(name, value);
  }

}
