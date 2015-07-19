package com.sudoplay.axion.spec.converter;

import com.sudoplay.axion.registry.TypeConverter;
import com.sudoplay.axion.spec.tag.TagFloat;

/**
 * The {@link TypeConverter} used to convert to and from a {@link TagFloat}.
 * <p>
 * Part of the original specification.
 *
 * @author Jason Taylor
 */
public class TagFloatConverter extends TypeConverter<TagFloat, Float> {

  @Override
  public TagFloat convert(final String name, final Float value) {
    return new TagFloat(name, value);
  }

  @Override
  public Float convert(final TagFloat tag) {
    return tag.get();
  }

}
