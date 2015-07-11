package com.sudoplay.axion.tag;

public abstract class Tag implements Cloneable {

  private String name;
  private ContainerTag parent;

  /**
   * Creates a new {@link Tag} with the name given.
   * 
   * @param newName
   *          the name for the new tag
   */
  public Tag(final String newName) {
    setName(newName);
  }

  /**
   * Sets the name of this {@link Tag}. If this tag belongs to a container tag,
   * the {@link ContainerTag#onChildNameChange(String, String)} of the container
   * tag is called.
   * 
   * @param newName
   *          the new name for this {@link Tag}
   * @return this {@link Tag}
   */
  public Tag setName(final String newName) {
    if (parent != null) {
      parent.onChildNameChange(name, newName);
    }
    name = (newName == null) ? "" : newName;
    return this;
  }

  /**
   * Returns the name for this {@link Tag}.
   * 
   * @return the name for this {@link Tag}
   */
  public String getName() {
    return (name == null) ? "" : name;
  }

  /**
   * Adds this {@link Tag} to a {@link ContainerTag}.
   * 
   * @param newParent
   *          the {@link ContainerTag} to add this {@link Tag} to
   * @return this {@link Tag}
   * @throws AxionInvalidTagException
   *           if the {@link ContainerTag} given is null
   * @throws AxionIllegalTagStateException
   *           if this {@link Tag} already has a parent {@link ContainerTag}
   */
  public Tag addTo(final ContainerTag newParent) throws AxionInvalidTagException, AxionIllegalTagStateException {
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

  /**
   * Returns the {@link ContainerTag} this {@link Tag} belongs to.
   * 
   * @return the {@link ContainerTag} this {@link Tag} belongs to
   */
  public ContainerTag getParent() {
    return parent;
  }

  /**
   * Returns <code>true</code> if this {@link Tag} belongs to a
   * {@link ContainerTag}.
   * 
   * @return <code>true</code> if this {@link Tag} belongs to a
   *         {@link ContainerTag}
   */
  public boolean hasParent() {
    return parent != null;
  }

  /**
   * Removes this {@link Tag} from its {@link ContainerTag}. If this {@link Tag}
   * doesn't belong to a container then it does nothing.
   * 
   * @return this {@link Tag}
   */
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

  /**
   * Creates a duplicate of this {@link Tag}.
   */
  @Override
  public abstract Tag clone();

}
