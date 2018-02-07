package edu.uwp.appfactory.racinezoo.Model;


import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;

/**
 * Created by hanh on 2/18/17.
 */

public interface DetailObject {
    String getName();
    String getSubTitle();
    ArrayList<DetailItem> getDetailItemList();
    List<RealmString> getImages();
}
