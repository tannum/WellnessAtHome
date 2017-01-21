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
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.bjl.tannum.wellnessathome.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class WellnessHomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, ViewTreeObserver.OnScrollChangedListener{


    private String page_url = "http://www.wellnessathomes.net";
    private SwipeRefreshLayout swipeContainer;
    WebView webView;
    me.zhanghai.android.materialprogressbar.MaterialProgressBar progressBar;
    private  boolean onLoadPage;

    public WellnessHomeFragment() {
        // Required empty public constructor
    }

    public static WellnessHomeFragment newInstance(){

        WellnessHomeFragment fragment = new WellnessHomeFragment();
        return  fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_wellness_home,container,false);

        swipeContainer = (SwipeRefreshLayout)layout.findViewById(R.id.swipeContainerHome);
        swipeContainer.setOnRefreshListener(this);

        //Mask: Progressbar
        //progressBar = (me.zhanghai.android.materialprogressbar.MaterialProgressBar)layout.findViewById(R.id.progress_bar_home);

        //Mask: WebView
        webView = (WebView)layout.findViewById(R.id.webWellnessHome);
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
       // Log.d("debug","On Refresh");
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
}
