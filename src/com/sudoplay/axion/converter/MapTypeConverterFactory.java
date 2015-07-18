package com.sudoplay.axion.converter;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.AxionWriteException;
import com.sudoplay.axion.registry.TagConverter;
import com.sudoplay.axion.registry.TagConverterFactory;
import com.sudoplay.axion.spec.tag.TagList;
import com.sudoplay.axion.tag.Tag;
import com.sudoplay.axion.util.AxionType;
import com.sudoplay.axion.util.AxionTypeToken;

import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Converts from {@link Map} to {@link TagList} and from {@link TagList} to {@link LinkedHashMap}.
 * <p>
 * Created by Jason Taylor on 7/17/2015.
 */
public class MapTypeConverterFactory implements TagConverterFactory {

  @Override
  public <T extends Tag, V> TagConverter<T, V> create(
      Axion axion,
      AxionTypeToken<V> typeToken
  ) {

    Type type = typeToken.getType();

    Class<? super V> rawType = typeToken.getRawType();
    if (!Map.class.isAssignableFrom(rawType)) {
      return null;
    }

    Class<?> rawTypeOfSrc = AxionType.getRawType(type);
    Type[] keyAndValueTypes = AxionType.getMapKeyAndValueTypes(type, rawTypeOfSrc);
    TagConverter<? extends Tag, ?> keyConverter = axion.getConverterForValue(AxionTypeToken.get(keyAndValueTypes[0]));
    TagConverter<? extends Tag, ?> valueConverter = axion.getConverterForValue(AxionTypeToken.get(keyAndValueTypes[1]));

    @SuppressWarnings("unchecked")
    TagConverter<T, V> result = new Converter(keyConverter, valueConverter);
    return result;
  }

  @Override
  public TagConverterFactory newInstance(Axion axion) {
    return new MapTypeConverterFactory();
  }

  private class Converter<K, V> extends TagConverter<TagList, Map<K, V>> {

    private final TagConverter<Tag, K> keyConverter;
    private final TagConverter<Tag, V> valueConverter;

    public Converter(
        TagConverter<Tag, K> keyConverter,
        TagConverter<Tag, V> valueConverter
    ) {
      this.keyConverter = keyConverter;
      this.valueConverter = valueConverter;
    }

    @Override
    public Map<K, V> convert(TagList tag) {
      TagList keyList = tag.get(0);
      TagList valueList = tag.get(1);
      Map<K, V> map = new LinkedHashMap<>();
      for (int i = 0; i < keyList.size(); ++i) {
        K key = keyConverter.convert(keyList.get(i));
        V value = valueConverter.convert(valueList.get(i));
        map.put(key, value);
      }
      return map;
    }

    @Override
    public TagList convert(String name, Map<K, V> map) {
      if (map.isEmpty()) {
        throw new IllegalArgumentException("Can't write an empty map");
      }

      K key = map.keySet().iterator().next();
      V value = map.values().iterator().next();

      if (key == null) {
        throw new AxionWriteException("Can't write a map with a null key");
      } else if (value == null) {
        throw new AxionWriteException("Can't write a map with a null value");
      }

      Tag keyTag = keyConverter.convert(null, key);
      Tag valueTag = valueConverter.convert(null, value);

      TagList list = new TagList(TagList.class);
      TagList keyList = new TagList(keyTag.getClass());
      TagList valueList = new TagList(valueTag.getClass());

      list.add(keyList);
      list.add(valueList);

      map.forEach((k, v) -> {
        if (k == null) {
          throw new AxionWriteException("Can't write a map with a null key");
        } else if (v == null) {
          throw new AxionWriteException("Can't write a map with a null value for key: " + k);
        }
        keyList.add(keyConverter.convert(null, k));
        valueList.add(valueConverter.convert(null, v));
      });

      return list;
    }
  }
}