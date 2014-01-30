package com.pxl.android.cemeo.ui;


import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.pxl.android.cemeo.R;
import com.pxl.android.cemeo.core.Contact;
import com.pxl.android.cemeo.core.Location;
import com.pxl.android.cemeo.core.OnDataPass;

import java.util.List;

import butterknife.InjectView;

import static com.pxl.android.cemeo.core.Constants.Extra.LOCATION;

public class SummaryActivity extends BootstrapActivity {

    @InjectView(R.id.tv_summary)
    protected TextView summary;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.add_locations_view);

        /*
        if (getIntent() != null && getIntent().getExtras() != null) {
            location = (Location) getIntent().getExtras().getSerializable(LOCATION);
        }
        */

        //getSupportActionBar().setHomeButtonEnabled(true);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //name.setText(String.format("%s", location.getName()));
        //street.setText(String.format("%s", location.getStreet()));
        //city.setText(String.format("%s", location.getCity()));
        //country.setText(String.format("%s", location.getCountry()));


    }






}
