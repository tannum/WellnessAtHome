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
import com.bjl.tannum.wellnessathome.R;

import org.w3c.dom.Text;

import java.util.Collections;
import java.util.List;

/**
 * Created by tannum on 1/15/2017 AD.
 */

public class BenefitsAdapter extends RecyclerView.Adapter<BenefitsAdapter.BenefitItemsHolder>{


    Context context;
    private LayoutInflater inflater;
    List<BenefitInfo> benefitInfos = Collections.emptyList();
    ClickListener clickListener;

    //Mask: Constructor
    public BenefitsAdapter(Context context, List<BenefitInfo> benefitInfos) {

        inflater = LayoutInflater.from(context);
        this.context = context;
        this.benefitInfos = benefitInfos;

    }

    //Mask: Interface
    public interface ClickListener{
        void onBenefitItemClicked(int position,View view);
    }
    public void SetOnBenefitItemClickListener(ClickListener clickListener){

        //Mask: Initial click listener.
        this.clickListener = clickListener;
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
        holder.header.setText(info.getHeader());
        //holder.content.setText(info.getContent());
    }

    @Override
    public int getItemCount() {
        return benefitInfos.size();
    }


    class BenefitItemsHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView thummail;
        TextView header;
        TextView content;
        TextView benefitTotal;
        TextView benefitRest;
        Button viewBenefit;



        public BenefitItemsHolder(View itemView) {
            super(itemView);

            thummail = (ImageView)itemView.findViewById(R.id.img_benefit);
            header = (TextView)itemView.findViewById(R.id.txtBenefitHeader);
            //content = (TextView)itemView.findViewById(R.id.txtBenefitContent);
            viewBenefit = (Button)itemView.findViewById(R.id.btnViewBenefit);
            benefitTotal = (TextView)itemView.findViewById(R.id.txtBenefitTotal);
            benefitRest = (TextView)itemView.findViewById(R.id.txtBenefitRest);
            viewBenefit.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onBenefitItemClicked(getAdapterPosition(),v);
        }
    }
}
