package edu.uwp.appfactory.racinezoo.EventScreen;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import edu.uwp.appfactory.racinezoo.DetailScreen.DetailActivity;
import edu.uwp.appfactory.racinezoo.Model.Event;
import edu.uwp.appfactory.racinezoo.Model.EventItem;
import edu.uwp.appfactory.racinezoo.Model.HeaderItem;
import edu.uwp.appfactory.racinezoo.Model.ListItem;
import edu.uwp.appfactory.racinezoo.R;
import edu.uwp.appfactory.racinezoo.Util.Config;
import edu.uwp.appfactory.racinezoo.Util.DateUtils;

public class EventsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "EventsAdapter";

    @NonNull
    private List<ListItem> items = Collections.emptyList();
    private Activity context;

    public EventsAdapter(@NonNull List<ListItem> items, Activity context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case ListItem.TYPE_HEADER: {
                View itemView = inflater.inflate(R.layout.event_header, parent, false);
                return new HeaderViewHolder(itemView);
            }
            case ListItem.TYPE_EVENT: {
                View itemView = inflater.inflate(R.layout.event_item, parent, false);
                return new EventViewHolder(itemView);
            }
            default:
                throw new IllegalStateException("unsupported item type");
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        int viewType = getItemViewType(position);
        switch (viewType) {
            case ListItem.TYPE_HEADER: {
                HeaderItem header = (HeaderItem) items.get(position);
                HeaderViewHolder holder = (HeaderViewHolder) viewHolder;
                holder.headerTitle.setText(header.getHeaderTitle());
                holder.headerDateRange.setText(header.getHeaderRange());
                break;
            }
            case ListItem.TYPE_EVENT: {
                EventItem event = (EventItem) items.get(position);
                final List<Event> eventsList = event.getEvents();
                EventViewHolder holder = (EventViewHolder) viewHolder;

                holder.txt_title.setText(DateUtils.formatDateAsDayOfWeek(eventsList.get(0).getStartDate()));
                holder.dayMonth.setText(DateUtils.formatDateAsDayOfMonth(eventsList.get(0).getStartDate()));
                if (holder.dynamicEventContent.getChildCount() == 0) {

                    for (int i = 0; i < eventsList.size(); i++) {
                        final Event tempEvent = eventsList.get(i);
                        View v = LayoutInflater.from(context).inflate(R.layout.event_details_item, null);
                        TextView eventName = (TextView) v.findViewById(R.id.event_name);
                        TextView eventTime = (TextView) v.findViewById(R.id.event_time);
                        eventName.setText(tempEvent.getName());
                        eventTime.setText(DateUtils.formatDateAsTime(tempEvent.getStartDate()));
                        if (i == eventsList.size() - 1) {
                            View grey = v.findViewById(R.id.grey_line);
                            grey.setVisibility(View.INVISIBLE);
                        }
                        v.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(context, DetailActivity.class);
                                intent.putExtra("name", tempEvent.getName());
                                intent.putExtra("type", Config.EVENT);
                                context.startActivity(intent);


                            }
                        });
                        holder.dynamicEventContent.addView(v);
                    }
                }
                break;
            }
            default:
                throw new IllegalStateException("unsupported item type");
        }
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);
        if (holder.getItemViewType() == ListItem.TYPE_EVENT) {
            EventViewHolder eventHolder = (EventViewHolder) holder;
            eventHolder.dynamicEventContent.removeAllViews();
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).getType();
    }

    private static class HeaderViewHolder extends RecyclerView.ViewHolder {

        TextView headerTitle;
        TextView headerDateRange;

        HeaderViewHolder(View itemView) {
            super(itemView);
            headerTitle = (TextView) itemView.findViewById(R.id.header_range_title);
            headerDateRange = (TextView) itemView.findViewById(R.id.header_date_range);
        }

    }

    private static class EventViewHolder extends RecyclerView.ViewHolder {

        TextView txt_title;
        TextView dayMonth;
        LinearLayout dynamicEventContent;

        EventViewHolder(View itemView) {
            super(itemView);
            txt_title = (TextView) itemView.findViewById(R.id.txt_title);
            dayMonth = (TextView) itemView.findViewById(R.id.day_month);
            dynamicEventContent = (LinearLayout) itemView.findViewById(R.id.dynamic_event_layout);
        }

    }

}
