package com.sudoplay.axion.spec.converter;

import com.sudoplay.axion.converter.TagConverter;
import com.sudoplay.axion.spec.tag.TagShort;

public class TagShortConverter implements TagConverter<TagShort, Short> {

  @Override
  public TagShort convert(String name, Short value) {
    return new TagShort(name, value);
  }

  @Override
  public Short convert(TagShort tag) {
    return tag.get();
  }

}
