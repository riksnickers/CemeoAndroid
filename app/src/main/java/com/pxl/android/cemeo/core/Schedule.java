package com.pxl.android.cemeo.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Schedule implements Serializable {

    private static final long serialVersionUID = -6641292855569752037L;

    private int Creator;
    private List<Participant> InvitedParticipants = new ArrayList<Participant>();
    private int Dateindex;
    private String BeforeDate;
    private int Duration;


    public int getCreator() {
        return Creator;
    }

    public void setCreator(int creator) {
        Creator = creator;
    }

    public List<Participant> getInvitedParticipants() {
        return InvitedParticipants;
    }

    public void setInvitedParticipants(List<Participant> invitedParticipants) {
        InvitedParticipants = invitedParticipants;
    }

    public int getDateindex() {
        return Dateindex;
    }

    public void setDateindex(int dateindex) {
        Dateindex = dateindex;
    }

    public String getBeforeDate() {
        return BeforeDate;
    }

    public void setBeforeDate(String beforeDate) {
        BeforeDate = beforeDate;
    }

    public int getDuration() {
        return Duration;
    }

    public void setDuration(int duration) {
        Duration = duration;
    }
}

