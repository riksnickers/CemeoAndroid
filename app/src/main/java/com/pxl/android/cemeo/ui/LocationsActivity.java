package com.pxl.android.cemeo.ui;


import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.pxl.android.cemeo.R;
import com.pxl.android.cemeo.core.Contact;
import com.pxl.android.cemeo.core.Location;
import com.squareup.picasso.Picasso;

import butterknife.InjectView;

import static com.pxl.android.cemeo.core.Constants.Extra.CONTACT;
import static com.pxl.android.cemeo.core.Constants.Extra.LOCATION;

public class LocationsActivity extends BootstrapActivity {

    @InjectView(R.id.tv_location_name)
    protected TextView name;
    @InjectView(R.id.tv_location_street)
    protected TextView street;
    @InjectView(R.id.tv_location_city)
    protected TextView city;
    @InjectView(R.id.tv_location_country)
    protected TextView country;

    protected Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.add_locations_view);

        if (getIntent() != null && getIntent().getExtras() != null) {
            location = (Location) getIntent().getExtras().getSerializable(LOCATION);
        }

        //getSupportActionBar().setHomeButtonEnabled(true);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        name.setText(String.format("%s", location.getName()));
        street.setText(String.format("%s", location.getStreet()));
        city.setText(String.format("%s", location.getCity()));
        country.setText(String.format("%s", location.getCountry()));


    }





}
