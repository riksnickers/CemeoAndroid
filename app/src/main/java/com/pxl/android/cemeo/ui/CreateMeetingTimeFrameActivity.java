package com.pxl.android.cemeo.ui;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.pxl.android.cemeo.R;
import com.pxl.android.cemeo.core.CreateMeeting;
import com.pxl.android.cemeo.ui.BootstrapFragmentActivity;
import com.squareup.otto.Bus;

import javax.inject.Inject;

import butterknife.InjectView;

public class CreateMeetingTimeFrameActivity extends BootstrapFragmentActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.create_meeting_timeframe);

        setTitle(R.string.create_meeting_timeframe);

    }


}
