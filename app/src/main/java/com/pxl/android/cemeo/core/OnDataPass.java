package com.pxl.android.cemeo.core;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jordy on 20/01/14.
 */
public interface OnDataPass {
    public void onReqContactsPass(List<Contact> selected);
    public void onContactsPass(List<Contact> selected);
    public void onTimeFramePass(String date , String time , String duration);
    public void onLocationPass(Location selected);
    public List<Contact> getSelected();
    public List<Contact> getRequired();
    public Location getLocation();
    public String getDate();
    public String getTime();
    public String getDuration();

}