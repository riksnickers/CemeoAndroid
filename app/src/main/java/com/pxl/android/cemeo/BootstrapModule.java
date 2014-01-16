package com.pxl.android.cemeo;


import android.accounts.AccountManager;
import android.content.Context;

import com.pxl.android.cemeo.authenticator.BootstrapAuthenticatorActivity;
import com.pxl.android.cemeo.authenticator.LogoutService;
import com.pxl.android.cemeo.ui.CarouselActivity;
import com.pxl.android.cemeo.ui.CheckInsListFragment;
import com.pxl.android.cemeo.ui.ContactActivity;
import com.pxl.android.cemeo.ui.ContactListFragment;
import com.pxl.android.cemeo.ui.CreateMeetingAddContactActivity;
import com.pxl.android.cemeo.ui.MeetingActivity;
import com.pxl.android.cemeo.ui.MeetingListFragment;
import com.pxl.android.cemeo.ui.UserActivity;
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
                        BootstrapApplication.class,
                        BootstrapAuthenticatorActivity.class,
                        CarouselActivity.class,
                        //CreateMeetingActivity.class,
                        //CreateMeetingTimeFrameActivity.class,
                        CheckInsListFragment.class,
                        //NewsActivity.class,
                        //NewsListFragment.class,
                        MeetingActivity.class,
                        MeetingListFragment.class,
                        CreateMeetingAddContactActivity.class,
                        ContactActivity.class,
                        ContactListFragment.class,
                        UserActivity.class,
                        UserListFragment.class

                }

        )

public class BootstrapModule {


    @Provides
    @Singleton
    LogoutService provideLogoutService(final Context context, final AccountManager accountManager) {
        return new LogoutService(context, accountManager);
    }

}
