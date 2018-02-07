package edu.uwp.appfactory.racinezoo.Navigation;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import edu.uwp.appfactory.racinezoo.AnimalListScreen.AnimalListFragment;
import edu.uwp.appfactory.racinezoo.EventScreen.EventFragment;
import edu.uwp.appfactory.racinezoo.InfoScreen.InfoFragment;
import edu.uwp.appfactory.racinezoo.MapScreen.MapFragment;

/**
 * Created by hanh on 2/9/17.
 */

public class NavigationPagerAdapter extends FragmentStatePagerAdapter{
    public NavigationPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new AnimalListFragment();
            case 1:
                return new MapFragment();
            case 2:
                return new EventFragment();
            case 3:
                return new InfoFragment();
        }

        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
