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
import com.pxl.android.cemeo.core.User;
import com.pxl.android.cemeo.util.Ln;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import static com.pxl.android.cemeo.core.Constants.Extra.USER;

public class UserDataFragment extends ItemListFragment<User> {

    @Inject
    protected BootstrapServiceProvider serviceProvider;
    @Inject
    protected LogoutService logoutService;

    protected User user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Injector.inject(this);
    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setEmptyText(R.string.no_user_info);

    }


    @Override
    protected void configureList(Activity activity, ListView listView) {
        super.configureList(activity, listView);

        listView.setFastScrollEnabled(true);
        listView.setDividerHeight(0);

        //getListAdapter().addHeader(activity.getLayoutInflater().inflate(R.layout.user_list_item_labels, null));
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
                    List<User> latest = new ArrayList<User>();



                    if (getActivity() != null)

                        user = serviceProvider.getService(getActivity()).getUserData();

                    if (user != null){
                        latest.add(user);
                        return latest;
                    }
                    else{
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


    public void onListItemClick(ListView l, View v, int position, long id) {
        //User user = ((User) l.getItemAtPosition(position));

        //startActivity(new Intent(getActivity(), UserDataActivity.class).putExtra(USER, user));
    }

    @Override
    public void onLoadFinished(Loader<List<User>> loader, List<User> items) {
        super.onLoadFinished(loader, items);

    }


    @Override
    protected int getErrorMessage(Exception exception) {
        return R.string.error_loading_userinfo;
    }

    @Override
    protected SingleTypeAdapter<User> createAdapter(List<User> items) {
        return new UserDataAdapter(getActivity().getLayoutInflater(), items);
    }
}
