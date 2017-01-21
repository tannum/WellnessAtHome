package com.bjl.tannum.wellnessathome.Controller.Fragment;


import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
public class WellnessCityFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, ViewTreeObserver.OnScrollChangedListener {



    private String page_url = "http://www.wellnesscity.co.th";
    private SwipeRefreshLayout swipeContainer;
    WebView webView;
    //me.zhanghai.android.materialprogressbar.MaterialProgressBar progressBar;
    private  boolean onLoadPage;

    public WellnessCityFragment() {
        // Required empty public constructor
    }

    public static WellnessCityFragment newInstance(){

        WellnessCityFragment fragment = new WellnessCityFragment();
        return  fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Log.d("debug","on CitFragment onCreateView");
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_wellness_city,container,false);

        swipeContainer = (SwipeRefreshLayout)layout.findViewById(R.id.swipeContainerCity);
        swipeContainer.setOnRefreshListener(this);

        //Mask: Progressbar
       // progressBar = (me.zhanghai.android.materialprogressbar.MaterialProgressBar)layout.findViewById(R.id.progress_bar_city);

        //Mask: WebView
        webView = (WebView)layout.findViewById(R.id.webWellnessCity);
        startWebView(page_url);

        return layout;

    }

    private void startWebView(String url){

        onLoadPage = true;
        webView.setWebViewClient(new WebViewClient(){

            ProgressDialog progressDialog;
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //Log.d("debug","On shouldOverrideUrlLoading ++++++");
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onLoadResource(WebView view, String url) {

                if(!onLoadPage)
                    return;
                //Log.d("debug","On Load page...++++++");
                if (progressDialog == null) {
                    // in standard case YourActivity.this
                    progressDialog = new ProgressDialog(getActivity());
                    progressDialog.setMessage("Loading...");
                    progressDialog.show();
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {

                // Log.d("debug","Load page finish ++++++");
                onLoadPage = false;
                try{
                    //Log.d("debug","Load page finish and hid diablog !!!!++++++");
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                        progressDialog = null;
                    }
                }catch(Exception exception){
                    // Log.d("debug","Load page finish exception *********");
                    exception.printStackTrace();
                }
            }
        });

        webView.getViewTreeObserver().addOnScrollChangedListener(this);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);
    }


    @Override
    public void onRefresh() {
        Log.d("debug","On Refresh");
        swipeContainer.setRefreshing(false);
        startWebView(page_url);
    }

    @Override
    public void onScrollChanged() {
        if (webView.getScrollY() == 0) {
            swipeContainer.setEnabled(true);
        } else {
            swipeContainer.setEnabled(false);
        }
    }


//    public void loadWebView(String url){
//
//        webView.loadUrl(page_url);
//        webView.setWebViewClient(new WebViewClient(){
//            @Override
//            public void onPageStarted(WebView view, String url, Bitmap favicon){
//                // Page loading started
//                Log.d("debug","onPageStarted , url = " + url);
//                progressBar.setVisibility(View.VISIBLE);
//            }
//            @Override
//            public void onPageFinished(WebView view, String url){
//                // Page loading finished
//                Log.d("debug","onPageFinished");
//                Toast.makeText(getActivity(),"Page Loaded.",Toast.LENGTH_SHORT).show();
//                progressBar.setShowProgressBackground(false);
//                progressBar.setVisibility(View.INVISIBLE);
//            }
//        });
//        webView.setWebChromeClient(new WebChromeClient(){
//
//            @Override
//            public void onProgressChanged(WebView view, int newProgress) {
//
//                //change your progress bar
//
//                int value = webView.getScrollY();
//                Log.d("debug","onProgressChanged , y = " + String.valueOf(value));
//            }
//        });
//    }

}
