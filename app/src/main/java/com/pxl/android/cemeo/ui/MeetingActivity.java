package com.pxl.android.cemeo.ui;

import android.os.Bundle;
import android.widget.TextView;

import com.pxl.android.cemeo.AndroidModule;
import com.pxl.android.cemeo.R;
import com.pxl.android.cemeo.core.Meeting;
import com.pxl.android.cemeo.core.Meetings;
import com.pxl.android.cemeo.core.Other;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import butterknife.InjectView;

import static com.pxl.android.cemeo.core.Constants.Extra.MEETING;

public class MeetingActivity extends BootstrapActivity {

    protected Meetings meetings;

    @InjectView(R.id.tv_meeting_date)
    protected TextView date;
    @InjectView(R.id.tv_meeting_time)
    protected TextView time;
    @InjectView(R.id.tv_meeting_duration)
    protected TextView duration;
    @InjectView(R.id.tv_meeting_location_name)
    protected TextView locname;
    @InjectView(R.id.tv_meeting_location_street)
    protected TextView locstreet;
    @InjectView(R.id.tv_meeting_location_city)
    protected TextView loccity;
    @InjectView(R.id.tv_meeting_location_country)
    protected TextView loccountry;
    @InjectView(R.id.tv_meeting_room_name)
    protected TextView roomname;
    @InjectView(R.id.tv_meeting_room_type)
    protected TextView roomtype;
    @InjectView(R.id.tv_meeting_invitees)
    protected TextView invitees;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.meetings);

        if (getIntent() != null && getIntent().getExtras() != null) {
            meetings = (Meetings) getIntent().getExtras().getSerializable(MEETING);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        String begindate = meetings.getMeeting().getBeginTime();

        DateTimeFormatter iso = ISODateTimeFormat.dateHourMinuteSecond();
        DateTime dateTime = iso.parseDateTime(begindate);

        int durint = meetings.getMeeting().getDuration().intValue();
        durint = durint /60;
        String dur = String.valueOf(durint) + " minutes";

        setTitle(R.string.label_meeting_page_title);

        date.setText(dateTime.toString("dd-MM-yyyy"));
        time.setText(dateTime.toString("hh:mm"));
        duration.setText(dur);

        roomname.setText(meetings.getSelf().getRoom().getName());
        roomtype.setText(meetings.getSelf().getRoom().getType());


        locname.setText(meetings.getSelf().getRoom().getLocationID().getName());
        locstreet.setText(meetings.getSelf().getRoom().getLocationID().getStreet() + " " + meetings.getSelf().getRoom().getLocationID().getNumber());
        loccity.setText(meetings.getSelf().getRoom().getLocationID().getCity());
        loccountry.setText(meetings.getSelf().getRoom().getLocationID().getCountry());

        String inv = "";
        if(!meetings.getOthers().isEmpty()){
            for(Other o : meetings.getOthers()){

                if(meetings.getOthers().size() == 1 ){

                    inv = o.getFirstName() + " " + o.getLastName();

                }else{

                    inv += o.getFirstName() + " " + o.getLastName() + "\r\n";
                }


            }

            inv = String.format(inv);
            invitees.setText(inv);
        }


    }

}
