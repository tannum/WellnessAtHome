package com.bjl.tannum.wellnessathome.Controller.Fragment;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.bjl.tannum.wellnessathome.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class WellnessResortFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener ,ViewTreeObserver.OnScrollChangedListener{

    private String page_url = "http://www.wellnessatresort.net";
    private SwipeRefreshLayout swipeContainer;
    WebView webView;
    me.zhanghai.android.materialprogressbar.MaterialProgressBar progressBar;

    public WellnessResortFragment() {
        // Required empty public constructor
    }

    public static WellnessResortFragment newInstance(){

        WellnessResortFragment fragment = new WellnessResortFragment();
        return  fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Log.d("debug","on ResortFragment onCreateView");
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_wellness_resort,container,false);



        swipeContainer = (SwipeRefreshLayout)layout.findViewById(R.id.swipeContainerResort);
        swipeContainer.setOnRefreshListener(this);

        //Mask: Progressbar
        progressBar = (me.zhanghai.android.materialprogressbar.MaterialProgressBar)layout.findViewById(R.id.progress_bar_resort);

        //Mask: WebView
        webView = (WebView)layout.findViewById(R.id.webWellnessResort);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getViewTreeObserver().addOnScrollChangedListener(this);
        webView.loadUrl(page_url);
        return layout;

    }

    @Override
    public void onRefresh() {
        Log.d("debug","On Refresh");
        swipeContainer.setRefreshing(false);
        loadWebView(page_url);
    }

    @Override
    public void onScrollChanged() {
        if (webView.getScrollY() == 0) {
            swipeContainer.setEnabled(true);
        } else {
            swipeContainer.setEnabled(false);
        }
    }

    public void loadWebView(String url){

        webView.loadUrl(page_url);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon){
                // Page loading started
                //Log.d("debug","onPageStarted , url = " + url);
                progressBar.setVisibility(View.VISIBLE);
            }
            @Override
            public void onPageFinished(WebView view, String url){
                // Page loading finished
                //Log.d("debug","onPageFinished");
                //Toast.makeText(getActivity(),"Page Loaded.",Toast.LENGTH_SHORT).show();
                progressBar.setShowProgressBackground(false);
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
        webView.setWebChromeClient(new WebChromeClient(){

            @Override
            public void onProgressChanged(WebView view, int newProgress) {

                //change your progress bar

                int value = webView.getScrollY();
                //Log.d("debug","onProgressChanged , y = " + String.valueOf(value));
            }
        });
    }
}
