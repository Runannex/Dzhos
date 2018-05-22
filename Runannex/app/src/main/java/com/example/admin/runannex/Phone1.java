package com.example.admin.runannex;

import android.view.View;

public class Phone1 {
    private String date;
    private View imageMap;
    private String info;

    public Phone1(String date, View imageMap, String info){
        this.date=date;
        this.info=info;
        this.imageMap = imageMap;
    }

    public String getDate() {
        return this.date;
    }
    public String setDate(String date) {
        return this.date = date;
    }
    public String getInfo() {

        return this.info;
    }
    public void setInfo(String info) {

        this.date = info;
    }
    public View getImageMap() {
        return this.imageMap;
    }
}
