

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
public class CreateMeetingPagerAdapter extends FragmentPagerAdapter {

    private final Resources resources;

    /**
     * Create pager adapter
     *
     * @param resources
     * @param fragmentManager
     */
    public CreateMeetingPagerAdapter(Resources resources, FragmentManager fragmentManager) {
        super(fragmentManager);
        this.resources = resources;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        switch (position) {
            case 0:
                ContactListFragment contactListFragment = new ContactListFragment();
                contactListFragment.setArguments(bundle);
                return contactListFragment;
            case 1:
                ReqContactListFragment reqcontactfragment = new ReqContactListFragment();
                reqcontactfragment.setArguments(bundle);
                return reqcontactfragment;
            case 2:
                TimeFrameFragment timeFrameFragment = new TimeFrameFragment();
                timeFrameFragment.setArguments(bundle);
                return timeFrameFragment;
            case 3:
                SummaryFragment summaryFragment = new SummaryFragment();
                summaryFragment.setArguments(bundle);
                return summaryFragment;
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return resources.getString(R.string.page_contacts);
            case 1:
                return resources.getString(R.string.page_reqcontacts);
            case 2:
                return resources.getString(R.string.page_timeframe);
            case 3:
                return resources.getString(R.string.page_summary);
            default:
                return null;
        }
    }
}
