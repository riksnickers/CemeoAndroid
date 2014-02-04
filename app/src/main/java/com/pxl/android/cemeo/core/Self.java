package com.pxl.android.cemeo.core;

import java.io.Serializable;

public class Self implements Serializable {

    private static final long serialVersionUID = -6641292855569795336L;


    private int MeetingId;
    private int UserId;
    private Room Room;


    public Room getRoom() {
        return Room;
    }

    public void setRoom(Room room) {
        Room = room;
    }

    public int getMeetingId() {
        return MeetingId;
    }

    public void setMeetingId(int meetingId) {
        MeetingId = meetingId;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }
}