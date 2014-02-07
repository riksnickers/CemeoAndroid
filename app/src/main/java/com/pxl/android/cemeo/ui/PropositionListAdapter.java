package com.pxl.android.cemeo.ui;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.pxl.android.cemeo.R;
import com.pxl.android.cemeo.core.Meeting;
import com.pxl.android.cemeo.core.MeetingProposition;
import com.pxl.android.cemeo.core.Other;
import com.pxl.android.cemeo.util.Ln;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.util.List;

public class PropositionListAdapter extends AlternatingColorListAdapter<MeetingProposition> {

    private Button statusbtn;

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
        return new int[]{R.id.tv_prop_date,R.id.tv_prop_time , R.id.tv_prop_location  , R.id.tv_prop_status ,R.id.status_btn_accepted};
    }

    @Override
    protected void update(int position, MeetingProposition item) {
        super.update(position, item);

        String begindate = item.getProposition().getBeginTime();
        DateTimeFormatter iso = ISODateTimeFormat.dateHourMinuteSecond();
        DateTime dateTime = iso.parseDateTime(begindate);

        //check answer
        int answernumber = item.getAnswer();
        String answer;

        switch (answernumber) {
            case 0:  answer = "Unconfirmed";
                break;
            case 1:  answer = "Accepted";
                break;
            case 2:  answer = "Rejected";
                break;
            case 3:  answer = "Online";
                break;
            default: answer = "Unknown";
                break;
        }

        setText(0, "Meeting on " + dateTime.toString("EEEE , dd-MM-yyyy"));
        setText(1, "At " + dateTime.toString("hh:mm"));
        setText(2, item.getProposition().getProposedRoom().getName());
        setText(3, "Current Status : ");
        setText(4, answer);



    }
}
