package com.pxl.android.cemeo.core;

import java.io.Serializable;

public class Other implements Serializable {

    private static final long serialVersionUID = -6641275155569795336L;


    private int UserId;
    private String FirstName;
    private String LastName;

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }
}