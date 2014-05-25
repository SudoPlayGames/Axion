package com.sudoplay.axion.spec.converter;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.converter.TagConverter;
import com.sudoplay.axion.spec.tag.TagShort;

public class TagShortConverter implements TagConverter<TagShort, Short> {

  @Override
  public TagShort convert(final String name, final Short value, final Axion axion) {
    return new TagShort(name, value);
  }

  @Override
  public Short convert(final TagShort tag, final Axion axion) {
    return tag.get();
  }

}
