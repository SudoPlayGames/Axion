package com.sudoplay.axion;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.sudoplay.axion.adapter.Interface_Adapter;
import com.sudoplay.axion.adapter.impl.DefaultAdapter;
import com.sudoplay.axion.streamwrapper.Interface_StreamWrapper;
import com.sudoplay.axion.streamwrapper.impl.StreamWrapper;
import com.sudoplay.axion.tag.Abstract_Tag;
import com.sudoplay.axion.tag.impl.TagCompound;

public class Axion {

  public enum CompressionType {
    Deflater, GZip, None
  }

  private Interface_Adapter adapter;
  private Interface_StreamWrapper streamWrapper;

  public Axion() {
    adapter = new DefaultAdapter();
    streamWrapper = StreamWrapper.GZIP_STREAM_WRAPPER;
  }

  public void setAdapter(final Interface_Adapter newAdapter) {
    adapter = newAdapter;
  }

  public void setCompressionType(final CompressionType newCompressionType) {
    switch (newCompressionType) {
    case Deflater:
      streamWrapper = StreamWrapper.DEFLATE_STREAM_WRAPPER;
      break;
    case None:
      streamWrapper = StreamWrapper.PASSTHRU_STREAM_WRAPPER;
      break;
    default:
    case GZip:
      streamWrapper = StreamWrapper.GZIP_STREAM_WRAPPER;
      break;
    }
  }

  public Abstract_Tag read(final InputStream inputStream) throws IOException {
    return readRaw(streamWrapper.wrapInput(inputStream));
  }

  public void write(final TagCompound tagCompound, final OutputStream outputStream) throws IOException {
    writeRaw(tagCompound, streamWrapper.wrapOutput(outputStream));
  }

  public Abstract_Tag readRaw(final InputStream in) throws IOException {
    return adapter.read(null, in);
  }

  public void writeRaw(final Abstract_Tag tag, final OutputStream out) throws IOException {
    adapter.write(tag, out);
  }

}
