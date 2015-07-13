package com.sudoplay.axion.api;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.spec.tag.TagCompound;
import com.sudoplay.axion.spec.tag.TagList;
import com.sudoplay.axion.tag.Tag;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Interface for the AxionReader object that is passed into the AxionWritable methods.
 * <p>
 * Facilitates the conversion of Tags to Objects.
 * <p>
 * Created by Jason Taylor on 7/12/2015.
 */
@SuppressWarnings("unused")
public interface AxionReader {

  <V> V read(String name);

  <V> V read(String name, Class<V> vClass);

  <T extends Tag> T readAsTag(String name);

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
