package com.sudoplay.axion.tag;

public class AxionIllegalTagStateException extends RuntimeException {

  private static final long serialVersionUID = 3928846000614440236L;

  public AxionIllegalTagStateException() {
    super();
  }

  public AxionIllegalTagStateException(String message) {
    super(message);
  }

  public AxionIllegalTagStateException(Throwable cause) {
    super(cause);
  }

  public AxionIllegalTagStateException(String message, Throwable cause) {
    super(message, cause);
  }

}
