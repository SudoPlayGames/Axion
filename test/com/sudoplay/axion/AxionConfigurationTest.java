package com.sudoplay.axion;

import com.sudoplay.axion.AxionConfiguration.CharacterEncodingType;
import com.sudoplay.axion.AxionConfiguration.CompressionType;
import com.sudoplay.axion.registry.TagAdapter;
import com.sudoplay.axion.registry.TypeConverter;
import com.sudoplay.axion.registry.TypeConverterFactory;
import com.sudoplay.axion.spec.converter.TagStringConverter;
import com.sudoplay.axion.spec.tag.TagByte;
import com.sudoplay.axion.util.AxionTypeToken;
import org.junit.Test;

import static org.junit.Assert.*;

public class AxionConfigurationTest {

  @Test
  public void testLocking() {

    Axion axion = Axion.createInstanceFrom(Axion.getExtInstance(), "ConfigurationTest");
    AxionConfiguration config = axion.configuration();

    config.lock();
    assertTrue(config.isLocked());
    assertFalse(config.isUnlocked());

    // New, copied configurations should always be unlocked.
    assertTrue(config.copy(axion).isUnlocked());

    check(config, axion);

    config.unlock();
    config.setImmutable();

    check(config, axion);

    /*
     * Must be unlocked and mutable to set as immutable.
     */
    try {
      config.unlock();
      fail("Expected AxionConfigurationException");
    } catch (AxionConfigurationException e) {
      // expected
    }

  }

  private void check(AxionConfiguration config, Axion axion) {
    /*
     * Must be unlocked and mutable to change character encoding type.
     */
    try {
      config.setCharacterEncodingType(CharacterEncodingType.ISO_8859_1);
      fail("Expected AxionConfigurationException");
    } catch (AxionConfigurationException e) {
      // expected
    }

    /*
     * Must be unlocked and mutable to change compression type.
     */
    try {
      config.setCompressionType(CompressionType.Deflater);
      fail("Expected AxionConfigurationException");
    } catch (AxionConfigurationException e) {
      // expected
    }

    /*
     * Must be unlocked and mutable to set as immutable.
     */
    try {
      config.setImmutable();
      fail("Expected AxionConfigurationException");
    } catch (AxionConfigurationException e) {
      // expected
    }

    /*
     * Must be unlocked and mutable to register adapter.
     */
    try {
      config.registerTag(axion, 1, TagByte.class, Byte.class, TagAdapter.Spec.BYTE, TypeConverter.Spec.BYTE);
      fail("Expected AxionConfigurationException");
    } catch (AxionConfigurationException e) {
      // expected
    }

    /*
     * Must be unlocked and mutable to register base adapter.
     */
    try {
      config.registerBaseTagAdapter(axion, TagAdapter.Spec.BASE);
      fail("Expected AxionConfigurationException");
    } catch (AxionConfigurationException e) {
      // expected
    }

    /*
     * Must be unlocked and mutable to register.
     */
    try {
      AxionTypeToken<?> typeToken = AxionTypeToken.get(Byte.class);
      config.registerFactory(
          axion,
          TypeConverterFactory.newFactory(
              AxionTypeToken.get(String.class),
              new TagStringConverter()
          )
      );
      fail("Expected AxionConfigurationException");
    } catch (AxionConfigurationException e) {
      // expected
    }

  }
}
