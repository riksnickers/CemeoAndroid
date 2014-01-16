package com.pxl.android.cemeo.core;

import java.io.Serializable;

public class Meeting implements Serializable {

    private static final long serialVersionUID = -6641292855569752036L;

    private int meetingID;
    private String creator;
    private String date;
    private String location;
    private int state;

    public int getMeetingID() {
        return meetingID;
    }

    public void setMeetingID(int meetingID) {
        this.meetingID = meetingID;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
