package com.pxl.android.cemeo;

import android.accounts.AccountManager;
import android.content.Context;

import com.pxl.android.cemeo.authenticator.BootstrapAuthenticatorActivity;
import com.pxl.android.cemeo.authenticator.LogoutService;
import com.pxl.android.cemeo.core.CreateMeeting;
import com.pxl.android.cemeo.ui.CreateMeetingActivity;
import com.pxl.android.cemeo.ui.CarouselActivity;
import com.pxl.android.cemeo.ui.CheckInsListFragment;
import com.pxl.android.cemeo.ui.MeetingActivity;
import com.pxl.android.cemeo.ui.MeetingListFragment;
import com.pxl.android.cemeo.ui.NewsActivity;
import com.pxl.android.cemeo.ui.NewsListFragment;
import com.pxl.android.cemeo.ui.UserActivity;
import com.pxl.android.cemeo.ui.UserListFragment;
import com.pxl.android.cemeo.ui.CreateMeetingTimeFrameActivity;
import com.squareup.otto.Bus;

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
                CreateMeetingActivity.class,
                CreateMeetingTimeFrameActivity.class,
                CheckInsListFragment.class,
                NewsActivity.class,
                NewsListFragment.class,
                MeetingActivity.class,
                MeetingListFragment.class,
                UserActivity.class,
                UserListFragment.class,
                CreateMeeting.class
        }

)
public class BootstrapModule  {

    @Singleton
    @Provides
    Bus provideOttoBus() {
        return new Bus();
    }

    @Provides
    @Singleton
    LogoutService provideLogoutService(final Context context, final AccountManager accountManager) {
        return new LogoutService(context, accountManager);
    }

}
