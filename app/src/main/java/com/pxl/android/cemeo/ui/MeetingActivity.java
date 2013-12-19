package com.pxl.android.cemeo.ui;

import android.os.Bundle;
import android.widget.TextView;

import com.pxl.android.cemeo.R;
import com.pxl.android.cemeo.core.Meeting;

import butterknife.InjectView;

import static com.pxl.android.cemeo.core.Constants.Extra.MEETING_ITEM;

public class MeetingActivity extends BootstrapActivity {

    protected Meeting meetingItem;

    @InjectView(R.id.tv_meeting_date) protected TextView date;
    @InjectView(R.id.tv_meeting_creator) protected TextView creator;
    @InjectView(R.id.tv_meeting_location) protected TextView location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.meetings);

        if(getIntent() != null && getIntent().getExtras() != null) {
            meetingItem = (Meeting) getIntent().getExtras().getSerializable(MEETING_ITEM);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        setTitle(meetingItem.getDate());

        date.setText(meetingItem.getDate());
        creator.setText(meetingItem.getCreator());
        location.setText(meetingItem.getLocation());




    }

}
