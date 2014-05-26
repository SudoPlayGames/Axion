package com.sudoplay.axion.adapter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.ext.adapter.TagBooleanAdapter;
import com.sudoplay.axion.ext.adapter.TagBooleanArrayAdapter;
import com.sudoplay.axion.ext.adapter.TagDoubleArrayAdapter;
import com.sudoplay.axion.ext.adapter.TagFloatArrayAdapter;
import com.sudoplay.axion.ext.adapter.TagLongArrayAdapter;
import com.sudoplay.axion.ext.adapter.TagShortArrayAdapter;
import com.sudoplay.axion.ext.adapter.TagStringArrayAdapter;
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
import com.sudoplay.axion.spec.tag.Tag;

public interface TagAdapter<T extends Tag> {

  public T read(final Tag parent, final DataInputStream in, final Axion axion) throws IOException;

  public void write(final T tag, final DataOutputStream out, final Axion axion) throws IOException;

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

  public static final TagBooleanAdapter BOOLEAN = new TagBooleanAdapter();
  public static final TagDoubleArrayAdapter DOUBLE_ARRAY = new TagDoubleArrayAdapter();
  public static final TagFloatArrayAdapter FLOAT_ARRAY = new TagFloatArrayAdapter();
  public static final TagLongArrayAdapter LONG_ARRAY = new TagLongArrayAdapter();
  public static final TagShortArrayAdapter SHORT_ARRAY = new TagShortArrayAdapter();
  public static final TagStringArrayAdapter STRING_ARRAY = new TagStringArrayAdapter();
  public static final TagBooleanArrayAdapter BOOLEAN_ARRAY = new TagBooleanArrayAdapter();

}
