package com.bjl.tannum.wellnessathome.Controller.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.bjl.tannum.wellnessathome.Controller.Fragment.CalendarFragment;
import com.bjl.tannum.wellnessathome.Controller.Fragment.EventAppointmentFragment;

/**
 * Created by Tannum on 06/01/17.
 */

public class MakeAppointmentAdapter extends FragmentStatePagerAdapter {

    CharSequence titles[];
    int numbOfTabs;

    public MakeAppointmentAdapter(FragmentManager fm ,CharSequence titles[], int mNumbOfTabs) {
        super(fm);

        this.titles = titles;
        this.numbOfTabs = mNumbOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        if(position == 0){
            CalendarFragment calendarFragment = new CalendarFragment();
            return  calendarFragment;
        }
        else if(position == 1){
            EventAppointmentFragment eventAppointmentFragment = new EventAppointmentFragment();
            return  eventAppointmentFragment;
        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public int getCount() {
        return numbOfTabs;
    }
}
