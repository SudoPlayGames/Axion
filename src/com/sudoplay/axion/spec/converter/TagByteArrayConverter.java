package com.sudoplay.axion.spec.converter;

import com.sudoplay.axion.converter.TagConverter;
import com.sudoplay.axion.spec.tag.TagByteArray;

public class TagByteArrayConverter implements TagConverter<TagByteArray, byte[]> {

  @Override
  public TagByteArray convert(String name, byte[] value) {
    return new TagByteArray(name, value);
  }

  @Override
  public byte[] convert(TagByteArray tag) {
    return tag.get();
  }

}
