package com.pxl.android.cemeo.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Room implements Serializable {

    private static final long serialVersionUID = -6691291855569752037L;

    private int RoomID;
    private String Name;
    private String Type;
    private Location LocationID;



    public int getRoomID() {
        return RoomID;
    }

    public void setRoomID(int roomID) {
        RoomID = roomID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public Location getLocationID() {
        return LocationID;
    }

    public void setLocationID(Location locationID) {
        LocationID = locationID;
    }
}



