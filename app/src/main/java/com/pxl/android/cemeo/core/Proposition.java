package com.pxl.android.cemeo.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Proposition implements Serializable {

    private static final long serialVersionUID = -6641292855569752037L;

    private String Id;
    private String ReservedSpotGuid;
    private Room ProposedRoom;
    private String BeginTime;
    private String EndTime;


    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String endTime) {
        EndTime = endTime;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getReservedSpotGuid() {
        return ReservedSpotGuid;
    }

    public void setReservedSpotGuid(String reservedSpotGuid) {
        ReservedSpotGuid = reservedSpotGuid;
    }

    public Room getProposedRoom() {
        return ProposedRoom;
    }

    public void setProposedRoom(Room proposedRoom) {
        ProposedRoom = proposedRoom;
    }

    public String getBeginTime() {
        return BeginTime;
    }

    public void setBeginTime(String beginTime) {
        BeginTime = beginTime;
    }
}



