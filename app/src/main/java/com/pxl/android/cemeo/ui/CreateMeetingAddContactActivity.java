package com.pxl.android.cemeo.ui;

import android.accounts.OperationCanceledException;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ListView;
import android.widget.Switch;

import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;
import com.pxl.android.cemeo.BootstrapServiceProvider;
import com.pxl.android.cemeo.R;
import com.pxl.android.cemeo.core.BootstrapService;
import com.pxl.android.cemeo.core.User;
import com.pxl.android.cemeo.util.Ln;
import com.pxl.android.cemeo.util.SafeAsyncTask;
import com.viewpagerindicator.TitlePageIndicator;

import net.simonvt.menudrawer.MenuDrawer;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.Views;

import static com.pxl.android.cemeo.core.Constants.Extra.CONTACTS_SELECTED;
import static com.pxl.android.cemeo.core.Constants.Extra.USER;


/**
 * Created by jordy on 13/01/14.
 */
public class CreateMeetingAddContactActivity extends BootstrapFragmentActivity{

    @InjectView(R.id.vp_meeting_pages)
    ViewPager pager;

    @Inject
    BootstrapServiceProvider serviceProvider;

    //private MenuDrawer menuDrawer;

    private boolean userHasAuthenticated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

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


    public void nextFragment(View view){


        //Ln.d("statuslog : user --> ");

        /*
        List<User> selecteduser = null;

        SparseBooleanArray checked = l.getCheckedItemPositions();
        for (int i = 0; i < checked.size(); i++) {
            if(checked.valueAt(i) == true) {

                User user = ((User) l.getItemAtPosition(checked.keyAt(i)));
                selecteduser.add(user);
                Ln.d("statuslog : user --> %s" , user.getLastName());
            }
        }

        new Intent(this, CreateMeetingAddContactActivity.class).putExtra(CONTACTS_SELECTED, (Serializable) selecteduser);

        */

        //volgend fragment
        pager.setCurrentItem(pager.getCurrentItem() + 1, true);
    }







}
