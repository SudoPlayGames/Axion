package com.sudoplay.axion.api.impl;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.AxionWriteException;
import com.sudoplay.axion.api.AxionWritable;
import com.sudoplay.axion.api.AxionWriter;
import com.sudoplay.axion.spec.tag.TagCompound;
import com.sudoplay.axion.spec.tag.TagList;
import com.sudoplay.axion.tag.Tag;

import java.util.Collection;
import java.util.Map;
import java.util.function.Predicate;

/**
 * Default implementation of the AxionWriter interface.
 * <p>
 * Created by Jason Taylor on 7/12/2015.
 */
@SuppressWarnings("unused")
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
    assertNotNull(name, "name");
    assertNotNull(tag, "tag");
    tagCompound.put(name, tag);
    return this;
  }

  @Override
  public AxionWriter write(String name, AxionWritable axionWritable) {
    assertNotNull(name, "name");
    assertNotNull(axionWritable, "axionWritable");
    this._writeAxionWritable(name, axionWritable);
    return this;
  }

  @Override
  public <V> AxionWriter write(String name, Collection<V> collection) {
    assertNotNull(name, "name");
    tagCompound.put(name, this._writeCollection(collection));
    return this;
  }

  // TODO: finish test
  @Override
  public <K, V> AxionWriter write(String name, Map<K, V> map) {
    assertNotNull(name, "name");
    assertNotNull(map, "map");
    tagCompound.put(name, this._writeMap(map));
    return this;
  }

  @Override
  public AxionWriter write(String name, Object object) {
    assertNotNull(name, "name");
    assertNotNull(object, "object");
    this._writeObject(name, object);
    return this;
  }

  @Override
  public <S extends Tag> AxionWriter writeIf(String name, S tag, Predicate<S> predicate) {
    assertNotNull(name, "name");
    assertNotNull(predicate, "predicate");
    if (predicate.test(tag)) {
      assertNotNull(tag, "tag");
      tagCompound.put(name, tag);
    }
    return this;
  }

  // TODO: test
  @Override
  public AxionWriter writeIf(String name, AxionWritable axionWritable, Predicate<AxionWritable> predicate) {
    assertNotNull(name, "name");
    assertNotNull(predicate, "predicate");
    if (predicate.test(axionWritable)) {
      assertNotNull(axionWritable, "axionWritable");
      this._writeAxionWritable(name, axionWritable);
    }
    return this;
  }

  // TODO: test
  @Override
  public <V> AxionWriter writeIf(String name, Collection<V> collection, Predicate<Collection<V>> predicate) {
    assertNotNull(name, "name");
    assertNotNull(predicate, "predicate");
    if (predicate.test(collection)) {
      assertNotNull(collection, "collection");
      tagCompound.put(name, this._writeCollection(collection));
    }
    return this;
  }

  @Override
  public <K, V> AxionWriter writeIf(String name, Map<K, V> map, Predicate<Map<K, V>> predicate) {
    assertNotNull(name, "name");
    assertNotNull(predicate, "predicate");
    if (predicate.test(map)) {
      assertNotNull(map, "map");
      tagCompound.put(name, this._writeMap(map));
    }
    return this;
  }

  @Override
  public <O> AxionWriter writeIf(String name, O object, Predicate<O> predicate) {
    assertNotNull(name, "name");
    assertNotNull(predicate, "predicate");
    if (predicate.test(object)) {
      assertNotNull(object, "object");
      this._writeObject(name, object);
    }
    return this;
  }

  @Override
  public <S extends Tag> AxionWriter writeIfNotNull(String name, S tag) {
    assertNotNull(name, "name");
    if (tag != null) tagCompound.put(name, tag);
    return this;
  }

  @Override
  public AxionWriter writeIfNotNull(String name, AxionWritable axionWritable) {
    assertNotNull(name, "name");
    if (axionWritable != null) this._writeAxionWritable(name, axionWritable);
    return this;
  }

  // TODO: test
  @Override
  public <V> AxionWriter writeIfNotNull(String name, Collection<V> collection) {
    assertNotNull(name, "name");
    if (collection != null) tagCompound.put(name, this._writeCollection(collection));
    return this;
  }

  // TODO: test
  @Override
  public <K, V> AxionWriter writeIfNotNull(String name, Map<K, V> map) {
    assertNotNull(name, "name");
    if (map != null) tagCompound.put(name, this._writeMap(map));
    return this;
  }

  @Override
  public <O> AxionWriter writeIfNotNull(String name, O object) {
    assertNotNull(name, "name");
    if (object != null) this._writeObject(name, object);
    return this;
  }

  /**
   * Writes an implementation of AxionWritable.
   * <p>
   * Assumes neither name or axionWritable parameter is null.
   *
   * @param name          name
   * @param axionWritable {@link AxionWritable} implementation
   */
  private void _writeAxionWritable(String name, AxionWritable axionWritable) {
    AxionWriter writer = axion.defaultWriter();
    axionWritable.write(writer);
    tagCompound.put(name, writer.getTagCompound());
  }

  /**
   * Maps or converts a mappable or convertible object.
   * <p>
   * Assumes neither name or object parameter is null.
   *
   * @param name   name
   * @param object object
   */
  private void _writeObject(String name, Object object) {
    if (axion.hasMapperFor(object.getClass())) {
      tagCompound.put(name, axion.createTagWithMapper(name, object));

    } else if (axion.hasConverterFor(object)) {
      tagCompound.put(name, axion.createTagWithConverter(name, object));

    } else {
      throw new AxionWriteException("Class has no mapper or converter registered: " + object.getClass()
          .toString());
    }
  }

  private <K, V> TagList _writeMap(Map<K, V> map) {
    if (map.isEmpty()) {
      throw new IllegalArgumentException("Can't write an empty map");
    }

    K key = map.keySet().iterator().next();
    V value = map.values().iterator().next();

    if (key == null) {
      throw new AxionWriteException("Can't write a map with a null key");
    } else if (value == null) {
      throw new AxionWriteException("Can't write a map with a null value");
    }

    Tag keyTag = (key instanceof AxionWritable)
        ? axion.createTagFrom((AxionWritable) key)
        : axion.createTagFrom(key);
    Tag valueTag = (value instanceof AxionWritable)
        ? axion.createTagFrom((AxionWritable) value)
        : axion.createTagFrom(value);

    TagList list = new TagList(TagList.class);
    TagList keyList = new TagList(keyTag.getClass());
    TagList valueList = new TagList(valueTag.getClass());

    list.add(keyList);
    list.add(valueList);

    map.forEach((k, v) -> {
      if (k == null) {
        throw new AxionWriteException("Can't write a map with a null key");
      } else if (v == null) {
        throw new AxionWriteException("Can't write a map with a null value for key: " + k);
      }
      keyList.add((k instanceof AxionWritable)
          ? axion.createTagFrom((AxionWritable) k)
          : axion.createTagFrom(k));
      valueList.add((v instanceof AxionWritable)
          ? axion.createTagFrom((AxionWritable) v)
          : axion.createTagFrom(v));
    });

    return list;
  }

  private <V> TagList _writeCollection(Collection<V> collection) {
    assertNotNull(collection, "collection");
    if (collection.isEmpty()) {
      throw new AxionWriteException("Can't write an empty collection");
    }

    V value = collection.iterator().next();

    if (value == null) {
      throw new AxionWriteException("Can't write a collection with a null value");
    }

    Tag valueTag = (value instanceof AxionWritable)
        ? axion.createTagFrom((AxionWritable) value)
        : axion.createTagFrom(value);

    TagList list = new TagList(valueTag.getClass());

    collection.forEach(v -> {
      if (v == null) {
        throw new AxionWriteException("Can't write a collection with a null value");
      }
      list.add((value instanceof AxionWritable)
          ? axion.createTagFrom((AxionWritable) value)
          : axion.createTagFrom(value));
    });

    return list;
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

  private <O> O assertNotNull(O o, String message) {
    if (o == null) throw new IllegalArgumentException("Parameter can't be null: " + message);
    return o;
  }

}
