package com.sudoplay.axion;

public class AxionInstanceCreationException extends RuntimeException {

  private static final long serialVersionUID = 8165926689975011925L;

  public AxionInstanceCreationException() {
    super();
  }

  public AxionInstanceCreationException(String message) {
    super(message);
  }

  public AxionInstanceCreationException(Throwable cause) {
    super(cause);
  }

  public AxionInstanceCreationException(String message, Throwable cause) {
    super(message, cause);
  }

}
