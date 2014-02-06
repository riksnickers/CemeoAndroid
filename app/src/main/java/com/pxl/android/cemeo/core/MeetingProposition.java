package com.pxl.android.cemeo.core;

import com.pxl.android.cemeo.AndroidModule$$ModuleAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MeetingProposition implements Serializable {

    private static final long serialVersionUID = -6641292855569752037L;

    private String InviteeID;
    private int Answer;
    private Proposition Proposition;
    private List<Contact> Others = new ArrayList<Contact>();


    public List<Contact> getOthers() {
        return Others;
    }

    public void setOthers(List<Contact> others) {
        Others = others;
    }

    public String getInviteeID() {
        return InviteeID;
    }

    public void setInviteeID(String inviteeID) {
        InviteeID = inviteeID;
    }

    public int getAnswer() {
        return Answer;
    }

    public void setAnswer(int answer) {
        Answer = answer;
    }

    public Proposition getProposition() {
        return Proposition;
    }

    public void setProposition(Proposition proposition) {
        Proposition = proposition;
    }
}



