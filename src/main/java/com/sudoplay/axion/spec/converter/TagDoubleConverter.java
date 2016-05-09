package com.sudoplay.axion.spec.converter;

import com.sudoplay.axion.registry.TypeConverter;
import com.sudoplay.axion.spec.tag.TagDouble;

/**
 * The {@link TypeConverter} used to convert to and from a {@link TagDouble}.
 * <p>
 * Part of the original specification.
 *
 * @author Jason Taylor
 */
public class TagDoubleConverter extends TypeConverter<TagDouble, Double> {

  @Override
  public TagDouble convert(final String name, final Double value) {
    return new TagDouble(name, value);
  }

  @Override
  public Double convert(final TagDouble tag) {
    return tag.get();
  }

}
