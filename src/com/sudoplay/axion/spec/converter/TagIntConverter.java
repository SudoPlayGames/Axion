package com.sudoplay.axion.spec.converter;

import com.sudoplay.axion.converter.TagConverter;
import com.sudoplay.axion.spec.tag.TagInt;

public class TagIntConverter implements TagConverter<TagInt, Integer> {

  @Override
  public TagInt convert(String name, Integer value) {
    return new TagInt(name, value);
  }

  @Override
  public Integer convert(TagInt tag) {
    return tag.get();
  }

}