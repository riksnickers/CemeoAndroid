package com.pxl.android.cemeo.ui;

import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.kevinsawicki.wishlist.SingleTypeAdapter;
import com.pxl.android.cemeo.BootstrapServiceProvider;
import com.pxl.android.cemeo.Injector;
import com.pxl.android.cemeo.R;
import com.pxl.android.cemeo.authenticator.LogoutService;
import com.pxl.android.cemeo.core.Contact;
import com.pxl.android.cemeo.core.OnDataPass;
import com.pxl.android.cemeo.util.Ln;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.InjectView;


/**
 * Created by jordy on 12/01/14.
 */
public class ReqContactListFragment extends ItemListFragment<Contact> {

    @Inject
    protected BootstrapServiceProvider serviceProvider;
    @Inject
    protected LogoutService logoutService;

    protected OnDataPass dataPasser;

    @InjectView(R.id.tv_no_contacts_selected)
    protected TextView noContacts;

    protected List<Contact> selected;
    protected List<Contact> required = new ArrayList<Contact>();


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
        return inflater.inflate(R.layout.create_req_meeting_contacts, null);
    }


    @Override
    protected void configureList(Activity activity, ListView listView) {
        super.configureList(activity, listView);

        listView.setFastScrollEnabled(true);
    }

    @Override
    LogoutService getLogoutService() {
        return logoutService;
    }


    @Override
    public Loader<List<Contact>> onCreateLoader(int id, Bundle args) {
        final List<Contact> initialItems = items;
        return new ThrowableLoader<List<Contact>>(getActivity(), items) {

            @Override
            public List<Contact> loadData() {

                if (getActivity() != null){
                    //latest = serviceProvider.getService(getActivity()).getContacts();

                    selected = dataPasser.getSelected();

                }

                if (selected != null){

                    for(Contact c : selected){
                        Ln.d("statuslog : selected : %s" , c.getLastName());
                    }

                    return selected;
                }else{
                    List<Contact> emptylist = new ArrayList<Contact>();
                    Contact c = new Contact();
                    c.setFirstName("No Contacts selected");
                    c.setLastName("");
                    emptylist.add(c);
                    //return Collections.emptyList();
                    return emptylist;
                }
            }
        };

    }



    public void onListItemClick(ListView l, View v, int position, long id) {

        SparseBooleanArray checked = l.getCheckedItemPositions();
        required.clear();

        for (int i = 0; i < checked.size(); i++){


            if (checked.get(i)){
                if(!(required.contains(l.getItemAtPosition(i))) || required.size() == 0){
                    //Toast.makeText(l.getContext(), "Checked !", Toast.LENGTH_SHORT).show();

                    required.add((Contact) l.getItemAtPosition(i));
                }

            }else{

                if(required.contains(l.getItemAtPosition(i))){
                    //Toast.makeText(l.getContext(), "UnChecked !", Toast.LENGTH_SHORT).show();
                    required.remove(l.getItemAtPosition(i));
                }
            }
        }

        passData(required);

    }

/*
    public void refreshFragment() {
        if(count1 != count2){
            Ln.d("statuslog : refresh !");
            count1++;
            forceRefresh();
        }

    }
*/
    @Override
    public void onLoadFinished(Loader<List<Contact>> loader, List<Contact> items) {
;
        super.onLoadFinished(loader, items);


    }



    @Override
    protected int getErrorMessage(Exception exception) {
        return R.string.error_loading_contacts;
    }

    @Override
    protected SingleTypeAdapter<Contact> createAdapter(List<Contact> items) {
        return new ReqContactListAdapter(getActivity().getLayoutInflater(), items);
    }

    @Override
    public void onAttach(Activity a) {
        super.onAttach(a);
        dataPasser = (OnDataPass) a;
    }


    public void passData(List<Contact> required) {
        dataPasser.onReqContactsPass(required);
    }




}
