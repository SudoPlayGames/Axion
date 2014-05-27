package com.sudoplay.axion.tag;

public class AxionIllegalTagNameException extends RuntimeException {

  private static final long serialVersionUID = 894086908200994498L;

  public AxionIllegalTagNameException() {
    super();
  }

  public AxionIllegalTagNameException(String message) {
    super(message);
  }

  public AxionIllegalTagNameException(Throwable cause) {
    super(cause);
  }

  public AxionIllegalTagNameException(String message, Throwable cause) {
    super(message, cause);
  }

}
