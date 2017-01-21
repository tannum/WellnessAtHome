package com.bjl.tannum.wellnessathome.Controller.Fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.bjl.tannum.wellnessathome.Controller.Activity.HomepageActivity;
import com.bjl.tannum.wellnessathome.Controller.Activity.LoginActivity;
import com.bjl.tannum.wellnessathome.Controller.Activity.MainActivity;
import com.bjl.tannum.wellnessathome.Controller.Adapter.NavRecyclerviewAdapter;
import com.bjl.tannum.wellnessathome.Model.navItemInfo;
import com.bjl.tannum.wellnessathome.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationDrawerFragment extends Fragment implements View.OnClickListener{


    public static final String PREF_FILE_NAME = "testpref";
    public static final String KEY_USER_LEARN_DRAWER = "user_learned_drawer";
    private RecyclerView recyclerView;
    private NavRecyclerviewAdapter adapter;
    private Button btnLogout;
    private Button btnWebLink1;
    private Button btnWebLink2;
    private Button btnWebLink3;
    private Button btnWebLink4;



    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private boolean mUserLearnedDrawer;
    private boolean mFromSaveInstanceState;
    private View containerView;


    public NavigationDrawerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mUserLearnedDrawer = Boolean.valueOf(readFromPreferences(getActivity(),KEY_USER_LEARN_DRAWER,"false"));
        if(savedInstanceState != null){
            mFromSaveInstanceState = true;
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {



        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_navigation_drawer,container,false);

        //Initial Component
        btnLogout = (Button) layout.findViewById(R.id.btnLogout);
        btnWebLink1 = (Button)layout.findViewById(R.id.btnWebLink1);
        btnWebLink2 = (Button)layout.findViewById(R.id.btnWebLink2);
        btnWebLink3 = (Button)layout.findViewById(R.id.btnWebLink3);
        btnWebLink4 = (Button)layout.findViewById(R.id.btnWebLink4);
        btnLogout.setOnClickListener(this);
        btnWebLink1.setOnClickListener(this);
        btnWebLink2.setOnClickListener(this);
        btnWebLink3.setOnClickListener(this);
        btnWebLink4.setOnClickListener(this);


        //Initial RecycleView
        recyclerView = (RecyclerView)layout.findViewById(R.id.drawerList);
        adapter = new NavRecyclerviewAdapter(getActivity(),getData());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return layout;
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btnLogout:
                Log.d("debug","Logout button clicked");
                mDrawerLayout.closeDrawers();
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
                break;
            case R.id.btnWebLink1:
                Intent intent_home = new Intent(getActivity(),HomepageActivity.class);
                intent_home.putExtra("URL","home");
                startActivity(intent_home);
                mDrawerLayout.closeDrawers();
                break;
            case R.id.btnWebLink2:
                Intent intent_resort = new Intent(getActivity(),HomepageActivity.class);
                intent_resort.putExtra("URL","resort");
                startActivity(intent_resort);
                mDrawerLayout.closeDrawers();
                break;
            case R.id.btnWebLink3:
                Intent intent_sahakron = new Intent(getActivity(),HomepageActivity.class);
                intent_sahakron.putExtra("URL","sahakron");
                startActivity(intent_sahakron);
                mDrawerLayout.closeDrawers();
                break;
            case R.id.btnWebLink4:
                Intent intent_city = new Intent(getActivity(),HomepageActivity.class);
                intent_city.putExtra("URL","city");
                startActivity(intent_city);
                mDrawerLayout.closeDrawers();
                break;
        }
    }


    public static List<navItemInfo> getData(){
        List<navItemInfo> data = new ArrayList<>();
        int[] icons = {R.mipmap.ic_person_black_24dp,R.mipmap.ic_notifications_black_24dp,R.mipmap.ic_live_help_black_24dp,R.mipmap.ic_format_list_bulleted_black_24dp};
        String[] titles ={"Edit Profile", "Notification Settings","Feedback & Support","Terms & Privacy"};

        for(int i = 0;i<titles.length && i<icons.length;i++){
            navItemInfo info = new navItemInfo();
            info.setIconId(icons[i]);
            info.setTitle(titles[i]);
            data.add(info);
        }
        return data;
    }
    public void setUp(int fragmentId, DrawerLayout drawerLayout, Toolbar toolbar) {

        containerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(),drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close){

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if(!mUserLearnedDrawer){
                    mUserLearnedDrawer = true;
                    saveToPrefereces(getActivity(),KEY_USER_LEARN_DRAWER,mUserLearnedDrawer+"");
                }
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();

            }
        };
        if(!mUserLearnedDrawer && !mFromSaveInstanceState){
            mDrawerLayout.openDrawer(containerView);
        }
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
    }

    public void saveToPrefereces(Context context, String preferenceName, String preferenceValue){

        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME,context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(preferenceName,preferenceValue);
        editor.commit();
    }
    public static String readFromPreferences(Context context , String preferenceName,String defaultValue){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME,context.MODE_PRIVATE);
        return sharedPreferences.getString(preferenceName,defaultValue);
    }


//    public interface OnOpenLinkListener {
//       public void onOpenLlink(String url);
//    }
}
