package com.pxl.android.cemeo.ui;


import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.pxl.android.cemeo.R;
import com.pxl.android.cemeo.core.User;
import com.pxl.android.cemeo.util.Ln;
import com.squareup.picasso.Picasso;

import butterknife.InjectView;

import static com.pxl.android.cemeo.core.Constants.Extra.USER;

public class UserDataActivity extends BootstrapActivity {

    @InjectView((R.id.iv_avatar))
    protected ImageView avatar;
    @InjectView(R.id.tv_email)
    protected TextView email;
    @InjectView(R.id.tv_firstname)
    protected TextView firstname;
    @InjectView(R.id.tv_lastname)
    protected TextView lastname;
    @InjectView(R.id.tv_location)
    protected TextView location;
    @InjectView(R.id.tv_userid)
    protected TextView userid;
    @InjectView(R.id.tv_username)
    protected TextView username;

    protected User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.user_view);

        if (getIntent() != null && getIntent().getExtras() != null) {
            user = (User) getIntent().getExtras().getSerializable(USER);
        }

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Picasso.with(this).load(R.drawable.gravatar_icon).placeholder(R.drawable.gravatar_icon).into(avatar);


        Ln.d("statuslog : *********************** : %s" , user.getEMail());


        email.setText(user.getEMail());
        firstname.setText(user.getFirstName());
        lastname.setText(user.getLastName());
        location.setText(user.getPreferedLocation().getName());
        userid.setText(user.getUserId());
        username.setText(user.getUserName());



    }


}
