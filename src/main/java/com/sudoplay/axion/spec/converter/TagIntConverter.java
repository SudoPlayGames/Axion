package com.sudoplay.axion.spec.converter;

import com.sudoplay.axion.registry.TypeConverter;
import com.sudoplay.axion.spec.tag.TagInt;

/**
 * The {@link TypeConverter} used to convert to and from a {@link TagInt}.
 * <p>
 * Part of the original specification.
 *
 * @author Jason Taylor
 */
public class TagIntConverter extends TypeConverter<TagInt, Integer> {

  @Override
  public TagInt convert(final String name, final Integer value) {
    return new TagInt(name, value);
  }

  @Override
  public Integer convert(final TagInt tag) {
    return tag.get();
  }

}