package com.bjl.tannum.wellnessathome.Controller.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.bjl.tannum.wellnessathome.R;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class CalendarFragment extends Fragment {


    private Calendar currentCalendar = Calendar.getInstance(Locale.getDefault());
    private SimpleDateFormat dateFormatForDisplaying = new SimpleDateFormat("dd-M-yyy hh:mm:ss a",Locale.getDefault());
    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMM - yyyy",Locale.getDefault());
    private CompactCalendarView compactCalendarView;
    private ActionBar toolbar;


    public CalendarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_calendar,container,false);

        //Mask: Initial ListView.
//        final List<String> mutableBookings = new ArrayList<>();
//        final ListView bookingsListView = (ListView)view.findViewById(R.id.bookings_listview);
//        final ArrayAdapter adapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,mutableBookings);


        //Mask: Initial CompactCalendar
//        compactCalendarView = (CompactCalendarView)view.findViewById(R.id.compactcalendar_view);
//        compactCalendarView.setUseThreeLetterAbbreviation(false);
//        compactCalendarView.setFirstDayOfWeek(Calendar.MONDAY);
//        compactCalendarView.invalidate();


        //set initial title
//        toolbar = ((ActionBarActivity) getActivity()).getSupportActionBar();
//        toolbar.setTitle(dateFormatForMonth.format(compactCalendarView.getFirstDayOfCurrentMonth()));

        return view;
    }

}
