package com.sudoplay.axion.api;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.spec.tag.TagCompound;

/**
 * Created by Jason Taylor on 7/12/2015.
 */
public interface AxionWriterFactory {

  AxionWriter create(Axion axion);

  AxionWriter create(TagCompound tagCompound, Axion axion);

}
