package edu.uwp.appfactory.racinezoo.AnimalListScreen;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.uwp.appfactory.racinezoo.Model.Animal;
import edu.uwp.appfactory.racinezoo.R;
import edu.uwp.appfactory.racinezoo.Util.Config;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by hanh on 2/11/17.
 */

public class AnimalListAdapter extends ArrayAdapter<Animal> implements SectionIndexer {

    private LayoutInflater  mInflater;

    private static final String TAG = "AnimalListAdapter";
    RealmResults<Animal> animals;


    public AnimalListAdapter(Context context, int resource, RealmResults<Animal> animals) {
        super(context, resource, animals);
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.animals = animals;
    }

    public void setAnimalList(RealmResults<Animal> animalList){
        this.animals = animalList;
    }
    public static class ViewHolder {
        public TextView animalName;
        public TextView section ;
    }



    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        int type = getItemViewType(position);
        if(convertView == null){
            vh = new ViewHolder();

            convertView = mInflater.inflate(R.layout.item_section,parent,false);
            vh.section = (TextView)convertView.findViewById(R.id.section);

            vh.animalName = (TextView) convertView.findViewById(R.id.animal_name);
            convertView.setTag(vh);
        }

        else {
            vh = (ViewHolder)convertView.getTag();
        }

        if (isSection(position)){
            vh.section.setVisibility(View.VISIBLE);
            vh.section.setText(Config.ALPHABET[getSectionForPosition(position)] +"");
        }
        else {
            vh.section.setVisibility(View.INVISIBLE);
        }
        vh.animalName.setText(animals.get(position).getName());

        return convertView;
    }

    private boolean isSection(int position){
        int section = getSectionForPosition(position);
        int sectionPosition = getPositionForSection(section);

        if( sectionPosition == position){
            return true;
        }
        else {
            return false;
        }
    }


    @Override
    public int getCount() {
        return animals.size();
    }



    //Implement SectionIndexer methods

    @Override
    public int getPositionForSection(int section) {
        for (int j =0; j < getCount(); j++) {
            String item = animals.get(j).getName();
            try {
                if (item.charAt(0) == Config.ALPHABET[section])
                    return j;
            }
            catch (IndexOutOfBoundsException e){
                return Config.ALPHABET.length*2 - j;
            }

        }
        return 0;
    }

    @Override
    public int getSectionForPosition(int postion) {
        String animal = animals.get(postion).getName();
        for(int i = 0; i < Config.ALPHABET.length; i++){
            if(animal.charAt(0)== Config.ALPHABET[i]){
                return i;
            }
        }

        return 0;
    }

    @Override
    public Object[] getSections() {

        return new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
                "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    }


}
