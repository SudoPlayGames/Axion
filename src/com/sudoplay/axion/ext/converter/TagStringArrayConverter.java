package com.sudoplay.axion.ext.converter;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.converter.TagConverter;
import com.sudoplay.axion.ext.tag.TagStringArray;

public class TagStringArrayConverter implements TagConverter<TagStringArray, String[]> {

  @Override
  public String[] convert(TagStringArray tag, Axion axion) {
    return tag.get();
  }

  @Override
  public TagStringArray convert(String name, String[] value, Axion axion) {
    return new TagStringArray(name, value);
  }

}
