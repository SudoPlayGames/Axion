package com.sudoplay.axion.spec.converter;

import com.sudoplay.axion.registry.TypeConverter;
import com.sudoplay.axion.spec.tag.TagString;

/**
 * The {@link TypeConverter} used to convert to and from a {@link TagString}.
 * <p>
 * Part of the original specification.
 *
 * @author Jason Taylor
 */
public class TagStringConverter extends TypeConverter<TagString, String> {

  @Override
  public TagString convert(final String name, final String value) {
    return new TagString(name, value);
  }

  @Override
  public String convert(final TagString tag) {
    return tag.get();
  }

}
