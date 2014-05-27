package com.sudoplay.axion.ext.converter;

import com.sudoplay.axion.adapter.TagConverter;
import com.sudoplay.axion.ext.tag.TagBoolean;

public class TagBooleanConverter extends TagConverter<TagBoolean, Boolean> {

  @Override
  public Boolean convert(final TagBoolean tag) {
    return tag.get();
  }

  @Override
  public TagBoolean convert(final String name, final Boolean value) {
    return new TagBoolean(name, value);
  }

}
