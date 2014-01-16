package com.pxl.android.cemeo.ui;

import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.github.kevinsawicki.wishlist.SingleTypeAdapter;
import com.github.kevinsawicki.wishlist.Toaster;
import com.pxl.android.cemeo.BootstrapServiceProvider;
import com.pxl.android.cemeo.Injector;
import com.pxl.android.cemeo.R;
import com.pxl.android.cemeo.authenticator.LogoutService;
import com.pxl.android.cemeo.core.User;
import com.pxl.android.cemeo.util.Ln;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.InjectView;

import static com.pxl.android.cemeo.core.Constants.Extra.CONTACTS_SELECTED;
import static com.pxl.android.cemeo.core.Constants.Extra.USER;

/**
 * Created by jordy on 12/01/14.
 */
public class ContactListFragment extends ItemListFragment<User> {

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

        setEmptyText(R.string.no_contacts);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.create_meeting_contacts, null);
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
    public Loader<List<User>> onCreateLoader(int id, Bundle args) {
        final List<User> initialItems = items;
        return new ThrowableLoader<List<User>>(getActivity(), items) {
            @Override
            public List<User> loadData() throws Exception {

                try {
                    List<User> latest = null;

                    if (getActivity() != null)
                        latest = serviceProvider.getService(getActivity()).getUsers();

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


    public void onListItemClick(ListView l, View v, int position, long id) {
        //User user = ((User) l.getItemAtPosition(position));

        //Ln.d("statuslog : klik ! --> %s" , user.getId());


        //startActivity(new Intent(getActivity(), ContactActivity.class).putExtra(USER, user));
    }




    @Override
    public void onLoadFinished(Loader<List<User>> loader, List<User> items) {
        super.onLoadFinished(loader, items);

    }

    @Override
    protected int getErrorMessage(Exception exception) {
        return R.string.error_loading_contacts;
    }

    @Override
    protected SingleTypeAdapter<User> createAdapter(List<User> items) {
        return new ContactListAdapter(getActivity().getLayoutInflater(), items);
    }


}
