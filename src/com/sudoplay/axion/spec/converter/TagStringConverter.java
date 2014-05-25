package com.sudoplay.axion.spec.converter;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.converter.TagConverter;
import com.sudoplay.axion.spec.tag.TagString;

public class TagStringConverter implements TagConverter<TagString, String> {

  @Override
  public TagString convert(final String name, final String value, final Axion axion) {
    return new TagString(name, value);
  }

  @Override
  public String convert(final TagString tag, final Axion axion) {
    return tag.get();
  }

}
