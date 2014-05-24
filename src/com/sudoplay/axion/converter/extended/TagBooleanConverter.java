package com.sudoplay.axion.converter.extended;

import com.sudoplay.axion.converter.TagConverter;
import com.sudoplay.axion.tag.extended.TagBoolean;

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
