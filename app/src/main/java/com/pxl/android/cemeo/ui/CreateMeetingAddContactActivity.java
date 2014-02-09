package com.pxl.android.cemeo.ui;

import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;

import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;
import com.github.kevinsawicki.wishlist.Toaster;
import com.github.kevinsawicki.wishlist.ViewUtils;
import com.pxl.android.cemeo.BootstrapServiceProvider;
import com.pxl.android.cemeo.R;
import com.pxl.android.cemeo.core.BootstrapService;
import com.pxl.android.cemeo.core.Contact;
import com.pxl.android.cemeo.core.Location;
import com.pxl.android.cemeo.core.OnDataPass;
import com.pxl.android.cemeo.core.Participant;
import com.pxl.android.cemeo.core.Schedule;
import com.pxl.android.cemeo.core.User;
import com.pxl.android.cemeo.util.Ln;
import com.pxl.android.cemeo.util.SafeAsyncTask;
import com.pxl.android.cemeo.util.Strings;
import com.viewpagerindicator.TitlePageIndicator;

import net.simonvt.menudrawer.MenuDrawer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.Views;

import static com.pxl.android.cemeo.core.Constants.Extra.CONTACT;
import static com.pxl.android.cemeo.core.Constants.Extra.CONTACTS_SELECTED;
import static com.pxl.android.cemeo.core.Constants.Extra.USER;


/**
 * Created by jordy on 13/01/14.
 */
public class CreateMeetingAddContactActivity extends BootstrapFragmentActivity implements OnDataPass {

    @InjectView(R.id.vp_meeting_pages)
    ViewPager pager;

    @InjectView(R.id.pb_loading)
    ProgressBar prog;

    protected List<Contact> selected;
    protected List<Contact> req;
    protected String date;
    protected String time;
    protected String duration;

    protected Location location;
    protected int uid = 0;


    @Inject
    BootstrapServiceProvider serviceProvider;

    //private MenuDrawer menuDrawer;

    private boolean userHasAuthenticated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

        super.onCreate(savedInstanceState);

        super.setContentView(R.layout.create_meeting_view);


        //navigation drawer
        //menuDrawer = MenuDrawer.attach(this);
        //menuDrawer.setContentView(R.layout.create_meeting_view);
        //menuDrawer.setDrawerIndicatorEnabled(true);

        Views.inject(this);

