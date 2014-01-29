package com.pxl.android.cemeo.core;


/**
 * Created by jordy on 29/01/14.
 */
public class RegisterDevice {

    protected String DeviceID;
    protected int Platform;

    public String getDeviceID() {
        return DeviceID;
    }

    public void setDeviceID(String deviceID) {
        DeviceID = deviceID;
    }


    public int getPlatform() {
        return Platform;
    }

    public void setPlatform(int platform) {
        Platform = platform;
    }
}
