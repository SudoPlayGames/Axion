package com.sudoplay.axion.api;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.tag.Tag;

public interface AxionWritable<T extends Tag> {

  public T write(final Axion axion);

  public void read(T tag, final Axion axion);

}
