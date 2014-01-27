
package com.pxl.android.cemeo.ui;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.OperationCanceledException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;
import com.google.android.gcm.GCMRegistrar;
import com.pxl.android.cemeo.BootstrapServiceProvider;
import com.pxl.android.cemeo.R;
import com.pxl.android.cemeo.core.BootstrapService;
import com.pxl.android.cemeo.util.Ln;
import com.pxl.android.cemeo.util.SafeAsyncTask;
import com.viewpagerindicator.TitlePageIndicator;

import net.jarlehansen.android.gcm.client.GCMUtils;
import net.simonvt.menudrawer.MenuDrawer;

import java.net.SocketTimeoutException;

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


        notifyMe();



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
            }
        }.execute();
    }

    private void notifyMe() {
        new SafeAsyncTask<Boolean>() {

            @Override
            public Boolean call() {

                GCMRegistrar.checkDevice(getApplicationContext());
                GCMUtils.checkExtended(getApplicationContext());
                GCMUtils.getAndSendRegId(getApplicationContext());

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
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void navigateToCreateMeeting() {
        final Intent i = new Intent(this, CreateMeetingAddContactActivity.class);
        startActivity(i);
    }




}
