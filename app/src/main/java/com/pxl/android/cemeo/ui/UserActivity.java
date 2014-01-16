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

public class UserActivity extends BootstrapActivity {

    @InjectView(R.id.iv_avatar)
    protected ImageView avatar;
    @InjectView(R.id.tv_name)
    protected TextView name;

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

        //Picasso.with(this).load(user.getAvatarUrl()).placeholder(R.drawable.gravatar_icon).into(avatar);
        Picasso.with(this).load(R.drawable.gravatar_icon).placeholder(R.drawable.gravatar_icon).into(avatar);

        Ln.d("statuslog : detail view  : %s", user.getFirstName());
        name.setText(String.format("%s %s", user.getFirstName(), user.getLastName()));

    }


}
