package com.sudoplay.axion.api.impl;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.api.AxionReader;
import com.sudoplay.axion.registry.AxionTagRegistrationException;
import com.sudoplay.axion.spec.tag.TagCompound;
import com.sudoplay.axion.spec.tag.TagList;
import com.sudoplay.axion.tag.Tag;
import com.sudoplay.axion.util.AxionFunctions;
import com.sudoplay.axion.util.AxionTypeToken;

import java.util.Collection;
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

  @Override
  public <V> V read(String name) {
    this.assertNotNull(name, "name");
    Tag tag = tagCompound.get(name);
    if (tag != null) {
      if (axion.hasConverterForTag(tag)) {
        return axion.convertTag(tag);
      }
      throw new AxionTagRegistrationException("No converter registered for tag: " + tag.getClass());
    }
    return null;
  }

  @Override
  public <V> V read(String name, V defaultValue) {
    this.assertNotNull(name, "name");
    Tag tag = tagCompound.get(name);
    return this.read(tag, defaultValue);
  }

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
  public <V, T extends Tag> V read(T tag) {
    this.assertNotNull(tag, "tag");
    return axion.convertTag(tag);
  }

  @Override
  public <V, T extends Tag> V read(T tag, V defaultValue) {
    if (tag != null) {
      if (axion.hasConverterForTag(tag)) {
        return AxionFunctions.ifNullChangeTo(defaultValue).apply(axion.convertTag(tag));
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
    return function.apply(axion.convertTag(tag));
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
      return function.apply(axion.convertTag(tag, AxionTypeToken.get(vClass)));
    } else {
      return null;
    }
  }

  @Override
  public <V, T extends Tag> V read(T tag, Class<V> vClass) {
    assertNotNull(tag, "tag");
    assertNotNull(vClass, "class");
    return axion.convertTag(tag, AxionTypeToken.get(vClass));
  }

  @Override
  public <V, T extends Tag> V read(T tag, Class<V> vClass, V defaultValue) {
    assertNotNull(vClass, "class");
    if (tag != null) {
      return axion.convertTag(tag, AxionTypeToken.get(vClass));
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
  public <K, V> void consumeMap(String name, Class<K> kClass, Class<V> vClass, BiConsumer<K, V> consumer) {
    assertNotNull(name, "name");
    Tag tag = tagCompound.get(name);
    if (tag != null) {
      if (tag instanceof TagList) {
        this._consumeMap((TagList) tag, kClass, vClass, consumer);
      } else {
        throw new IllegalArgumentException("Expected TagList, got: " + tag.getClass());
      }
    } else { // if (tag == null)
      throw new IllegalArgumentException("Can't find tag for given name: " + name);
    }
  }

  @Override
  public <K, V> Map<K, V> consumeMap(String name, Class<K> kClass, Class<V> vClass, Map<K, V> map) {
    assertNotNull(map, "map");
    this.consumeMap(name, kClass, vClass, map::put);
    return map;
  }

  @Override
  public <K, V, T extends Tag> void consumeMap(T tag, Class<K> kClass, Class<V> vClass, BiConsumer<K, V> consumer) {
    assertNotNull(tag, "tag");
    if (tag instanceof TagList) {
      this._consumeMap((TagList) tag, kClass, vClass, consumer);
    } else {
      throw new IllegalArgumentException("Expected TagList, got: " + tag.getClass());
    }
  }

  @Override
  public <K, V, T extends Tag> Map<K, V> consumeMap(T tag, Class<K> kClass, Class<V> vClass, Map<K, V> map) {
    assertNotNull(map, "map");
    this.consumeMap(tag, kClass, vClass, map::put);
    return map;
  }

  private <K, V> void _consumeMap(TagList tagList, Class<K> kClass, Class<V> vClass, BiConsumer<K, V> consumer) {
    assertNotNull(kClass, "class");
    assertNotNull(vClass, "class");
    assertNotNull(consumer, "consumer");
    TagList keyList = tagList.get(0);
    TagList valueList = tagList.get(1);
    AxionTypeToken<K> kAxionTypeToken = AxionTypeToken.get(kClass);
    AxionTypeToken<V> vAxionTypeToken = AxionTypeToken.get(vClass);
    for (int i = 0; i < keyList.size(); ++i) {
      K key = axion.convertTag(keyList.get(i), kAxionTypeToken);
      V value = axion.convertTag(valueList.get(i), vAxionTypeToken);
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
  public <K, V, T extends Tag> Stream<Map.Entry<K, V>> streamMap(T tag, Class<K> kClass, Class<V> vClass) {
    assertNotNull(tag, "tag");
    if (tag instanceof TagList) {
      return this._streamMap((TagList) tag, kClass, vClass);
    } else {
      throw new IllegalArgumentException("Expected TagList, got: " + tag.getClass());
    }
  }

  private <K, V> Stream<Map.Entry<K, V>> _streamMap(TagList tag, Class<K> kClass, Class<V> vClass) {
    assertNotNull(kClass, "class");
    assertNotNull(vClass, "class");
    LinkedHashMap<K, V> map = new LinkedHashMap<>(tag.size());
    TagList keyList = tag.get(0);
    TagList valueList = tag.get(1);
    AxionTypeToken<K> kAxionTypeToken = AxionTypeToken.get(kClass);
    AxionTypeToken<V> vAxionTypeToken = AxionTypeToken.get(vClass);
    for (int i = 0; i < keyList.size(); ++i) {
      K key = axion.convertTag(keyList.get(i), kAxionTypeToken);
      V value = axion.convertTag(valueList.get(i), vAxionTypeToken);
      map.put(key, value);
    }
    return map.entrySet().stream();
  }

  @Override
  public <V> void consumeCollection(String name, Class<V> vClass, Consumer<V> consumer) {
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

  @Override
  public <V> Collection<V> consumeCollection(String name, Class<V> vClass, Collection<V> collection) {
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
  }

  @Override
  public <V, T extends Tag> void consumeCollection(T tag, Class<V> vClass, Consumer<V> consumer) {
    assertNotNull(tag, "tag");
    assertNotNull(consumer, "consumer");
    if (tag instanceof TagList) {
      this._consumeCollection((TagList) tag, vClass, consumer);
    } else { // not TagList
      throw new IllegalArgumentException("Expected TagList, got: " + tag.getClass());
    }
  }

  @Override
  public <V, T extends Tag> Collection<V> consumeCollection(T tag, Class<V> vClass, Collection<V> collection) {
    assertNotNull(tag, "tag");
    assertNotNull(collection, "collection");
    if (tag instanceof TagList) {
      this._consumeCollection((TagList) tag, vClass, collection::add);
      return collection;
    } else { // not TagList
      throw new IllegalArgumentException("Expected TagList, got: " + tag.getClass());
    }
  }

  private <V> void _consumeCollection(TagList tag, Class<V> vClass, Consumer<V> consumer) {
    assertNotNull(vClass, "class");
    tag.forEach(t -> consumer.accept(axion.convertTag(t, AxionTypeToken.get(vClass))));
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
  public <V, T extends Tag> Stream<V> streamCollection(T tag, Class<V> vClass) {
    assertNotNull(tag, "tag");
    assertNotNull(vClass, "class");
    if (tag instanceof TagList) {
      return this._streamCollection((TagList) tag, tag.getClass(), vClass);
    } else {
      throw new IllegalArgumentException("Expected TagList, got: " + tag.getClass());
    }
  }

  private <V, T extends Tag> Stream<V> _streamCollection(TagList tag, Class<T> tClass, Class<V> vClass) {
    AxionTypeToken<V> vAxionTypeToken = AxionTypeToken.get(vClass);
    return tag.stream(tClass)
        .map(t -> axion.convertTag(t, vAxionTypeToken))
        .collect(Collectors.toCollection(LinkedList::new))
        .stream();
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
