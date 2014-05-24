package com.sudoplay.axion.spec.converter;

import com.sudoplay.axion.converter.TagConverter;
import com.sudoplay.axion.spec.tag.TagDouble;

public class TagDoubleConverter implements TagConverter<TagDouble, Double> {

  @Override
  public TagDouble convert(String name, Double value) {
    return new TagDouble(name, value);
  }

  @Override
  public Double convert(TagDouble tag) {
    return tag.get();
  }

}
