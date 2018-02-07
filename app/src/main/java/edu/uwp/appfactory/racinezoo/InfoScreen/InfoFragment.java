package edu.uwp.appfactory.racinezoo.InfoScreen;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;

import edu.uwp.appfactory.racinezoo.InfoScreen.InfoDetailActivity;
import edu.uwp.appfactory.racinezoo.Prefs;
import edu.uwp.appfactory.racinezoo.R;
import edu.uwp.appfactory.racinezoo.Util.BeaconUtil;
import edu.uwp.appfactory.racinezoo.Util.Config;

import static edu.uwp.appfactory.racinezoo.Util.Config.*;

public class InfoFragment extends Fragment implements BeaconUtil.BluetoothDialogListener{

    public interface BeaconControllerContract {
        void initBeaconController();
        void deinitBeaconController();
    }

    private View rootView;

    private LinearLayout hourInfo;
    private LinearLayout directionInfo;
    private LinearLayout showsInfo;
    private LinearLayout aboutInfo;
    private LinearLayout privacyInfo;
    private LinearLayout adminInfo;
    private LinearLayout foodInfo;
    private LinearLayout contactInfo;

    private Switch exploreModeSwitch;
    private BeaconUtil.BluetoothDialogListener listener;

    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_info, container, false);
        context = getActivity();

        hourInfo = (LinearLayout)rootView.findViewById(R.id.info_hour);

        hourInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, InfoDetailActivity.class);
                intent.putExtra("type",INFO_TYPE_HOUR);
                startActivity(intent);


            }
        });

        directionInfo =(LinearLayout)rootView.findViewById(R.id.info_direction);

        directionInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, InfoDetailActivity.class);
                intent.putExtra("type",INFO_TYPE_DIRECT);
                startActivity(intent);
            }
        });


        showsInfo =(LinearLayout)rootView.findViewById(R.id.info_show);

        showsInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, InfoDetailActivity.class);
                intent.putExtra("type",INFO_TYPE_SHOW);
                startActivity(intent);
            }
        });

        aboutInfo=(LinearLayout)rootView.findViewById(R.id.info_about);

        aboutInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, InfoDetailActivity.class);
                intent.putExtra("type",INFO_TYPE_ABOUT);
                startActivity(intent);
            }
        });

        privacyInfo =(LinearLayout)rootView.findViewById(R.id.info_privacy);

        privacyInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, InfoDetailActivity.class);
                intent.putExtra("type",INFO_TYPE_PRIVACY);
                startActivity(intent);
            }
        });

        adminInfo =(LinearLayout)rootView.findViewById(R.id.info_admin);

        adminInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, InfoDetailActivity.class);
                intent.putExtra("type",INFO_TYPE_ADMIN);
                startActivity(intent);
            }
        });

        foodInfo =(LinearLayout)rootView.findViewById(R.id.info_food);

        foodInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, InfoDetailActivity.class);
                intent.putExtra("type",INFO_TYPE_FOOD);
                startActivity(intent);
            }
        });

        contactInfo =(LinearLayout)rootView.findViewById(R.id.info_contact);

        contactInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, InfoDetailActivity.class);
                intent.putExtra("type",INFO_TYPE_CONTACT);
                startActivity(intent);
            }
        });


        listener = this;
        final LinearLayout exploreModeLayout = (LinearLayout) rootView.findViewById(R.id.info_explore_mode);
        exploreModeSwitch = (Switch) rootView.findViewById(R.id.explore_mode_switch);
        exploreModeSwitch.setChecked(Prefs.isExploreMode());
        exploreModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                BeaconControllerContract activity = (BeaconControllerContract)getActivity();
                if(isChecked){
                    BeaconUtil.promptForBluetooth(context, listener );
                    activity.initBeaconController();
                } else {
                    activity.deinitBeaconController();
                }
                Prefs.setPrefExploreMode(isChecked);
            }
        });
        exploreModeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exploreModeSwitch.performClick();
            }
        });


        return rootView;
    }


    @Override
    public void setEnable(boolean enable) {
        exploreModeSwitch.setChecked(enable);
        Prefs.setPrefExploreMode(enable);
    }
}
