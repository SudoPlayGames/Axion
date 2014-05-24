package com.sudoplay.axion.tag.extended;

import com.sudoplay.axion.tag.spec.Tag;

public class TagBoolean extends Tag {

  private boolean data;

  public TagBoolean(final String newName) {
    super(newName);
  }

  public TagBoolean(final String newName, final boolean newBoolean) {
    super(newName);
    data = newBoolean;
  }

  public void set(final boolean newBoolean) {
    data = newBoolean;
  }

  public boolean get() {
    return data;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + (data ? 1231 : 1237);
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!super.equals(obj))
      return false;
    if (getClass() != obj.getClass())
      return false;
    TagBoolean other = (TagBoolean) obj;
    if (data != other.data)
      return false;
    return true;
  }

  @Override
  public Tag clone() {
    return new TagBoolean(getName(), data);
  }

}
