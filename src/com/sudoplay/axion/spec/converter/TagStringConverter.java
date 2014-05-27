package com.sudoplay.axion.spec.converter;

import com.sudoplay.axion.adapter.TagConverter;
import com.sudoplay.axion.spec.tag.TagString;

public class TagStringConverter extends TagConverter<TagString, String> {

  @Override
  public TagString convert(final String name, final String value) {
    return new TagString(name, value);
  }

  @Override
  public String convert(final TagString tag) {
    return tag.get();
  }

}
