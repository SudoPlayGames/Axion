package com.sudoplay.axion.ext.converter;

import com.sudoplay.axion.adapter.TagConverter;
import com.sudoplay.axion.ext.tag.TagFloatArray;

/**
 * The {@link TagConverter} used to convert to and from a {@link TagFloatArray}.
 * <p>
 * Part of the extended, custom specification.
 * 
 * @author Jason Taylor
 */
public class TagFloatArrayConverter extends TagConverter<TagFloatArray, float[]> {

  @Override
  public float[] convert(final TagFloatArray tag) {
    return tag.get();
  }

  @Override
  public TagFloatArray convert(final String name, final float[] value) {
    return new TagFloatArray(name, value);
  }

}
