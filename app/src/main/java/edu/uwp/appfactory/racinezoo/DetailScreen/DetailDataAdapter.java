package edu.uwp.appfactory.racinezoo.DetailScreen;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.support.transition.ChangeBounds;
import android.support.transition.Transition;
import android.support.transition.TransitionManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import edu.uwp.appfactory.racinezoo.Model.Animal;
import edu.uwp.appfactory.racinezoo.Model.DetailItem;
import edu.uwp.appfactory.racinezoo.Model.RealmString;
import edu.uwp.appfactory.racinezoo.R;
import edu.uwp.appfactory.racinezoo.Util.Config;
import io.realm.Realm;
import io.realm.RealmList;

/**
 * Created by hanh on 2/18/17.
 */


public class DetailDataAdapter extends RecyclerView.Adapter<DetailDataAdapter.DataViewHolder> {


    private DetailActivity context;
    private List<DetailItem> detailItems;
    private int mExpandedPosition = -1;
    private RecyclerView rv;



    public void setRv(RecyclerView rv) {
        this.rv = rv;
    }

    public DetailDataAdapter( DetailActivity context){
        detailItems = new ArrayList<>();
        this.context = context;
    }

    public void setDetailItems(List<DetailItem> detailItems){
        this.detailItems = detailItems;
    }



    public class DataViewHolder extends RecyclerView.ViewHolder{

        RelativeLayout header;
        TextView headerText;
        View content;
        View root;
        ImageButton arrow;
        public DataViewHolder(View itemView) {
            super(itemView);
            root = itemView;
            header = (RelativeLayout)itemView.findViewById(R.id.header);
            headerText = (TextView)itemView.findViewById(R.id.header_text);
            content = itemView.findViewById(R.id.content);
            arrow = (ImageButton)itemView.findViewById(R.id.menu_arrow);


        }
    }

