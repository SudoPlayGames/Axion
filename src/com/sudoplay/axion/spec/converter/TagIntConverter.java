package com.sudoplay.axion.spec.converter;

import com.sudoplay.axion.adapter.TagConverter;
import com.sudoplay.axion.spec.tag.TagInt;

public class TagIntConverter extends TagConverter<TagInt, Integer> {

  @Override
  public TagInt convert(final String name, final Integer value) {
    return new TagInt(name, value);
  }

  @Override
  public Integer convert(final TagInt tag) {
    return tag.get();
  }

}