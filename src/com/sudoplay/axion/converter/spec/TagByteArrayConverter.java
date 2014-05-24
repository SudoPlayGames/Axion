package com.sudoplay.axion.converter.spec;

import com.sudoplay.axion.converter.TagConverter;
import com.sudoplay.axion.tag.spec.TagByteArray;

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
