package com.sudoplay.axion.api;

import com.sudoplay.axion.spec.tag.Tag;

public interface AxionWriteable<T extends Tag> {

  public T write();

  public void read(T tag);

}
