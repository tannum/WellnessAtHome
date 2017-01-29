package com.bjl.tannum.wellnessathome.Controller.Activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.bjl.tannum.wellnessathome.Controller.Adapter.WellnesWebAdapter;
import com.bjl.tannum.wellnessathome.Controller.Fragment.NavigationDrawerFragment;
import com.bjl.tannum.wellnessathome.R;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    ViewPager pager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Mask: setup tool bar
        toolbar = (Toolbar)findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Mask: setup navigation drawer
        NavigationDrawerFragment drawderFragment = (NavigationDrawerFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawderFragment.setUp(R.id.fragment_navigation_drawer,(DrawerLayout)findViewById(R.id.drawer_layout),toolbar);

        //Mask: ViewPager
        WellnesWebAdapter wellnesWebAdapter = new WellnesWebAdapter(getSupportFragmentManager());
        pager = (ViewPager)findViewById(R.id.pager);
        pager.setAdapter(wellnesWebAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //Log.d("debug","************* On Create Menu");
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int item_id = item.getItemId();
        Log.d("debug","item id = " + String.valueOf(item_id));
        switch (item_id){
            case R.id.action_emergency:
                break;
            case R.id.action_appointment:
                startActivity(new Intent(MainActivity.this,AppointmentActivity.class));
                break;
            case R.id.action_promotion:
                startActivity(new Intent(MainActivity.this,PromotionActivity.class));
                break;
            case R.id.action_location:
                startActivity(new Intent(MainActivity.this,LocationActivity.class));
                break;
            case R.id.action_logout:
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
