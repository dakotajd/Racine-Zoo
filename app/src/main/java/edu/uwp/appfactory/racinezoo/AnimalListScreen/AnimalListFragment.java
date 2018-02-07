package edu.uwp.appfactory.racinezoo.AnimalListScreen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import edu.uwp.appfactory.racinezoo.CustomView.SideSelector;
import edu.uwp.appfactory.racinezoo.DetailScreen.DetailActivity;
import edu.uwp.appfactory.racinezoo.Model.Animal;
import edu.uwp.appfactory.racinezoo.R;
import edu.uwp.appfactory.racinezoo.Util.Config;
import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmResults;


public class AnimalListFragment extends Fragment implements SearchView.OnQueryTextListener{

    private View animalView;
    private Context context;

    private ListView listView;
    private AnimalListAdapter adapter;
    private RealmResults<Animal> animalList;

    private MenuItem searchItem;
    private MenuItem filter;
    private Spinner categoryFilter;
    private static final String TAG = "AnimalsFragment";

    private Realm realm;

    public AnimalListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        realm = Realm.getDefaultInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        animalView = inflater.inflate(R.layout.fragment_animal, container, false);




        listView = (ListView) animalView.findViewById(R.id.animal_list);
        SideSelector sideSelector = (SideSelector) animalView.findViewById(R.id.side_selector);

        realm = Realm.getDefaultInstance();
        animalList = realm.where(Animal.class).findAllSorted("name");
        context = getActivity();
        adapter = new AnimalListAdapter(getActivity(), 0, animalList);
        listView.setAdapter(adapter);
        sideSelector.setListView(listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                /*Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("name", animalList.get(position).getName());
                intent.putExtra("type", Config.ANIMAL);
                startActivity(intent);*/
                if (savedInstanceState == null) {
                    Intent intent = new Intent(getActivity(), DetailActivity.class);
                    intent.putExtra("name", animalList.get(position).getName());
                    intent.putExtra("type", Config.ANIMAL);

                    startActivity(intent);
                }
            }
        });


        return animalView;
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Do something that differs the Activity's menu here
        menu.clear();
        inflater.inflate(R.menu.animal_list_menu, menu);
        searchItem = menu.findItem(R.id.action_search);
        filter = menu.findItem(R.id.menu_filter);

        setUpCategorySpinner(menu);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint("Search animal ...");
        searchView.setOnQueryTextListener(this);

        searchView.setIconifiedByDefault(false);
        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                filter.setVisible(false);
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                filter.setVisible(true);
                return true;
            }
        });


        super.onCreateOptionsMenu(menu, inflater);

    }

    public void setUpCategorySpinner(Menu menu) {


        categoryFilter = (Spinner) MenuItemCompat.getActionView(filter);
        final ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.category_array, R.layout.spinner_item);


        categoryAdapter.setDropDownViewResource(R.layout.spinner_item);
        categoryFilter.setAdapter(categoryAdapter);
        categoryFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (view == null) {
                    return;

                }
                TextView category = (TextView) view.findViewById(R.id.text);

                if (category.getText().toString().compareTo("All") == 0) {
                    animalList = realm.where(Animal.class).findAllSorted("name");
                } else {
                    animalList = realm.where(Animal.class).equalTo("category", category.getText().toString(), Case.INSENSITIVE).findAllSorted("name");

                }




                adapter.setAnimalList(animalList);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

        }

        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }




    @Override
    public boolean onQueryTextChange(String newText) {
        queryAnimal(newText);
        return true;
    }

    public void queryAnimal(String containText){
        animalList = realm.where(Animal.class).contains("name", containText, Case.INSENSITIVE).findAllSorted("name");
        adapter.setAnimalList(animalList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (realm != null) {
            realm.close();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
