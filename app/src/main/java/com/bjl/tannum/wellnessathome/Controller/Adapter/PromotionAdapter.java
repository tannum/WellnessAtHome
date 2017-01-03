package com.bjl.tannum.wellnessathome.Controller.Adapter;

import android.content.Context;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bjl.tannum.wellnessathome.Model.promotionItemInfo;
import com.bjl.tannum.wellnessathome.R;

import java.util.Collections;
import java.util.List;

/**
 * Created by tannum on 1/3/2017 AD.
 */

public class PromotionAdapter extends RecyclerView.Adapter<PromotionAdapter.PromotionItemsHolder>{

    private LayoutInflater inflater;
    List<promotionItemInfo> promotionItemInfos = Collections.emptyList();
    Context context;

    public PromotionAdapter(Context context ,List<promotionItemInfo> promotionItemInfos) {
        inflater = LayoutInflater.from(context);
        this.promotionItemInfos = promotionItemInfos;
        this.context = context;
    }

    @Override
    public PromotionItemsHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.promotion_row,parent,false);
        PromotionItemsHolder holder = new PromotionItemsHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(PromotionItemsHolder holder, int position) {
        promotionItemInfo info = promotionItemInfos.get(position);

        holder.imgPromotion.setImageResource(info.getThumnailId());
        holder.textViewHeader.setText(info.getTxtPromotionHeader());
        holder.textViewHeaderContent.setText(info.getTxtPromotionHeaderContent());
        holder.textViewContent.setText(info.getTxtPromotionContent());


    }

    @Override
    public int getItemCount() {
        return promotionItemInfos.size();
    }

    class PromotionItemsHolder extends RecyclerView.ViewHolder{

        ImageView imgPromotion;
        TextView textViewHeader;
        TextView textViewHeaderContent;
        TextView textViewContent;

        public PromotionItemsHolder(View itemView) {
            super(itemView);

            imgPromotion = (ImageView)itemView.findViewById(R.id.image_promotion);
            textViewHeader = (TextView)itemView.findViewById(R.id.promotion_header);
            textViewHeaderContent = (TextView)itemView.findViewById(R.id.promotion_header_content);
            textViewContent = (TextView)itemView.findViewById(R.id.promotion_content);

        }
    }
}
