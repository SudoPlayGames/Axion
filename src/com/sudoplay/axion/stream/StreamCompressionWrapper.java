package com.sudoplay.axion.stream;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.InflaterInputStream;

/**
 * The {@link StreamCompressionWrapper} is an interface to create wrappers
 * responsible for wrapping the given {@link InputStream} or
 * {@link OutputStream} with a compression stream.
 * <p>
 * Implementations of this interface should contain <b>no state</b>.
 * 
 * @author Jason Taylor
 */
public interface StreamCompressionWrapper {

  public InputStream wrap(final InputStream inputStream) throws IOException;

  public OutputStream wrap(final OutputStream outputStream) throws IOException;

  /**
   * Wraps the given streams in a {@link GZIPInputStream} or
   * {@link GZIPOutputStream}.
   */
  public static final StreamCompressionWrapper GZIP_STREAM_COMPRESSION_WRAPPER = new StreamCompressionWrapper() {
    @Override
    public OutputStream wrap(OutputStream outputStream) throws IOException {
      return new GZIPOutputStream(outputStream);
    }

    @Override
    public InputStream wrap(InputStream inputStream) throws IOException {
      return new GZIPInputStream(inputStream);
    }
  };

  /**
   * Wraps the given streams in an {@link InflaterInputStream} or a
   * {@link DeflaterOutputStream}.
   */
  public static final StreamCompressionWrapper DEFLATER_STREAM_COMPRESSION_WRAPPER = new StreamCompressionWrapper() {
    @Override
    public OutputStream wrap(OutputStream outputStream) throws IOException {
      return new DeflaterOutputStream(outputStream);
    }

    @Override
    public InputStream wrap(InputStream inputStream) throws IOException {
      return new InflaterInputStream(inputStream);
    }
  };

  /**
   * Performs no wrapping; simply passes the given stream through.
   */
  public static final StreamCompressionWrapper PASSTHROUGH_STREAM_COMPRESSION_WRAPPER = new StreamCompressionWrapper() {
    @Override
    public OutputStream wrap(OutputStream outputStream) throws IOException {
      return outputStream;
    }

    @Override
    public InputStream wrap(InputStream inputStream) throws IOException {
      return inputStream;
    }
  };

}
