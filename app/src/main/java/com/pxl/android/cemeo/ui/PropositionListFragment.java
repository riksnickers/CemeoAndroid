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
import com.pxl.android.cemeo.core.MeetingProposition;
import com.pxl.android.cemeo.core.Proposition;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import static com.pxl.android.cemeo.core.Constants.Extra.MEETING;
import static com.pxl.android.cemeo.core.Constants.Extra.PROPOSITION;

public class PropositionListFragment extends ItemListFragment<MeetingProposition> {

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

        setEmptyText(R.string.no_propositions);


    }

    @Override
    protected void configureList(Activity activity, ListView listView) {
        super.configureList(activity, listView);

        listView.setFastScrollEnabled(true);
        listView.setDividerHeight(0);

        getListAdapter()
                .addHeader(activity.getLayoutInflater()
                        .inflate(R.layout.meetings_list_item_labels, null));
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
    public Loader<List<MeetingProposition>> onCreateLoader(int id, Bundle args) {
        final List<MeetingProposition> initialItems = items;
        return new ThrowableLoader<List<MeetingProposition>>(getActivity(), items) {

            @Override
            public List<MeetingProposition> loadData() throws Exception {
                try {
                    if (getActivity() != null) {
                        return serviceProvider.getService(getActivity()).getPropositions();
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
    protected SingleTypeAdapter<MeetingProposition> createAdapter(List<MeetingProposition> items) {
        return new PropositionListAdapter(getActivity().getLayoutInflater(), items);
    }

    public void onListItemClick(ListView l, View v, int position, long id) {
        MeetingProposition prop = ((MeetingProposition) l.getItemAtPosition(position));

        startActivity(new Intent(getActivity(), PropositionActivity.class).putExtra(PROPOSITION, prop));
    }

    @Override
    protected int getErrorMessage(Exception exception) {
        return R.string.error_loading_propositions;
    }



}
