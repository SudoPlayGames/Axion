package com.sudoplay.axion.stream;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.InflaterInputStream;

public interface StreamCompressionWrapper {

  public InputStream wrapInput(final InputStream inputStream) throws IOException;

  public OutputStream wrapOutput(final OutputStream outputStream) throws IOException;

  public static final StreamCompressionWrapper PASSTHRU_STREAM_WRAPPER = new StreamCompressionWrapper() {

    @Override
    public OutputStream wrapOutput(OutputStream outputStream) throws IOException {
      return outputStream;
    }

    @Override
    public InputStream wrapInput(InputStream inputStream) throws IOException {
      return inputStream;
    }
  };

  public static final StreamCompressionWrapper GZIP_STREAM_WRAPPER = new StreamCompressionWrapper() {

    @Override
    public InputStream wrapInput(InputStream inputStream) throws IOException {
      return new GZIPInputStream(inputStream);
    }

    @Override
    public OutputStream wrapOutput(OutputStream outputStream) throws IOException {
      return new GZIPOutputStream(outputStream);
    }

  };

  public static final StreamCompressionWrapper DEFLATE_STREAM_WRAPPER = new StreamCompressionWrapper() {

    @Override
    public InputStream wrapInput(InputStream inputStream) throws IOException {
      return new InflaterInputStream(inputStream);
    }

    @Override
    public OutputStream wrapOutput(OutputStream outputStream) throws IOException {
      return new DeflaterOutputStream(outputStream);
    }

  };

}
