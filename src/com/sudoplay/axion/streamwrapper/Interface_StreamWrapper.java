package com.sudoplay.axion.streamwrapper;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface Interface_StreamWrapper {

  public InputStream wrapInput(final InputStream inputStream) throws IOException;

  public OutputStream wrapOutput(final OutputStream outputStream) throws IOException;

}
