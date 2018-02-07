package edu.uwp.appfactory.racinezoo.Model;


import java.util.Objects;

import edu.uwp.appfactory.racinezoo.Util.Config;

/**
 * Created by hanh on 2/18/17.
 */

public class DetailItem {
    private String title;
    private Object data;
    private int type;


    public DetailItem(String title, Object data, int type){
        this.data = data;
        this.title = title;
        this.type = type;
    }

    public DetailItem(String title, Object data){
        this.data = data;
        this.title = title;
        this.type = Config.DETAIL_TYPE_GENERAL;
    }


    public String getTitle() {
        return title;
    }


    public Object getContent() {
        return data;
    }


    public int getType() {
        return type;
    }
}
