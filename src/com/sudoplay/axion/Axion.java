package com.sudoplay.axion;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.sudoplay.axion.adapter.DefaultAdapter;
import com.sudoplay.axion.adapter.Adapter;
import com.sudoplay.axion.stream.StreamCompressionWrapper;
import com.sudoplay.axion.tag.Tag;
import com.sudoplay.axion.tag.TagCompound;

public class Axion {

  public enum CompressionType {
    Deflater, GZip, None
  }

  private Adapter adapter;
  private StreamCompressionWrapper streamWrapper;

  public Axion() {
    adapter = new DefaultAdapter();
    streamWrapper = StreamCompressionWrapper.GZIP_STREAM_WRAPPER;
  }

  public void setAdapter(final Adapter newAdapter) {
    adapter = newAdapter;
  }

  public void setCompressionType(final CompressionType newCompressionType) {
    switch (newCompressionType) {
    case Deflater:
      streamWrapper = StreamCompressionWrapper.DEFLATE_STREAM_WRAPPER;
      break;
    case None:
      streamWrapper = StreamCompressionWrapper.PASSTHRU_STREAM_WRAPPER;
      break;
    default:
    case GZip:
      streamWrapper = StreamCompressionWrapper.GZIP_STREAM_WRAPPER;
      break;
    }
  }

  public Tag read(final InputStream inputStream) throws IOException {
    return readRaw(streamWrapper.wrapInput(inputStream));
  }

  public void write(final TagCompound tagCompound, final OutputStream outputStream) throws IOException {
    writeRaw(tagCompound, streamWrapper.wrapOutput(outputStream));
  }

  public Tag readRaw(final InputStream in) throws IOException {
    return adapter.read(null, in);
  }

  public void writeRaw(final Tag tag, final OutputStream out) throws IOException {
    adapter.write(tag, out);
  }

}
