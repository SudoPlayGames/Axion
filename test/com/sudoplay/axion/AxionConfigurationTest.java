package com.sudoplay.axion;

import static org.junit.Assert.*;

import org.junit.Test;

import com.sudoplay.axion.AxionConfiguration.CharacterEncodingType;
import com.sudoplay.axion.AxionConfiguration.CompressionType;
import com.sudoplay.axion.adapter.TagAdapter;
import com.sudoplay.axion.adapter.TagConverter;
import com.sudoplay.axion.spec.tag.TagByte;

public class AxionConfigurationTest {

  @Test
  public void testLocking() {

    Axion axion = Axion.createFrom(Axion.getExt(), "ConfigurationTest");
    AxionConfiguration config = axion.getConfiguration();

    config.lock();
    assertTrue(config.isLocked());
    assertFalse(config.isUnlocked());

    // New, copied configurations should always be unlocked.
    config.clone().assertUnlocked();

    check(config);

    config.unlock();
    config.setImmutable();

    check(config);

    /*
     * Must be unlocked and mutable to set as immutable.
     */
    try {
      config.unlock();
      fail("Expected IllegalStateException");
    } catch (IllegalStateException e) {
      // expected
    }

  }

  private void check(AxionConfiguration config) {
    /*
     * Must be unlocked and mutable to change character encoding type.
     */
    try {
      config.setCharacterEncodingType(CharacterEncodingType.ISO_8859_1);
      fail("Expected IllegalStateException");
    } catch (IllegalStateException e) {
      // expected
    }

    /*
     * Must be unlocked and mutable to change compression type.
     */
    try {
      config.setCompressionType(CompressionType.Deflater);
      fail("Expected IllegalStateException");
    } catch (IllegalStateException e) {
      // expected
    }

    /*
     * Must be unlocked and mutable to set as immutable.
     */
    try {
      config.setImmutable();
      fail("Expected IllegalStateException");
    } catch (IllegalStateException e) {
      // expected
    }

    /*
     * Must be unlocked and mutable to register adapter.
     */
    try {
      config.registerTag(1, TagByte.class, Byte.class, TagAdapter.Spec.BYTE, TagConverter.Spec.BYTE);
      fail("Expected IllegalStateException");
    } catch (IllegalStateException e) {
      // expected
    }

  }

}
