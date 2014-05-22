package com.sudoplay.axion.streamwrapper;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface Interface_StreamWrapper {

  public DataInput wrapInput(final InputStream inputStream) throws IOException;

  public DataOutput wrapOutput(final OutputStream outputStream) throws IOException;

}
