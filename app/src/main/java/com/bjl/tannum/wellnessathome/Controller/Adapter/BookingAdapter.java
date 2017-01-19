package com.bjl.tannum.wellnessathome.Controller.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bjl.tannum.wellnessathome.Model.BookingInfo;
import com.bjl.tannum.wellnessathome.R;

import org.w3c.dom.Text;

import java.util.Collections;
import java.util.List;

/**
 * Created by Tannum on 19/01/17.
 */

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.BookingItemsHolder>{


    Context context;
    private LayoutInflater inflater;
    List<BookingInfo> bookingInfos = Collections.emptyList();
    ClickListener clickListener;


    //Mask: Constructor
    public BookingAdapter(Context context, List<BookingInfo> bookingInfos) {

        inflater = LayoutInflater.from(context);
        this.context = context;
        this.bookingInfos = bookingInfos;

    }


    //Mask: Interface
    public interface ClickListener{
        void onBookingItemClicked(int position,View view);
    }
    public void SetOnBookingItemClickListener(ClickListener clickListener){

        //Mask: Initial click listener.
        this.clickListener = clickListener;
    }

    @Override
    public BookingItemsHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.available_row,parent,false);
        BookingItemsHolder holder = new BookingItemsHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(BookingItemsHolder holder, int position) {

        BookingInfo info = bookingInfos.get(position);
        holder.thumbnail.setImageResource(info.getOffice_image_id());
        holder.office_name.setText(info.getOfficer_name());
        holder.reserved_time.setText(info.getReserved_time());
    }

    @Override
    public int getItemCount() {
        return bookingInfos.size();
    }


    class BookingItemsHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView thumbnail;
        TextView office_name;
        TextView reserved_time;
        Button btnBooking;

        public BookingItemsHolder(View itemView) {
            super(itemView);

            thumbnail = (ImageView)itemView.findViewById(R.id.officer_pic);
            office_name = (TextView)itemView.findViewById(R.id.officer_name);
            reserved_time = (TextView)itemView.findViewById(R.id.txtAvailable_time);
            btnBooking = (Button)itemView.findViewById(R.id.btnBooking);
            btnBooking.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {
            clickListener.onBookingItemClicked(getAdapterPosition(),view);
        }
    }
}