    @Override
    public DataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = null;
        switch (viewType){
            case Config.DETAIL_TYPE_GENERAL:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_general,parent,false);
                break;
            case Config.DETAIL_TYPE_FACT:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_fact,parent,false);
                break;
            case Config.DETAIL_TYPE_LOCATION:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_location,parent,false);
                break;
            case Config.DETAIL_TYPE_INFO:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_info,parent,false);
                break;
            case Config.DETAIL_TYPE_CLICKABLE_ANIMAL:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_fact,parent,false);


        }

        DataViewHolder holder = new DataViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final DataViewHolder holder, final int position) {

        final boolean isExpanded = position == mExpandedPosition;
        holder.content.setVisibility(isExpanded? View.VISIBLE: View.GONE);
        holder.root.setActivated(isExpanded);

        if(!isExpanded){
            holder.arrow.setRotation(180);
        }
        holder.arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mExpandedPosition = isExpanded? -1: position;


                if(isExpanded == false){
                    ObjectAnimator rotateArrow = ObjectAnimator.ofFloat(holder.arrow, "rotation", 180,  0);
                    rotateArrow.setDuration(250);
                    rotateArrow.start();

                }
                else {
                    ObjectAnimator rotateArrow = ObjectAnimator.ofFloat(holder.arrow, "rotation", 0,  180);
                    rotateArrow.setDuration(250);
                    rotateArrow.start();
                }


                Transition mFadeTransition = new ChangeBounds();
                mFadeTransition.setDuration(200);
                TransitionManager.beginDelayedTransition(rv,mFadeTransition);
                notifyDataSetChanged();
            }
        });

        holder.header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mExpandedPosition = isExpanded? -1: position;


                if(isExpanded == false){
                    ObjectAnimator rotateArrow = ObjectAnimator.ofFloat(holder.arrow, "rotation", 180,  0);
                    rotateArrow.setDuration(250);
                    rotateArrow.start();

                }
                else {
                    ObjectAnimator rotateArrow = ObjectAnimator.ofFloat(holder.arrow, "rotation", 0,  180);
                    rotateArrow.setDuration(250);
                    rotateArrow.start();
                }


                Transition mFadeTransition = new ChangeBounds();
                mFadeTransition.setDuration(200);
                TransitionManager.beginDelayedTransition(rv,mFadeTransition);
                notifyDataSetChanged();

            }
        });


        DetailItem detailItem = detailItems.get(position);
        holder.headerText.setText(detailItem.getTitle());

        switch (getItemViewType(position)){
            case Config.DETAIL_TYPE_GENERAL:
                ((TextView)holder.content).setText((String)detailItem.getContent());
                break;
            case Config.DETAIL_TYPE_LOCATION:
                final LatLng location = (LatLng) detailItem.getContent();
                SupportMapFragment mapFragment = (SupportMapFragment) context.getSupportFragmentManager().findFragmentById(R.id.map);
                mapFragment.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap googleMap) {
                        if(location!=null){

                            googleMap.addMarker(new MarkerOptions().position(location)
                                    .title(""));

                            googleMap.getUiSettings().setZoomControlsEnabled(false);
                            googleMap.getUiSettings().setZoomGesturesEnabled(false);
                            googleMap.getUiSettings().setScrollGesturesEnabled(false);
                            googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

                            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location ,18));
                        }
                    }
                });
                break;
            case Config.DETAIL_TYPE_FACT:
                LinearLayout contentContainer = ((LinearLayout)holder.content);
                contentContainer.removeAllViews();
                    RealmList<RealmString> facts = (RealmList<RealmString>) detailItem.getContent();
                    for(RealmString fact:facts){
                        View view = LayoutInflater.from(context).inflate(R.layout.item_fact, null);
                        TextView textView = (TextView) view.findViewById(R.id.fact_text);
                        textView.setText(fact.toString());
                        contentContainer.addView(view);
                }

                break;
            case Config.DETAIL_TYPE_INFO:

                 //TODO: Here is where you write code to set the info section
                ArrayList<String> info = (ArrayList<String>)detailItem.getContent();

                TextView animalClass = (TextView)holder.content.findViewById(R.id.content_class);
                animalClass.setText(info.get(0));
                TextView order = (TextView)holder.content.findViewById(R.id.content_order);
                order.setText(info.get(1));
                TextView family = (TextView)holder.content.findViewById(R.id.content_family);
                family.setText(info.get(2));
                TextView genus = (TextView)holder.content.findViewById(R.id.content_genus);
                genus.setText(info.get(3));
                TextView species = (TextView)holder.content.findViewById(R.id.content_species);
                species.setText(info.get(4));
                break;
            case Config.DETAIL_TYPE_CLICKABLE_ANIMAL:
                LinearLayout container = ((LinearLayout)holder.content);
                container.removeAllViews();
                RealmList<RealmString> animals = (RealmList<RealmString>) detailItem.getContent();
                Realm realm = Realm.getDefaultInstance();
                for(final RealmString animal : animals){
                    View view = LayoutInflater.from(context).inflate(R.layout.item_clickable_animal, null);
                    TextView textView = (TextView) view.findViewById(R.id.animal_text);
                    textView.setText(animal.toString());
                    if (realm.where(Animal.class).contains("name", animal.toString()).findFirst() != null) {
                        LinearLayout layout = (LinearLayout) view.findViewById(R.id.animal_clickable_layout);
                        layout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(context, DetailActivity.class);
                                intent.putExtra("name", animal.toString());
                                intent.putExtra("type", Config.ANIMAL);
                                context.startActivity(intent);
                            }
                        });
                    } else {
                        textView.setTextColor(Color.BLACK);
                    }
                    realm.close();
                    container.addView(view);
                }

        }


    }

    @Override
    public int getItemViewType(int position) {
        return detailItems.get(position).getType();

    }


    @Override
    public int getItemCount() {
        return detailItems.size();
    }


}
