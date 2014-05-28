package com.sudoplay.axion;

import org.junit.Assert;
import org.junit.Test;

import com.sudoplay.axion.AxionConfigurationProtection.ProtectionMode;

public class AxionConfigurationProtectionTest {

  private static final AxionConfigurationProtection getConfig(final ProtectionMode newMode) {
    return new AxionConfigurationProtection(newMode);
  }

  @Test
  public void test_constructor() {
    Assert.assertTrue(getConfig(ProtectionMode.Unlocked).isUnlocked());
    Assert.assertTrue(getConfig(ProtectionMode.Locked).isLocked());
    Assert.assertTrue(getConfig(ProtectionMode.Immutable).isImmutable());
  }

  @Test
  public void test_lock() {
    AxionConfigurationProtection config = getConfig(ProtectionMode.Unlocked);
    config.lock();
    Assert.assertTrue(config.isLocked());

    // should throw
    try {
      config.setImmutable();
      Assert.fail("Expected AxionConfigurationException");
    } catch (AxionConfigurationException e) {
      // expected
    }

    // should throw
    try {
      config.assertUnlocked();
      Assert.fail("Expected AxionConfigurationException");
    } catch (AxionConfigurationException e) {
      // expected
    }

    // should do nothing
    config.lock();
  }

  @Test
  public void test_unlock() {
    AxionConfigurationProtection config = getConfig(ProtectionMode.Locked);
    config.unlock();
    Assert.assertTrue(config.isUnlocked());

    // should not throw
    config.assertUnlocked();
  }

  @Test
  public void test_setImmutable() {
    AxionConfigurationProtection config = getConfig(ProtectionMode.Unlocked);
    config.setImmutable();
    Assert.assertTrue(config.isImmutable());

    // should do nothing
    config.setImmutable();

    // should throw
    try {
      config.unlock();
      Assert.fail("Expected AxionConfigurationException");
    } catch (AxionConfigurationException e) {
      // expected
    }

    // should throw
    try {
      config.lock();
      Assert.fail("Expected AxionConfigurationException");
    } catch (AxionConfigurationException e) {
      // expected
    }

    // should throw
    try {
      config.assertMutable();
      Assert.fail("Expected AxionConfigurationException");
    } catch (AxionConfigurationException e) {
      // expected
    }

  }

}
