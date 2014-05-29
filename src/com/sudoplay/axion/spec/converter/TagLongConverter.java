package com.sudoplay.axion.spec.converter;

import com.sudoplay.axion.adapter.TagConverter;
import com.sudoplay.axion.spec.tag.TagLong;

/**
 * The {@link TagConverter} used to convert to and from a {@link TagLong}.
 * <p>
 * Part of the original specification.
 * 
 * @author Jason Taylor
 */
public class TagLongConverter extends TagConverter<TagLong, Long> {

  @Override
  public TagLong convert(final String name, final Long value) {
    return new TagLong(name, value);
  }

  @Override
  public Long convert(final TagLong tag) {
    return tag.get();
  }

}
