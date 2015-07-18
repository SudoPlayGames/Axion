package com.sudoplay.axion.converter;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.api.AxionReader;
import com.sudoplay.axion.api.AxionWritable;
import com.sudoplay.axion.api.AxionWriter;
import com.sudoplay.axion.registry.TagConverter;
import com.sudoplay.axion.registry.TagConverterFactory;
import com.sudoplay.axion.spec.tag.TagCompound;
import com.sudoplay.axion.system.ObjectConstructor;
import com.sudoplay.axion.tag.Tag;
import com.sudoplay.axion.util.AxionTypeToken;

/**
 * Created by Jason Taylor on 7/17/2015.
 */
public class AxionWritableTypeConverterFactory implements TagConverterFactory {

  @Override
  public <T extends Tag, V> TagConverter<T, V> create(Axion axion, AxionTypeToken<V> typeToken) {
    Class<? super V> rawType = typeToken.getRawType();
    if (!AxionWritable.class.isAssignableFrom(rawType)) {
      return null;
    }

    ObjectConstructor<V> constructor = axion.getConstructorConstructor(typeToken);

    @SuppressWarnings("unchecked")
    TagConverter<T, V> result = new Converter(axion, constructor);

    return result;
  }

  @Override
  public TagConverterFactory newInstance(Axion axion) {
    return new AxionWritableTypeConverterFactory();
  }

  private class Converter<V> extends TagConverter<TagCompound, AxionWritable> {

    private final Axion axion;
    private final ObjectConstructor<V> constructor;

    private Converter(
        Axion axion,
        ObjectConstructor<V> constructor
    ) {
      this.axion = axion;
      this.constructor = constructor;
    }

    @Override
    public AxionWritable convert(TagCompound tag) {
      AxionWritable writable = (AxionWritable) constructor.construct();
      AxionReader reader = axion.defaultReader(tag);
      writable.read(reader);
      return writable;
    }

    @Override
    public TagCompound convert(String name, AxionWritable writable) {
      AxionWriter writer = axion.defaultWriter();
      writable.write(writer);
      return writer.getTagCompound();
    }
  }

}
