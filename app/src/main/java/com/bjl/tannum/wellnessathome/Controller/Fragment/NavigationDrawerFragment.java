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
import android.widget.Button;
import android.widget.TextView;

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
        btnLogout.setOnClickListener(this);

        //Initial RecycleView
        recyclerView = (RecyclerView)layout.findViewById(R.id.drawerList);
        adapter = new NavRecyclerviewAdapter(getActivity(),getData());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return layout;
    }


    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.btnLogout){
            Log.d("debug","Logout button clicked");
            mDrawerLayout.closeDrawers();
            Intent intent = new Intent(getContext(), LoginActivity.class);
            startActivity(intent);
            getActivity().finish();
        }
    }


    public static List<navItemInfo> getData(){
        List<navItemInfo> data = new ArrayList<>();
        int[] icons = {R.mipmap.ic_person_black_48dp,R.mipmap.ic_notifications_black_48dp,R.mipmap.ic_help_black_48dp,R.mipmap.ic_receipt_black_48dp};
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


}
