package com.sudoplay.axion.converter;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.registry.TypeConverter;
import com.sudoplay.axion.registry.TypeConverterFactory;
import com.sudoplay.axion.tag.Tag;
import com.sudoplay.axion.util.AxionTypeToken;

/**
 * Created by Jason Taylor on 7/18/2015.
 */
public class ObjectTypeConverterFactory implements TypeConverterFactory {
  @SuppressWarnings("unchecked")
  @Override
  public <T extends Tag, V> TypeConverter<T, V> create(Axion axion, AxionTypeToken<V> typeToken) {
    if (typeToken.getRawType() == Object.class) {
      return (TypeConverter<T, V>) new ObjectTypeConverter<T>(axion);
    }
    return null;
  }

  @Override
  public TypeConverterFactory newInstance(Axion axion) {
    return new ObjectTypeConverterFactory();
  }

  private class ObjectTypeConverter<TT extends Tag> extends TypeConverter<TT, Object> {
    public ObjectTypeConverter(Axion axion) {
      this.axion = axion;
    }

    @Override
    public Object convert(TT tag) {
      return axion.convertTag(tag);
    }

    @Override
    public TT convert(String name, Object value) {
      return axion.convertValue(name, value);
    }
  }
}
