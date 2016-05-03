package com.sudoplay.axion;

@SuppressWarnings("unused")
public class AxionConfigurationException extends RuntimeException {

  private static final long serialVersionUID = -631191996864653516L;

  public AxionConfigurationException() {
    super();
  }

  public AxionConfigurationException(String message) {
    super(message);
  }

  public AxionConfigurationException(Throwable cause) {
    super(cause);
  }

  public AxionConfigurationException(String message, Throwable cause) {
    super(message, cause);
  }

}
