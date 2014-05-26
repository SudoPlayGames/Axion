package com.sudoplay.axion;

public class AxionReadException extends RuntimeException {

  private static final long serialVersionUID = -6898311001687011797L;

  public AxionReadException() {
    super();
  }

  public AxionReadException(String message) {
    super(message);
  }

  public AxionReadException(Throwable cause) {
    super(cause);
  }

  public AxionReadException(String message, Throwable cause) {
    super(message, cause);
  }

}
