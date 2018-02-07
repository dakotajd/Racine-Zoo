package edu.uwp.appfactory.racinezoo.EventScreen;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import edu.uwp.appfactory.racinezoo.Model.Event;
import edu.uwp.appfactory.racinezoo.Model.EventItem;
import edu.uwp.appfactory.racinezoo.Model.HeaderItem;
import edu.uwp.appfactory.racinezoo.Model.ListItem;
import edu.uwp.appfactory.racinezoo.Model.RealmString;
import edu.uwp.appfactory.racinezoo.R;
import edu.uwp.appfactory.racinezoo.Util.DateUtils;
import edu.uwp.appfactory.racinezoo.Util.GsonUtil;
import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

import static android.content.Context.ALARM_SERVICE;

public class EventFragment extends Fragment {

    private static final int REQUEST_CODE = 5;

    private Realm mRealm;

    private static final String TAG = "EventsFragment";

    private RecyclerView recyclerView;

    @NonNull
    private List<ListItem> items = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mRealm = Realm.getDefaultInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View eventView = inflater.inflate(R.layout.fragment_event, container, false);


        Date endOfYear = new Date();
        Calendar c = new GregorianCalendar();
        c.setTime(endOfYear);
        c.set(DateUtils.getCurrentYear(), 11, 31);
        //Previously only events that had not happened yet would show, but this is just to show all events so you can see the design of the layout
        Date showAllEvents = new Date();
        showAllEvents.setTime(1420099178);
        RealmResults<Event> result = mRealm.where(Event.class).between("startDate", showAllEvents, c.getTime()).findAll();
        buildEventSet(toMapWeek(result));
        recyclerView = (RecyclerView) eventView.findViewById(R.id.event_lst_items);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new EventsAdapter(items, getActivity()));
        return eventView;
    }

    @NonNull
    private List<Event> loadEvents() {
        List<Event> events = new ArrayList<>();
        for (int i = 1; i < 50; i++) {
            events.add(new Event("event " + i, buildRandomDateInCurrentMonth()));
        }
        return events;
    }

    private Date buildRandomDateInCurrentMonth() {
        Random random = new Random();
        return DateUtils.buildDate(random.nextInt(31) + 1);
    }

    private Map<Integer, List<Event>> toMapWeek(@NonNull List<Event> events) {
        Map<Integer, List<Event>> mapMonth = new TreeMap<>();
        Calendar c = Calendar.getInstance();
        String[] months = new DateFormatSymbols().getMonths();
        for (int i = 0; i < months.length; i++) {
            mapMonth.put(i, new ArrayList<Event>());
        }
        for (Event event : events) {
            int currentMonth = DateUtils.getMonthNumberFromDate(event.getStartDate());
            mapMonth.get(currentMonth).add(event);
        }
        for (int i = 0; i < 12; i++) {
            if (mapMonth.get(i).size() == 0) {
                mapMonth.remove(i);
            } else {
                Collections.sort(mapMonth.get(i), new Comparator<Event>() {
                    @Override
                    public int compare(Event o1, Event o2) {
                        return o1.getStartDate().compareTo(o2.getStartDate());
                    }
                });
            }
        }
        return mapMonth;
    }

    private void buildEventSet(Map<Integer, List<Event>> events) {
        String[] months = new DateFormatSymbols().getMonths();
        for (Integer num : events.keySet()) {
            items.add(new HeaderItem(months[num], ""));
            List<Event> eventsSubList = events.get(num);
            items.addAll(groupEvents(eventsSubList));
        }
    }

    public List<EventItem> groupEvents(List<Event> events) {
        List<EventItem> tempItems = new ArrayList<>();
        List<Event> tempEventList = new ArrayList<>();
        int previousDay = -1;
        int currentDay;
        for (int i = 0; i < events.size(); i++) {
            currentDay = DateUtils.getDayOfMonthFromDate(events.get(i).getStartDate());
            if (previousDay == -1 || previousDay == currentDay) {
                tempEventList.add(events.get(i));
                previousDay = currentDay;
            } else {
                tempItems.add(new EventItem(tempEventList));
                tempEventList = new ArrayList<>();
                i -= 1;
                previousDay = -1;
            }
        }
        tempItems.add(new EventItem(tempEventList));
        return tempItems;
    }

    public void populateRealmDB(List<Event> events) {
        for (Event event : events) {
            mRealm.beginTransaction();
            mRealm.copyToRealm(event);
            mRealm.commitTransaction();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mRealm != null) {
            mRealm.close();
        }
    }




    /*@Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        *//*inflater.inflate(R.menu.search_menu, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setQueryHint("Search events...");
        searchView.setIconifiedByDefault(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (!s.equals("") || !s.matches("\\s+")) {
                    Date endOfYear = new Date();
                    Calendar c = new GregorianCalendar();
                    c.setTime(endOfYear);
                    c.set(DateUtils.getCurrentYear(), 11, 31);
                    RealmResults<Event> result = mRealm.where(Event.class).between("startDate", new Date(), c.getTime()).contains("name", s, Case.INSENSITIVE).findAll();
                    items = new ArrayList<ListItem>();
                    buildEventSet(toMapWeek(result));
                    recyclerView.setAdapter(new EventsAdapter(items, getContext()));
                    recyclerView.getAdapter().notifyDataSetChanged();
                }
                return false;
            }
        });*//*
    }*/

    public static void setEventNotification(Event e, Context context) {
        Intent intent = new Intent(context, EventAlarmReceiver.class);
        String notificationInfo = DateUtils.getDateForNotification(e.getStartDate(), e.getEndDate());
        intent.putExtra("NOTIF_INFO", notificationInfo);
        intent.putExtra("EVENT_NAME", e.getName());

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, REQUEST_CODE, intent, 0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, e.getStartDate().getTime() , pendingIntent);
        System.out.println("Time Total ----- "+(System.currentTimeMillis()+e.getStartDate().getTime()));
        SimpleDateFormat df = new SimpleDateFormat("hh:mm:ss");
        Log.d("EVENTNOTIF", "notif set for: " + df.format(e.getStartDate()));
    }
}
