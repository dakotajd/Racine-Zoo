package edu.uwp.appfactory.racinezoo.DetailScreen;


import edu.uwp.appfactory.racinezoo.Model.Animal;
import edu.uwp.appfactory.racinezoo.Model.DetailObject;
import edu.uwp.appfactory.racinezoo.Model.Event;
import edu.uwp.appfactory.racinezoo.R;
import io.realm.Realm;
import me.relex.circleindicator.CircleIndicator;

import android.content.Intent;
import android.os.Bundle;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import static edu.uwp.appfactory.racinezoo.Util.Config.ANIMAL;
import static edu.uwp.appfactory.racinezoo.Util.Config.EVENT;
import static edu.uwp.appfactory.racinezoo.Util.Config.LOCATION;

public class DetailPager extends AppCompatActivity {


    ViewPager viewPager;
    DetailPagerAdapter adapter;
    DetailObject detailObject;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_pager);

        viewPager = (ViewPager)findViewById(R.id.view_pager);
        viewPager.setOffscreenPageLimit(3);  //this will change depending on how we import pictures

        Realm realm = Realm.getDefaultInstance();

        Intent intent = getIntent();
        int type = intent.getIntExtra("type", ANIMAL);
        String name = intent.getStringExtra("name");
        switch (type){
            case ANIMAL:
                detailObject = realm.copyFromRealm(realm.where(Animal.class).equalTo("name",name).findFirst());
                break;
            case LOCATION:
                break;
            case EVENT:
                detailObject = realm.copyFromRealm(realm.where(Event.class).equalTo("name",name).findFirst());
                break;
        }

        realm.close();
        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);

        adapter = new DetailPagerAdapter(this,detailObject.getImages());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                adapter.getImages().get(position).resetScaleAndCenter();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        indicator.setViewPager(viewPager);

    }

}