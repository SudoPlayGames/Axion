package com.sudoplay.axion;

@SuppressWarnings("unused")
public class AxionInstanceException extends RuntimeException {

  private static final long serialVersionUID = 8165926689975011925L;

  public AxionInstanceException() {
    super();
  }

  public AxionInstanceException(String message) {
    super(message);
  }

  public AxionInstanceException(Throwable cause) {
    super(cause);
  }

  public AxionInstanceException(String message, Throwable cause) {
    super(message, cause);
  }

}
