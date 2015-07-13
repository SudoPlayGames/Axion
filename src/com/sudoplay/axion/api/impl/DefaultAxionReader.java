package com.sudoplay.axion.api.impl;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.AxionReadException;
import com.sudoplay.axion.AxionWriteException;
import com.sudoplay.axion.api.AxionReader;
import com.sudoplay.axion.api.AxionWritable;
import com.sudoplay.axion.registry.AxionTagRegistrationException;
import com.sudoplay.axion.spec.tag.TagCompound;
import com.sudoplay.axion.spec.tag.TagList;
import com.sudoplay.axion.tag.Tag;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Default implementation of the AxionReader interface.
 * <p>
 * Created by Jason Taylor on 7/12/2015.
 */
@SuppressWarnings("unused")
public class DefaultAxionReader implements AxionReader {

  private TagCompound tagCompound;
  private final Axion axion;

  public DefaultAxionReader(TagCompound tagCompound, Axion axion) {
    this.tagCompound = tagCompound;
    this.axion = axion;
  }

  @Override
  public <V> V read(String name) {
    Tag in = tagCompound.get(name);
    return this.read(in);
  }

  @Override
  public <V, T extends Tag> V read(T tag) {
    if (axion.hasConverterFor(tag)) {
      return axion.convertToValue(tag);
    }
    throw new AxionTagRegistrationException("No converter registered for tag: " + tag.getClass());
  }

  @Override
  public <V> V read(String name, Class<V> vClass) {
    Tag in = tagCompound.get(name);
    return this.read(in, vClass);
  }

  @Override
  public <V, T extends Tag> V read(T tag, Class<V> vClass) {
    if (AxionWritable.class.isAssignableFrom(vClass)
        && tag.getClass() == TagCompound.class) {
      try {
        Constructor<V> oConstructor = vClass.getDeclaredConstructor();
        oConstructor.setAccessible(true);
        V oObject = oConstructor.newInstance();
        ((AxionWritable) oObject).read(new DefaultAxionReader((TagCompound) tag, axion));
        return oObject;
      } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
        String message = "Failed to find nullary constructor for class: " + vClass.toString();
        throw new AxionReadException(message, e);
      }

    } else if (axion.hasMapperFor(vClass)) {
      return axion.createObjectFrom(tag, vClass);

    }
    throw new AxionWriteException("Class not assignable from AxionWritable and no mapper registered: " + vClass);
  }

  @Override
  public <T extends Tag> T readAsTag(String name) {
    return tagCompound.get(name);
  }

  @Override
  public <K, V> void readMap(String name, Class<K> kClass, Class<V> vClass, BiConsumer<K, V> consumer) {
    TagList in = tagCompound.get(name);
    AxionReader.readMap(axion, in, kClass, vClass, consumer);
  }

  @Override
  public <V> void readCollection(String name, Class<V> vClass, Consumer<V> consumer) {
    TagList in = tagCompound.get(name);
    AxionReader.readCollection(axion, in, vClass, consumer);
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
  public AxionReader setTagCompound(TagCompound tagCompound) {
    this.tagCompound = tagCompound;
    return this;
  }
}
