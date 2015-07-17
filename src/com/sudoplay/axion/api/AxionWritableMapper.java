package com.sudoplay.axion.api;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.mapper.AxionMapper;
import com.sudoplay.axion.mapper.AxionMapperFactory;
import com.sudoplay.axion.spec.tag.TagCompound;
import com.sudoplay.axion.tag.Tag;
import com.sudoplay.axion.util.AxionTypeToken;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Created by Jason Taylor on 7/16/2015.
 */
public class AxionWritableMapper<W extends AxionWritable> implements AxionMapper<TagCompound, W> {

  public static AxionMapperFactory FACTORY = new AxionMapperFactory() {
    @Override
    public <T extends Tag, V> AxionMapper<T, V> create(Axion axion, AxionTypeToken<V> candidateTypeToken) {
      Type type = candidateTypeToken.getType();

      Class<? super V> rawType = candidateTypeToken.getRawType();
      if (!AxionWritable.class.isAssignableFrom(rawType)) {
        return null;
      }

      return new AxionWritableMapper<W>(new Supplier<W>() {
        @Override
        public W get() {
          return null;
        }
      });
    }
  };

  public AxionWritableMapper(Supplier<W> supplier) {

  }

  @Override
  public TagCompound createTagFrom(String name, AxionWritable object, Axion axion) {
    return null;
  }

  @Override
  public W createObjectFrom(TagCompound tag, Axion axion) {
    return null;
  }
}
