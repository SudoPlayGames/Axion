package com.sudoplay.axion.adapter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.sudoplay.axion.Axion;
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
import com.sudoplay.axion.spec.tag.TagByte;
import com.sudoplay.axion.spec.tag.TagByteArray;
import com.sudoplay.axion.spec.tag.TagCompound;
import com.sudoplay.axion.spec.tag.TagDouble;
import com.sudoplay.axion.spec.tag.TagFloat;
import com.sudoplay.axion.spec.tag.TagInt;
import com.sudoplay.axion.spec.tag.TagIntArray;
import com.sudoplay.axion.spec.tag.TagList;
import com.sudoplay.axion.spec.tag.TagLong;
import com.sudoplay.axion.spec.tag.TagShort;
import com.sudoplay.axion.spec.tag.TagString;

public interface TagAdapter<T extends Tag> {

  public T read(final Tag parent, final DataInputStream in, final Axion axion) throws IOException;

  public void write(final T tag, final DataOutputStream out, final Axion axion) throws IOException;

  public static final TagAdapter<TagByte> BYTE = new TagByteAdapter();
  public static final TagAdapter<TagByteArray> BYTE_ARRAY = new TagByteArrayAdapter();
  public static final TagAdapter<TagCompound> COMPOUND = new TagCompoundAdapter();
  public static final TagAdapter<TagDouble> DOUBLE = new TagDoubleAdapter();
  public static final TagAdapter<TagFloat> FLOAT = new TagFloatAdapter();
  public static final TagAdapter<TagInt> INT = new TagIntAdapter();
  public static final TagAdapter<TagList> LIST = new TagListAdapter();
  public static final TagAdapter<TagLong> LONG = new TagLongAdapter();
  public static final TagAdapter<TagShort> SHORT = new TagShortAdapter();
  public static final TagAdapter<TagString> STRING = new TagStringAdapter();
  public static final TagAdapter<TagIntArray> INT_ARRAY = new TagIntArrayAdapter();

}
