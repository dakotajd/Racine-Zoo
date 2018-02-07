package edu.uwp.appfactory.racinezoo.MapScreen;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import edu.uwp.appfactory.racinezoo.MapScreen.GoogleMapFragment;
import edu.uwp.appfactory.racinezoo.MapScreen.GraphicMapFragment;

/**
 * Created by hanh on 2/24/17.
 */

public class MapPagerAdapter extends FragmentStatePagerAdapter {
    public MapPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new GraphicMapFragment();
            case 1:
                return new GoogleMapFragment();

        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Graphic";
            case 1:
                return "Satelite";
        }
        return super.getPageTitle(position);
    }
}
