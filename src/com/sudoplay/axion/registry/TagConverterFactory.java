package com.sudoplay.axion.registry;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.tag.Tag;
import com.sudoplay.axion.util.AxionTypeToken;

/**
 * Interface for a factory that produces {@link TagConverter} implementations.
 * <p>
 * Created by Jason Taylor on 7/17/2015.
 */
public interface TagConverterFactory {

  <T extends Tag, V> TagConverter<T, V> create(
      Axion axion,
      AxionTypeToken<V> typeToken
  );

  TagConverterFactory newInstance(
      Axion axion
  );

  static <TT extends Tag, VV> TagConverterFactory newFactory(
      AxionTypeToken<VV> typeToken,
      TagConverter<TT, VV> tagConverter
  ) {
    return new TagConverterFactory() {
      @SuppressWarnings("unchecked")
      @Override
      public <T extends Tag, V> TagConverter<T, V> create(
          Axion axion,
          AxionTypeToken<V> candidateTypeToken
      ) {
        return candidateTypeToken.equals(typeToken) ? (TagConverter<T, V>) tagConverter : null;
      }

      @Override
      public TagConverterFactory newInstance(Axion axion) {
        return TagConverterFactory.newFactory(
            typeToken,
            tagConverter.newInstance(axion)
        );
      }
    };
  }

}
