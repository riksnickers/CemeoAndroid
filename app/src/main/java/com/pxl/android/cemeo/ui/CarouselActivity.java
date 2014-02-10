
package com.pxl.android.cemeo.ui;

import android.accounts.AccountsException;
import android.accounts.OperationCanceledException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.AndroidRuntimeException;
import android.view.View;
import android.widget.Toast;

import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;
import com.google.gson.Gson;
import com.pxl.android.cemeo.BootstrapServiceProvider;
import com.pxl.android.cemeo.R;
import com.pxl.android.cemeo.core.BootstrapService;
import com.pxl.android.cemeo.core.MeetingProposition;
import com.pxl.android.cemeo.core.PropositionAnswer;
import com.pxl.android.cemeo.gcm.GCMUtils;
import com.pxl.android.cemeo.util.Ln;
import com.pxl.android.cemeo.util.SafeAsyncTask;
import com.viewpagerindicator.TitlePageIndicator;

import net.simonvt.menudrawer.MenuDrawer;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.Views;


/**
 * Activity to view the carousel and view pager indicator with fragments.
 */
public class CarouselActivity extends BootstrapFragmentActivity {

    @InjectView(R.id.tpi_header)
    TitlePageIndicator indicator;
    @InjectView(R.id.vp_pages)
    ViewPager pager;

    @Inject
    BootstrapServiceProvider serviceProvider;

    private MenuDrawer menuDrawer;

    private boolean userHasAuthenticated = false;
    private String json;
    private List<MeetingProposition> proplist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

        super.onCreate(savedInstanceState);


        //navigation drawer
        menuDrawer = MenuDrawer.attach(this);
        menuDrawer.setMenuView(R.layout.navigation_drawer);
        menuDrawer.setContentView(R.layout.carousel_view);
        menuDrawer.setSlideDrawable(R.drawable.ic_drawer);
        menuDrawer.setDrawerIndicatorEnabled(true);

        Views.inject(this);

