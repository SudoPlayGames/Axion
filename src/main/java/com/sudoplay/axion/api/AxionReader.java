package com.sudoplay.axion.api;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.spec.tag.TagCompound;
import com.sudoplay.axion.spec.tag.TagList;
import com.sudoplay.axion.tag.Tag;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Interface for the AxionReader object that is passed into the AxionWritable methods.
 * <p>
 * Facilitates the conversion of Tags to Objects.
 * <p>
 * Created by Jason Taylor on 7/12/2015.
 */
@SuppressWarnings("unused")
public interface AxionReader {

  /**
   * Returns true if the reader contains a tag with the name given.
   *
   * @param name tag name
   * @return true if the reader contains a tag with the name given
   */
  boolean has(String name);

  // --------------------------------------------------------------------------

  <T extends Tag> T getTag(String name);

  <T extends Tag> T getTag(String name, T defaultTag);

  <T extends Tag> T getTag(String name, Function<T, T> function);

  // --------------------------------------------------------------------------

  <V> V read(String name);

  <V> V read(Tag tag);

  <V> V read(String name, Type type);

  <V> V read(Tag tag, Type type);

  // --------------------------------------------------------------------------

  <V> V readDefault(String name, V defaultValue);

  <V> V readDefault(Tag tag, V defaultValue);

  <V> V readDefault(String name, Type type, V defaultValue);

  <V> V readDefault(Tag tag, Type type, V defaultValue);

  // --------------------------------------------------------------------------

  <V> V map(String name, Function<V, V> function);

  <V> V map(Tag tag, Function<V, V> function);

  <V> V map(String name, Type type, Function<V, V> function);

  <V> V map(Tag tag, Type type, Function<V, V> function);

  // --------------------------------------------------------------------------

  //<K, V> Map<K, V> readMap(String name, Class<K> kClass, Class<V> vClass, Map<K, V> map);

  //<K, V> Map<K, V> readMap(Tag tag, Class<K> kClass, Class<V> vClass, Map<K, V> map);

  <K, V> void forEachInMap(String name, Class<K> kClass, Class<V> vClass, BiConsumer<K, V> consumer);

  <K, V> void forEachInMap(TagList tag, Class<K> kClass, Class<V> vClass, BiConsumer<K, V> consumer);

  <K, V> Stream<Map.Entry<K, V>> streamMap(String name, Class<K> kClass, Class<V> vClass);

  <K, V> Stream<Map.Entry<K, V>> streamMap(TagList tag, Class<K> kClass, Class<V> vClass);

  // --------------------------------------------------------------------------

  //<V> Collection<V> readCollection(String name, Class<V> vClass, Collection<V> collection);

  //<V> Collection<V> readCollection(Tag tag, Class<V> vClass, Collection<V> collection);

  <V> void forEachInCollection(String name, Class<V> vClass, Consumer<V> consumer);

  <V> void forEachInCollection(TagList tag, Class<V> vClass, Consumer<V> consumer);

  <V> Stream<V> streamCollection(String name, Class<V> vClass);

  <V> Stream<V> streamCollection(TagList tag, Class<V> vClass);

  // --------------------------------------------------------------------------

  AxionReader getReader(String name);

  AxionReader newReader(TagCompound tagCompound);

  /**
   * @return Axion instance
   */
  Axion getAxion();

  /**
   * @return this reader's backing TagCompound
   */
  TagCompound getTagCompound();

  /**
   * Sets this reader's backing TagCompound
   *
   * @param tagCompound tag
   * @return this reader for convenience
   */
  AxionReader setTagCompound(TagCompound tagCompound);

}
