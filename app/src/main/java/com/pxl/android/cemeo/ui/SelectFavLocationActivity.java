
package com.pxl.android.cemeo.ui;

import android.accounts.OperationCanceledException;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.kevinsawicki.http.HttpRequest;
import com.google.gson.Gson;
import com.pxl.android.cemeo.BootstrapServiceProvider;
import com.pxl.android.cemeo.R;
import com.pxl.android.cemeo.core.BootstrapService;
import com.pxl.android.cemeo.core.Contact;
import com.pxl.android.cemeo.core.Location;
import com.pxl.android.cemeo.core.OnDataPass;
import com.pxl.android.cemeo.gcm.GCMUtils;
import com.pxl.android.cemeo.gcm.log.GCMUtilsLog;
import com.pxl.android.cemeo.util.Ln;
import com.pxl.android.cemeo.util.SafeAsyncTask;

import java.util.List;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.Views;

import static com.pxl.android.cemeo.core.Constants.Http.URL_SET_LOCATION;


/**
 * Activity to view the carousel and view pager indicator with fragments.
 */
public class SelectFavLocationActivity extends BootstrapFragmentActivity implements OnDataPass {

    @InjectView(R.id.vp_location_page)
    ViewPager pager;

    @InjectView(R.id.pb_loading)
    ProgressBar prog;

    protected Location location;
    protected String json;
    protected int result;


    @Inject
    BootstrapServiceProvider serviceProvider;

    //private MenuDrawer menuDrawer;

    private boolean userHasAuthenticated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

        super.onCreate(savedInstanceState);

        super.setContentView(R.layout.select_location);


        //navigation drawer
        //menuDrawer = MenuDrawer.attach(this);
        //menuDrawer.setContentView(R.layout.create_meeting_view);
        //menuDrawer.setDrawerIndicatorEnabled(true);

        Views.inject(this);

        checkAuth();

    }

    private void initScreen() {
        if (userHasAuthenticated) {
            pager.setAdapter(new LocationPagerAdapter(getResources(), getSupportFragmentManager()));

            pager.setCurrentItem(1);

        }


    }

    private void checkAuth() {
        new SafeAsyncTask<Boolean>() {

            @Override
            public Boolean call() throws Exception {
                final BootstrapService svc = serviceProvider.getService(SelectFavLocationActivity.this);

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

    @Override
    public void onDataPass(List<Contact> selected) {

    }

    @Override
    public void onContactsPass(List<Contact> selected) {

    }

    @Override
    public void onTimeFramePass(String date, String time, String duration) {

    }

    @Override
    public void onLocationPass(Location selected) {

        this.location = selected;
        Ln.d("statuslog : Location : %s - %s - %s", this.location.getName(), this.location.getCity(), this.location.getCountry());

    }



    public void sendLocation(View view) {


        new SafeAsyncTask<Boolean>() {

            @Override
            public Boolean call() {

                Location l = new Location();

                l.setLocationID(location.getLocationID());

                Gson gson = new Gson();
                json = gson.toJson(l);


                String key = "Bearer " + GCMUtils.getAuthKey();

                Ln.d("statuslog : json : %s" , json );

                HttpRequest request = HttpRequest.post(URL_SET_LOCATION).header("Authorization", key).header("Content-Type", "application/json").send(json).connectTimeout(20000);

                Ln.d("statuslog : result : %s" , request.code() );
                result = request.code();
                return true;


            }

            @Override
            protected void onSuccess(Boolean aBoolean) throws Exception {
                showResult();

            }
        }.execute();
        }

        public void showResult(){

            if((result == 200)){
                Toast.makeText(getApplicationContext(), "Location changed !", Toast.LENGTH_SHORT).show();
                finish();
            }else {
                Toast.makeText(getApplicationContext(), "Location change failed , please try again later.", Toast.LENGTH_SHORT).show();
            }

        }




}







