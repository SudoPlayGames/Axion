package com.sudoplay.axion.ext.converter;

import com.sudoplay.axion.ext.tag.TagBoolean;
import com.sudoplay.axion.registry.TagConverter;

/**
 * The {@link TagConverter} used to convert to and from a {@link TagBoolean}.
 * <p>
 * Part of the extended, custom specification.
 * 
 * @author Jason Taylor
 */
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
