package com.sudoplay.axion.adapter;

import java.io.IOException;

import com.sudoplay.axion.AxionInstanceCreationException;
import com.sudoplay.axion.ext.adapter.TagBooleanAdapter;
import com.sudoplay.axion.ext.adapter.TagBooleanArrayAdapter;
import com.sudoplay.axion.ext.adapter.TagDoubleArrayAdapter;
import com.sudoplay.axion.ext.adapter.TagFloatArrayAdapter;
import com.sudoplay.axion.ext.adapter.TagLongArrayAdapter;
import com.sudoplay.axion.ext.adapter.TagShortArrayAdapter;
import com.sudoplay.axion.ext.adapter.TagStringArrayAdapter;
import com.sudoplay.axion.spec.adapter.BaseTagAdapter;
import com.sudoplay.axion.spec.adapter.TagByteAdapter;
import com.sudoplay.axion.spec.adapter.TagByteArrayAdapter;
import com.sudoplay.axion.spec.adapter.TagCompoundAdapter;
import com.sudoplay.axion.spec.adapter.TagDoubleAdapter;
import com.sudoplay.axion.spec.adapter.TagFloatAdapter;
import com.sudoplay.axion.spec.adapter.TagIntAdapter;
import com.sudoplay.axion.spec.adapter.TagIntArrayAdapter;
import com.sudoplay.axion.spec.adapter.TagListAdapter;
import com.sudoplay.axion.spec.adapter.TagLongAdapter;
import com.sudoplay.axion.spec.adapter.TagShortAdapter;
import com.sudoplay.axion.spec.adapter.TagStringAdapter;
import com.sudoplay.axion.stream.AxionInputStream;
import com.sudoplay.axion.stream.AxionOutputStream;
import com.sudoplay.axion.tag.Tag;

/**
 * Classes that extend the abstract {@link TagAdapter} class define how
 * {@link Tag}s are read from and written to the underlying streams.
 * 
 * @author Jason Taylor
 * 
 * @param <T>
 *          {@link Tag} type
 */
public abstract class TagAdapter<T extends Tag> extends RegistryAccessor {

  /**
   * Group of {@link TagAdapter}s that conform to the original NBT
   * specification.
   */
  public static class Spec {
    public static final BaseTagAdapter BASE = new BaseTagAdapter();
    public static final TagByteAdapter BYTE = new TagByteAdapter();
    public static final TagByteArrayAdapter BYTE_ARRAY = new TagByteArrayAdapter();
    public static final TagCompoundAdapter COMPOUND = new TagCompoundAdapter();
    public static final TagDoubleAdapter DOUBLE = new TagDoubleAdapter();
    public static final TagFloatAdapter FLOAT = new TagFloatAdapter();
    public static final TagIntAdapter INT = new TagIntAdapter();
    public static final TagListAdapter LIST = new TagListAdapter();
    public static final TagLongAdapter LONG = new TagLongAdapter();
    public static final TagShortAdapter SHORT = new TagShortAdapter();
    public static final TagStringAdapter STRING = new TagStringAdapter();
    public static final TagIntArrayAdapter INT_ARRAY = new TagIntArrayAdapter();
  }

  /**
   * Group of {@link TagAdapter}s that conform to Axion's custom, extended
   * specification.
   */
  public static class Ext {
    public static final TagBooleanAdapter BOOLEAN = new TagBooleanAdapter();
    public static final TagDoubleArrayAdapter DOUBLE_ARRAY = new TagDoubleArrayAdapter();
    public static final TagFloatArrayAdapter FLOAT_ARRAY = new TagFloatArrayAdapter();
    public static final TagLongArrayAdapter LONG_ARRAY = new TagLongArrayAdapter();
    public static final TagShortArrayAdapter SHORT_ARRAY = new TagShortArrayAdapter();
    public static final TagStringArrayAdapter STRING_ARRAY = new TagStringArrayAdapter();
    public static final TagBooleanArrayAdapter BOOLEAN_ARRAY = new TagBooleanArrayAdapter();
  }

  /**
   * Reads a {@link Tag} from the {@link AxionInputStream}.
   * 
   * @param parent
   *          the parent of the {@link Tag} being read
   * @param in
   *          the {@link AxionInputStream}
   * @return the {@link Tag} read
   * @throws IOException
   */
  public abstract T read(final Tag parent, final AxionInputStream in) throws IOException;

  /**
   * Writes a {@link Tag} to the {@link AxionOutputStream}.
   * 
   * @param tag
   *          the {@link Tag} to write
   * @param out
   *          the {@link AxionOutputStream} to write to
   * @throws IOException
   */
  public abstract void write(final T tag, final AxionOutputStream out) throws IOException;

  /**
   * Creates a new instance of this {@link TagAdapter} and assigns a reference
   * to the {@link TagRegistry} given.
   * 
   * @param newTagRegistry
   *          the {@link TagRegistry} to assign to the new instance
   * @return a new instance of this {@link TagAdapter}
   * @throws AxionInstanceCreationException
   */
  @SuppressWarnings("unchecked")
  protected <A extends Tag> TagAdapter<A> newInstance(final TagRegistry newTagRegistry) throws AxionInstanceCreationException {
    try {
      TagAdapter<A> newInstance = this.getClass().newInstance();
      newInstance.setRegistry(newTagRegistry);
      return newInstance;
    } catch (Exception e) {
      throw new AxionInstanceCreationException("Unable to instantiate new adapter of type [" + this.getClass().getSimpleName() + "]", e);
    }
  }

}
