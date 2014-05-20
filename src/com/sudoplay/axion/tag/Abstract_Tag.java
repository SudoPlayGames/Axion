package com.sudoplay.axion.tag;

public abstract class Abstract_Tag implements Interface_Tag {

  private String name;

  public Abstract_Tag(final String newName) {
    setName(newName);
  }

  public void setName(final String newName) {
    name = (newName == null) ? "" : newName;
  }

  public String getName() {
    return (name == null) ? "" : name;
  }

}
