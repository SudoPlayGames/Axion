package com.sudoplay.axion.converter.spec;

import com.sudoplay.axion.converter.TagConverter;
import com.sudoplay.axion.tag.spec.TagFloat;

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
