package com.bjl.tannum.wellnessathome.Controller.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bjl.tannum.wellnessathome.Model.AppointmentInfo;
import com.bjl.tannum.wellnessathome.R;

import org.w3c.dom.Text;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by Tannum on 19/01/17.
 */

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventItemsHolder> {

    Context context;
    private List<AppointmentInfo> appointmentInfos = Collections.emptyList();
    private LayoutInflater inflater;

    public EventAdapter(Context context, List<AppointmentInfo> appointmentInfos) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.appointmentInfos = appointmentInfos;
    }

    @Override
    public EventItemsHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.appointment_row,parent,false);
        EventItemsHolder holder = new EventItemsHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(EventItemsHolder holder, int position) {

        AppointmentInfo info = appointmentInfos.get(position);
        holder.doctor_pic.setImageResource(info.getDoctor_pic());
        holder.doctor_name.setText(info.getDoctor_name());
        holder.appointment_date.setText(info.getMeting_date());
        holder.appointment_time.setText(info.getMeting_time());
        holder.appointment_place.setText(info.getMeting_place());
    }

    @Override
    public int getItemCount() {
        return appointmentInfos.size();
    }

    class EventItemsHolder extends RecyclerView.ViewHolder {

        ImageView doctor_pic;
        TextView appointment_date;
        TextView appointment_time;
        TextView appointment_place;
        TextView doctor_name;

        public EventItemsHolder(View itemView) {
            super(itemView);

            doctor_pic = (ImageView)itemView.findViewById(R.id.imgAppointmentOfficer);
            appointment_date = (TextView)itemView.findViewById(R.id.txtAppointmentDate);
            appointment_time = (TextView)itemView.findViewById(R.id.txtAppointmentTime);
            appointment_place = (TextView)itemView.findViewById(R.id.txtAppointmentLocation);
            doctor_name = (TextView)itemView.findViewById(R.id.txtAppointmentOfficeName);
        }
    }
}
