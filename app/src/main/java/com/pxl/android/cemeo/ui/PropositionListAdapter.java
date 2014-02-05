package com.pxl.android.cemeo.ui;

import android.view.LayoutInflater;

import com.pxl.android.cemeo.R;
import com.pxl.android.cemeo.core.Meeting;
import com.pxl.android.cemeo.core.MeetingProposition;
import com.pxl.android.cemeo.core.Other;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

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
        return new int[]{R.id.tv_prop_date,R.id.tv_prop_time , R.id.tv_prop_location , R.id.tv_prop_status};
    }

    @Override
    protected void update(int position, MeetingProposition item) {
        super.update(position, item);


        String begindate = item.getProposition().getBeginTime();
        DateTimeFormatter iso = ISODateTimeFormat.dateHourMinuteSecond();
        DateTime dateTime = iso.parseDateTime(begindate);

        setText(0, dateTime.toString("dd-MM-yyyy"));
        setText(1, dateTime.toString("hh:mm"));
        setText(2, item.getProposition().getProposedRoom().getName());
        //setText(3, item.getProposition().getEndTime());




    }
}
