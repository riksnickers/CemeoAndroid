package com.pxl.android.cemeo.ui;

import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
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
    protected String date;
    protected String time;
    protected String duration;

    protected Location location;


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
        schedule.setCreator(4);

        //participants
        Participant p1 = new Participant();
        Participant p2 = new Participant();
        p1.setId(1);
        p1.setImportant(true);
        p2.setId(2);
        p2.setImportant(true);
        List<Participant> list = new ArrayList<Participant>();
        list.add(p1);
        list.add(p2);


        schedule.setInvitedParticipants(list);
        schedule.setDateindex(5);
        schedule.setBeforeDate("2014-01-28T20:25:58+0100");
        schedule.setDuration(1800);


        final Schedule s = schedule;

        new SafeAsyncTask<Boolean>() {

            @Override
            public Boolean call() throws Exception {
                final BootstrapService svc = serviceProvider.getService(CreateMeetingAddContactActivity.this);

                Boolean res = svc.createMeeting(s);

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
                    Toast.makeText( getApplicationContext(), "Meeting Creation failed ! !" , Toast.LENGTH_LONG).show();
                }
                //Ln.d("statuslog : Meeting Created successful!" );



            }
        }.execute();



    }


    @Override
    public void onDataPass(List<Contact> selected) {


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
    public void onLocationPass(Location location) {

        this.location = location;
        Ln.d("statuslog : Location : %s - %s - %s" , this.location.getName() , this.location.getCity() , this.location.getCountry() );

    }

    private CreateMeetingAddContactActivity show(final View view) {
        ViewUtils.setGone(view, false);
        return this;
    }

    private CreateMeetingAddContactActivity hide(final View view) {
        ViewUtils.setGone(view, true);
        return this;
    }


    public List<Contact> getSelected() {
        return selected;
    }

    public Location getLocation() {
        return location;
    }


    public String getDate() {
        return date;
    }


    public String getTime() {
        return time;
    }


    public String getDuration() {
        return duration;
    }

}
