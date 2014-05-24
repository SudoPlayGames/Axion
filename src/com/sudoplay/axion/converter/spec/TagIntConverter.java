package com.sudoplay.axion.converter.spec;

import com.sudoplay.axion.converter.TagConverter;
import com.sudoplay.axion.tag.spec.TagInt;

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