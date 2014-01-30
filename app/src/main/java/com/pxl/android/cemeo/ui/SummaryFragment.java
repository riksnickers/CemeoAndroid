package com.pxl.android.cemeo.ui;

import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.github.kevinsawicki.wishlist.SingleTypeAdapter;
import com.pxl.android.cemeo.BootstrapServiceProvider;
import com.pxl.android.cemeo.Injector;
import com.pxl.android.cemeo.R;
import com.pxl.android.cemeo.authenticator.LogoutService;
import com.pxl.android.cemeo.core.Contact;
import com.pxl.android.cemeo.core.Location;
import com.pxl.android.cemeo.core.OnDataPass;
import com.pxl.android.cemeo.ui.CreateMeetingAddContactActivity;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.InjectView;

/**
 * Created by jordy on 12/01/14.
 */
public class SummaryFragment extends SherlockFragment {

    @Inject
    protected BootstrapServiceProvider serviceProvider;
    @Inject
    protected LogoutService logoutService;

    protected OnDataPass dataPasser;

    @InjectView(R.id.tv_summary)
    TextView summary;

    private Location l;
    private String duration;
    private String time;
    private String date;
    private List<Contact> selected;
    private List<Contact> req;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Injector.inject(this);
/*
        getData();

        summary.setText(l.getName());
        */

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.create_meeting_summary, null);
    }


/*
    @Override
    public void onAttach(Activity a) {

        super.onAttach(a);
        dataPasser = (OnDataPass) a;

    }
*/

    public void getData() {
        /*
        l = dataPasser.getLocation();
        date = dataPasser.getDate();
        duration = dataPasser.getDuration();
        time = dataPasser.getTime();
        selected = dataPasser.getSelected();
        req = dataPasser.getRequired();

        */
    }


}
