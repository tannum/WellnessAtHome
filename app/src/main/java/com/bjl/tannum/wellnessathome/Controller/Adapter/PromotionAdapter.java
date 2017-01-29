package com.bjl.tannum.wellnessathome.Controller.Adapter;

import android.content.Context;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bjl.tannum.wellnessathome.Model.PromotionInfo;
import com.bjl.tannum.wellnessathome.Model.promotionItemInfo;
import com.bjl.tannum.wellnessathome.R;

import java.util.Collections;
import java.util.List;

/**
 * Created by tannum on 1/3/2017 AD.
 */

public class PromotionAdapter extends RecyclerView.Adapter<PromotionAdapter.PromotionItemsHolder>{

    private LayoutInflater inflater;
    List<PromotionInfo> promotionItemInfos = Collections.emptyList();
    Context context;
    ClickListener clickListener;

    public PromotionAdapter(Context context ,List<PromotionInfo> promotionItemInfos) {
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
        PromotionInfo info = promotionItemInfos.get(position);

        holder.thumbnail.setImageResource(info.getThumbnailId());
        holder.txtPromotionHeader.setText(info.getHeader());
        holder.txtPromotionContent.setText(info.getContent());
    }

    @Override
    public int getItemCount() {
        return promotionItemInfos.size();
    }

    public interface ClickListener{
        void onBookingPromotionItemClicked(int position,View view);
    }

    public void SetOnBookingPromotionItemClickListener(ClickListener clickListener){
        this.clickListener = clickListener;
    }


    class PromotionItemsHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView thumbnail;
        TextView txtPromotionHeader;
        TextView txtPromotionContent;
        Button btnBookingPromotion;

        public PromotionItemsHolder(View itemView) {
            super(itemView);

            thumbnail = (ImageView)itemView.findViewById(R.id.image_promotion);
            txtPromotionHeader = (TextView)itemView.findViewById(R.id.promotion_header_content);
            txtPromotionContent = (TextView)itemView.findViewById(R.id.promotion_content);
            btnBookingPromotion = (Button)itemView.findViewById(R.id.btnBookingPromotion);
            btnBookingPromotion.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onBookingPromotionItemClicked(getAdapterPosition(),v);
        }
    }
}
