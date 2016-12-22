package com.bjl.tannum.wellnessathome.Controller.Fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bjl.tannum.wellnessathome.Controller.Adapter.NavRecyclerviewAdapter;
import com.bjl.tannum.wellnessathome.Model.navItemInfo;
import com.bjl.tannum.wellnessathome.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationDrawerFragment extends Fragment {


    public static final String PREF_FILE_NAME = "testpref";
    public static final String KEY_USER_LEARN_DRAWER = "user_learned_drawer";
    private RecyclerView recyclerView;
    private NavRecyclerviewAdapter adapter;
    private TextView txtLogout;



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
        txtLogout = (TextView)layout.findViewById(R.id.txtMenuLogout);

        //Initial RecycleView
        recyclerView = (RecyclerView)layout.findViewById(R.id.drawerList);
        adapter = new NavRecyclerviewAdapter(getActivity(),getData());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return layout;
    }

    public static List<navItemInfo> getData(){
        List<navItemInfo> data = new ArrayList<>();
        int[] icons = {R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher,};
        String[] titles ={"Test1", "Test2","Test3","Test4"};

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
