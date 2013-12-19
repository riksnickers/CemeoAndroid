package com.pxl.android.cemeo.ui;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.pxl.android.cemeo.R;
import com.pxl.android.cemeo.core.CreateMeeting;

import javax.inject.Inject;

import com.squareup.otto.Bus;

import butterknife.InjectView;

public class CreateMeetingActivity extends BootstrapFragmentActivity {


    private long[] selectedContacts;

    @Inject Bus BUS;

    //@InjectView(R.id.chronometer) protected TextView chronometer;
    //@InjectView(R.id.start) protected Button start;
    //@InjectView(R.id.stop) protected Button stop;
    //@InjectView(R.id.pause) protected Button pause;
    //@InjectView(R.id.resume) protected Button resume;

    @InjectView(R.id.addcontactsbtn) protected Button create;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.create_meeting_contacts);

        setTitle(R.string.create_meeting);

        /*
        start.setOnClickListener(this);
        stop.setOnClickListener(this);
        pause.setOnClickListener(this);
        resume.setOnClickListener(this);

        */
    }

    @Override
    protected void onResume() {
        super.onResume();

        BUS.register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        BUS.unregister(this);
    }


    /**
     * Checks to see if the timer service is running or not.
     * @return true if the service is running otherwise false.
     */
    private boolean isCreateMeetingRunning() {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (CreateMeeting.class.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }


    public void AddContacts(View view) {

        Intent intent = new Intent(this, CreateMeetingTimeFrameActivity.class);
        //EditText editText = (EditText) findViewById(R.id.edit_message);
        //String message = editText.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

}
