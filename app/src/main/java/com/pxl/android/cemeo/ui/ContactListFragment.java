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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.github.kevinsawicki.wishlist.SingleTypeAdapter;
import com.github.kevinsawicki.wishlist.Toaster;
import com.pxl.android.cemeo.BootstrapServiceProvider;
import com.pxl.android.cemeo.Injector;
import com.pxl.android.cemeo.R;
import com.pxl.android.cemeo.authenticator.LogoutService;
import com.pxl.android.cemeo.core.Contact;
import com.pxl.android.cemeo.core.OnDataPass;
import com.pxl.android.cemeo.core.User;
import com.pxl.android.cemeo.util.Ln;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;


/**
 * Created by jordy on 12/01/14.
 */
public class ContactListFragment extends ItemListFragment<Contact> {

    @Inject
    protected BootstrapServiceProvider serviceProvider;
    @Inject
    protected LogoutService logoutService;

    protected OnDataPass dataPasser;

    protected List<Contact> selected = new ArrayList<Contact>();


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

        listView.setFastScrollEnabled(true);
        //listView.setDividerHeight(0);
        //getListAdapter().addHeader(activity.getLayoutInflater().inflate(R.layout.add_contact_list_item_labels, null));

/*
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Ln.d("statuslog : Selected : %s", "-------------------------------------------------");


                //Contact contact = (Contact) parent.getItemAtPosition(position);
                //Toast.makeText(parent.getContext(), parent.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();


                //Contact contact = (Contact) parent.getSelectedItem();
                //selected.add(contact);

                //passData(selected);


                //Ln.d("statuslog : Selected : %s", contact.getFirstName());



            }
        });

*/
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
            public List<Contact> loadData() throws Exception {

                try {
                    List<Contact> latest = null;

                    if (getActivity() != null)
                        latest = serviceProvider.getService(getActivity()).getContacts();

                    if (latest != null)
                        return latest;
                    else{
                    List<Contact> emptylist = new ArrayList<Contact>();
                    Contact c = new Contact();
                    c.setFirstName("Error loading contacts");
                    c.setLastName("");
                    emptylist.add(c);
                    //return Collections.emptyList();
                    return emptylist;
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

        SparseBooleanArray checked = l.getCheckedItemPositions();


        for (int i = 0; i < checked.size(); i++){


            if (checked.get(i)){
                if(!(selected.contains(l.getItemAtPosition(i)))){
                    //Toast.makeText(l.getContext(), "Checked !", Toast.LENGTH_SHORT).show();
                    selected.add((Contact) l.getItemAtPosition(i));
                }

            }else{

                if(selected.contains(l.getItemAtPosition(i))){
                    //Toast.makeText(l.getContext(), "UnChecked !", Toast.LENGTH_SHORT).show();
                    selected.remove(l.getItemAtPosition(i));
                }
            }
        }

        passData(selected);

    }




    @Override
    public void onLoadFinished(Loader<List<Contact>> loader, List<Contact> items) {
        super.onLoadFinished(loader, items);

    }

    @Override
    protected int getErrorMessage(Exception exception) {
        return R.string.error_loading_contacts;
    }

    @Override
    protected SingleTypeAdapter<Contact> createAdapter(List<Contact> items) {
        return new ContactListAdapter(getActivity().getLayoutInflater(), items);
    }

    @Override
    public void onAttach(Activity a) {
        super.onAttach(a);
        dataPasser = (OnDataPass) a;
    }


    public void passData(List<Contact> selected) {
        dataPasser.onContactsPass(selected);
    }




}
