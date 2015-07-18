package com.sudoplay.axion.api.impl;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.api.AxionWritable;
import com.sudoplay.axion.api.AxionWriter;
import com.sudoplay.axion.spec.tag.TagCompound;
import com.sudoplay.axion.spec.tag.TagList;
import com.sudoplay.axion.tag.Tag;
import com.sudoplay.axion.util.AxionTypeToken;

import java.text.AttributedCharacterIterator;
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
    this._write(name, axionWritable);
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
  public <K, V> AxionWriter write(String name, Map<K, V> map, AxionTypeToken<Map<K, V>> typeToken) {
    assertNotNull(name, "name");
    assertNotNull(map, "map");
    tagCompound.put(name, axion.convertValue(map, typeToken));
    return this;
  }

  @Override
  public AxionWriter write(String name, Object object) {
    assertNotNull(name, "name");
    assertNotNull(object, "object");
    this._write(name, object);
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
      this._write(name, axionWritable);
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
      tagCompound.put(name, axion.convertValue(map));
    }
    return this;
  }

  @Override
  public <O> AxionWriter writeIf(String name, O object, Predicate<O> predicate) {
    assertNotNull(name, "name");
    assertNotNull(predicate, "predicate");
    if (predicate.test(object)) {
      assertNotNull(object, "object");
      this._write(name, object);
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
    if (axionWritable != null) this._write(name, axionWritable);
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
    if (map != null) tagCompound.put(name, axion.convertValue(map));
    return this;
  }

  @Override
  public <O> AxionWriter writeIfNotNull(String name, O object) {
    assertNotNull(name, "name");
    if (object != null) this._write(name, object);
    return this;
  }

  /**
   * Calls {@link Axion#convertValue(Object)} and writes the result.
   * <p>
   * Neither name or object parameter can be null.
   *
   * @param name   name
   * @param object object
   */
  private void _write(String name, Object object) {
    assertNotNull(name, "name");
    assertNotNull(object, "object");
    tagCompound.put(name, axion.convertValue(object));
  }

  private <V> TagList _writeCollection(Collection<V> collection) {
    return axion.convertValue(collection);
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
