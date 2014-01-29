package com.pxl.android.cemeo.core;

import com.google.gson.annotations.SerializedName;
import com.pxl.android.cemeo.util.Strings;

import java.io.Serializable;

public class User implements Serializable {

    //private static final long serialVersionUID = -7495897652017488896L;

    protected String EMail;
    protected String FirstName;
    protected String aspUser;
    protected String LastName;
    protected Location PreferedLocation;
    protected String UserId;
    protected String UserName;

    @SerializedName("access_token")
    protected String sessionToken;



    public String getUserId() {
        return UserId;
    }

    public String getFirstName() {
        return FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public String getEMail() {
        return EMail;
    }

    public Location getPreferedLocation() {
        return PreferedLocation;
    }

    public String getUserName() {
        return UserName;
    }

    public String getAspUser() {
        return aspUser;
    }
}
