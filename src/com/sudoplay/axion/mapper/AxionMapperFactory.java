package com.sudoplay.axion.mapper;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.tag.Tag;
import com.sudoplay.axion.util.AxionTypeToken;

import java.lang.reflect.Type;

/**
 * Interface for a factory to produce {@link AxionMapper} implementations.
 * <p>
 * Created by Jason Taylor on 7/16/2015.
 */
public interface AxionMapperFactory {
  <T extends Tag, V> AxionMapper<T, V> create(
      Axion axion,
      AxionTypeToken<V> valueTypeToken
  );

  static <TT extends Tag, VV> AxionMapperFactory newFactory(
      Class<VV> vvClass,
      AxionMapper<TT, VV> mapper
  ) {
    return AxionMapperFactory.newFactory(AxionTypeToken.get(vvClass), mapper);
  }

  static <TT extends Tag, VV> AxionMapperFactory newFactory(
      AxionTypeToken<VV> type,
      AxionMapper<TT, VV> mapper
  ) {
    return new AxionMapperFactory() {
      @SuppressWarnings("unchecked")
      @Override
      public <T extends Tag, V> AxionMapper<T, V> create(
          Axion axion,
          AxionTypeToken<V> valueTypeToken
      ) {
        return valueTypeToken.equals(type) ? (AxionMapper<T, V>) mapper : null;
      }
    };
  }

}
