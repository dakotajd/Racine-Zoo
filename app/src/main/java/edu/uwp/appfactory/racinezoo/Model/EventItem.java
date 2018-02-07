package edu.uwp.appfactory.racinezoo.Model;

/**
 * Items within an event
 * <p>
 * Check later for scope 2/22 (public)
 */

import android.support.annotation.NonNull;

import java.util.List;

public class EventItem extends ListItem {

    @NonNull
    private List<Event> events;

    public EventItem(@NonNull List<Event> events) {
        this.events = events;
    }

    @NonNull
    public List<Event> getEvents() {
        return this.events;
    }

    @Override
    public int getType() {
        return TYPE_EVENT;
    }

}