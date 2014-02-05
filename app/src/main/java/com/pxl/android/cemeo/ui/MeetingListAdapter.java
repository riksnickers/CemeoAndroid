package com.pxl.android.cemeo.ui;

import android.view.LayoutInflater;

import com.pxl.android.cemeo.R;

import com.pxl.android.cemeo.core.Meetings;
import com.pxl.android.cemeo.core.Other;
import com.pxl.android.cemeo.util.Strings;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeParser;
import org.joda.time.format.DateTimeParserBucket;
import org.joda.time.format.ISODateTimeFormat;

import java.util.List;

public class MeetingListAdapter extends AlternatingColorListAdapter<Meetings> {
    /**
     * @param inflater
     * @param items
     * @param selectable
     */
    public MeetingListAdapter(LayoutInflater inflater, List<Meetings> items, boolean selectable) {
        super(R.layout.meetings_list_item, inflater, items, selectable);
    }

    /**
     * @param inflater
     * @param items
     */
    public MeetingListAdapter(LayoutInflater inflater, List<Meetings> items) {
        super(R.layout.meetings_list_item, inflater, items);
    }

    @Override
    protected int[] getChildViewIds() {
        return new int[]{R.id.tv_meeting_date,R.id.tv_meeting_time,R.id.tv_meeting_duration , R.id.tv_meeting_location , R.id.tv_meeting_room ,R.id.tv_meeting_invitees};
    }

    @Override
    protected void update(int position, Meetings item) {
        super.update(position, item);

        String begindate = item.getMeeting().getBeginTime();

        DateTimeFormatter iso = ISODateTimeFormat.dateHourMinuteSecond();
        DateTime dateTime = iso.parseDateTime(begindate);

        int duration = item.getMeeting().getDuration().intValue();
        duration = duration /60;
        String dur = String.valueOf(duration) + " minutes";


        setText(0, dateTime.toString("dd-MM-yyyy"));
        setText(1, dateTime.toString("hh:mm"));
        setText(2, dur);
        setText(3, item.getSelf().getRoom().getLocationID().getName());
        setText(4, item.getSelf().getRoom().getName());

        String invitees = "";
        if(!item.getOthers().isEmpty()){
            for(Other o : item.getOthers()){

                if(item.getOthers().size() == 1 ){

                    invitees = o.getFirstName() + " " + o.getLastName();

                }else{

                    invitees += o.getFirstName() + " " + o.getLastName() + "\r\n";
                }


            }

        invitees = String.format(invitees);
        setText(5 , invitees);
        }
    }
}
