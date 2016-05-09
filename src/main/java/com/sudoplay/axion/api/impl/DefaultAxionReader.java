package com.sudoplay.axion.api.impl;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.api.AxionReader;
import com.sudoplay.axion.registry.AxionTagRegistrationException;
import com.sudoplay.axion.registry.TypeConverter;
import com.sudoplay.axion.spec.tag.TagCompound;
import com.sudoplay.axion.spec.tag.TagList;
import com.sudoplay.axion.tag.Tag;
import com.sudoplay.axion.util.AxionContract;
import com.sudoplay.axion.util.AxionTypeToken;

import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

  // --------------------------------------------------------------------------

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

  // --------------------------------------------------------------------------

  @Override
  public <V> V read(String name) {
    this.assertNotNull(name, "name");
    return this.read((Tag) tagCompound.get(name));
  }

  @Override
  public <V> V read(Tag tag) {
    if (tag != null) {
      if (axion.hasConverterForTag(tag)) {
        return axion.fromTag(tag);
      }
      throw new AxionTagRegistrationException("No converter registered for tag: " + tag.getClass());
    }
    return null;
  }

  @Override
  public <V> V read(String name, Type type) {
    this.assertNotNull(name, "name");
    return this.read((Tag) tagCompound.get(name), type);
  }

  @Override
  public <V> V read(Tag tag, Type type) {
    assertNotNull(type, "type");
    if (tag != null) {
      return axion.fromTag(tag, type);
    }
    return null;
  }

  // --------------------------------------------------------------------------

  @Override
  public <V> V readDefault(String name, V defaultValue) {
    assertNotNull(name, "name");
    return this.readDefault((Tag) tagCompound.get(name), defaultValue);
  }

  @Override
  public <V> V readDefault(Tag tag, V defaultValue) {
    if (tag != null) {
      V value = axion.fromTag(tag);
      if (value == null) {
        value = defaultValue;
      }
      return value;
    } else {
      return defaultValue;
    }
  }

  @Override
  public <V> V readDefault(String name, Type type, V defaultValue) {
    assertNotNull(name, "name");
    return this.readDefault((Tag) tagCompound.get(name), type, defaultValue);
  }

  @Override
  public <V> V readDefault(Tag tag, Type type, V defaultValue) {
    assertNotNull(type, "type");
    if (tag != null) {
      return axion.fromTag(tag, type);
    } else {
      return defaultValue;
    }
  }

  // --------------------------------------------------------------------------

  @Override
  public <V> V map(String name, Function<V, V> function) {
    this.assertNotNull(name, "name");
    Tag tag = tagCompound.get(name);
    if (tag != null) {
      return this.map(tag, function);
    } else {
      return null;
    }
  }

  @Override
  public <V> V map(Tag tag, Function<V, V> function) {
    this.assertNotNull(tag, "tag");
    this.assertNotNull(function, "function");
    return function.apply(axion.fromTag(tag));
  }

  @Override
  public <V> V map(String name, Type type, Function<V, V> function) {
    assertNotNull(name, "name");
    assertNotNull(type, "type");
    assertNotNull(function, "function");
    Tag tag = tagCompound.get(name);
    if (tag != null) {
      return function.apply(axion.fromTag(tag, type));
    } else {
      return null;
    }
  }

  @Override
  public <V> V map(Tag tag, Type type, Function<V, V> function) {
    return function.apply(this.read(tag, type));
  }

  // --------------------------------------------------------------------------

  /*@Override
  public <V> V readMap(String name, V defaultValue) {
    this.assertNotNull(name, "name");
    Tag tag = tagCompound.get(name);
    return this.read(tag, defaultValue);
  }*/

  @Override
  public <K, V> void forEachInMap(
      String name,
      Class<K> kClass,
      Class<V> vClass,
      BiConsumer<K, V> consumer
  ) {
    assertNotNull(name, "name");
    Tag tag = tagCompound.get(name);
    if (tag != null) {
      if (tag instanceof TagList) {
        assertNotNull(kClass, "kClass");
        assertNotNull(vClass, "vClass");
        AxionTypeToken<K> kAxionTypeToken = AxionTypeToken.get(kClass);
        AxionTypeToken<V> vAxionTypeToken = AxionTypeToken.get(vClass);
        this._consumeMap((TagList) tag, kAxionTypeToken, vAxionTypeToken, consumer);
      } else {
        throw new IllegalArgumentException("Expected TagList, got: " + tag.getClass());
      }
    } else { // if (tag == null)
      throw new IllegalArgumentException("Can't find tag for given name: " + name);
    }
  }

  /*@Override
  public <K, V> Map<K, V> readMap(
      String name,
      Class<K> kClass,
      Class<V> vClass,
      Map<K, V> map
  ) {
    assertNotNull(map, "map");
    this.forEachInMap(name, kClass, vClass, map::put);
    return map;
  }*/

  /*@Override
  public <K, V> Map<K, V> readMap(
      String name,
      AxionTypeToken<K> kTypeToken,
      AxionTypeToken<V> vTypeToken,
      Map<K, V> map
  ) {
    return null;
  }*/

  @Override
  public <K, V> void forEachInMap(
      TagList tag,
      Class<K> kClass,
      Class<V> vClass,
      BiConsumer<K, V> consumer
  ) {
    assertNotNull(tag, "tag");
    assertNotNull(kClass, "kClass");
    assertNotNull(vClass, "vClass");
    AxionTypeToken<K> kAxionTypeToken = AxionTypeToken.get(kClass);
    AxionTypeToken<V> vAxionTypeToken = AxionTypeToken.get(vClass);
    this._consumeMap(tag, kAxionTypeToken, vAxionTypeToken, consumer);
  }

  /*@Override
  public <K, V> Map<K, V> readMap(Tag tag, Class<K> kClass, Class<V> vClass, Map<K, V> map) {
    assertNotNull(map, "map");
    this.forEachInMap(tag, kClass, vClass, map::put);
    return map;
  }*/

  private <K, V> void _consumeMap(
      TagList tagList,
      AxionTypeToken<K> kAxionTypeToken,
      AxionTypeToken<V> vAxionTypeToken,
      BiConsumer<K, V> consumer
  ) {
    assertNotNull(kAxionTypeToken, "kAxionTypeToken");
    assertNotNull(vAxionTypeToken, "vAxionTypeToken");
    assertNotNull(consumer, "consumer");
    TypeConverter<TagList, K> keyConverter = axion.getConverter(kAxionTypeToken);
    TypeConverter<TagList, V> valueConverter = axion.getConverter(vAxionTypeToken);
    TagList keyList = tagList.get(0);
    TagList valueList = tagList.get(1);
    for (int i = 0; i < keyList.size(); ++i) {
      K key = keyConverter.convert(keyList.get(i));
      V value = valueConverter.convert(valueList.get(i));
      consumer.accept(key, value);
    }
  }

  @Override
  public <K, V> Stream<Map.Entry<K, V>> streamMap(String name, Class<K> kClass, Class<V> vClass) {
    Tag tag = tagCompound.get(name);
    if (tag != null) {
      if (tag instanceof TagList) {
        return this._streamMap((TagList) tag, kClass, vClass);
      } else {
        throw new IllegalArgumentException("Expected TagList, got: " + tag.getClass());
      }
    } else { // if (tag == null)
      throw new IllegalArgumentException("Can't find tag for given name: " + name);
    }
  }

  @Override
  public <K, V> Stream<Map.Entry<K, V>> streamMap(
      TagList tag,
      Class<K> kClass,
      Class<V> vClass
  ) {
    assertNotNull(tag, "tag");
    return this._streamMap(tag, kClass, vClass);
  }

  private <K, V> Stream<Map.Entry<K, V>> _streamMap(TagList tag, Class<K> kClass, Class<V> vClass) {
    assertNotNull(kClass, "class");
    assertNotNull(vClass, "class");
    LinkedHashMap<K, V> map = new LinkedHashMap<>(tag.size());
    TagList keyList = tag.get(0);
    TagList valueList = tag.get(1);
    for (int i = 0; i < keyList.size(); ++i) {
      K key = axion.fromTag(keyList.get(i), kClass);
      V value = axion.fromTag(valueList.get(i), vClass);
      map.put(key, value);
    }
    return map.entrySet().stream();
  }

  @Override
  public <V> void forEachInCollection(String name, Class<V> vClass, Consumer<V> consumer) {
    assertNotNull(name, "name");
    assertNotNull(consumer, "consumer");
    Tag tag = tagCompound.get(name);
    if (tag != null) {
      if (tag instanceof TagList) {
        this._consumeCollection((TagList) tag, vClass, consumer);
      } else { // not TagList
        throw new IllegalArgumentException("Expected TagList, got: " + tag.getClass());
      }
    } else { // if (tag == null)
      throw new IllegalArgumentException("Can't find tag for given name: " + name);
    }
  }

  /*@Override
  public <V> Collection<V> readCollection(String name, Class<V> vClass, Collection<V> collection) {
    assertNotNull(name, "name");
    assertNotNull(collection, "collection");
    Tag tag = tagCompound.get(name);
    if (tag != null) {
      if (tag instanceof TagList) {
        this._consumeCollection((TagList) tag, vClass, collection::add);
        return collection;
      } else { // not TagList
        throw new IllegalArgumentException("Expected TagList, got: " + tag.getClass());
      }
    } else { // if (tag == null)
      throw new IllegalArgumentException("Can't find tag for given name: " + name);
    }
  }*/

  @Override
  public <V> void forEachInCollection(TagList tag, Class<V> vClass, Consumer<V> consumer) {
    assertNotNull(tag, "tag");
    assertNotNull(consumer, "consumer");
    this._consumeCollection(tag, vClass, consumer);
  }

  /*@Override
  public <V, T extends Tag> Collection<V> readCollection(T tag, Class<V> vClass, Collection<V> collection) {
    assertNotNull(tag, "tag");
    assertNotNull(collection, "collection");
    if (tag instanceof TagList) {
      this._consumeCollection((TagList) tag, vClass, collection::add);
      return collection;
    } else { // not TagList
      throw new IllegalArgumentException("Expected TagList, got: " + tag.getClass());
    }
  }*/

  private <V> void _consumeCollection(TagList tag, Class<V> vClass, Consumer<V> consumer) {
    assertNotNull(vClass, "class");
    tag.forEach(t -> consumer.accept(axion.fromTag(t, vClass)));
  }

  @Override
  public <V> Stream<V> streamCollection(String name, Class<V> vClass) {
    assertNotNull(name, "name");
    assertNotNull(vClass, "class");
    Tag tag = tagCompound.get(name);
    if (tag != null) {
      if (tag instanceof TagList) {
        return this._streamCollection((TagList) tag, tag.getClass(), vClass);
      } else {
        throw new IllegalArgumentException("Expected TagList, got: " + tag.getClass());
      }
    } else { // if (tag == null)
      throw new IllegalArgumentException("Can't find tag for given name: " + name);
    }
  }

  @Override
  public <V> Stream<V> streamCollection(TagList tag, Class<V> vClass) {
    assertNotNull(tag, "tag");
    assertNotNull(vClass, "class");
    return this._streamCollection(tag, tag.getClass(), vClass);
  }

  @SuppressWarnings("unchecked")
  private <V, T extends Tag> Stream<V> _streamCollection(
      TagList tag,
      Class<T> tClass,
      Type type
  ) {
    return (Stream<V>) tag.stream(tClass)
        .map(t -> axion.fromTag(t, type))
        .collect(Collectors.toCollection(LinkedList::new))
        .stream();
  }

  @Override
  public AxionReader getReader(String name) {
    AxionContract.assertArgumentNotNull(name, "name");
    return newReader(tagCompound.get(name));
  }

  @Override
  public AxionReader newReader(TagCompound tagCompound) {
    AxionContract.assertArgumentNotNull(tagCompound, "tagCompound");
    return axion.newReader(tagCompound);
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
