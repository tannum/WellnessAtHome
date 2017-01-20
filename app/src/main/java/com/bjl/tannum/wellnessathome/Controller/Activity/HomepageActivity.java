package com.bjl.tannum.wellnessathome.Controller.Activity;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bjl.tannum.wellnessathome.Controller.Fragment.WellnessCityFragment;
import com.bjl.tannum.wellnessathome.Controller.Fragment.WellnessHomeFragment;
import com.bjl.tannum.wellnessathome.Controller.Fragment.WellnessResortFragment;
import com.bjl.tannum.wellnessathome.Controller.Fragment.WellnessSahakornFragment;
import com.bjl.tannum.wellnessathome.R;

public class HomepageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        //Mask: get message from intent
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if(extras == null){
            finish();
        }
        String homepage = extras.getString("URL");

        //Mask: Open homepage
        if(homepage.equals("home")){
            WellnessHomeFragment fragment = new WellnessHomeFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.homepage_fragment_container,fragment);
            transaction.commit();
        }
        else if(homepage.equals("city")){
            WellnessCityFragment fragment = new WellnessCityFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.homepage_fragment_container,fragment);
            transaction.commit();
        }
        else if(homepage.equals("resort")){
            WellnessResortFragment fragment = new WellnessResortFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.homepage_fragment_container,fragment);
            transaction.commit();
        }
        else if(homepage.equals("sahakron")){
            WellnessSahakornFragment fragment = new WellnessSahakornFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.homepage_fragment_container,fragment);
            transaction.commit();
        }
        else{
            finish();
        }


    }
}
