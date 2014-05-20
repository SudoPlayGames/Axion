package com.sudoplay.axion.tag;

public abstract class Abstract_NBT implements Interface_NBT {

  private String name;

  public Abstract_NBT(final String newName) {
    setName(newName);
  }

  public void setName(final String newName) {
    name = (newName == null) ? "" : newName;
  }

  public String getName() {
    return (name == null) ? "" : name;
  }

}
