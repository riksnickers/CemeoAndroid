package com.pxl.android.cemeo.ui;

import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.ListView;

import com.github.kevinsawicki.wishlist.SingleTypeAdapter;
import com.pxl.android.cemeo.BootstrapServiceProvider;
import com.pxl.android.cemeo.Injector;
import com.pxl.android.cemeo.R;
import com.pxl.android.cemeo.authenticator.LogoutService;
import com.pxl.android.cemeo.core.Meeting;
import com.pxl.android.cemeo.core.Meetings;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import static com.pxl.android.cemeo.core.Constants.Extra.MEETING;

public class MeetingListFragment extends ItemListFragment<Meetings> {

    @Inject
    protected BootstrapServiceProvider serviceProvider;
    @Inject
    protected LogoutService logoutService;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Injector.inject(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setEmptyText(R.string.no_meetings);


    }

    @Override
    protected void configureList(Activity activity, ListView listView) {
        super.configureList(activity, listView);

        listView.setFastScrollEnabled(true);
        listView.setDividerHeight(0);

        //getListAdapter().addHeader(activity.getLayoutInflater().inflate(R.layout.meetings_list_item_labels, null));
    }

    @Override
    LogoutService getLogoutService() {
        return logoutService;
    }

    @Override
    public void onDestroyView() {
        setListAdapter(null);

        super.onDestroyView();
    }

    @Override
    public Loader<List<Meetings>> onCreateLoader(int id, Bundle args) {
        final List<Meetings> initialItems = items;
        return new ThrowableLoader<List<Meetings>>(getActivity(), items) {

            @Override
            public List<Meetings> loadData() throws Exception {
                try {
                    if (getActivity() != null) {
                        return serviceProvider.getService(getActivity()).getMeetings();
                    } else {
                        return Collections.emptyList();
                    }

                } catch (OperationCanceledException e) {
                    Activity activity = getActivity();
                    if (activity != null)
                        activity.finish();
                    return initialItems;
                }
            }
        };
    }

    @Override
    protected SingleTypeAdapter<Meetings> createAdapter(List<Meetings> items) {
        return new MeetingListAdapter(getActivity().getLayoutInflater(), items);
    }

    public void onListItemClick(ListView l, View v, int position, long id) {
        Meetings meetings = ((Meetings) l.getItemAtPosition(position));

        startActivity(new Intent(getActivity(), MeetingActivity.class).putExtra(MEETING, meetings));
    }

    @Override
    protected int getErrorMessage(Exception exception) {
        return R.string.error_loading_meetings;
    }
}
