

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
                UserDataFragment userdatafragment = new UserDataFragment();
                userdatafragment.setArguments(bundle);
                return userdatafragment;
            case 1:
                MeetingListFragment meetingfragment = new MeetingListFragment();
                meetingfragment.setArguments(bundle);
                return meetingfragment;
            case 2:
                PropositionListFragment propositionfragment = new PropositionListFragment();
                propositionfragment.setArguments(bundle);
                return propositionfragment;
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return resources.getString(R.string.page_user_data);
            case 1:
                return resources.getString(R.string.page_meetings);
            case 2:
                return resources.getString(R.string.page_propositions);
            default:
                return null;
        }
    }
}
