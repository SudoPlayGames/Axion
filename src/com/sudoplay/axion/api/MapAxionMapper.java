package com.sudoplay.axion.api;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.AxionWriteException;
import com.sudoplay.axion.mapper.AxionMapper;
import com.sudoplay.axion.spec.tag.TagList;
import com.sudoplay.axion.tag.Tag;

import java.util.Map;
import java.util.function.BiConsumer;

/**
 * Created by Jason Taylor on 7/15/2015.
 */
public class MapAxionMapper implements AxionMapper<TagList, Map> {
  @SuppressWarnings("unchecked")
  @Override
  public TagList createTagFrom(String name, Map map, Axion axion) {
    if (map.isEmpty()) {
      throw new IllegalArgumentException("Can't write an empty map");
    }

    Object key = map.keySet().iterator().next();
    Object value = map.values().iterator().next();

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
  public Map createObjectFrom(TagList tag, Axion axion) {
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
