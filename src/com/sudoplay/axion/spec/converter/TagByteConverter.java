package com.sudoplay.axion.spec.converter;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.converter.TagConverter;
import com.sudoplay.axion.spec.tag.TagByte;

public class TagByteConverter implements TagConverter<TagByte, Byte> {

  @Override
  public TagByte convert(final String name, final Byte value, final Axion axion) {
    return new TagByte(name, value);
  }

  @Override
  public Byte convert(final TagByte tag, final Axion axion) {
    return tag.get();
  }

}
