package edu.uwp.appfactory.racinezoo.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import edu.uwp.appfactory.racinezoo.Util.Config;
import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by dakota on 4/29/17.
 */

public class Marker extends RealmObject implements DetailObject {

    @SerializedName("location")
    private String location;

    @SerializedName("description")
    private String description;

    @SerializedName("animals")
    private RealmList<RealmString> animals;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        description = description;
    }

    public RealmList<RealmString> getAnimals() {
        return animals;
    }

    public void setAnimals(RealmList<RealmString> animals) {
        this.animals = animals;
    }

    @Override
    public String getName() {
        return location;
    }

    @Override
    public String getSubTitle() {
        return null;
    }

    @Override
    public ArrayList<DetailItem> getDetailItemList() {
        ArrayList<DetailItem> detailItems = new ArrayList<>();
        detailItems.add(new DetailItem("Description", description));
        if (animals != null && animals.size() > 0) {
            detailItems.add(new DetailItem("Animals", animals, Config.DETAIL_TYPE_FACT));
        }

        for (int i = 0; i < detailItems.size(); i++){
            Object data = detailItems.get(i).getContent();
            if(data == null) {
                detailItems.remove(detailItems.get(i));
                i--;
            }
        }

        return detailItems;
    }

    @Override
    public List<RealmString> getImages() {
        return null;
    }
}
