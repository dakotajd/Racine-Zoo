package edu.uwp.appfactory.racinezoo.Model;

import io.realm.RealmObject;

/**
 * Created by dakota on 3/11/17.
 */

public class RealmString extends RealmObject {

    private String value;

    public RealmString() {
    }

    public RealmString(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
