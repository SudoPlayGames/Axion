package com.sudoplay.axion.converter.spec;

import com.sudoplay.axion.converter.TagConverter;
import com.sudoplay.axion.tag.spec.TagLong;

public class TagLongConverter implements TagConverter<TagLong, Long> {

  @Override
  public TagLong convert(String name, Long value) {
    return new TagLong(name, value);
  }

  @Override
  public Long convert(TagLong tag) {
    return tag.get();
  }

}
