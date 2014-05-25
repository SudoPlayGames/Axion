package com.sudoplay.axion.adapter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.spec.tag.Tag;

public interface TagAdapter {

  public Tag read(final Tag parent, final DataInputStream in, final Axion axion) throws IOException;

  public void write(final Tag tag, final DataOutputStream out, final Axion axion) throws IOException;

}
