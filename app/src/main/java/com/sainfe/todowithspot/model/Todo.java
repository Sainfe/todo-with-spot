package com.sainfe.todowithspot.model;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.GeoPoint;

public class Todo {
    private String content;
    private Timestamp time;
    private Boolean isDone;
    private Boolean isAlarm;
    private GeoPoint place;
    private int placeType;
    private Timestamp createTime;

    public Todo() {
    }

    public Todo(String content, Timestamp time, Boolean isDone, Boolean isAlarm, GeoPoint place, int placeType, Timestamp createTime) {
        this.content = content;
        this.time = time;
        this.isDone = isDone;
        this.isAlarm = isAlarm;
        this.place = place;
        this.placeType = placeType;
        this.createTime = createTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public Boolean getDone() {
        return isDone;
    }

    public void setDone(Boolean done) {
        isDone = done;
    }

    public Boolean getAlarm() {
        return isAlarm;
    }

    public void setAlarm(Boolean alarm) {
        isAlarm = alarm;
    }

    public GeoPoint getPlace() {
        return place;
    }

    public void setPlace(GeoPoint place) {
        this.place = place;
    }

    public int getPlaceType() {
        return placeType;
    }

    public void setPlaceType(int placeType) {
        this.placeType = placeType;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }
}
