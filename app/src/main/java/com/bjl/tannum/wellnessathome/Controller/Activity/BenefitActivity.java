package com.bjl.tannum.wellnessathome.Controller.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.bjl.tannum.wellnessathome.Controller.Adapter.BenefitsAdapter;
import com.bjl.tannum.wellnessathome.Model.BenefitInfo;
import com.bjl.tannum.wellnessathome.R;

import java.util.ArrayList;
import java.util.List;

public class BenefitActivity extends AppCompatActivity implements BenefitsAdapter.ClickListener {


    List<BenefitInfo> benefitInfos;
    private RecyclerView recyclerView;
    private BenefitsAdapter benefitsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_benefit);


        //Mask: Make Dummy information.
        benefitInfos = new ArrayList<BenefitInfo>();
        benefitInfos.add(new BenefitInfo("Content1 FfffffffffffffffFfffffffffffffffFfffffffffffffffFfffffffffffffffFfffffffffffffffFfffffffffffffffFfffffffffffffffFfffffffffffffffFfffffffffffffffFfffffffffffffffFfffffffffffffffFfffffffffffffff","Header1",R.drawable.promotion1));
        benefitInfos.add(new BenefitInfo("Content2","Header2",R.drawable.promotion2));
        benefitInfos.add(new BenefitInfo("Content3","Header3",R.drawable.promotion3));
        benefitInfos.add(new BenefitInfo("Content4","Header4",R.drawable.promotion4));
        benefitInfos.add(new BenefitInfo("Content5","Header5",R.drawable.promotion5));


        //Mask: Initial RecyclerView
        recyclerView = (RecyclerView)findViewById(R.id.benefit_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        benefitsAdapter = new BenefitsAdapter(this,benefitInfos);
        benefitsAdapter.SetOnBenefitItemClickListener(this);
        recyclerView.setAdapter(benefitsAdapter);


    }

    @Override
    public void onBenefitItemClicked(int position, View view) {
        Log.d("debug","position = " + String.valueOf(position));
    }
}
