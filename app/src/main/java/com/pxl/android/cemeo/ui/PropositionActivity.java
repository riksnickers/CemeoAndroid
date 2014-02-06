package com.pxl.android.cemeo.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.pxl.android.cemeo.R;
import com.pxl.android.cemeo.core.Meeting;
import com.pxl.android.cemeo.core.MeetingProposition;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

import butterknife.InjectView;

import static com.pxl.android.cemeo.core.Constants.Extra.MEETING;
import static com.pxl.android.cemeo.core.Constants.Extra.PROPOSITION;

public class PropositionActivity extends BootstrapActivity {

    protected MeetingProposition prop;

    @Inject
    CarouselActivity carousel;

    @InjectView(R.id.tv_prop_date)
    protected TextView date;
    @InjectView(R.id.tv_prop_time)
    protected TextView time;
    @InjectView(R.id.tv_prop_duration)
    protected TextView duration;
    @InjectView(R.id.tv_prop_status)
    protected TextView status;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.propositions);

        if (getIntent() != null && getIntent().getExtras() != null) {
            prop = (MeetingProposition) getIntent().getExtras().getSerializable(PROPOSITION);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        /*
        *   Listeners buttons accept , reject , ..
        */
        Button acceptbtn = (Button) findViewById(R.id.acceptbtn);

        acceptbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v ) {

                carousel.accept(prop.getInviteeID() , getApplicationContext());


            }
        });

        Button rejectbtn = (Button) findViewById(R.id.rejectbtn);

        rejectbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v ) {

                carousel.reject(prop.getInviteeID(), getApplicationContext());

            }
        });

        Button onlinebtn = (Button) findViewById(R.id.onlinebtn);

        onlinebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v ) {

                carousel.online(prop.getInviteeID(), getApplicationContext());

            }
        });

        Button unconfirmedbtn = (Button) findViewById(R.id.unconfirmedbtn);

        unconfirmedbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v ) {
                 carousel.unconfirm(prop.getInviteeID(), getApplicationContext());

            }
        });






        setTitle("Propostion information");
        String begindate = prop.getProposition().getBeginTime();
        DateTimeFormatter iso = ISODateTimeFormat.dateHourMinuteSecond();
        DateTime dateTime = iso.parseDateTime(begindate);

        //check answer
        int answernumber = prop.getAnswer();
        String answer;

        switch (answernumber) {
            case 0:  answer = "Unconfirmed";
                break;
            case 1:  answer = "Accepted";
                break;
            case 2:  answer = "Rejected";
                break;
            case 3:  answer = "Online";
                break;
            default: answer = "Unknown";
                break;
        }



        date.setText(dateTime.toString("dd-MM-yyyy"));
        time.setText(dateTime.toString("hh:mm"));
        duration.setText(prop.getProposition().getProposedRoom().getName());
        status.setText(answer);
    }

}
