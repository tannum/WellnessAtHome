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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.bjl.tannum.wellnessathome.Controller.Activity.AddEventActivity;
import com.bjl.tannum.wellnessathome.Controller.Adapter.BookingAdapter;
import com.bjl.tannum.wellnessathome.Model.BookingInfo;
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
public class CalendarFragment extends Fragment implements View.OnClickListener ,BookingAdapter.ClickListener{


    private Calendar currentCalendar = Calendar.getInstance(Locale.getDefault());
    private SimpleDateFormat dateFormatForDisplaying = new SimpleDateFormat("dd-M-yyy hh:mm:ss a",Locale.getDefault());
    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMM - yyyy",Locale.getDefault());
    private CompactCalendarView compactCalendarView;
    private ActionBar toolbar;
    FloatingActionButton actionButton;

    List<BookingInfo> bookingInfos;
    RecyclerView recyclerView;
    BookingAdapter bookingAdapter;



    private static final int SECOND_ACTIVITY_RESULT_CODE = 0;

    public CalendarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState ) {

        View view = inflater.inflate(R.layout.fragment_calendar,container,false);


        //Mask: Init RecyclerView
        bookingInfos = new ArrayList<BookingInfo>();
        recyclerView = (RecyclerView)view.findViewById(R.id.available_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        bookingAdapter = new BookingAdapter(getContext(),bookingInfos);
        bookingAdapter.SetOnBookingItemClickListener(this);
        recyclerView.setAdapter(bookingAdapter);

        //Mask: Init CompactCalendarView.
        compactCalendarView = (CompactCalendarView) view.findViewById(R.id.compactcalendar_view);
        compactCalendarView.setUseThreeLetterAbbreviation(false);
        compactCalendarView.setFirstDayOfWeek(Calendar.MONDAY);
        compactCalendarView.invalidate();

        //Mask: Set initial title
        toolbar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        toolbar.setTitle(dateFormatForMonth.format(compactCalendarView.getFirstDayOfCurrentMonth()));

        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {

                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
                String[] dt = format.format(dateClicked.getTime()).split("-");
                int dom = Integer.valueOf(dt[0]);


                bookingInfos.clear();
                bookingAdapter.notifyDataSetChanged();
                if((dom % 2) == 0){
                    bookingInfos.add(new BookingInfo(R.drawable.doctor1,"office1\noffice1","19/01/2017","12.00-18.00"));
                    bookingInfos.add(new BookingInfo(R.drawable.doctor2,"office1\noffice1","19/01/2017","12.00-18.00"));
                    bookingAdapter.notifyDataSetChanged();

                }
                else {
                    bookingInfos.add(new BookingInfo(R.drawable.doctor2,"office1\noffice1","19/01/2017","12.00-18.00"));
                    bookingInfos.add(new BookingInfo(R.drawable.doctor1,"office1\noffice1","19/01/2017","12.00-18.00"));
                    bookingInfos.add(new BookingInfo(R.drawable.doctor3,"office1\noffice1","19/01/2017","12.00-18.00"));
                    bookingAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                toolbar.setTitle(dateFormatForMonth.format(firstDayOfNewMonth));
            }
        });


//        //Init FAB button
//        ImageView imageView = new ImageView(getActivity());
//        imageView.setImageResource(R.mipmap.ic_launcher);
//        actionButton = new FloatingActionButton.Builder(getActivity()).setContentView(imageView).build();
//        actionButton.setOnClickListener(this);



        //Mask: Make Calender event information
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();
        for(int i = 0;i<5;i++){
            calendar.setTime(today);
            calendar.add(Calendar.DATE,i);
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyy-hh-mm-ss");
            String dt[] = format.format(calendar.getTime()).split("-");
            addEvent(Integer.valueOf(dt[0]),
                    Integer.valueOf(dt[1])-1,
                    Integer.valueOf(dt[2]),
                    Integer.valueOf(dt[3]),
                    Integer.valueOf(dt[4]));
        }

        //Mask: Make Dummy officer available information.
        bookingInfos.add(new BookingInfo(R.drawable.doctor1,"office1\noffice1","19/01/2017","12.00-18.00"));
        bookingInfos.add(new BookingInfo(R.drawable.doctor2,"office1\noffice1","19/01/2017","12.00-18.00"));
        bookingInfos.add(new BookingInfo(R.drawable.doctor3,"office1\noffice1","19/01/2017","12.00-18.00"));

        return view;
    }






