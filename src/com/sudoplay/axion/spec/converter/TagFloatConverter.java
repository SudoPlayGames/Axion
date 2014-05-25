package com.sudoplay.axion.spec.converter;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.converter.TagConverter;
import com.sudoplay.axion.spec.tag.TagFloat;

public class TagFloatConverter implements TagConverter<TagFloat, Float> {

  @Override
  public TagFloat convert(final String name, final Float value, final Axion axion) {
    return new TagFloat(name, value);
  }

  @Override
  public Float convert(final TagFloat tag, final Axion axion) {
    return tag.get();
  }

}
