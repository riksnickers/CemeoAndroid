package com.pxl.android.cemeo.ui;

import android.view.LayoutInflater;

import com.pxl.android.cemeo.R;
import com.pxl.android.cemeo.core.Meeting;

import java.util.List;

public class MeetingListAdapter extends AlternatingColorListAdapter<Meeting> {
    /**
     * @param inflater
     * @param items
     * @param selectable
     */
    public MeetingListAdapter(LayoutInflater inflater, List<Meeting> items,
                              boolean selectable) {
        super(R.layout.meetings_list_item, inflater, items, selectable);
    }

    /**
     * @param inflater
     * @param items
     */
    public MeetingListAdapter(LayoutInflater inflater, List<Meeting> items) {
        super(R.layout.meetings_list_item, inflater, items);
    }

    @Override
    protected int[] getChildViewIds() {
        return new int[]{R.id.tv_meeting_date, R.id.tv_meeting_creator, R.id.tv_meeting_location};
    }

    @Override
    protected void update(int position, Meeting item) {
        super.update(position, item);

        setText(0, item.getDate());
        setText(1, item.getCreator());
        setText(2, item.getLocation());
        //setNumber(R.id.tv_date, item.getCreatedAt());
    }
}
