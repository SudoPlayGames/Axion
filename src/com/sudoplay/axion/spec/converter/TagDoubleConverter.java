package com.sudoplay.axion.spec.converter;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.converter.TagConverter;
import com.sudoplay.axion.spec.tag.TagDouble;

public class TagDoubleConverter implements TagConverter<TagDouble, Double> {

  @Override
  public TagDouble convert(final String name, final Double value, final Axion axion) {
    return new TagDouble(name, value);
  }

  @Override
  public Double convert(final TagDouble tag, final Axion axion) {
    return tag.get();
  }

}
