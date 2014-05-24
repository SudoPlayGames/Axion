package com.sudoplay.axion.ext.converter;

import com.sudoplay.axion.converter.TagConverter;
import com.sudoplay.axion.ext.tag.TagBoolean;

public class TagBooleanConverter implements TagConverter<TagBoolean, Boolean> {

  @Override
  public Boolean convert(TagBoolean tag) {
    return tag.get();
  }

  @Override
  public TagBoolean convert(String name, Boolean value) {
    return new TagBoolean(name, value);
  }

}
