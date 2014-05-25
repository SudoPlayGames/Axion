package com.sudoplay.axion.ext.converter;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.converter.TagConverter;
import com.sudoplay.axion.ext.tag.TagBoolean;

public class TagBooleanConverter implements TagConverter<TagBoolean, Boolean> {

  @Override
  public Boolean convert(final TagBoolean tag, final Axion axion) {
    return tag.get();
  }

  @Override
  public TagBoolean convert(final String name, final Boolean value, final Axion axion) {
    return new TagBoolean(name, value);
  }

}
