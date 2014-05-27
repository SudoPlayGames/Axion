package com.sudoplay.axion.spec.converter;

import com.sudoplay.axion.adapter.TagConverter;
import com.sudoplay.axion.spec.tag.TagShort;

public class TagShortConverter extends TagConverter<TagShort, Short> {

  @Override
  public TagShort convert(final String name, final Short value) {
    return new TagShort(name, value);
  }

  @Override
  public Short convert(final TagShort tag) {
    return tag.get();
  }

}
