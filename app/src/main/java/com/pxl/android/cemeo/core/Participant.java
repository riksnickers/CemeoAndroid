package com.pxl.android.cemeo.core;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Participant implements Serializable {

    private static final long serialVersionUID = -7495457652017488896L;

    protected int id;
    protected Boolean Important;


    public Boolean getImportant() {
        return Important;
    }

    public void setImportant(Boolean important) {
        Important = important;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

