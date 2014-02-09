package com.pxl.android.cemeo;


import android.accounts.AccountManager;
import android.content.Context;

import com.pxl.android.cemeo.authenticator.BootstrapAuthenticatorActivity;
import com.pxl.android.cemeo.authenticator.LogoutService;
import com.pxl.android.cemeo.ui.AboutActivity;
import com.pxl.android.cemeo.ui.CarouselActivity;
import com.pxl.android.cemeo.ui.ContactActivity;
import com.pxl.android.cemeo.ui.ContactListFragment;
import com.pxl.android.cemeo.ui.CreateMeetingAddContactActivity;
import com.pxl.android.cemeo.ui.LocationsActivity;
import com.pxl.android.cemeo.ui.LocationsListFragment;
import com.pxl.android.cemeo.ui.MeetingActivity;
import com.pxl.android.cemeo.ui.MeetingListFragment;
import com.pxl.android.cemeo.ui.PropositionActivity;
import com.pxl.android.cemeo.ui.PropositionListFragment;
import com.pxl.android.cemeo.ui.ReqContactActivity;
import com.pxl.android.cemeo.ui.ReqContactListAdapter;
import com.pxl.android.cemeo.ui.ReqContactListFragment;
import com.pxl.android.cemeo.ui.SelectFavLocationActivity;
import com.pxl.android.cemeo.ui.SummaryActivity;
import com.pxl.android.cemeo.ui.SummaryFragment;
import com.pxl.android.cemeo.ui.UserActivity;
import com.pxl.android.cemeo.ui.UserDataActivity;
import com.pxl.android.cemeo.ui.UserDataFragment;
import com.pxl.android.cemeo.ui.UserListFragment;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger module for setting up provides statements.
 * Register all of your entry points below.
 */
@Module
        (
                complete = false,

                injects = {
                        GCMIntentService.class,
                        AboutActivity.class,
                        BootstrapApplication.class,
                        BootstrapAuthenticatorActivity.class,
                        CarouselActivity.class,
                        UserDataActivity.class,
                        UserDataFragment.class,
                        PropositionActivity.class,
                        PropositionListFragment.class,
                        MeetingActivity.class,
                        MeetingListFragment.class,
                        CreateMeetingAddContactActivity.class,
                        ContactActivity.class,
                        ContactListFragment.class,
                        ReqContactActivity.class,
                        ReqContactListFragment.class,
                        LocationsActivity.class,
                        LocationsListFragment.class,
                        SummaryFragment.class,
                        SummaryActivity.class,
                        UserActivity.class,
                        UserListFragment.class,
                        SelectFavLocationActivity.class

                }

        )

public class BootstrapModule {


    @Provides
    @Singleton
    LogoutService provideLogoutService(final Context context, final AccountManager accountManager) {
        return new LogoutService(context, accountManager);
    }

}
