package com.sudoplay.axion.api.impl;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.api.AxionWriter;
import com.sudoplay.axion.spec.tag.TagCompound;
import com.sudoplay.axion.tag.Tag;

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
  public <S extends Tag> AxionWriter write(
      String name,
      S tag
  ) {
    assertNotNull(name, "name");
    assertNotNull(tag, "tag");
    tagCompound.put(name, tag);
    return this;
  }

  @Override
  public AxionWriter write(
      String name,
      Object object
  ) {
    assertNotNull(name, "name");
    assertNotNull(object, "object");

    if (object instanceof Tag) {
      tagCompound.put(name, (Tag) object);

    } else {
      this._write(name, object);
    }

    return this;
  }

  @Override
  public <S extends Tag> AxionWriter writeIf(
      String name,
      S tag,
      Predicate<S> predicate
  ) {
    assertNotNull(name, "name");
    assertNotNull(predicate, "predicate");
    if (predicate.test(tag)) {
      assertNotNull(tag, "tag");
      tagCompound.put(name, tag);
    }
    return this;
  }

  @Override
  public <O> AxionWriter writeIf(
      String name,
      O object,
      Predicate<O> predicate
  ) {
    assertNotNull(name, "name");
    assertNotNull(predicate, "predicate");
    if (predicate.test(object)) {
      assertNotNull(object, "object");
      this._write(name, object);
    }
    return this;
  }

  @Override
  public <S extends Tag> AxionWriter writeIfNotNull(
      String name,
      S tag
  ) {
    assertNotNull(name, "name");
    if (tag != null) tagCompound.put(name, tag);
    return this;
  }

  @Override
  public <O> AxionWriter writeIfNotNull(
      String name,
      O object
  ) {
    assertNotNull(name, "name");
    if (object != null) this._write(name, object);
    return this;
  }

  /**
   * Calls {@link Axion#toTag(Object)} and writes the result.
   * <p>
   * Neither name or object parameter can be null.
   *
   * @param name   name
   * @param object object
   */
  private void _write(
      String name,
      Object object
  ) {
    assertNotNull(name, "name");
    assertNotNull(object, "object");
    tagCompound.put(name, axion.toTag(object));
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
