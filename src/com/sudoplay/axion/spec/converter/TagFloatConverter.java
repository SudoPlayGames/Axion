package com.sudoplay.axion.spec.converter;

import com.sudoplay.axion.adapter.TagConverter;
import com.sudoplay.axion.spec.tag.TagFloat;

public class TagFloatConverter extends TagConverter<TagFloat, Float> {

  @Override
  public TagFloat convert(final String name, final Float value) {
    return new TagFloat(name, value);
  }

  @Override
  public Float convert(final TagFloat tag) {
    return tag.get();
  }

}
