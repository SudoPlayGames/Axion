package com.sudoplay.axion.converter.spec;

import com.sudoplay.axion.converter.TagConverter;
import com.sudoplay.axion.tag.spec.TagByte;

public class TagByteConverter implements TagConverter<TagByte, Byte> {

  @Override
  public TagByte convert(String name, Byte value) {
    return new TagByte(name, value);
  }

  @Override
  public Byte convert(TagByte tag) {
    return tag.get();
  }

}
