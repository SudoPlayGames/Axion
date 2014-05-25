package com.sudoplay.axion.spec.converter;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.converter.TagConverter;
import com.sudoplay.axion.spec.tag.TagInt;

public class TagIntConverter implements TagConverter<TagInt, Integer> {

  @Override
  public TagInt convert(final String name, final Integer value, final Axion axion) {
    return new TagInt(name, value);
  }

  @Override
  public Integer convert(final TagInt tag, final Axion axion) {
    return tag.get();
  }

}