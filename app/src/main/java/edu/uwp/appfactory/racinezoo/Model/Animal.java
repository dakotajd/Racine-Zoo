package edu.uwp.appfactory.racinezoo.Model;



import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import edu.uwp.appfactory.racinezoo.Util.Config;
import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by hanh on 2/11/17.
 */

public class Animal extends RealmObject implements DetailObject {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    private String animalClass;
    @SerializedName("order")
    private String order;
    @SerializedName("family")
    private String family;
    private String genus;
    private String species;
    @SerializedName("latinName")
    private String latinName;
    @SerializedName("habitatAndRange")
    private String habitatAndRange;

    @SerializedName("images")
    private RealmList<RealmString> images;

    @SerializedName("interestingFacts")
    private RealmList<RealmString> facts;

    /*
    These attributes belong to detail items
     */

    @SerializedName("description")
    private String description;

    @SerializedName("adultSize")
    private String adultSize;
    @SerializedName("dietInTheWild")
    private String dietInTheWild;
    @SerializedName("reproduction")
    private String reproduction;
    @SerializedName("lifeSpan")
    private String lifeSpan;
    @SerializedName("perils")
    private String perils;
    @SerializedName("protection")
    private String protection;
    @SerializedName("voice")
    private String voice;

    @SerializedName("ecology")
    private String ecology;
    @SerializedName("conservationStatus")
    private String conservationStatus;
    @SerializedName("dietAtTheZoo")
    private String dietAtTheZoo;
    @SerializedName("atTheRacineZoo")
    private String atTheRacineZoo;

//    private String subTitle;
//    private String imagePaths;

//
    @SerializedName("type")
    private String category;


public Animal(){
    animalClass = "Class";
    genus = "Genus";
    species = "Species";

}


    //location
    private double lat;
    private double lon;



    public ArrayList<DetailItem> getDetailItemList(){
        ArrayList<DetailItem> detailItems = new ArrayList<>();


       detailItems.add(new DetailItem("Facts",facts,Config.DETAIL_TYPE_FACT));
        Log.d("Animal","fact count"+ facts.size());

        //TODO: add lat lon to json
        //detailItems.add(new DetailItem("Location", new LatLng(lat,lon), Config.DETAIL_TYPE_LOCATION));



        ArrayList<String> info = new ArrayList<>();
        info.add(animalClass);
        info.add(order);
        info.add(family);
        info.add(genus);
        info.add(species);

        detailItems.add(new DetailItem("Info",info,Config.DETAIL_TYPE_INFO));

        detailItems.add(new DetailItem("Description",description));

        detailItems.add(new DetailItem("Habitat and Range",habitatAndRange));
        detailItems.add(new DetailItem("Adult Size", adultSize));
        detailItems.add(new DetailItem("Life Span", lifeSpan));



        for (int i = 0; i < detailItems.size(); i++){
            if(detailItems.get(i).getContent()==null){
                detailItems.remove(detailItems.get(i));
            }
        }

        return detailItems;
    }

    @Override
    public String getSubTitle() {
        return null;
    }

    //Setters & Getters

//    public String getSubTitle() {
//        return subTitle;
//    }
//
//    public void setSubTitle(String subTitle) {
//        this.subTitle = subTitle;
//    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public List<RealmString> getImages() {
        return images;
    }



}
