package com.sudoplay.axion.ext.converter;

import com.sudoplay.axion.adapter.TagConverter;
import com.sudoplay.axion.ext.tag.TagStringArray;

public class TagStringArrayConverter extends TagConverter<TagStringArray, String[]> {

  @Override
  public String[] convert(final TagStringArray tag) {
    return tag.get();
  }

  @Override
  public TagStringArray convert(final String name, final String[] value) {
    return new TagStringArray(name, value);
  }

}
