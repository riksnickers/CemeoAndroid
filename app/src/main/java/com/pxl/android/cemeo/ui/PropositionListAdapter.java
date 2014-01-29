package com.pxl.android.cemeo.ui;

import android.view.LayoutInflater;

import com.pxl.android.cemeo.R;
import com.pxl.android.cemeo.core.Meeting;
import com.pxl.android.cemeo.core.MeetingProposition;

import java.util.List;

public class PropositionListAdapter extends AlternatingColorListAdapter<MeetingProposition> {
    /**
     * @param inflater
     * @param items
     * @param selectable
     */
    public PropositionListAdapter(LayoutInflater inflater, List<MeetingProposition> items,
                                  boolean selectable) {
        super(R.layout.propositions_list_item, inflater, items, selectable);
    }

    /**
     * @param inflater
     * @param items
     */
    public PropositionListAdapter(LayoutInflater inflater, List<MeetingProposition> items) {
        super(R.layout.propositions_list_item, inflater, items);
    }

    @Override
    protected int[] getChildViewIds() {
        return new int[]{R.id.tv_prop_date, R.id.tv_prop_location, R.id.tv_prop_creator};
    }

    @Override
    protected void update(int position, MeetingProposition item) {
        super.update(position, item);

        setText(0, item.getProposition().getBeginTime());
        setText(1, item.getProposition().getProposedRoom().getName());
        //setText(2, item.getProposition().);
        //setNumber(R.id.tv_date, item.getCreatedAt());
    }
}
