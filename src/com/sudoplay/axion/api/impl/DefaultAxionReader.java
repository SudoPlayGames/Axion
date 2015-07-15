package com.sudoplay.axion.api.impl;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.AxionReadException;
import com.sudoplay.axion.api.AxionReader;
import com.sudoplay.axion.api.AxionWritable;
import com.sudoplay.axion.registry.AxionTagRegistrationException;
import com.sudoplay.axion.spec.tag.TagCompound;
import com.sudoplay.axion.spec.tag.TagList;
import com.sudoplay.axion.tag.Tag;
import com.sudoplay.axion.util.AxionFunctions;

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
    this.assertNotNull(name, "name");
    return tagCompound.containsKey(name);
  }

  @Override
  public <V> V read(String name) {
    this.assertNotNull(name, "name");
    Tag tag = tagCompound.get(name);
    if (tag != null) {
      if (axion.hasConverterFor(tag)) {
        return axion.convertToValue(tag);
      }
      throw new AxionTagRegistrationException("No converter registered for tag: " + tag.getClass());
    }
    return null;
  }

  @Override
  public <V> V read(String name, V defaultValue) {
    this.assertNotNull(name, "name");
    return this.read((Tag) tagCompound.get(name), defaultValue);
  }

  @Override
  public <V> V map(String name, Function<V, V> function) {
    this.assertNotNull(name, "name");
    Tag tag = tagCompound.get(name);
    if (tag != null) {
      return this.map((Tag) tagCompound.get(name), function);
    } else {
      return null;
    }
  }

  @Override
  public <V, T extends Tag> V read(T tag) {
    this.assertNotNull(tag, "tag");
    if (axion.hasConverterFor(tag)) {
      return axion.convertToValue(tag);
    }
    throw new AxionTagRegistrationException("No converter registered for tag: " + tag.getClass());
  }

  @Override
  public <V, T extends Tag> V read(T tag, V defaultValue) {
    if (tag != null) {
      if (axion.hasConverterFor(tag)) {
        return AxionFunctions.ifNullChangeTo(defaultValue).apply(axion.convertToValue(tag));
      }
      throw new AxionTagRegistrationException("No converter registered for tag: " + tag.getClass());
    } else {
      return defaultValue;
    }
  }

  @Override
  public <V, T extends Tag> V map(T tag, Function<V, V> function) {
    this.assertNotNull(tag, "tag");
    this.assertNotNull(function, "function");
    if (axion.hasConverterFor(tag)) {
      return function.apply(axion.convertToValue(tag));
    }
    throw new AxionTagRegistrationException("No converter registered for tag: " + tag.getClass());
  }

  @Override
  public <V> V read(String name, Class<V> vClass) {
    assertNotNull(name, "name");
    assertNotNull(vClass, "class");
    Tag tag = tagCompound.get(name);
    if (tag != null) {
      return this.read(tag, vClass);
    }
    return null;
  }

  @Override
  public <V> V read(String name, Class<V> vClass, V defaultValue) {
    assertNotNull(name, "name");
    assertNotNull(vClass, "class");
    Tag tag = tagCompound.get(name);
    if (tag != null) {
      return this.read(tag, vClass);
    } else {
      return defaultValue;
    }
  }

  @Override
  public <V> V map(String name, Class<V> vClass, Function<V, V> function) {
    assertNotNull(name, "name");
    assertNotNull(vClass, "class");
    assertNotNull(function, "function");
    Tag tag = tagCompound.get(name);
    if (tag != null) {
      V value = this.read(tag, vClass);
      return function.apply(value);
    } else {
      return null;
    }
  }

  @Override
  public <V, T extends Tag> V read(T tag, Class<V> vClass) {
    assertNotNull(tag, "tag");
    assertNotNull(vClass, "class");
    if (AxionWritable.class.isAssignableFrom(vClass) && tag.getClass() == TagCompound.class) {
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
    throw new AxionReadException("Class not assignable from AxionWritable and no mapper registered: " + vClass);
  }

  @Override
  public <V, T extends Tag> V read(T tag, Class<V> vClass, V defaultValue) {
    if (tag != null) {
      return this.map(tag, vClass, AxionFunctions.ifNullChangeTo(defaultValue));
    } else {
      return defaultValue;
    }
  }

  @Override
  public <V, T extends Tag> V map(T tag, Class<V> vClass, Function<V, V> function) {
    return function.apply(this.read(tag, vClass));
  }

  @Override
  public <T extends Tag> T getTag(String name) {
    assertNotNull(name, "name");
    return tagCompound.get(name);
  }

  @Override
  public <T extends Tag> T getTag(String name, T defaultTag) {
    assertNotNull(name, "name");
    T in = tagCompound.get(name);
    return (in != null) ? in : defaultTag;
  }

  @Override
  public <T extends Tag> T getTag(String name, Function<T, T> function) {
    assertNotNull(name, "name");
    assertNotNull(function, "function");
    T in = tagCompound.get(name);
    if (in != null) {
      return function.apply(in);
    } else {
      return null;
    }
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

  private <O> O assertNotNull(O o, String message) {
    if (o == null) throw new IllegalArgumentException("Parameter can't be null: " + message);
    return o;
  }

}
