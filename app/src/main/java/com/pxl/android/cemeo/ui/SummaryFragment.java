package com.pxl.android.cemeo.ui;

import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.github.kevinsawicki.wishlist.SingleTypeAdapter;
import com.google.gson.Gson;
import com.pxl.android.cemeo.BootstrapServiceProvider;
import com.pxl.android.cemeo.Injector;
import com.pxl.android.cemeo.R;
import com.pxl.android.cemeo.authenticator.LogoutService;
import com.pxl.android.cemeo.core.BootstrapService;
import com.pxl.android.cemeo.core.Contact;
import com.pxl.android.cemeo.core.Location;
import com.pxl.android.cemeo.core.OnDataPass;
import com.pxl.android.cemeo.core.PropositionAnswer;
import com.pxl.android.cemeo.ui.CreateMeetingAddContactActivity;
import com.pxl.android.cemeo.util.Ln;
import com.pxl.android.cemeo.util.SafeAsyncTask;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.InjectView;

/**
 * Created by jordy on 12/01/14.
 */
public class SummaryFragment extends SherlockFragment {



    protected OnDataPass dataPasser;

    private TextView attendees;
    private TextView required;
    private TextView timeframe;
    private TextView duration;


    private String timefr;
    private String dura;

    private List<Contact> selected;
    private List<Contact> req;
    private String selectedcontacts = "";
    private String requiredcontacts = "";

    private Button createmeeting;



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


        View v = inflater.inflate(R.layout.create_meeting_summary, container , false);

        attendees = (TextView) v.findViewById(R.id.tv_sum_attendees);
        required = (TextView) v.findViewById(R.id.tv_summary_req_attendees);
        timeframe = (TextView) v.findViewById(R.id.tv_summary_timeframe);
        duration = (TextView) v.findViewById(R.id.tv_summary_duration);
        //createmeeting = (Button) v.findViewById(R.id.createmeetingbtn);

        new SafeAsyncTask<Boolean>() {


            @Override
            public Boolean call()throws Exception{


                getData();

                if(!selectedcontacts.isEmpty()){
                    selectedcontacts = "";
                }

                for(Contact c : selected){

                    selectedcontacts += c.getFirstName()+ " " + c.getLastName() + "\r\n";
                }

                if(!requiredcontacts.isEmpty()){
                    requiredcontacts = "";
                }

                for(Contact c : req){

                    requiredcontacts += c.getFirstName()+ " " + c.getLastName() + "\r\n";
                }

                Ln.d("statuslog: ---->> %s" , requiredcontacts);

                Boolean res = !requiredcontacts.isEmpty();

                return res;

            }

            @Override
            protected void onException(Exception e) throws RuntimeException {
                super.onException(e);
                if (e instanceof OperationCanceledException) {
                    Toast.makeText( getActivity().getApplicationContext(), "An error occurred" , Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            protected void onSuccess(Boolean res) throws Exception {

                if(res == true){


                    attendees.setText(selectedcontacts);
                    required.setText(requiredcontacts);
                    timeframe.setText(timefr);
                    duration.setText(dura);


                    //Toast.makeText(getActivity().getApplicationContext(), "gelukt!", Toast.LENGTH_LONG).show();



                }else{
                    //Toast.makeText( getActivity().getApplicationContext(), "mislukt!" , Toast.LENGTH_LONG).show();
                }


            }


        }.execute();


        return v;
    }



    @Override
    public void onAttach(Activity a) {

        super.onAttach(a);
        dataPasser = (OnDataPass) a;

    }


    public void getData() {

        selected = dataPasser.getSelected();
        req = dataPasser.getRequired();

        timefr = dataPasser.getDate();
        dura = dataPasser.getDuration();




    }





}
