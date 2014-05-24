package com.sudoplay.axion.converter.spec;

import com.sudoplay.axion.converter.TagConverter;
import com.sudoplay.axion.tag.spec.TagDouble;

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
