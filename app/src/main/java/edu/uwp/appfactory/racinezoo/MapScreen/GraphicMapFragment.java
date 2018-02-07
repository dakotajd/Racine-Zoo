package edu.uwp.appfactory.racinezoo.MapScreen;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.qozix.tileview.TileView;
import com.qozix.tileview.markers.MarkerLayout;

import java.util.MissingFormatArgumentException;

import edu.uwp.appfactory.racinezoo.DetailScreen.DetailActivity;
import edu.uwp.appfactory.racinezoo.Model.Marker;
import edu.uwp.appfactory.racinezoo.Model.ZooLocation;
import edu.uwp.appfactory.racinezoo.R;
import edu.uwp.appfactory.racinezoo.Util.Config;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by hanh on 2/24/17.
 */

public class GraphicMapFragment extends Fragment {

    private TileView tileView;
    private ImageView mapView;
    private Context context;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getActivity();

        tileView = new TileView(getActivity());
        tileView.setSize( 4800, 3200 );  // the original size of the untiled image
        tileView.setViewportPadding(512);
        tileView.setShouldRenderWhilePanning(true);
        tileView.setShouldScaleToFit(false);
        tileView.setScaleLimits(0.3f, 1.75f);
        tileView.setScale(0.5f);
        tileView.setBackgroundColor(Color.BLACK);
        tileView.setScrollX(400);
        tileView.setScrollY(80);



        tileView.addDetailLevel( 1f, "tiles/map/zoo_map_%d_%d.jpg", 256, 256);



        if (savedInstanceState == null) {
            setMarkers();
        }

        return tileView;
    }


    public void setMarkers(){
        //OLD MARKERS
        Realm realm = Realm.getDefaultInstance();
        RealmResults<ZooLocation> locations = realm.where(ZooLocation.class).findAll();
        for (ZooLocation loc : locations) {
            createMarker(loc.getTitle(), loc.getMapX(), loc.getMapY());
        }
        realm.close();
        //createMarker("Land of the Giants" ,1100,990);
        //createMarker("Private Event Area" ,1140,660);
        //createMarker("Camel Rides",1330,1385);
        //createMarker("Giraffe Encounter",1120,1590);
        //createMarker("Play Zoo",1610,1240);
        //createMarker("Junior League of Racine Zoo Choo Express Train Depot",2010,1315);
        //createMarker("Helen Ireland Wildlife Theatre", 1950,1455);
        //createMarker("Kiwanis Memorial Amphitheatre",1880,545);
        //createMarker("Pheasantry",2300,780);
        //createMarker("A. B. Modine Pond",2270,1930);
        //createMarker("Lake Michigan",2640,463);
        //createMarker("Zoo Beach",2910,665);
        //createMarker("Bear Ridge",2800,845);
        //createMarker("Meerkat Manor",2585,1170);
        //createMarker("Great Cat Canyon",2624,1557);
        //createMarker("Stork Aviary",2890,1557);
        //createMarker("Vanishing Kingdom",2685,1778);
        //createMarker("Max and Jenny’s Jungle Grill",2980,1700);
        //createMarker("Walkabout Creek", 3410,1720);
        //createMarker("Handicapped Parking",3700,1850);
        //createMarker("Benstead Discovery Center",3760,1965);
        //createMarker("Zootique",3550,2160);
        //createMarker("Entrance",3750,2175);
        //createMarker("Norco Aviary",3510,1320);
        //createMarker("Racine Zoological Society Offices",3450,1020);
        //createMarker("Zoo Parking",3130,2650);
        //createMarker("Racine Rotary West Safari Base Camp", 1550, 730);
        //createMarker("Robinson Crusoe Adventure Land", 3080, 1220);
        //createMarker("Barnyard Safari", 3250, 1350);


        //createMarker("Restroom",3630,2020);
        //createMarker("Restroom",3045,1650);
        //createMarker("Restroom",2610,1850);
        //createMarker("Restroom",1220,965);

    }

    public void createMarker(String locationTitle, float x, float y){
        ImageView marker = new ImageView(context);
        marker.setImageResource(R.drawable.marker);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(100, 100);
        marker.setLayoutParams(layoutParams);


        marker.setTag(locationTitle);
        tileView.addMarker( marker, x, y, -0.5f, -0.75f );

        //set up location for marker here





        tileView.setMarkerTapListener(new MarkerLayout.MarkerTapListener() {
            @Override
            public void onMarkerTap(View view, int x, int y) {
                Realm realm = Realm.getDefaultInstance();
                //Marker location = realm.where(Marker.class).equalTo("location", (String)view.getTag()).findFirst();
                ZooLocation location = realm.where(ZooLocation.class).equalTo("title", (String)view.getTag()).findFirst();
                if (location != null && (location.getDescription() != null || location.getAnimals().size() > 0)) {
                    Intent intent = new Intent(getActivity(), DetailActivity.class);
                    intent.putExtra("name", location.getName());
                    intent.putExtra("type", Config.LOCATION);
                    startActivity(intent);
                    //TODO: goto fragment detail
                } else {
                    Toast.makeText(context, "There is no additional information available for this location.",Toast.LENGTH_SHORT).show();
                }
                realm.close();
            }
        });
    }





}