//    private void addEvents(int month, int year) {
//        currentCalendar.setTime(new Date());
//        currentCalendar.set(Calendar.DAY_OF_MONTH, 1);
//        Date firstDayOfMonth = currentCalendar.getTime();
//        for (int i = 0; i < 6; i++) {
//            currentCalendar.setTime(firstDayOfMonth);
//            if (month > -1) {
//                currentCalendar.set(Calendar.MONTH, month);
//            }
//            if (year > -1) {
//                currentCalendar.set(Calendar.ERA, GregorianCalendar.AD);
//                currentCalendar.set(Calendar.YEAR, year);
//            }
//            currentCalendar.add(Calendar.DATE, i);
//            setToMidnight(currentCalendar);
//            long timeInMillis = currentCalendar.getTimeInMillis();
//
//            List<Event> events = getEvents(timeInMillis, i);
//           // Log.d("debug","Event time " + currentCalendar.getTime());
//            compactCalendarView.addEvents(events);
//        }
//    }









//    private List<Event> getEvents(long timeInMillis, int day) {
//        if (day < 2) {
//            return Arrays.asList(new Event(Color.argb(255, 169, 68, 65), timeInMillis, "Event at " + new Date(timeInMillis)));
//        } else if ( day > 2 && day <= 4) {
//            return Arrays.asList(
//                    new Event(Color.argb(255, 169, 68, 65), timeInMillis, "Event at " + new Date(timeInMillis)),
//                    new Event(Color.argb(255, 100, 68, 65), timeInMillis, "Event 2 at " + new Date(timeInMillis)));
//        } else {
//            return Arrays.asList(
//                    new Event(Color.argb(255, 169, 68, 65), timeInMillis, "Event at " + new Date(timeInMillis) ),
//                    new Event(Color.argb(255, 100, 68, 65), timeInMillis, "Event 2 at " + new Date(timeInMillis)),
//                    new Event(Color.argb(255, 70, 68, 65), timeInMillis, "Event 3 at " + new Date(timeInMillis)));
//        }
//    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SECOND_ACTIVITY_RESULT_CODE){
            if(resultCode == RESULT_OK){

                String title = data.getStringExtra("keyBookingTitle");
                String bookingDate= data.getStringExtra("keyBookingDate");
                String bookingTime = data.getStringExtra("keyBookingTime");
                int reminderId = data.getIntExtra("keyReminderId",0);
                Log.d("debug","Booking Event = "+ title + " " + bookingDate + " " + bookingTime + " " + String.valueOf(reminderId));

                String[] date = bookingDate.split("/");
                String[] time = bookingTime.substring(0,5).split(":");
//                Log.d("debug","splite_date: " + date[0] + " " + date[1] + " " + date[2]);
//                Log.d("debug","splite_time: " + time[0] + " " + time[1]);

//                addEvent(Integer.valueOf(date[0]),
//                        Integer.valueOf(date[1])-1,
//                        Integer.valueOf(date[2]),
//                        Integer.valueOf(time[0]),
//                        Integer.valueOf(time[1]));
            }
        }
    }

    private void addEvent(int day , int month , int year, int hour, int min){

        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.set(Calendar.DAY_OF_MONTH,day);
        calendar.set(Calendar.MONTH,month);
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.HOUR_OF_DAY , hour);
        calendar.set(Calendar.MINUTE,min);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);

        //Log.d("debug","Event time = " + calendar.getTime());

        long timeInMillis = calendar.getTimeInMillis();
        List<Event> events = Arrays.asList(new Event(Color.argb(255, 169, 68, 65), timeInMillis, "Event at " + new Date(timeInMillis)));
        compactCalendarView.addEvents(events);
    }

    @Override
    public void onClick(View v) {
        if(v == actionButton){
            Log.d("debug","Floating button on flagment");
            Intent intent = new Intent(getActivity(),AddEventActivity.class);
            startActivityForResult(intent,SECOND_ACTIVITY_RESULT_CODE);
        }
    }

    @Override
    public void onBookingItemClicked(int position, View view) {
        Log.d("debug","On booking click , position = " + String.valueOf(position));
        Intent intent = new Intent(getActivity(),AddEventActivity.class);
        startActivityForResult(intent,SECOND_ACTIVITY_RESULT_CODE);
    }



}
