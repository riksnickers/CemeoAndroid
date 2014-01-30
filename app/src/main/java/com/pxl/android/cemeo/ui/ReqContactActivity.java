package com.pxl.android.cemeo.ui;


import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.pxl.android.cemeo.R;
import com.pxl.android.cemeo.core.Contact;
import com.squareup.picasso.Picasso;

import butterknife.InjectView;

import static com.pxl.android.cemeo.core.Constants.Extra.CONTACT;

public class ReqContactActivity extends BootstrapActivity {

    @InjectView(R.id.iv_contact_avatar)
    protected ImageView avatar;
    @InjectView(R.id.tv_contact_name)
    protected TextView name;

    protected Contact contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.add_contact_view);

        if (getIntent() != null && getIntent().getExtras() != null) {
            contact = (Contact) getIntent().getExtras().getSerializable(CONTACT);
        }

        //getSupportActionBar().setHomeButtonEnabled(true);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Picasso.with(this).load(user.getAvatarUrl()).placeholder(R.drawable.gravatar_icon).into(avatar);
        Picasso.with(this).load(R.drawable.gravatar_icon).placeholder(R.drawable.gravatar_icon).into(avatar);

        name.setText(String.format("%s %s", contact.getFirstName(), contact.getLastName()));


    }





}
