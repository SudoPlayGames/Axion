package com.sudoplay.axion.spec.converter;

import com.sudoplay.axion.registry.TypeConverter;
import com.sudoplay.axion.spec.tag.TagShort;

/**
 * The {@link TypeConverter} used to convert to and from a {@link TagShort}.
 * <p>
 * Part of the original specification.
 *
 * @author Jason Taylor
 */
public class TagShortConverter extends TypeConverter<TagShort, Short> {

  @Override
  public TagShort convert(final String name, final Short value) {
    return new TagShort(name, value);
  }

  @Override
  public Short convert(final TagShort tag) {
    return tag.get();
  }

}
