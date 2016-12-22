package com.bjl.tannum.wellnessathome.Controller.Activity;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.webkit.WebView;

import com.bjl.tannum.wellnessathome.Controller.Fragment.NavigationDrawerFragment;
import com.bjl.tannum.wellnessathome.R;

public class MainActivity extends AppCompatActivity {

    WebView webView;
    private Toolbar toolbar;

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


        //Init webView
        webView = (WebView)findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("http://www.wellnessathomes.net");

    }
    @Override
    public void onResume() {
        super.onResume();
        Log.d("debug","On Resume");
    }
}
