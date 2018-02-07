package edu.uwp.appfactory.racinezoo.Model;

import android.support.annotation.NonNull;

/**
 * Each header item will contain one to many event items
 * <p>
 * Check later for scope 2/22 (public)
 */

public class HeaderItem extends ListItem {

    @NonNull
    private String headerTitle;

    @NonNull
    private String headerDateRange;

    public HeaderItem(@NonNull String headerTitle, @NonNull String headerDateRange) {
        this.headerDateRange = headerDateRange;
        this.headerTitle = headerTitle;
    }

    @Override
    public int getType() {
        return TYPE_HEADER;
    }

    public String getHeaderTitle() {
        return headerTitle;
    }

    public String getHeaderRange() {
        return headerDateRange;
    }
}