        checkAuth();

    }

    private void initScreen() {
        if (userHasAuthenticated) {
            pager.setAdapter(new CreateMeetingPagerAdapter(getResources(), getSupportFragmentManager()));

            pager.setCurrentItem(0);

        }


    }



    private void checkAuth() {
        new SafeAsyncTask<Boolean>() {

            @Override
            public Boolean call() throws Exception {
                final BootstrapService svc = serviceProvider.getService(CreateMeetingAddContactActivity.this);

                return svc != null;

            }

            @Override
            protected void onException(Exception e) throws RuntimeException {
                super.onException(e);
                if (e instanceof OperationCanceledException) {
                    // User cancelled the authentication process (back button, etc).
                    // Since auth could not take place, lets finish this activity.
                    finish();
                }
            }

            @Override
            protected void onSuccess(Boolean hasAuthenticated) throws Exception {
                super.onSuccess(hasAuthenticated);
                userHasAuthenticated = true;
                initScreen();
            }
        }.execute();
    }


    public void nextFragment( View v){

        //Next Fragment
        pager.setCurrentItem(pager.getCurrentItem() + 1, true);

    }

    public void createMeeting(View v) throws Exception{

        Schedule schedule = new Schedule();


        //get user id
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int uid = sharedPreferences.getInt("userid" , 0);
        schedule.setCreator(uid);


        //participants
        List<Participant> list = new ArrayList<Participant>();
        for(Contact c : selected ){

            Participant p1 = new Participant();
            p1.setId( Integer.parseInt(c.getId()) );
            if(req.contains(c)){
               p1.setImportant(true);
            }else{
               p1.setImportant(false);
            }
            list.add(p1);
        }

        schedule.setInvitedParticipants(list);

        if(this.date.compareTo("Today") == 1){
            schedule.setDateindex(0);
        }else if(this.date.compareTo("WithinThisWorkWeek") == 1){
            schedule.setDateindex(1);
        }else if(this.date.compareTo("Within7Days") == 1){
            schedule.setDateindex(2);
        }else if(this.date.compareTo("WithinThisMonth") == 1){
            schedule.setDateindex(3);
        }else if(this.date.compareTo("Within30Days") == 1){
            schedule.setDateindex(4);
        }else if(this.date.compareTo("BeforeADate") == 1){
            schedule.setDateindex(5);
            schedule.setBeforeDate("2014-01-28T20:25:58+0100");
        }else{
            schedule.setDateindex(0);
        }



        if(this.duration.compareTo("30 min") == 1){
            schedule.setDuration(1800);
        }else if(this.duration.compareTo("1 hour") == 1){
            schedule.setDuration(3600);
        }else if(this.duration.compareTo("2 hours") == 1){
            schedule.setDuration(7200);
        }else if(this.duration.compareTo("3 hours") == 1){
            schedule.setDuration(10800);
        }else if(this.duration.compareTo("Unknown") == 1){
            schedule.setDuration(99999);
        }else if(this.duration.compareTo("All day") == 1){
            schedule.setDuration(28800);
        }else{
            schedule.setDuration(99999);
        }


        final Schedule s = schedule;

        new SafeAsyncTask<Boolean>() {

            @Override
            public Boolean call() throws Exception {
                final BootstrapService svc = serviceProvider.getService(CreateMeetingAddContactActivity.this);

                setProgressBarVisibility(true);

                Boolean res = false;
                if(s.getCreator() != 0){
                     res = svc.createMeeting(s);
                }else{
                    setProgressBarVisibility(false);
                    return res == false;
                }
                setProgressBarVisibility(false);
                return res == true;

            }

            @Override
            protected void onException(Exception e) throws RuntimeException {
                super.onException(e);
                if (e instanceof OperationCanceledException) {
                    // User cancelled the authentication process (back button, etc).
                    // Since auth could not take place, lets finish this activity.
                    //finish();


                    Toast.makeText( getApplicationContext(), "Meeting Creation Failed !" , Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            protected void onSuccess(Boolean res) throws Exception {
                //super.onSuccess(hasAuthenticated);
                //userHasAuthenticated = true;
                //initScreen();
                //prog.setVisibility(0);
                //Toaster.showShort(getParent() , "Meeting Created Successful !");
                if(res == true){
                    Toast.makeText( getApplicationContext(), "Meeting Created Successful !" , Toast.LENGTH_LONG).show();
                    finish();
                }else{
                    // 1. Instantiate an AlertDialog.Builder with its constructor
                    AlertDialog.Builder builder = new AlertDialog.Builder(getParent());

                    // 2. Chain together various setter methods to set the dialog characteristics
                    builder.setMessage(R.string.dialog_text).setTitle(R.string.dialog_title)
                            .setPositiveButton(R.string.dialog_yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // FIRE ZE MISSILES!
                                }
                            })
                            .setNegativeButton(R.string.dialog_no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // User cancelled the dialog
                                    finish();
                                }
                            });
                    // 3. Get the AlertDialog from create()
                    AlertDialog dialog = builder.create();
                    //Toast.makeText( getApplicationContext(), "Meeting Creation failed ! !" , Toast.LENGTH_LONG).show();
                }
                //Ln.d("statuslog : Meeting Created successful!" );



            }
        }.execute();



    }


    @Override
    public void onReqContactsPass(List<Contact> req) {
        this.req = req;

    }



    @Override
    public void onTimeFramePass(String date , String time , String duration) {

        this.date = date;
        this.time = time;
        this.duration = duration;
        Ln.d("statuslog : TimeFrame : %s - %s - %s" , this.date , this.time , this.duration );
    }

    @Override
    public void onContactsPass(List<Contact> selected) {
        this.selected = selected;
        Ln.d("statuslog : Contacts : ");

        for(Contact c : selected){
            Ln.d("statuslog :  %s - %s - %s" , c.getFirstName() , c.getLastName() , c.getId() );
        }
    }

    @Override
    public List<Contact> getSelected(){
        return this.selected;
    }


    @Override
    public void onLocationPass(Location location) {


        this.location = location;
        /*
        Ln.d("statuslog : Location : %s - %s - %s" , this.location.getName() , this.location.getCity() , this.location.getCountry() );
        */
    }

    @Override
    public List<Contact> getRequired() {
        return req;
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public String getDate() {
        return date;
    }

    @Override
    public String getTime() {
        return time;
    }

    @Override
    public String getDuration() {
        return duration;
    }

}
