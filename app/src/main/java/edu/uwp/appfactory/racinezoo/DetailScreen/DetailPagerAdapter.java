package edu.uwp.appfactory.racinezoo.DetailScreen;

import android.content.Context;
import android.media.Image;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import java.util.ArrayList;
import java.util.List;

import edu.uwp.appfactory.racinezoo.Model.RealmString;
import edu.uwp.appfactory.racinezoo.R;

public class DetailPagerAdapter extends PagerAdapter {

    private ArrayList<SubsamplingScaleImageView> imageHolder = new ArrayList<>();


    private Context ctx;
    private LayoutInflater inflater;
    private List<RealmString> images;

    public DetailPagerAdapter(Context ctx, List<RealmString> images){
        this.ctx = ctx;
        this.images = images;

    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view ==(LinearLayout)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        inflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.detail_pager_item,container,false);

        SubsamplingScaleImageView img = (SubsamplingScaleImageView)v.findViewById(R.id.imageView);
        img.setImage(ImageSource.asset("images/"+images.get(position).getValue()));
        //img.setMaxScale(0.5f);
        img.setMinScale(0.1f);
        img.setMaxScale(10.0f);
        imageHolder.add(img);
        container.addView(v);


        return v;
    }

    @Override
    public void destroyItem(View container, int position, Object object) {  //deptricated... works for now
        container.refreshDrawableState();
    }

    public ArrayList<SubsamplingScaleImageView> getImages( ){
        return imageHolder;
    }


}