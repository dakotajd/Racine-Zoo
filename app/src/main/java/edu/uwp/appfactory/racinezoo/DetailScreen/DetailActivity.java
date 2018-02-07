package edu.uwp.appfactory.racinezoo.DetailScreen;

import android.content.Context;
import android.content.Intent;

import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Vibrator;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.altbeacon.beacon.BeaconConsumer;

import java.util.List;

import edu.uwp.appfactory.racinezoo.Beacon.BeaconController;
import edu.uwp.appfactory.racinezoo.Model.Animal;
import edu.uwp.appfactory.racinezoo.Model.DetailObject;
import edu.uwp.appfactory.racinezoo.Model.Event;
import edu.uwp.appfactory.racinezoo.Model.Marker;
import edu.uwp.appfactory.racinezoo.Model.RealmString;
import edu.uwp.appfactory.racinezoo.Model.ZooLocation;
import edu.uwp.appfactory.racinezoo.Prefs;
import edu.uwp.appfactory.racinezoo.R;
import edu.uwp.appfactory.racinezoo.Util.BeaconUtil;
import edu.uwp.appfactory.racinezoo.Util.DateUtils;
import io.realm.Realm;
import io.realm.RealmResults;

import static edu.uwp.appfactory.racinezoo.Util.Config.ANIMAL;
import static edu.uwp.appfactory.racinezoo.Util.Config.EVENT;
import static edu.uwp.appfactory.racinezoo.Util.Config.LOCATION;

public class DetailActivity extends AppCompatActivity implements BeaconConsumer{

    DetailObject detailObject;

    Context context;

    private int type;

    private String name;

    private Realm realm;

    private List<RealmString> images;

    private BeaconController beaconController;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            if (Prefs.isExploreMode()) {
                Bundle bundle = message.getData();
                int id = bundle.getInt("beaconId");
                DetailObject animal = realm.where(Animal.class).equalTo("id", id).findFirst();
                DetailObject location = realm.where(ZooLocation.class).equalTo("id", id).findFirst();
                if (animal != null) {
                    long lastSeenTime = Prefs.getAnimalLastSeenTime(animal.getName());
                    Prefs.setSeenAnimal(animal.getName());
                    if (DateUtils.checkAnimalTime(lastSeenTime)) {
                        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                        v.vibrate(150);
                        detailObject = animal;

                        setDataToUI();
                    }
                } else if (location != null) {
                    long lastSeenTime = Prefs.getLocationLastSeenTime(location.getName());
                    Prefs.setSeenLocation(location.getName());
                    if (DateUtils.checkAnimalTime(lastSeenTime)) {
                        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                        v.vibrate(150);
                        detailObject = location;
                        setDataToUI();
                    }
                }
            }
                return true;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);

        realm = Realm.getDefaultInstance();

        Intent intent = getIntent();
        type = intent.getIntExtra("type", ANIMAL);
        name = intent.getStringExtra("name");
        switch (type){
            case ANIMAL:
                detailObject = realm.where(Animal.class).contains("name",name).findFirst();
                if(Prefs.isExploreMode()){
                    beaconController = new BeaconController(this);
                }

                break;
            case LOCATION:
                detailObject = realm.where(ZooLocation.class).equalTo("title", name).findFirst();
                if (Prefs.isExploreMode()) {
                    beaconController = new BeaconController(this);
                }
                break;
            case EVENT:
                detailObject = realm.where(Event.class).equalTo("name",name).findFirst();
                break;
        }

        initUI();
        setDataToUI();
        context = this;



    }

    TextView title;
    TextView subTitle;
    RecyclerView dataList;
    DetailDataAdapter adapter;
    LinearLayout imageHolder;
    ImageView imageView;
    LinearLayout imageHolderLayout;

    public void initUI(){
        title = (TextView) findViewById(R.id.title);
        subTitle = (TextView) findViewById(R.id.sub_title);
        dataList = (RecyclerView) findViewById(R.id.data_list);
        adapter = new DetailDataAdapter(this);
        dataList.setAdapter(adapter);
        adapter.setRv(dataList);
        dataList.setFocusable(false);


        imageHolder = (LinearLayout) findViewById(R.id.image_holder);
        TextView tv = (TextView) findViewById(R.id.animal_album_fullscreen);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(context, DetailPager.class);
                myIntent.putExtra("type",type);
                myIntent.putExtra("name",name);
                startActivity(myIntent);
            }
        };

        tv.setOnClickListener(onClickListener);

        imageHolder.setOnClickListener(onClickListener);

        imageView = (ImageView)findViewById(R.id.animal_album);

        imageHolderLayout = (LinearLayout)findViewById(R.id.image_holder);
    }


    public void setDataToUI() {


        title.setText(detailObject.getName());


        subTitle.setText(detailObject.getSubTitle());

        adapter.setDetailItems(detailObject.getDetailItemList());
        adapter.notifyDataSetChanged();


        images = detailObject.getImages();
        name = detailObject.getName();

        if(images!=null && images.size()>0){
            imageHolderLayout.setVisibility(View.VISIBLE);
            Glide.with(this).load(Uri.parse("file:///android_asset/images/"+images.get(0))).into(imageView);
        }
        else {

            imageHolderLayout.setVisibility(View.GONE);
        }

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (type == EVENT) {
            getMenuInflater().inflate(R.menu.notification_menu, menu);
            return true;
        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (type == EVENT) {
            switch (item.getItemId()) {
                case R.id.notification:
                    Event e = (Event) detailObject;
                    openCal(e);
                    }
                    return false;
            }
        return false;
    }

    public void openCal(Event e) {
        Intent intent = new Intent(Intent.ACTION_EDIT);
        intent.setType("vnd.android.cursor.item/event");
        intent.putExtra(CalendarContract.Events.TITLE, e.getName());
        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, e.getStartDate().getTime());
        intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, e.getEndDate().getTime());
        intent.putExtra(CalendarContract.Events.ALL_DAY, false);
        intent.putExtra(CalendarContract.Events.DESCRIPTION,e.getDescription());
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (realm != null) {
            realm.close();
        }

        if(beaconController!=null){
            beaconController.unbindConsumer(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(beaconController!=null){
            beaconController.bindConsumer(this);
            beaconController.setHandler(handler);
        }

    }

    @Override
    public void onStop(){
        super.onStop();
        if(beaconController!=null){
            beaconController.setHandler(null);
        }
    }

    @Override
    public void onBeaconServiceConnect() {
        beaconController.connectToService();
    }
}
