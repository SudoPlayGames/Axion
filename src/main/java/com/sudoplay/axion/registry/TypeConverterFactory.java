package com.sudoplay.axion.registry;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.tag.Tag;
import com.sudoplay.axion.util.AxionTypeToken;

/**
 * Interface for a factory that produces {@link TypeConverter} implementations.
 * <p>
 * Created by Jason Taylor on 7/17/2015.
 */
public interface TypeConverterFactory {

  <T extends Tag, V> TypeConverter<T, V> create(
      Axion axion,
      AxionTypeToken<V> typeToken
  );

  TypeConverterFactory newInstance(
      Axion axion
  );

  static <TT extends Tag, VV> TypeConverterFactory newFactory(
      AxionTypeToken<VV> typeToken,
      TypeConverter<TT, VV> typeConverter
  ) {
    return new TypeConverterFactory() {
      @SuppressWarnings("unchecked")
      @Override
      public <T extends Tag, V> TypeConverter<T, V> create(
          Axion axion,
          AxionTypeToken<V> candidateTypeToken
      ) {
        return candidateTypeToken.equals(typeToken) ? (TypeConverter<T, V>) typeConverter : null;
      }

      @Override
      public TypeConverterFactory newInstance(Axion axion) {
        return TypeConverterFactory.newFactory(
            typeToken,
            typeConverter.newInstance(axion)
        );
      }
    };
  }

}
