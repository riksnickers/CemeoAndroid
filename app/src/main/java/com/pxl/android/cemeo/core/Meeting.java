package com.pxl.android.cemeo.core;

import java.io.Serializable;

public class Meeting implements Serializable {

    private static final long serialVersionUID = -6641292855569752036L;

    private int meetingID;
    private String BeginTime;
    private Double Duration;

    public int getMeetingID() {
        return meetingID;
    }

    public void setMeetingID(int meetingID) {
        this.meetingID = meetingID;
    }

    public String getBeginTime() {
        return BeginTime;
    }

    public void setBeginTime(String beginTime) {
        BeginTime = beginTime;
    }

    public Double getDuration() {
        return Duration;
    }

    public void setDuration(Double duration) {
        Duration = duration;
    }
}