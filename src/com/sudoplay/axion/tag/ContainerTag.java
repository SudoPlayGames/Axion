package com.sudoplay.axion.tag;

import java.util.Iterator;

/**
 * A {@link ContainerTag} can hold other tags.
 * 
 * @author Jason Taylor
 */
public abstract class ContainerTag extends Tag implements Iterable<Tag> {

  public ContainerTag(String newName) {
    super(newName);
  }

  public abstract int size();

  public abstract Iterator<Tag> iterator();

  public abstract boolean contains(final Tag tag);

  public abstract void clear();

  /**
   * Called when a child tag's name changes.
   * 
   * @param oldName
   *          the name before the change
   * @param newName
   *          the name after the change
   */
  protected abstract void onChildNameChange(final String oldName, final String newName);

  /**
   * Called when a child is added to this container tag.
   * 
   * @param tag
   *          tag to add
   */
  protected abstract void onChildAddition(final Tag tag);

  /**
   * Called when a child tag is removed from this container tag.
   * 
   * @param tag
   *          tag to remove
   */
  protected abstract void onChildRemoval(final Tag tag);

}
