package com.pxl.android.cemeo.ui;

import android.accounts.AccountsException;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.github.kevinsawicki.http.HttpRequest;
import com.github.kevinsawicki.wishlist.SingleTypeAdapter;
import com.github.kevinsawicki.wishlist.Toaster;
import com.google.android.gcm.GCMRegistrar;
import com.google.gson.Gson;
import com.pxl.android.cemeo.BootstrapServiceProvider;
import com.pxl.android.cemeo.Injector;
import com.pxl.android.cemeo.R;
import com.pxl.android.cemeo.authenticator.LogoutService;
import com.pxl.android.cemeo.core.Contact;
import com.pxl.android.cemeo.core.Location;
import com.pxl.android.cemeo.core.OnDataPass;
import com.pxl.android.cemeo.gcm.GCMUtils;
import com.pxl.android.cemeo.gcm.log.GCMUtilsLog;
import com.pxl.android.cemeo.util.Ln;
import com.pxl.android.cemeo.util.SafeAsyncTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import static com.pxl.android.cemeo.core.Constants.Http.URL_CREATE_MEETING;
import static com.pxl.android.cemeo.core.Constants.Http.URL_MEETING;
import static com.pxl.android.cemeo.core.Constants.Http.URL_SET_LOCATION;

/**
 * Created by jordy on 12/01/14.
 */
public class LocationsListFragment extends ItemListFragment<Location> {

    @Inject
    protected BootstrapServiceProvider serviceProvider;
    @Inject
    protected LogoutService logoutService;

    OnDataPass dataPasser;

    Location selected;
    private String json;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Injector.inject(this);


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setEmptyText(R.string.no_locations);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.create_meeting_locations, null);
    }


    @Override
    protected void configureList(Activity activity, ListView listView) {
        super.configureList(activity, listView);

        //listView.callOnClick();
        listView.setFastScrollEnabled(true);
        //listView.setDividerHeight(0);


        //getListAdapter().addHeader(activity.getLayoutInflater().inflate(R.layout.add_contact_list_item_labels, null));
    }

    @Override
    LogoutService getLogoutService() {
        return logoutService;
    }


    @Override
    public Loader<List<Location>> onCreateLoader(int id, Bundle args) {
        final List<Location> initialItems = items;
        return new ThrowableLoader<List<Location>>(getActivity(), items) {
            @Override
            public List<Location> loadData() throws Exception {

                try {
                    List<Location> latest = null;

                    if (getActivity() != null)
                        latest = serviceProvider.getService(getActivity()).getLocations();

                    if (latest != null)
                        return latest;
                    else
                        return Collections.emptyList();
                } catch (OperationCanceledException e) {
                    Activity activity = getActivity();
                    if (activity != null)
                        activity.finish();
                    return initialItems;
                }
            }
        };

    }
/*

    public void onListItemClick(ListView l, View v, int position, long id) {

        int checked = l.getCheckedItemPosition();

        selected = (Location) l.getItemAtPosition(checked);
        //Toast.makeText(l.getContext(), "Checked ! " + selected.getName() , Toast.LENGTH_SHORT).show();

        passLocation(selected);



    }

*/

    public void onListItemClick(ListView l, View v, int position, long id) {

        int checked = l.getCheckedItemPosition();

        selected = (Location) l.getItemAtPosition(checked);
        //Toast.makeText(l.getContext(), "Checked ! " + selected.getName() , Toast.LENGTH_SHORT).show();

        passLocation(selected);



    }


    @Override
    public void onLoadFinished(Loader<List<Location>> loader, List<Location> items) {
        super.onLoadFinished(loader, items);

    }

    @Override
    protected int getErrorMessage(Exception exception) {
        return R.string.error_loading_locations;
    }

    @Override
    protected SingleTypeAdapter<Location> createAdapter(List<Location> items) {
        return new LocationsListAdapter(getActivity().getLayoutInflater(), items);
    }

    @Override
    public void onAttach(Activity a) {
        super.onAttach(a);
        dataPasser = (OnDataPass) a;
    }


    public void passLocation(Location selected) {
        dataPasser.onLocationPass(selected);
    }



}
