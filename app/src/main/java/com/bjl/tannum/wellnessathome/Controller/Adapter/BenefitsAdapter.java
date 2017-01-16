package com.bjl.tannum.wellnessathome.Controller.Adapter;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bjl.tannum.wellnessathome.Model.BenefitInfo;
import com.bjl.tannum.wellnessathome.Model.promotionItemInfo;
import com.bjl.tannum.wellnessathome.R;

import java.util.Collections;
import java.util.List;

/**
 * Created by tannum on 1/15/2017 AD.
 */

public class BenefitsAdapter extends RecyclerView.Adapter<BenefitsAdapter.BenefitItemsHolder>{


    Context context;
    private LayoutInflater inflater;
    List<BenefitInfo> benefitInfos = Collections.emptyList();


    public BenefitsAdapter(Context context, List<BenefitInfo> benefitInfos) {

        inflater = LayoutInflater.from(context);
        this.context = context;
        this.benefitInfos = benefitInfos;

    }


    @Override
    public BenefitItemsHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.benefit_row,parent,false);
        BenefitItemsHolder holder = new BenefitItemsHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(BenefitItemsHolder holder, int position) {

        BenefitInfo info = benefitInfos.get(position);
        holder.thummail.setImageResource(info.getThumbnailId());
        holder.description.setText(info.getDescription());
    }

    @Override
    public int getItemCount() {
        return benefitInfos.size();
    }


    class BenefitItemsHolder extends RecyclerView.ViewHolder{

        ImageView thummail;
        TextView description;
        Button viewBenefit;


        public BenefitItemsHolder(View itemView) {
            super(itemView);


            thummail = (ImageView)itemView.findViewById(R.id.img_benefit);
            description = (TextView)itemView.findViewById(R.id.txtBenefitDescription);
            viewBenefit = (Button)itemView.findViewById(R.id.btnViewBenefit);

        }

    }
}
