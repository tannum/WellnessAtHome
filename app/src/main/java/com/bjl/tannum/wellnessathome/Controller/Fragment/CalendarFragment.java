package com.bjl.tannum.wellnessathome.Controller.Fragment;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Debug;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.bjl.tannum.wellnessathome.Controller.Activity.AddEventActivity;
import com.bjl.tannum.wellnessathome.R;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class CalendarFragment extends Fragment implements View.OnClickListener{


    private Calendar currentCalendar = Calendar.getInstance(Locale.getDefault());
    private SimpleDateFormat dateFormatForDisplaying = new SimpleDateFormat("dd-M-yyy hh:mm:ss a",Locale.getDefault());
    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMM - yyyy",Locale.getDefault());
    private CompactCalendarView compactCalendarView;
    private ActionBar toolbar;
    FloatingActionButton actionButton;

    private static final int SECOND_ACTIVITY_RESULT_CODE = 0;

    public CalendarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState ) {

        View view = inflater.inflate(R.layout.fragment_calendar,container,false);

        //Mask: Init ListView
        final List<String> mutableBookings = new ArrayList<>();
        final ListView bookingsListView = (ListView) view.findViewById(R.id.bookings_listview);
        final ArrayAdapter adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, mutableBookings);
        bookingsListView.setAdapter(adapter);



        //Mask: Init CompactCalendarView.
        compactCalendarView = (CompactCalendarView) view.findViewById(R.id.compactcalendar_view);

        // below allows you to configure color for the current day in the month
        // compactCalendarView.setCurrentDayBackgroundColor(getResources().getColor(R.color.black));
        // below allows you to configure colors for the current day the user has selected
        // compactCalendarView.setCurrentSelectedDayBackgroundColor(getResources().getColor(R.color.dark_red));
        compactCalendarView.setUseThreeLetterAbbreviation(false);
        compactCalendarView.setFirstDayOfWeek(Calendar.MONDAY);
        compactCalendarView.invalidate();

        addEvents(-1, -1);
        addEvents(Calendar.DECEMBER, -1);


        //Mask: Set initial title
        toolbar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        toolbar.setTitle(dateFormatForMonth.format(compactCalendarView.getFirstDayOfCurrentMonth()));

        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                toolbar.setTitle(dateFormatForMonth.format(dateClicked));
                Log.d("debug", "inside onclick " + dateFormatForDisplaying.format(dateClicked));
                List<Event> bookingsFromMap = compactCalendarView.getEvents(dateClicked);
                if(bookingsFromMap != null){
                    Log.d("debug", bookingsFromMap.toString());
                    mutableBookings.clear();
                    for(Event booking : bookingsFromMap){
                        mutableBookings.add((String)booking.getData());
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                toolbar.setTitle(dateFormatForMonth.format(firstDayOfNewMonth));
            }
        });


        //Init FAB button
        ImageView imageView = new ImageView(getActivity());
        imageView.setImageResource(R.mipmap.ic_launcher);
        actionButton = new FloatingActionButton.Builder(getActivity()).setContentView(imageView).build();
        actionButton.setOnClickListener(this);


        return view;
    }

    private void addEvents(int month, int year) {
        currentCalendar.setTime(new Date());
        currentCalendar.set(Calendar.DAY_OF_MONTH, 1);
        Date firstDayOfMonth = currentCalendar.getTime();
        for (int i = 0; i < 6; i++) {
            currentCalendar.setTime(firstDayOfMonth);
            if (month > -1) {
                currentCalendar.set(Calendar.MONTH, month);
            }
            if (year > -1) {
                currentCalendar.set(Calendar.ERA, GregorianCalendar.AD);
                currentCalendar.set(Calendar.YEAR, year);
            }
            currentCalendar.add(Calendar.DATE, i);
            setToMidnight(currentCalendar);
            long timeInMillis = currentCalendar.getTimeInMillis();

            List<Event> events = getEvents(timeInMillis, i);
           // Log.d("debug","Event time " + currentCalendar.getTime());
            compactCalendarView.addEvents(events);
        }
    }

    private List<Event> getEvents(long timeInMillis, int day) {
        if (day < 2) {
            return Arrays.asList(new Event(Color.argb(255, 169, 68, 65), timeInMillis, "Event at " + new Date(timeInMillis)));
        } else if ( day > 2 && day <= 4) {
            return Arrays.asList(
                    new Event(Color.argb(255, 169, 68, 65), timeInMillis, "Event at " + new Date(timeInMillis)),
                    new Event(Color.argb(255, 100, 68, 65), timeInMillis, "Event 2 at " + new Date(timeInMillis)));
        } else {
            return Arrays.asList(
                    new Event(Color.argb(255, 169, 68, 65), timeInMillis, "Event at " + new Date(timeInMillis) ),
                    new Event(Color.argb(255, 100, 68, 65), timeInMillis, "Event 2 at " + new Date(timeInMillis)),
                    new Event(Color.argb(255, 70, 68, 65), timeInMillis, "Event 3 at " + new Date(timeInMillis)));
        }
    }

    private void setToMidnight(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SECOND_ACTIVITY_RESULT_CODE){
            if(resultCode == RESULT_OK){
                String title = data.getStringExtra("keyBookingTitle");
                String bookingTime = data.getStringExtra("keyBookingTime");
                int reminderId = data.getIntExtra("keyReminderId",0);





                Log.d("debug","Booking Event = "+ title + " " + bookingTime + " " + String.valueOf(reminderId));
            }
        }
    }

    @Override
    public void onClick(View v) {
        if(v == actionButton){
            Log.d("debug","Floating button on flagment");
            Intent intent = new Intent(getActivity(),AddEventActivity.class);
            startActivityForResult(intent,SECOND_ACTIVITY_RESULT_CODE);
        }
    }
}