        checkAuth();






    }

    private void initScreen() {
        if (userHasAuthenticated) {
            pager.setAdapter(new BootstrapPagerAdapter(getResources(), getSupportFragmentManager()));

            indicator.setViewPager(pager);
            pager.setCurrentItem(1);

        }

        setNavListeners();
    }

    private void checkAuth() {
        new SafeAsyncTask<Boolean>() {

            @Override
            public Boolean call() throws Exception {
                final BootstrapService svc = serviceProvider.getService(CarouselActivity.this);

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
                notifyMe();

            }
        }.execute();
    }

    private void notifyMe() {
        new SafeAsyncTask<Boolean>() {

            @Override
            public Boolean call() {


                try {
                    String authkey = serviceProvider.getService(getParent()).getApiKey();
                    GCMUtils.setAuthKey(authkey);
                    Ln.d("statuslog : test ********" + GCMUtils.getAuthKey());


                    //dev only
                    //GCMRegistrar.checkDevice(getApplicationContext());
                    //GCMUtils.checkExtended(getApplicationContext());

                    GCMUtils.getAndSendRegId(getApplicationContext());

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (AccountsException e) {
                    e.printStackTrace();
                } catch (AndroidRuntimeException e){
                    e.printStackTrace();
                } catch(Exception e){
                    e.printStackTrace();
                }

                return true;


            }



        }.execute();
    }


    private void setNavListeners() {

        menuDrawer.findViewById(R.id.home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuDrawer.toggleMenu();
            }
        });

        menuDrawer.findViewById(R.id.createmeeting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuDrawer.toggleMenu();
                navigateToCreateMeeting();
            }
        });

        menuDrawer.findViewById(R.id.about_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuDrawer.toggleMenu();
                navigateToAboutPage();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                menuDrawer.toggleMenu();
                return true;
            case R.id.createmeeting:
                navigateToCreateMeeting();
                return true;
            case R.id.about_menu:
                navigateToAboutPage();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void navigateToAboutPage() {
        final Intent i = new Intent(this, AboutActivity.class);
        startActivity(i);
    }


    private void navigateToCreateMeeting() {
        final Intent i = new Intent(this, CreateMeetingAddContactActivity.class);
        startActivity(i);
    }

    public void changeLocation(View view){

        Ln.d("statuslog : change location !");
        startActivity(new Intent(this, SelectFavLocationActivity.class));



    }


    public void accept(final String InviteeID , final Context c){


        new SafeAsyncTask<Boolean>() {

            @Override
            public Boolean call()throws Exception{

                proplist = serviceProvider.getService(getParent()).getPropositions();

                //json van maken
                PropositionAnswer answer = new PropositionAnswer();
                answer.setAnswer(1);
                answer.setInviteeID(InviteeID);

                Gson gson = new Gson();
                json = gson.toJson(answer);

                final BootstrapService svc = serviceProvider.getService(CarouselActivity.this);

                Boolean res = svc.answerProposition(answer);

                return res == true;


            }


            @Override
            protected void onException(Exception e) throws RuntimeException {
                super.onException(e);
                if (e instanceof OperationCanceledException) {
                    Toast.makeText( c, "Failed to accept meeting!" , Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            protected void onSuccess(Boolean res) throws Exception {

                if(res == true){
                    Toast.makeText( c, "Meeting Accepted !" , Toast.LENGTH_LONG).show();

                }else{
                    Toast.makeText( c, "Failed to accept meeting!" , Toast.LENGTH_LONG).show();
                }
            }


        }.execute();



    }

    public void reject(final String InviteeID , final Context c){

        new SafeAsyncTask<Boolean>() {

            @Override
            public Boolean call()throws Exception{

                proplist = serviceProvider.getService(getParent()).getPropositions();

                //json van maken
                PropositionAnswer answer = new PropositionAnswer();
                answer.setAnswer(2);
                answer.setInviteeID(InviteeID);

                Gson gson = new Gson();
                json = gson.toJson(answer);

                final BootstrapService svc = serviceProvider.getService(CarouselActivity.this);

                Boolean res = svc.answerProposition(answer);

                return res == true;


            }


            @Override
            protected void onException(Exception e) throws RuntimeException {
                super.onException(e);
                if (e instanceof OperationCanceledException) {
                    Toast.makeText( c, "Failed to reject the meeting!" , Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            protected void onSuccess(Boolean res) throws Exception {

                if(res == true){
                    Toast.makeText(c , "Meeting Rejected !", Toast.LENGTH_LONG).show();
                    //finish();
                }else{
                    Toast.makeText( c, "Failed to reject the meeting!" , Toast.LENGTH_LONG).show();
                }
            }



        }.execute();


    }

    public void unconfirm(final String InviteeID , final Context c){

        new SafeAsyncTask<Boolean>() {

            @Override
            public Boolean call()throws Exception{

                proplist = serviceProvider.getService(getParent()).getPropositions();

                //json van maken
                PropositionAnswer answer = new PropositionAnswer();
                answer.setAnswer(0);
                answer.setInviteeID(InviteeID);

                Gson gson = new Gson();
                json = gson.toJson(answer);

                final BootstrapService svc = serviceProvider.getService(CarouselActivity.this);

                Boolean res = svc.answerProposition(answer);

                return res == true;


            }


            @Override
            protected void onException(Exception e) throws RuntimeException {
                super.onException(e);
                if (e instanceof OperationCanceledException) {
                    Toast.makeText( c, "Failed to uncomfirm meeting!" , Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            protected void onSuccess(Boolean res) throws Exception {

                if(res == true){
                    Toast.makeText( c , "Meeting Unconfirmed !", Toast.LENGTH_LONG).show();
                    //finish();
                }else{
                    Toast.makeText( c, "Failed to uncomfirm meeting!" , Toast.LENGTH_LONG).show();
                }
            }



        }.execute();


    }

    public void online(final String InviteeID , final Context c){

        //Ln.d("statuslog: Position is: %s" , position);


        new SafeAsyncTask<Boolean>() {

            @Override
            public Boolean call()throws Exception{

                proplist = serviceProvider.getService(getParent()).getPropositions();

                //json van maken
                PropositionAnswer answer = new PropositionAnswer();
                answer.setAnswer(3);
                answer.setInviteeID(InviteeID);

                Gson gson = new Gson();
                json = gson.toJson(answer);

                final BootstrapService svc = serviceProvider.getService(CarouselActivity.this);

                Boolean res = svc.answerProposition(answer);

                return res == true;


            }


            @Override
            protected void onException(Exception e) throws RuntimeException {
                super.onException(e);
                if (e instanceof OperationCanceledException) {
                    Toast.makeText( c, "Failed to accept meeting online!" , Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            protected void onSuccess(Boolean res) throws Exception {

                if(res == true){
                    Toast.makeText(c, "Meeting Accepted Online !", Toast.LENGTH_LONG).show();
                    //finish();
                }else{
                    Toast.makeText( c, "Failed to accept meeting online!" , Toast.LENGTH_LONG).show();
                }
            }



        }.execute();


    }




}
