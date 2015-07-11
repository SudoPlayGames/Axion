package com.sudoplay.axion;

public class AxionWriteException extends RuntimeException {

  private static final long serialVersionUID = -5549811049717327797L;

  public AxionWriteException() {
    super();
  }

  public AxionWriteException(String message) {
    super(message);
  }

  public AxionWriteException(Throwable cause) {
    super(cause);
  }

  public AxionWriteException(String message, Throwable cause) {
    super(message, cause);
  }

}
