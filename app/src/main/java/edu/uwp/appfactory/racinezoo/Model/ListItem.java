package edu.uwp.appfactory.racinezoo.Model;

/**
 * Determines a head or event type
 *
 * Check later for scope 2/22 (public)
 */

public abstract class ListItem {

    public static final int TYPE_HEADER = 0;
    public static final int TYPE_EVENT = 1;

    abstract public int getType();

}