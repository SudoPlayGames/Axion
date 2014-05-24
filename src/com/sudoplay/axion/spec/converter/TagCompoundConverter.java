package com.sudoplay.axion.spec.converter;

import java.util.HashMap;
import java.util.Map;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.converter.TagConverter;
import com.sudoplay.axion.spec.tag.Tag;
import com.sudoplay.axion.spec.tag.TagCompound;

@SuppressWarnings("rawtypes")
public class TagCompoundConverter implements TagConverter<TagCompound, Map> {

  @Override
  public TagCompound convert(String name, Map value) {
    Map<String, Tag> tags = new HashMap<String, Tag>();
    for (Object o : value.keySet()) {
      String n = (String) o;
      tags.put(n, Axion.convertToTag(n, value.get(n)));
    }
    return new TagCompound(name, tags);
  }

  @Override
  public Map convert(TagCompound tag) {
    Map<String, Object> map = new HashMap<String, Object>();
    Map<String, Tag> tags = tag.getAsMap();
    for (String name : tags.keySet()) {
      Tag t = tags.get(name);
      map.put(t.getName(), Axion.convertToValue(t));
    }
    return map;
  }

}
