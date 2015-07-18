package com.sudoplay.axion.spec.converter;

import com.sudoplay.axion.registry.TagConverter;
import com.sudoplay.axion.registry.TagConverterFactory;
import com.sudoplay.axion.spec.tag.TagByte;
import com.sudoplay.axion.util.AxionTypeToken;

/**
 * The {@link TagConverter} used to convert to and from a {@link TagByte}.
 * <p>
 * Part of the original specification.
 *
 * @author Jason Taylor
 */
public class TagByteConverter extends TagConverter<TagByte, Byte> {

  public static TagConverterFactory FACTORY = TagConverterFactory.newFactory(AxionTypeToken.get(Byte.class), new TagByteConverter());

  @Override
  public TagByte convert(final String name, final Byte value) {
    return new TagByte(name, value);
  }

  @Override
  public Byte convert(final TagByte tag) {
    return tag.get();
  }

}
