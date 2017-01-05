package com.bjl.tannum.wellnessathome.Controller.Activity;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.bjl.tannum.wellnessathome.R;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.util.Date;
import java.util.Locale;

public class AppointmentActivity extends AppCompatActivity {

    CompactCalendarView compactCalendarView;
    private java.text.SimpleDateFormat dateformatMonth = new java.text.SimpleDateFormat("MMM- yyyy", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);




        compactCalendarView = (CompactCalendarView)findViewById(R.id.compactcalendar_view);
        compactCalendarView.setUseThreeLetterAbbreviation(true);

        Event evl = new Event(Color.RED,1477054800000L, "Teachers Professiional Day");

        compactCalendarView.addEvent(evl);
        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                Context context = getApplicationContext();
                if(dateClicked.toString().compareTo("Fri Oct 21 09:00:00 AST 2016") == 0){
                    Toast.makeText(context, "Teacher Professional Daay",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(context,"No Events Planded ",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                //actionbar.setTitle(dateformatMonth.format(firstDayOfNewMonth));
            }
        });


    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
