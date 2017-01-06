package com.bjl.tannum.wellnessathome.Controller.Activity;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.bjl.tannum.wellnessathome.Controller.Adapter.MakeAppointmentAdapter;
import com.bjl.tannum.wellnessathome.Controller.Library.SlidingTabLayout;
import com.bjl.tannum.wellnessathome.R;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.util.Date;
import java.util.Locale;

public class AppointmentActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ViewPager pager;
    private MakeAppointmentAdapter adapter;
    private SlidingTabLayout tabs;
    private CharSequence titles[]= {"Home","Events"};
    private int numberOfTabs = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        toolbar = (Toolbar)findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        //View Pager
        adapter =  new MakeAppointmentAdapter(getSupportFragmentManager(), titles, numberOfTabs);
        pager = (ViewPager) findViewById(R.id.calendar_pager);
        pager.setAdapter(adapter);

        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.colorBlack);
            }
        });
        tabs.setViewPager(pager);

    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
