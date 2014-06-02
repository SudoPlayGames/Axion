package com.sudoplay.axion.spec.converter;

import java.util.HashMap;
import java.util.Map;

import com.sudoplay.axion.registry.TagConverter;
import com.sudoplay.axion.spec.tag.TagCompound;
import com.sudoplay.axion.tag.Tag;

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
    Map<String, Tag> tags = new HashMap<String, Tag>();
    for (Object o : value.keySet()) {
      String n = (String) o;
      tags.put(n, convertToTag(n, value.get(n)));
    }
    return new TagCompound(name, tags);
  }

  @Override
  public Map convert(final TagCompound tag) {
    Map<String, Object> map = new HashMap<String, Object>();
    Map<String, Tag> tags = tag.getAsMap();
    for (String name : tags.keySet()) {
      Tag t = tags.get(name);
      map.put(t.getName(), convertToValue(t));
    }
    return map;
  }

}
