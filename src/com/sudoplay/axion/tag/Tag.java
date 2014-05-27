package com.sudoplay.axion.tag;

public abstract class Tag implements Cloneable {

  private String name;
  private ContainerTag parent;

  public Tag(final String newName) {
    setName(newName);
  }

  public Tag setName(final String newName) {
    if (parent != null) {
      parent.onChildNameChange(name, newName);
    }
    name = (newName == null) ? "" : newName;
    return this;
  }

  public String getName() {
    return (name == null) ? "" : name;
  }

  public Tag addTo(final ContainerTag newParent) {
    if (newParent == null) {
      throw new AxionInvalidTagException("Can't set parent tag to null; use removeFromParent() to remove this tag from its parent");
    } else if (parent != null) {
      throw new AxionIllegalTagStateException("Tag [" + this.toString() + "] already has parent [" + parent.toString()
          + "]; use removeFromParent() to remove this tag from its parent before assigning a new parent");
    }
    parent = newParent;
    parent.onChildAddition(this);
    return this;
  }

  public ContainerTag getParent() {
    return parent;
  }

  public boolean hasParent() {
    return parent != null;
  }

  public Tag removeFromParent() {
    if (parent != null) {
      parent.onChildRemoval(this);
    }
    parent = null;
    return this;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Tag other = (Tag) obj;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    return true;
  }

  @Override
  public String toString() {
    if (!name.equals("") || name.equals(null)) {
      return this.getClass().getSimpleName() + "(\"" + name + "\")";
    } else {
      return this.getClass().getSimpleName();
    }
  }

  @Override
  public abstract Tag clone();

}
