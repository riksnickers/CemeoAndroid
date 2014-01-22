

package com.pxl.android.cemeo.ui;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.pxl.android.cemeo.R;

/**
 * Pager adapter
 */
public class BootstrapPagerAdapter extends FragmentPagerAdapter {

    private final Resources resources;

    /**
     * Create pager adapter
     *
     * @param resources
     * @param fragmentManager
     */
    public BootstrapPagerAdapter(Resources resources, FragmentManager fragmentManager) {
        super(fragmentManager);
        this.resources = resources;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        switch (position) {
            case 0:
                MeetingListFragment meetingFragment = new MeetingListFragment();
                meetingFragment.setArguments(bundle);
                return meetingFragment;
            case 1:
                UserDataFragment userdatafragment = new UserDataFragment();
                userdatafragment.setArguments(bundle);
                return userdatafragment;
            case 2:
                UserDataFragment userdatafragment2 = new UserDataFragment();
                userdatafragment2.setArguments(bundle);
                return userdatafragment2;
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return resources.getString(R.string.page_meetings);
            case 1:
                return resources.getString(R.string.page_user_data);
            case 2:
                return resources.getString(R.string.page_checkins);
            default:
                return null;
        }
    }
}
