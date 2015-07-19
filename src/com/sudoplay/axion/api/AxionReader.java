package com.sudoplay.axion.api;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.registry.TagAdapter;
import com.sudoplay.axion.registry.TypeConverter;
import com.sudoplay.axion.spec.tag.TagCompound;
import com.sudoplay.axion.tag.Tag;
import com.sudoplay.axion.util.AxionTypeToken;

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

  /**
   * Locates a convertible Tag by name and returns its value or null if the tag doesn't exist.
   * <p>
   * The name parameter can't be null.
   *
   * @param name tag name
   * @param <V>  return value type
   * @return tag's value or null if the tag doesn't exist
   */
  <V> V read(String name);

  /**
   * Locates a convertible Tag by name, and returns its value or the default value if the tag doesn't exist.
   * <p>
   * The name parameter can't be null.
   *
   * @param name         tag name
   * @param defaultValue default return value
   * @param <V>          return value type
   * @return tag's value or default value if tag doesn't exist
   */
  <V> V read(String name, V defaultValue);

  /**
   * Locates a convertible Tag by name, converts the Tag to its value, and applies the given function to the value
   * before returning it. Null is returned if the tag doesn't exist.
   * <p>
   * The name parameter can not be null.
   *
   * @param name     tag name
   * @param function function
   * @param <V>      return value type
   * @return tag's value after function or null if the tag doesn't exist
   */
  <V> V map(String name, Function<V, V> function);

  /**
   * Converts a tag to its value and returns it.
   * <p>
   * The name parameter can not be null.
   *
   * @param tag tag
   * @param <V> value type
   * @param <T> tag type
   * @return tag's value
   */
  <V, T extends Tag> V read(T tag);

  /**
   * Returns the tag's value or the default value if the tag parameter is null.
   * <p>
   * The tag parameter can not be null.
   *
   * @param tag          tag
   * @param defaultValue default value
   * @param <V>          value type
   * @param <T>          tag type
   * @return tag's value or the default value if the tag parameter is null
   */
  <V, T extends Tag> V read(T tag, V defaultValue);

  /**
   * Converts the given tag to its value, applies the given function, and returns the result.
   * <p>
   * Neither the tag or function parameter can be null.
   *
   * @param tag      tag
   * @param function function
   * @param <V>      value type
   * @param <T>      tag type
   * @return tag's value after function
   */
  <V, T extends Tag> V map(T tag, Function<V, V> function);

  /**
   * Locates the tag by name and converts it to its value if the given class is an AxionWritable instance or a mappable
   * class. Returns null if the tag doesn't exist.
   * <p>
   * Neither the name or class parameter can be null.
   * <p>
   * Note: This method won't read tags registered with {@link Axion#registerTag(int, Class, Class, TagAdapter,
   * TypeConverter)}. To read registered tags see the {@link AxionReader#read(String)} method.
   *
   * @param name   tag name
   * @param vClass value class
   * @param <V>    value type
   * @return tag's value or null if the tag doesn't exist
   */
  <V> V read(String name, Class<V> vClass);

  <V> V read(String name, AxionTypeToken<V> typeToken);

  /**
   * Locates the tag by name and converts it to its value if the given class is an AxionWritable instance or a mappable
   * class. Returns the given default value if the tag doesn't exist.
   * <p>
   * Neither the name or class parameter can be null.
   * <p>
   * Note: This method won't read tags registered with {@link Axion#registerTag(int, Class, Class, TagAdapter,
   * TypeConverter)}. To read registered tags see the {@link AxionReader#read(String, V)} method.
   *
   * @param name         tag name
   * @param vClass       value class
   * @param defaultValue default value
   * @param <V>          value type
   * @return tag's value or the default value if the tag doesn't exist
   */
  <V> V read(String name, Class<V> vClass, V defaultValue);

  /**
   * Locates the tag by name and converts it to its value if the given class is an AxionWritable instance or a mappable
   * class. The given function is applied to the value before returning it. Returns null if the tag doesn't exist.
   * <p>
   * Neither the name, class, or function parameter can be null.
   *
   * @param name     tag name
   * @param vClass   value class
   * @param function function
   * @param <V>      value type
   * @return tag's value after function
   */
  <V> V map(String name, Class<V> vClass, Function<V, V> function);

  /**
   * Converts the given tag to its value.
   * <p>
   * Neither the tag, or class parameter can be null.
   * <p>
   * Note: This method won't read tags registered with {@link Axion#registerTag(int, Class, Class, TagAdapter,
   * TypeConverter)}. To read registered tags see the {@link AxionReader#read(Tag)} method.
   *
   * @param tag    tag
   * @param vClass value class
   * @param <V>    value type
   * @param <T>    tag type
   * @return tag's value
   */
  <V, T extends Tag> V read(T tag, Class<V> vClass);

  /**
   * Converts the given tag to its value. If the tag parameter is null, the given defaultValue is returned instead.
   * <p>
   * The class parameter can't be null.
   * <p>
   * Note: This method won't read tags registered with {@link Axion#registerTag(int, Class, Class, TagAdapter,
   * TypeConverter)}. To read registered tags see the {@link AxionReader#read(Tag, V)} method.
   *
   * @param tag          tag
   * @param vClass       value class
   * @param defaultValue default value
   * @param <V>          value type
   * @param <T>          tag type
   * @return tag's value or the default value if the tag parameter is null
   */
  <V, T extends Tag> V read(T tag, Class<V> vClass, V defaultValue);

  /**
   * Converts the given tag to its value, then applies the given function before returning the value.
   * <p>
   * Neither the tag, class or function parameters can be null.
   *
   * @param tag      tag
   * @param vClass   value class
   * @param function function
   * @param <V>      value type
   * @param <T>      tag type
   * @return tag's value after function
   */
  <V, T extends Tag> V map(T tag, Class<V> vClass, Function<V, V> function);

  /**
   * Returns the tag with the given name or null if the tag doesn't exist.
   * <p>
   * The name parameter can't be null.
   *
   * @param name tag name
   * @param <T>  tag value
   * @return tag or null if the tag doesn't exist
   */
  <T extends Tag> T getTag(String name);

  /**
   * Returns the tag with the given name or, if the tag doesn't exist, it returns the given default tag.
   * <p>
   * The name parameter can't be null.
   *
   * @param name       tag name
   * @param defaultTag default tag
   * @param <T>        tag type
   * @return tag or default tag if tag doesn't exist
   */
  <T extends Tag> T getTag(String name, T defaultTag);

  /**
   * Finds the tag with the given name, applies the given function to the tag, and returns the tag. Null is returned if
   * the tag doesn't exist.
   * <p>
   * Neither the name or function parameter can be null.
   *
   * @param name     tag name
   * @param function function
   * @param <T>      tag type
   * @return tag or null if tag doesn't exist
   */
  <T extends Tag> T getTag(String name, Function<T, T> function);

  /**
   * Reads a map into the given map.
   * <p>
   * Neither the name, class, or map parameter can be null.
   *
   * @param name   tag name
   * @param kClass key class
   * @param vClass value class
   * @param map    map
   * @param <K>    key type
   * @param <V>    value type
   * @return the populated map
   */
  <K, V> Map<K, V> consumeMap(String name, Class<K> kClass, Class<V> vClass, Map<K, V> map);

  /**
   * Reads a map and passes its keys and values to the given consumer.
   * <p>
   * Neither the name, class, or consumer parameter can be null.
   *
   * @param name     tag name
   * @param kClass   key class
   * @param vClass   value class
   * @param consumer consumer
   * @param <K>      key type
   * @param <V>      value type
   */
  <K, V> void consumeMap(String name, Class<K> kClass, Class<V> vClass, BiConsumer<K, V> consumer);

  /**
   * Reads a map into the given map.
   * <p>
   * Neither the tag, class, or map parameter can be null.
   *
   * @param tag    tag
   * @param kClass key class
   * @param vClass value class
   * @param map    map
   * @param <K>    key type
   * @param <V>    value type
   * @param <T>    tag type
   * @return the populated map
   */
  <K, V, T extends Tag> Map<K, V> consumeMap(T tag, Class<K> kClass, Class<V> vClass, Map<K, V> map);

  /**
   * Reads a map and passes its keys and values to the given consumer.
   * <p>
   * Neither the tag, class, or consumer parameter can be null.
   *
   * @param tag      tag
   * @param kClass   key class
   * @param vClass   value class
   * @param consumer consumer
   * @param <K>      key type
   * @param <V>      value type
   * @param <T>      tag type
   */
  <K, V, T extends Tag> void consumeMap(T tag, Class<K> kClass, Class<V> vClass, BiConsumer<K, V> consumer);

  /**
   * Reads a map and returns a stream.
   *
   * @param name   tag name
   * @param kClass key class
   * @param vClass value class
   * @param <K>    key type
   * @param <V>    value type
   * @return stream
   */
  <K, V> Stream<Map.Entry<K, V>> streamMap(String name, Class<K> kClass, Class<V> vClass);

  /**
   * Reads a map and returns a stream.
   *
   * @param tag    tag
   * @param kClass key class
   * @param vClass value class
   * @param <K>    key type
   * @param <V>    value type
   * @return stream
   */
  <K, V, T extends Tag> Stream<Map.Entry<K, V>> streamMap(T tag, Class<K> kClass, Class<V> vClass);

  /**
   * Reads a collection into the given collection.
   * <p>
   * Neither the name, class, or collection parameter can be null.
   *
   * @param name       tag name
   * @param vClass     value class
   * @param collection collection
   * @param <V>        value type
   * @return the populated collection
   */
  <V> Collection<V> consumeCollection(String name, Class<V> vClass, Collection<V> collection);

  /**
   * Reads a collection and offers each element in the collection to the given consumer.
   * <p>
   * Neither the name, class, or consumer parameter can be null.
   *
   * @param name     tag name
   * @param vClass   value class
   * @param consumer consumer
   * @param <V>      value type
   */
  <V> void consumeCollection(String name, Class<V> vClass, Consumer<V> consumer);

  /**
   * Reads a collection into the given collection.
   * <p>
   * Neither the tag, class, or collection parameter can be null.
   *
   * @param tag        tag
   * @param vClass     value class
   * @param collection collection
   * @param <V>        value type
   * @return the populated collection
   */
  <V, T extends Tag> Collection<V> consumeCollection(T tag, Class<V> vClass, Collection<V> collection);

  /**
   * Reads a collection and offers each element in the collection to the given consumer.
   * <p>
   * Neither the tag, class, or consumer parameter can be null.
   *
   * @param tag      tag
   * @param vClass   value class
   * @param consumer consumer
   * @param <V>      value type
   */
  <V, T extends Tag> void consumeCollection(T tag, Class<V> vClass, Consumer<V> consumer);

  /**
   * Returns a stream of the collections values.
   *
   * @param name   tag name
   * @param vClass value class
   * @param <V>    value type
   * @return a stream of the collections values
   */
  <V> Stream<V> streamCollection(String name, Class<V> vClass);

  /**
   * Returns a stream of the collections values.
   *
   * @param tag    tag
   * @param vClass value class
   * @param <V>    value type
   * @param <T>    tag type
   * @return a stream of the collections values
   */
  <V, T extends Tag> Stream<V> streamCollection(T tag, Class<V> vClass);

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
