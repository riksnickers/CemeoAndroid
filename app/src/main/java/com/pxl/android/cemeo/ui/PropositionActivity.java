package com.pxl.android.cemeo.ui;

import android.os.Bundle;
import android.widget.TextView;

import com.pxl.android.cemeo.R;
import com.pxl.android.cemeo.core.Meeting;
import com.pxl.android.cemeo.core.MeetingProposition;

import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.InjectView;

import static com.pxl.android.cemeo.core.Constants.Extra.MEETING;
import static com.pxl.android.cemeo.core.Constants.Extra.PROPOSITION;

public class PropositionActivity extends BootstrapActivity {

    protected MeetingProposition prop;

    @InjectView(R.id.tv_prop_date)
    protected TextView date;
    @InjectView(R.id.tv_prop_creator)
    protected TextView creator;
    @InjectView(R.id.tv_prop_location)
    protected TextView location;

    private String times;
    private String dates;
    private String datesfull;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.propositions);

        if (getIntent() != null && getIntent().getExtras() != null) {
            prop = (MeetingProposition) getIntent().getExtras().getSerializable(PROPOSITION);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        String datestring = prop.getProposition().getBeginTime() + "Z";
        DateTime dt = ISODateTimeFormat.dateTime().parseDateTime(datestring);

        datesfull = dt.toString("EEEE , D MMMM YYYY");
        dates = dt.toString("dd-MM-yyyy");
        times = dt.toString("hh:mm");


        if( dates != null){
            setTitle(datesfull);
            date.setText(dates);
            creator.setText(times);
            //creator.setText(prop.getProposition());
            location.setText(prop.getProposition().getProposedRoom().getName());


        }else{
            setTitle("Geen datum beschikbaar");

        }
    }

}
