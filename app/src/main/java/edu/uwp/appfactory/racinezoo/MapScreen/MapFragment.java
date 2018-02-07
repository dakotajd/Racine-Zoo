package edu.uwp.appfactory.racinezoo.MapScreen;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uwp.appfactory.racinezoo.CustomView.CustomViewPager;
import edu.uwp.appfactory.racinezoo.R;


public class MapFragment extends Fragment {


    private View rootView;

    private CustomViewPager mViewPager;
    private MapPagerAdapter adapter;
    private Context context;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_map, container, false);
        context = getActivity();
        mViewPager = (CustomViewPager) rootView.findViewById(R.id.view_pager);
        adapter = new MapPagerAdapter(getChildFragmentManager());
        mViewPager.setAdapter(adapter);


        setupTabLayout();
        return rootView;
    }


    public void setupTabLayout(){
        final TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setTabTextColors(ContextCompat.getColor(context,R.color.textColorLight),ContextCompat.getColor(context,R.color.textColorPrimary));
    }
}
