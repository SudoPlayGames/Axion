package com.sudoplay.axion.spec.converter;

import com.sudoplay.axion.registry.TagConverter;
import com.sudoplay.axion.spec.tag.TagCompound;
import com.sudoplay.axion.tag.Tag;

import java.util.HashMap;
import java.util.Map;

/**
 * The {@link TagConverter} used to convert a {@link TagCompound} into a {@link Map}.
 * <p>
 * Part of the original specification.
 *
 * @author Jason Taylor
 */
@SuppressWarnings("rawtypes")
public class TagCompoundConverter extends TagConverter<TagCompound, Map> {

  @Override
  public TagCompound convert(final String name, final Map value) {
    /*
     The integration of mappers and converters, and the addition of the
     MapTypeConverter, has created conflict with map conversion and it
     has been removed for now.
     */

    /*
    Map<String, Tag> tags = new HashMap<>();
    for (Object o : value.keySet()) {
      String n = (String) o;
      tags.put(n, axion.convertValue(n, value.get(n)));
    }
    return new TagCompound(name, tags);
    */
    
    throw new UnsupportedOperationException();
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
