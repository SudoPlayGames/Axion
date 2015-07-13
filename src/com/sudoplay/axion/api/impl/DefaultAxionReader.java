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
import java.util.function.Function;

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
  public boolean has(String name) {
    return tagCompound.containsKey(name);
  }

  @Override
  public <V> V read(String name) {
    Tag in = tagCompound.get(name);
    return this.read(in);
  }

  @Override
  public <V> V read(String name, V defaultValue) {
    return this.read(name, (Function<V, V>) value -> value == null ? defaultValue : value);
  }

  @Override
  public <V> V read(String name, Function<V, V> function) {
    return function.apply(this.read(name));
  }

  @Override
  public <V, T extends Tag> V read(T tag) {
    if (axion.hasConverterFor(tag)) {
      return axion.convertToValue(tag);
    }
    throw new AxionTagRegistrationException("No converter registered for tag: " + tag.getClass());
  }

  @Override
  public <V, T extends Tag> V read(T tag, V defaultValue) {
    return this.read(tag, (Function<V, V>) value -> value == null ? defaultValue : value);
  }

  @Override
  public <V, T extends Tag> V read(T tag, Function<V, V> function) {
    return function.apply(read(tag));
  }

  @Override
  public <V> V read(String name, Class<V> vClass) {
    Tag in = tagCompound.get(name);
    return this.read(in, vClass);
  }

  @Override
  public <V> V read(String name, Class<V> vClass, V defaultValue) {
    return this.read(name, vClass, (Function<V, V>) value -> value == null ? defaultValue : value);
  }

  @Override
  public <V> V read(String name, Class<V> vClass, Function<V, V> function) {
    return function.apply(read(name, vClass));
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
  public <V, T extends Tag> V read(T tag, Class<V> vClass, V defaultValue) {
    return this.read(tag, vClass, (Function<V, V>) value -> value == null ? defaultValue : value);
  }

  @Override
  public <V, T extends Tag> V read(T tag, Class<V> vClass, Function<V, V> function) {
    return function.apply(this.read(tag, vClass));
  }

  @Override
  public <T extends Tag> T readAsTag(String name) {
    return tagCompound.get(name);
  }

  @Override
  public <T extends Tag> T readAsTag(String name, T defaultTag) {
    return readAsTag(name, (Function<T, T>) value -> value == null ? defaultTag : value);
  }

  @Override
  public <T extends Tag> T readAsTag(String name, Function<T, T> function) {
    return function.apply(this.readAsTag(name));
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
