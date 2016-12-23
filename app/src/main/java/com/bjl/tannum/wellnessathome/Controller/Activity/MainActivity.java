package com.bjl.tannum.wellnessathome.Controller.Activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.bjl.tannum.wellnessathome.Controller.Fragment.NavigationDrawerFragment;
import com.bjl.tannum.wellnessathome.R;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    WebView webView;
    private Toolbar toolbar;
    me.zhanghai.android.materialprogressbar.MaterialProgressBar progressBar;
    private SwipeRefreshLayout swipeContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(this);

        //Mask: setup tool bar
        toolbar = (Toolbar)findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Mask: setup navigation drawer
        NavigationDrawerFragment drawderFragment = (NavigationDrawerFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawderFragment.setUp(R.id.fragment_navigation_drawer,(DrawerLayout)findViewById(R.id.drawer_layout),toolbar);

        //Mask: Progressbar
        progressBar = (me.zhanghai.android.materialprogressbar.MaterialProgressBar)findViewById(R.id.progress_bar);

        //WebView
        webView = (WebView)findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        loadWebView();


    }

    @Override
    public void onRefresh() {
        Log.d("debug","On Refresh");
        swipeContainer.setRefreshing(false);
        loadWebView();
    }

    private void loadWebView(){
        //Webview
        webView.loadUrl("http://www.wellnessathomes.net");
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon){
                // Page loading started
                // Do something
                Log.d("debug","onPageStarted");
                progressBar.setVisibility(View.VISIBLE);
            }
            @Override
            public void onPageFinished(WebView view, String url){
                // Page loading finished
                Log.d("debug","onPageFinished");
                Toast.makeText(MainActivity.this,"Page Loaded.",Toast.LENGTH_SHORT).show();
                progressBar.setShowProgressBackground(false);
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
        webView.setWebChromeClient(new WebChromeClient(){

            @Override
            public void onProgressChanged(WebView view, int newProgress) {

                //change your progress bar
                Log.d("debug","onProgressChanged");
            }
        });
    }
}
