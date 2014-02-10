package com.pxl.android.cemeo.core;


import java.io.Serializable;

public class Contact implements Serializable {

    private static final long serialVersionUID = -7495897652017488896L;


    protected String id;
    protected String FirstName;
    protected String LastName;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String FirstName) {
        this.FirstName = FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String LastName) {
        this.LastName = LastName;
    }


}
