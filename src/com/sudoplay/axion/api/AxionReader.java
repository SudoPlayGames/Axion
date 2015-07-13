package com.sudoplay.axion.api;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.spec.tag.TagCompound;
import com.sudoplay.axion.spec.tag.TagList;
import com.sudoplay.axion.tag.Tag;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Interface for the AxionReader object that is passed into the AxionWritable methods.
 * <p>
 * Facilitates the conversion of Tags to Objects.
 * <p>
 * Created by Jason Taylor on 7/12/2015.
 */
@SuppressWarnings("unused")
public interface AxionReader {

  boolean has(String name);

  <V> V read(String name);

  <V> V read(String name, Function<V, V> function);

  <V, T extends Tag> V read(T tag);

  <V, T extends Tag> V read(T tag, Function<V, V> function);

  <V> V read(String name, Class<V> vClass);

  <V> V read(String name, Class<V> vClass, Function<V, V> function);

  <V, T extends Tag> V read(T tag, Class<V> vClass);

  <V, T extends Tag> V read(T tag, Class<V> vClass, Function<V, V> function);

  <T extends Tag> T readAsTag(String name);

  <T extends Tag> T readAsTag(String name, Function<T, T> function);

  <K, V> void readMap(String name, Class<K> kClass, Class<V> vClass, BiConsumer<K, V> consumer);

  <V> void readCollection(String name, Class<V> vClass, Consumer<V> consumer);

  Axion getAxion();

  TagCompound getTagCompound();

  AxionReader setTagCompound(TagCompound tagCompound);

  static <V> void readCollection(
      Axion axion,
      TagList in,
      Class<V> classOfO,
      Consumer<V> consumer
  ) {
    in.forEach(tag -> {
      consumer.accept(axion.createFromTag(tag, classOfO));
    });
  }

  static <K, V> void readMap(
      Axion axion,
      TagList in,
      Class<K> kClass,
      Class<V> vClass,
      BiConsumer<K, V> consumer
  ) {
    in.forEach(tag -> {
      TagCompound entry = (TagCompound) tag;
      K key = axion.createFromTag(entry.get("key"), kClass);
      V value = axion.createFromTag(entry.get("value"), vClass);
      consumer.accept(key, value);
    });
  }

}
