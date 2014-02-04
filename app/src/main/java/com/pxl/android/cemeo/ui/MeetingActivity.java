package com.pxl.android.cemeo.ui;

import android.os.Bundle;
import android.widget.TextView;

import com.pxl.android.cemeo.R;
import com.pxl.android.cemeo.core.Meeting;
import com.pxl.android.cemeo.core.Meetings;

import butterknife.InjectView;

import static com.pxl.android.cemeo.core.Constants.Extra.MEETING;

public class MeetingActivity extends BootstrapActivity {

    protected Meetings meetings;

    @InjectView(R.id.tv_meeting_date)
    protected TextView date;
    @InjectView(R.id.tv_meeting_location)
    protected TextView location;
    @InjectView(R.id.tv_meeting_room)
    protected TextView room;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.meetings);

        if (getIntent() != null && getIntent().getExtras() != null) {
            meetings = (Meetings) getIntent().getExtras().getSerializable(MEETING);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        setTitle(meetings.getMeeting().getBeginTime());

        date.setText(meetings.getMeeting().getBeginTime());
        location.setText(meetings.getSelf().getRoom().getLocationID().getName());
        room.setText(meetings.getSelf().getRoom().getName());


    }

}
