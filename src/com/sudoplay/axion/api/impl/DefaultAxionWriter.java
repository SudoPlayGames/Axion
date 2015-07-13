package com.sudoplay.axion.api.impl;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.AxionWriteException;
import com.sudoplay.axion.api.AxionWritable;
import com.sudoplay.axion.api.AxionWriter;
import com.sudoplay.axion.spec.tag.TagCompound;
import com.sudoplay.axion.tag.Tag;

import java.util.Collection;
import java.util.Map;

/**
 * Created by Jason Taylor on 7/12/2015.
 */
public class DefaultAxionWriter implements AxionWriter {

  private TagCompound tagCompound;
  private final Axion axion;

  public DefaultAxionWriter(Axion axion) {
    this(new TagCompound(), axion);
  }

  public DefaultAxionWriter(TagCompound tagCompound, Axion axion) {
    this.tagCompound = tagCompound;
    this.axion = axion;
  }

  @Override
  public <S extends Tag> AxionWriter write(String name, S tag) {
    tagCompound.put(name, tag);
    return this;
  }

  @Override
  public AxionWriter write(String name, AxionWritable axionWritable) {
    tagCompound.put(name, axionWritable.write(new DefaultAxionWriter(axion)));
    return this;
  }

  @Override
  public AxionWriter write(String name, Object object) {
    if (axion.hasMapperFor(object.getClass())) {
      tagCompound.put(name, axion.createTagWithMapper(name, object));
      return this;

    } else if (axion.hasConverterFor(object)) {
      tagCompound.put(name, axion.createTagWithConverter(name, object));
      return this;
    }
    throw new AxionWriteException("Class has no converter registered, and no mapper registered: " + object.getClass()
        .toString());
  }

  @Override
  public <K, V> AxionWriter writeMap(String name, Map<K, V> map) {
    tagCompound.put(name, AxionWriter.writeMap(axion, map));
    return this;
  }

  @Override
  public AxionWriter writeCollection(String name, Collection<?> collection, Class<? extends Tag> tagClass) {
    tagCompound.put(name, AxionWriter.writeCollection(axion, collection, tagClass));
    return this;
  }

  @Override
  public Axion getAxion() {
    return axion;
  }

  @Override
  public TagCompound getTagCompound() {
    return tagCompound;
  }

  @Override
  public AxionWriter setTagCompound(TagCompound tagCompound) {
    this.tagCompound = tagCompound;
    return this;
  }
}
