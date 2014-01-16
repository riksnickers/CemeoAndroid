package com.pxl.android.cemeo.ui;


import android.text.TextUtils;
import android.view.LayoutInflater;

import com.github.kevinsawicki.wishlist.SingleTypeAdapter;
import com.pxl.android.cemeo.BootstrapApplication;
import com.pxl.android.cemeo.R;
import com.pxl.android.cemeo.core.User;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Adapter to display a list of traffic items
 */
public class ContactListAdapter extends SingleTypeAdapter<User> {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MMMM dd");

    /**
     * @param inflater
     * @param items
     */
    public ContactListAdapter(LayoutInflater inflater, List<User> items) {
        super(inflater, R.layout.add_contact_list_item);

        setItems(items);
    }

    /**
     * @param inflater
     */
    public ContactListAdapter(LayoutInflater inflater) {
        this(inflater, null);

    }

    @Override
    public long getItemId(final int position) {
        String id = getItem(position).getId();
        return !TextUtils.isEmpty(id) ? id.hashCode() : super.getItemId(position);
    }

    @Override
    protected int[] getChildViewIds() {
        return new int[]{R.id.iv_contact_avatar, R.id.tv_contact_name};
    }


    @Override
    protected void update(int position, User user) {

        Picasso.with(BootstrapApplication.getInstance())
                //.load(user.getAvatarUrl()) --> afb van de spectifieke gebruiker laden
                .load(R.drawable.gravatar_icon)
                .placeholder(R.drawable.gravatar_icon)
                .into(imageView(0));

        setText(1, String.format("%1$s %2$s", user.getFirstName(), user.getLastName()));


    }


}
