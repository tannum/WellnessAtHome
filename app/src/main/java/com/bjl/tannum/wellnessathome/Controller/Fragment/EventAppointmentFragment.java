package com.bjl.tannum.wellnessathome.Controller.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bjl.tannum.wellnessathome.Controller.Adapter.BookingAdapter;
import com.bjl.tannum.wellnessathome.Controller.Adapter.EventAdapter;
import com.bjl.tannum.wellnessathome.Model.AppointmentInfo;
import com.bjl.tannum.wellnessathome.Model.BookingInfo;
import com.bjl.tannum.wellnessathome.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventAppointmentFragment extends Fragment {

    private RecyclerView recyclerView;
    private EventAdapter eventAdapter;
    List<AppointmentInfo> appointmentInfos;

    public EventAppointmentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Mask: Init view
        View view = inflater.inflate(R.layout.fragment_event_appointment,container,false);

        //Mask: Init RecyclerView.
        appointmentInfos = new ArrayList<AppointmentInfo>();
        recyclerView = (RecyclerView)view.findViewById(R.id.event_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        eventAdapter = new EventAdapter(getContext(),appointmentInfos);
        recyclerView.setAdapter(eventAdapter);

//        //Mask: Init RecyclerView
//        bookingInfos = new ArrayList<BookingInfo>();
//        recyclerView = (RecyclerView)view.findViewById(R.id.available_list);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        bookingAdapter = new BookingAdapter(getContext(),bookingInfos);
//        bookingAdapter.SetOnBookingItemClickListener(this);
//        recyclerView.setAdapter(bookingAdapter);


//        bookingInfos.add(new BookingInfo(R.drawable.logo2,"office1\noffice1","19/01/2017","12.00-18.00"));
//        bookingInfos.add(new BookingInfo(R.drawable.logo2,"office1\noffice1","19/01/2017","12.00-18.00"));
//        bookingInfos.add(new BookingInfo(R.drawable.logo2,"office1\noffice1","19/01/2017","12.00-18.00"));
//        bookingInfos.add(new BookingInfo(R.drawable.logo2,"office1\noffice1","19/01/2017","12.00-18.00"));

        //Mask: Make dummy information
        appointmentInfos.add(new AppointmentInfo(R.drawable.logo4,"Jame Derinton","Meelek Sangtong","19-10-2030","18.00","Hostpital") );
        appointmentInfos.add(new AppointmentInfo(R.drawable.logo4,"Jame Derinton","Meelek Sangtong","19-10-2030","18.00","Hostpital") );
        appointmentInfos.add(new AppointmentInfo(R.drawable.logo4,"Jame Derinton","Meelek Sangtong","19-10-2030","18.00","Hostpital") );
        appointmentInfos.add(new AppointmentInfo(R.drawable.logo4,"Jame Derinton","Meelek Sangtong","19-10-2030","18.00","Hostpital") );
        appointmentInfos.add(new AppointmentInfo(R.drawable.logo4,"Jame Derinton","Meelek Sangtong","19-10-2030","18.00","Hostpital") );
        appointmentInfos.add(new AppointmentInfo(R.drawable.logo4,"Jame Derinton","Meelek Sangtong","19-10-2030","18.00","Hostpital") );

        eventAdapter.notifyDataSetChanged();

        return view;
    }

}
