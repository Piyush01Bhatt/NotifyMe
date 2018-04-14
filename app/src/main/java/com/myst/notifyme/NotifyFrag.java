package com.myst.notifyme;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import java.sql.Time;

/**
 * Created by Myst on 1/19/2017.
 */

public class NotifyFrag extends Fragment {

    Spinner days;
    Communicator communicate;
    TimeTable currentTimeTable;
    EditText dayQuery;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        communicate = (Communicator)context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_notify,container,false);

        days = (Spinner) layout.findViewById(R.id.days_spinner);

        currentTimeTable = new TimeTable(layout);
        communicate.setObject(days, currentTimeTable);
        return layout;
    }
}
