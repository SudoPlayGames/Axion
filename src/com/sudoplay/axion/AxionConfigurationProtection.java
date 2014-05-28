package com.sudoplay.axion;

/**
 * The {@link AxionConfigurationProtection} class manages the protection level
 * of the {@link AxionConfiguration} it belongs to.
 * 
 * @author Jason Taylor
 */
public class AxionConfigurationProtection {

  /**
   * The current protection mode.
   * 
   * @see ProtectionMode
   */
  private ProtectionMode configurationProtectionMode;

  /**
   * The protection mode for an {@link AxionConfiguration}.
   * 
   * @see AxionConfiguration#lock()
   * @see AxionConfiguration#unlock()
   * @see AxionConfiguration#setImmutable()
   */
  protected static enum ProtectionMode {
    /**
     * The {@link AxionConfiguration} can be modified, including setting the
     * protection mode to <b>Locked</b> or <b>Immutable</b>.
     * 
     * @see AxionConfiguration#lock()
     * @see AxionConfiguration#unlock()
     */
    Unlocked,

    /**
     * The {@link AxionConfiguration} can't be modified unless first unlocked
     * via {@link AxionConfiguration#unlock()}.
     * 
     * @see AxionConfiguration#lock()
     * @see AxionConfiguration#unlock()
     */
    Locked,

    /**
     * The {@link AxionConfiguration} can't be modified, period.
     * 
     * @see AxionConfiguration#setImmutable()
     */
    Immutable
  }

  /**
   * Creates a new instance of {@link AxionConfigurationProtection} with the
   * give {@link ProtectionMode}.
   * 
   * @param newConfigurationProtectionMode
   */
  protected AxionConfigurationProtection(final ProtectionMode newConfigurationProtectionMode) {
    configurationProtectionMode = newConfigurationProtectionMode;
  }

  /**
   * Changes the protection mode to <b>Locked</b>. Any attempt to change this
   * configuration while it is locked will result in an exception. Use
   * {@link #unlock()} to unlock.
   * <p>
   * Can't use when <b>Immutable</b>.
   * 
   * @see #unlock()
   * @see #setImmutable()
   */
  protected void lock() {
    assertMutable();
    configurationProtectionMode = ProtectionMode.Locked;
  }

  /**
   * Changes the protection mode to <b>Unlocked</b>.
   * <p>
   * Can't use when <b>Immutable</b>.
   * 
   * @see #lock()
   * @see #setImmutable()
   */
  protected void unlock() {
    assertMutable();
    configurationProtectionMode = ProtectionMode.Unlocked;
  }

  /**
   * Changes the protection mode to <b>Immutable</b>. Once this mode is set, it
   * can't be undone.
   * 
   * @see #lock()
   * @see #unlock()
   */
  protected void setImmutable() {
    assertUnlocked();
    assertMutable();
    configurationProtectionMode = ProtectionMode.Immutable;
  }

  /**
   * @return true if protection mode is <b>Locked</b>
   */
  protected boolean isLocked() {
    return configurationProtectionMode == ProtectionMode.Locked;
  }

  /**
   * @return true if protection mode is <b>Unlocked</b>
   */
  protected boolean isUnlocked() {
    return configurationProtectionMode == ProtectionMode.Unlocked;
  }

  /**
   * @return true if protection mode is <b>Immutable</b>
   */
  protected boolean isImmutable() {
    return configurationProtectionMode == ProtectionMode.Immutable;
  }

  /**
   * @throws AxionConfigurationException
   *           if protection mode is <b>Locked</b>
   */
  protected void assertUnlocked() throws AxionConfigurationException {
    if (configurationProtectionMode == ProtectionMode.Locked) {
      throw new AxionConfigurationException(Axion.class.getSimpleName() + " instance has been locked and can't be modified");
    }
  }

  /**
   * @throws AxionConfigurationException
   *           if protection mode is <b>Immutable</b>
   */
  protected void assertMutable() throws AxionConfigurationException {
    if (configurationProtectionMode == ProtectionMode.Immutable) {
      throw new AxionConfigurationException(Axion.class.getSimpleName() + " instance is immutable and can't be modified");
    }
  }

}
