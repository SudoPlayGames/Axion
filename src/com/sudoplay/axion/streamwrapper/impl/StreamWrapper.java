package com.sudoplay.axion.streamwrapper.impl;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.InflaterInputStream;

import com.sudoplay.axion.streamwrapper.Interface_StreamWrapper;

public class StreamWrapper {

  public static final Interface_StreamWrapper PASSTHRU_STREAM_WRAPPER = new Interface_StreamWrapper() {

    @Override
    public OutputStream wrapOutput(OutputStream outputStream) throws IOException {
      return outputStream;
    }

    @Override
    public InputStream wrapInput(InputStream inputStream) throws IOException {
      return inputStream;
    }
  };

  public static final Interface_StreamWrapper GZIP_STREAM_WRAPPER = new Interface_StreamWrapper() {

    @Override
    public InputStream wrapInput(InputStream inputStream) throws IOException {
      return new DataInputStream(new GZIPInputStream(inputStream));
    }

    @Override
    public OutputStream wrapOutput(OutputStream outputStream) throws IOException {
      return new DataOutputStream(new GZIPOutputStream(outputStream));
    }

  };

  public static final Interface_StreamWrapper DEFLATE_STREAM_WRAPPER = new Interface_StreamWrapper() {

    @Override
    public InputStream wrapInput(InputStream inputStream) throws IOException {
      return new DataInputStream(new InflaterInputStream(inputStream));
    }

    @Override
    public OutputStream wrapOutput(OutputStream outputStream) throws IOException {
      return new DataOutputStream(new DeflaterOutputStream(outputStream));
    }

  };

}
