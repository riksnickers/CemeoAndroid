package com.pxl.android.cemeo.ui;

import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.actionbarsherlock.app.SherlockFragment;
import com.github.kevinsawicki.wishlist.SingleTypeAdapter;
import com.pxl.android.cemeo.BootstrapServiceProvider;
import com.pxl.android.cemeo.Injector;
import com.pxl.android.cemeo.R;
import com.pxl.android.cemeo.authenticator.LogoutService;
import com.pxl.android.cemeo.core.User;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by jordy on 12/01/14.
 */
public class TimeFrameFragment extends SherlockFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.create_meeting_timeframe, container , false);

        Context c = getActivity().getApplicationContext();

        //Date spinner
        Spinner spinner = (Spinner) v.findViewById(R.id.spinner_date);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource( c , R.array.date_array, R.layout.select_timeframe_list_item);
        adapter.setDropDownViewResource(R.layout.select_timeframe_spinner_dropdown);
        spinner.setAdapter(adapter);

        //Time spinner
        Spinner spinner2 = (Spinner) v.findViewById(R.id.spinner_time);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource( c , R.array.time_array, R.layout.select_timeframe_list_item);
        adapter2.setDropDownViewResource(R.layout.select_timeframe_spinner_dropdown);
        spinner2.setAdapter(adapter2);

        //Duration
        Spinner spinner3 = (Spinner) v.findViewById(R.id.spinner_duration);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource( c , R.array.duration_array, R.layout.select_timeframe_list_item);
        adapter3.setDropDownViewResource(R.layout.select_timeframe_spinner_dropdown);
        spinner3.setAdapter(adapter3);


        return v;

    }



























































}
