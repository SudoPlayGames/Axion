package com.sudoplay.axion.api;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.spec.tag.TagCompound;

/**
 * Created by Jason Taylor on 7/12/2015.
 */
public interface AxionReaderFactory {

  AxionReader create(Axion axion);

  AxionReader create(TagCompound tagCompound, Axion axion);

}
