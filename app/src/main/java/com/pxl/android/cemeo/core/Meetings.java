package com.pxl.android.cemeo.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Meetings implements Serializable {

    private static final long serialVersionUID = -6644592855569632036L;


    private Meeting Meeting;
    private Self Self;
    private List<Other> Others = new ArrayList<Other>();

    public List<Other> getOthers() {
        return Others;
    }

    public void setOthers(List<Other> others) {
        Others = others;
    }

    public Meeting getMeeting() {
        return Meeting;
    }

    public void setMeeting(Meeting meeting) {
        Meeting = meeting;
    }

    public Self getSelf() {
        return Self;
    }

    public void setSelf(Self self) {
        Self = self;
    }
}