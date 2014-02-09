package com.pxl.android.cemeo.ui;


import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.pxl.android.cemeo.BootstrapApplication;
import com.pxl.android.cemeo.R;
import com.squareup.picasso.Picasso;

import butterknife.InjectView;

public class AboutActivity extends BootstrapActivity {

    @InjectView(R.id.tv_about_us)
    protected TextView about1;
    @InjectView(R.id.tv_about_us_2)
    protected TextView about2;
    @InjectView(R.id.tv_about_us_3)
    protected TextView about3;
    @InjectView(R.id.tv_about_us_4)
    protected TextView about4;
    @InjectView(R.id.tv_about_us_5)
    protected TextView about5;
    @InjectView(R.id.iv_avatar)
    protected ImageView imagecegeka;
    @InjectView(R.id.tv_title)
    protected TextView title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.about);

        //getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        title.setText(String.format("Cegeka Meeting Organiser"));
        about1.setText(String.format("CeMeO"));
        about2.setText(String.format("Made By Group 3"));
        about3.setText(String.format("For Cegeka"));
        about4.setText(String.format(""));
        about5.setText(String.format("Android development : Jordy Baldewijns"));


    }






}
