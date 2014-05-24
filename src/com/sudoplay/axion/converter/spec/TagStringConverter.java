package com.sudoplay.axion.converter.spec;

import com.sudoplay.axion.converter.TagConverter;
import com.sudoplay.axion.tag.spec.TagString;

public class TagStringConverter implements TagConverter<TagString, String> {

  @Override
  public TagString convert(String name, String value) {
    return new TagString(name, value);
  }

  @Override
  public String convert(TagString tag) {
    return tag.get();
  }

}
