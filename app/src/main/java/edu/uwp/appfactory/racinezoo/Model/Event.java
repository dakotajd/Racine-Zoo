package edu.uwp.appfactory.racinezoo.Model;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;

import edu.uwp.appfactory.racinezoo.Util.DateUtils;
import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by hanh on 2/18/17.
 */

public class Event extends RealmObject implements DetailObject {

    private int eventId;

    @SerializedName("name")
    private String name;

    @SerializedName("start")
    private Date startDate;

    @SerializedName("end")
    private Date endDate;

    @SerializedName("notifyByDefault")
    private boolean notifyByDefault;

    @SerializedName("description")
    private String description;

    @SerializedName("urlTitle")
    private String urlTitle;

    @SerializedName("url")
    private String url;

    @SerializedName("images")
    private RealmList<RealmString> images;

    public Event() {
    }

    public Event(String title, Date startDate) {
        this.name = title;
        this.startDate = startDate;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public boolean isNotifyByDefault() {
        return notifyByDefault;
    }

    public void setNotifyByDefault(boolean notifyByDefault) {
        this.notifyByDefault = notifyByDefault;
    }

    public RealmList<RealmString> getImages() {
        return images;
    }

    public void setImages(RealmList<RealmString> images) {
        this.images = images;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrlTitle() {
        return urlTitle;
    }

    public void setUrlTitle(String urlTitle) {
        this.urlTitle = urlTitle;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public ArrayList<DetailItem> getDetailItemList() {
        ArrayList<DetailItem> detailItems = new ArrayList<>();
        if (description != null) {
            detailItems.add(new DetailItem("Description", description.replaceAll("\\\\n", System.getProperty("line.separator") + System.getProperty("line.separator"))));
        }
        detailItems.add(new DetailItem("Signup Link", url));

        for (int i = 0; i < detailItems.size(); i++){
            Object data = detailItems.get(i).getContent();
            if(data == null) {
                detailItems.remove(detailItems.get(i));
                i--;
            }
        }

        return detailItems;
    }

    public ArrayList<DetailItem> getDetailInfo() {
        return null;
    }

    @Override
    public String getSubTitle() {
        return DateUtils.formatDatesAsSubtitle(startDate, endDate);
    }
}
