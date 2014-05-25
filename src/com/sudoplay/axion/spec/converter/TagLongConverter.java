package com.sudoplay.axion.spec.converter;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.converter.TagConverter;
import com.sudoplay.axion.spec.tag.TagLong;

public class TagLongConverter implements TagConverter<TagLong, Long> {

  @Override
  public TagLong convert(final String name, final Long value, final Axion axion) {
    return new TagLong(name, value);
  }

  @Override
  public Long convert(final TagLong tag, final Axion axion) {
    return tag.get();
  }

}
