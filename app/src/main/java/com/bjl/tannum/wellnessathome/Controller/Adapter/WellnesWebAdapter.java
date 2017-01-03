package com.bjl.tannum.wellnessathome.Controller.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.bjl.tannum.wellnessathome.Controller.Fragment.WellnessCityFragment;
import com.bjl.tannum.wellnessathome.Controller.Fragment.WellnessHomeFragment;
import com.bjl.tannum.wellnessathome.Controller.Fragment.WellnessResortFragment;
import com.bjl.tannum.wellnessathome.Controller.Fragment.WellnessSahakornFragment;

/**
 * Created by tannum on 12/29/2016 AD.
 */

public class WellnesWebAdapter extends FragmentPagerAdapter {

    public WellnesWebAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        Log.d("debug","get item , position = " + String.valueOf(position));

        if(position == 0){
            return WellnessHomeFragment.newInstance();
        }
        else if(position == 1){
            return  WellnessResortFragment.newInstance();
        }
        else if (position == 2){
            return WellnessSahakornFragment.newInstance();
        }
        else if (position == 3){
            return WellnessCityFragment.newInstance();
        }

        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
