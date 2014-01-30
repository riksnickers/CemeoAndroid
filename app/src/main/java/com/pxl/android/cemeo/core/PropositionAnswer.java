package com.pxl.android.cemeo.core;

import java.io.Serializable;

public class PropositionAnswer implements Serializable {

    private static final long serialVersionUID = -7495457686217488896L;

    protected String InviteeID;
    protected int Answer;

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
}