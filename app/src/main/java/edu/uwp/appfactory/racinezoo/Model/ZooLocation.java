package edu.uwp.appfactory.racinezoo.Model;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import edu.uwp.appfactory.racinezoo.Util.Config;
import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by ChenMingxi on 4/22/17.
 */

public class ZooLocation extends RealmObject implements DetailObject {

    @SerializedName("id")
    private int id;
    @SerializedName("title")
    private String title;
    @SerializedName("latitude")
    private double lat;
    @SerializedName("longitude")
    private double lon;
    @SerializedName("image")
    private String image;
    @SerializedName("description")
    private String description;
    @SerializedName("animals")
    private RealmList<RealmString> animals;
    @SerializedName("x")
    private int mapX;
    @SerializedName("y")
    private int mapY;



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    @Override
    public String getName() {
        return title;
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
            detailItems.add(new DetailItem("Animals", animals, Config.DETAIL_TYPE_CLICKABLE_ANIMAL));
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RealmList<RealmString> getAnimals() {
        return animals;
    }

    public void setAnimals(RealmList<RealmString> animals) {
        this.animals = animals;
    }

    public int getMapX() {
        return mapX;
    }

    public void setMapX(int mapX) {
        this.mapX = mapX;
    }

    public int getMapY() {
        return mapY;
    }

    public void setMapY(int mapY) {
        this.mapY = mapY;
    }
}
