package com.pxl.android.cemeo.ui;


import android.text.TextUtils;
import android.view.LayoutInflater;

import com.github.kevinsawicki.wishlist.SingleTypeAdapter;
import com.pxl.android.cemeo.BootstrapApplication;
import com.pxl.android.cemeo.R;
import com.pxl.android.cemeo.core.Contact;
import com.pxl.android.cemeo.core.Location;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Adapter to display a list of locations
 */
public class LocationsListAdapter extends SingleTypeAdapter<Location> {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MMMM dd");

    /**
     * @param inflater
     * @param items
     */
    public LocationsListAdapter(LayoutInflater inflater, List<Location> items) {
        super(inflater, R.layout.add_location_list_item);

        setItems(items);
    }

    /**
     * @param inflater
     */
    public LocationsListAdapter(LayoutInflater inflater) {
        this(inflater, null);

    }

    @Override
    public long getItemId(final int position) {
        int id = getItem(position).getLocationID();
        //slechte code
        String id2 = Integer.toString(id);
        return !TextUtils.isEmpty(id2) ? id2.hashCode() : super.getItemId(position);
    }

    @Override
    protected int[] getChildViewIds() {
        return new int[]{R.id.tv_location_name , R.id.tv_location_street , R.id.tv_location_city , R.id.tv_location_country};
    }


    @Override
    protected void update(int position, Location location) {


        setText(0, String.format("%s", location.getName()));
        setText(1, String.format("%s", location.getStreet()));
        setText(2, String.format("%s", location.getCity()));
        setText(3, String.format("%s", location.getCountry()));


    }


}
