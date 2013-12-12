package com.pxl.android.cemeo;

import android.accounts.AccountManager;
import android.content.Context;

import com.pxl.android.cemeo.authenticator.BootstrapAuthenticatorActivity;
import com.pxl.android.cemeo.authenticator.LogoutService;
import com.pxl.android.cemeo.core.CheckIn;
import com.pxl.android.cemeo.core.TimerService;
import com.pxl.android.cemeo.ui.BootstrapTimerActivity;
import com.pxl.android.cemeo.ui.CarouselActivity;
import com.pxl.android.cemeo.ui.CheckInsListFragment;
import com.pxl.android.cemeo.ui.ItemListFragment;
import com.pxl.android.cemeo.ui.NewsActivity;
import com.pxl.android.cemeo.ui.NewsListFragment;
import com.pxl.android.cemeo.ui.UserActivity;
import com.pxl.android.cemeo.ui.UserListFragment;
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
                BootstrapTimerActivity.class,
                CheckInsListFragment.class,
                NewsActivity.class,
                NewsListFragment.class,
                UserActivity.class,
                UserListFragment.class,
                TimerService.class
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
