package edu.uwp.appfactory.racinezoo.MapScreen;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import edu.uwp.appfactory.racinezoo.DetailScreen.DetailActivity;
import edu.uwp.appfactory.racinezoo.Model.ZooLocation;
import edu.uwp.appfactory.racinezoo.R;
import edu.uwp.appfactory.racinezoo.Util.Config;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by hanh on 2/24/17.
 */

public class GoogleMapFragment extends SupportMapFragment implements OnMapReadyCallback {


    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        getMapAsync(this);
        return super.onCreateView(layoutInflater, viewGroup, bundle);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng location = new LatLng(42.749878, -87.783451);
        googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 18));

        LatLngBounds ZOO = new LatLngBounds(
                new LatLng(42.746742, -87.784897), new LatLng(42.752046, -87.781518));
        // Constrain the camera target to the Adelaide bounds.
        googleMap.setLatLngBoundsForCameraTarget(ZOO);

        setUpMap(googleMap);


    }

    private void setUpMap(GoogleMap map) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<ZooLocation> locations = realm.where(ZooLocation.class).findAll();
        for (ZooLocation location : locations) {
            String imageName = location.getImage();
            if (imageName.equals("FeaturePin")) {
                map.addMarker(new MarkerOptions().position(new LatLng(location.getLat(), location.getLon())).
                        title(location.getTitle()).
                        icon(BitmapDescriptorFactory.fromResource(R.drawable.featurepin)));
            } else if (imageName.equals("BathroomPin")) {
                map.addMarker(new MarkerOptions().position(new LatLng(location.getLat(), location.getLon())).
                        title(location.getTitle()).
                        icon(BitmapDescriptorFactory.fromResource(R.drawable.bathroompin)));
            } else {
                map.addMarker(new MarkerOptions().position(new LatLng(location.getLat(), location.getLon())).
                        title(location.getTitle()).
                        icon(BitmapDescriptorFactory.fromResource(R.drawable.animalpin)));
            }


        }
        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Realm realm = Realm.getDefaultInstance();
                //edu.uwp.appfactory.racinezoo.Model.Marker location = realm.where(edu.uwp.appfactory.racinezoo.Model.Marker.class).equalTo("location", marker.getTitle()).findFirst();
                ZooLocation location = realm.where(ZooLocation.class).equalTo("title", marker.getTitle()).findFirst();
                if (location != null && (location.getDescription() != null || location.getAnimals().size() > 0)) {
                    Intent intent = new Intent(getActivity(), DetailActivity.class);
                    intent.putExtra("name", location.getTitle());
                    intent.putExtra("type", Config.LOCATION);
                    startActivity(intent);
                    //TODO: goto fragment detail
                } else {
                    Toast.makeText(getContext(), "There is no additional information available for this location.", Toast.LENGTH_SHORT).show();
                }
                realm.close();
            }
        });


        realm.close();

    }
}
