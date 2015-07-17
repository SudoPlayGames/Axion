package com.sudoplay.axion.api;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.AxionWriteException;
import com.sudoplay.axion.mapper.AxionMapper;
import com.sudoplay.axion.mapper.AxionMapperFactory;
import com.sudoplay.axion.spec.tag.TagList;
import com.sudoplay.axion.tag.Tag;
import com.sudoplay.axion.util.AxionType;
import com.sudoplay.axion.util.AxionTypeToken;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created by Jason Taylor on 7/16/2015.
 */
public class MapAxionMapperFactory implements AxionMapperFactory {
  @Override
  public <T extends Tag, V> AxionMapper<T, V> create(Axion axion, AxionTypeToken<V> candidateTypeToken) {
    Type type = candidateTypeToken.getType();

    Class<? super V> rawType = candidateTypeToken.getRawType();
    if (!Map.class.isAssignableFrom(rawType)) {
      return null;
    }

    Class<?> rawTypeOfSrc = AxionType.getRawType(type);
    Type[] keyAndValueTypes = AxionType.getMapKeyAndValueTypes(type, rawTypeOfSrc);
    AxionTypeToken<?> keyTypeToken = AxionTypeToken.get(keyAndValueTypes[0]);
    AxionTypeToken<?> valueTypeToken = AxionTypeToken.get(keyAndValueTypes[1]);

    if (axion.hasMapperFor(keyAndValueTypes[0])) {
      // TODO
    } else if (axion.hasConverterFor(keyAndValueTypes[0])) {

    }

    return new MapAxionMapper<MK, MV>(keyTypeToken, valueTypeToken);
  }

  private final class MapAxionMapper<K, V> implements AxionMapper<TagList, Map<K, V>> {

    private final AxionTypeToken<K> keyTypeToken;
    private final AxionTypeToken<V> valueTypeToken;

    private MapAxionMapper(AxionTypeToken<K> keyTypeToken, AxionTypeToken<V> valueTypeToken) {
      this.keyTypeToken = keyTypeToken;
      this.valueTypeToken = valueTypeToken;
    }

    @SuppressWarnings("unchecked")
    @Override
    public TagList createTagFrom(String name, Map<K, V> map, Axion axion) {
      if (map.isEmpty()) {
        throw new IllegalArgumentException("Can't write an empty map");
      }

      K key = map.keySet().iterator().next();
      V value = map.values().iterator().next();

      assertKeyValueNotNull(key, value);

      Tag keyTag = axion.createTagFrom(key);
      Tag valueTag = axion.createTagFrom(value);

      TagList list = new TagList(TagList.class);
      TagList keyList = new TagList(keyTag.getClass());
      TagList valueList = new TagList(valueTag.getClass());

      list.add(keyList);
      list.add(valueList);

      map.forEach((k, v) -> {
        assertKeyValueNotNull(k, v);
        keyList.add(axion.createTagFrom(k));
        valueList.add(axion.createTagFrom(v));
      });

      return list;
    }

    @Override
    public Map<K, V> createObjectFrom(TagList tag, Axion axion) {
      return null;
    }

    private void assertKeyValueNotNull(Object key, Object value) {
      if (key == null) {
        throw new AxionWriteException("Can't write a map with a null key");
      } else if (value == null) {
        throw new AxionWriteException("Can't write a map with a null value for key: " + key);
      }
    }

  }
}
