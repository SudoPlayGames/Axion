package com.sudoplay.axion.spec.converter;

import com.sudoplay.axion.adapter.TagConverter;
import com.sudoplay.axion.spec.tag.TagDouble;

/**
 * The {@link TagConverter} used to convert to and from a {@link TagDouble}.
 * <p>
 * Part of the original specification.
 * 
 * @author Jason Taylor
 */
public class TagDoubleConverter extends TagConverter<TagDouble, Double> {

  @Override
  public TagDouble convert(final String name, final Double value) {
    return new TagDouble(name, value);
  }

  @Override
  public Double convert(final TagDouble tag) {
    return tag.get();
  }

}
