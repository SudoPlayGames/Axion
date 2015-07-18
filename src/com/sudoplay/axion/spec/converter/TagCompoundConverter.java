package com.sudoplay.axion.spec.converter;

import com.sudoplay.axion.registry.TagConverter;
import com.sudoplay.axion.spec.tag.TagCompound;
import com.sudoplay.axion.tag.Tag;

import java.util.HashMap;
import java.util.Map;

/**
 * The {@link TagConverter} used to convert to and from a {@link TagCompound}.
 * <p>
 * Part of the original specification.
 *
 * @author Jason Taylor
 */
@SuppressWarnings("rawtypes")
public class TagCompoundConverter extends TagConverter<TagCompound, Map> {

  @Override
  public TagCompound convert(final String name, final Map value) {
    Map<String, Tag> tags = new HashMap<>();
    for (Object o : value.keySet()) {
      String n = (String) o;
      tags.put(n, axion.convertValue(n, value.get(n)));
    }
    return new TagCompound(name, tags);
  }

  @Override
  public Map convert(final TagCompound tag) {
    Map<String, Object> map = new HashMap<>();
    Map<String, Tag> tags = tag.getAsMap();
    for (String name : tags.keySet()) {
      Tag t = tags.get(name);
      map.put(t.getName(), axion.convertTag(t));
    }
    return map;
  }

}
