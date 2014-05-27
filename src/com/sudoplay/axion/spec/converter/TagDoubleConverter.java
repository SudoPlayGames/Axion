package com.sudoplay.axion.spec.converter;

import com.sudoplay.axion.adapter.TagConverter;
import com.sudoplay.axion.spec.tag.TagDouble;

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
