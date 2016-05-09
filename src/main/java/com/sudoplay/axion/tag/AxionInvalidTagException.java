package com.sudoplay.axion.tag;

@SuppressWarnings("unused")
public class AxionInvalidTagException extends RuntimeException {

  private static final long serialVersionUID = 713766273941609280L;

  public AxionInvalidTagException() {
    super();
  }

  public AxionInvalidTagException(String message) {
    super(message);
  }

  public AxionInvalidTagException(Throwable cause) {
    super(cause);
  }

  public AxionInvalidTagException(String message, Throwable cause) {
    super(message, cause);
  }

}
