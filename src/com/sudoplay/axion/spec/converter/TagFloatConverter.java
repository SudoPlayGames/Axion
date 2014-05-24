package com.sudoplay.axion.spec.converter;

import com.sudoplay.axion.converter.TagConverter;
import com.sudoplay.axion.spec.tag.TagFloat;

public class TagFloatConverter implements TagConverter<TagFloat, Float> {

  @Override
  public TagFloat convert(String name, Float value) {
    return new TagFloat(name, value);
  }

  @Override
  public Float convert(TagFloat tag) {
    return tag.get();
  }

}
