package com.bjl.tannum.wellnessathome.Model;

/**
 * Created by Tannum on 19/01/17.
 */

public class AppointmentInfo {

    String patient_name;
    String doctor_name;
    String meting_date;
    String meting_time;
    String meting_place;
    int doctor_pic;


    public AppointmentInfo(int doctor_pic,String patient_name, String doctor_name, String meting_date, String meting_time, String meting_place) {
        this.doctor_pic = doctor_pic;
        this.patient_name = patient_name;
        this.doctor_name = doctor_name;
        this.meting_date = meting_date;
        this.meting_time = meting_time;
        this.meting_place = meting_place;
    }

    public int getDoctor_pic() {
        return doctor_pic;
    }

    public void setDoctor_pic(int doctor_pic) {
        this.doctor_pic = doctor_pic;
    }

    public String getPatient_name() {
        return patient_name;
    }

    public void setPatient_name(String patient_name) {
        this.patient_name = patient_name;
    }

    public String getDoctor_name() {
        return doctor_name;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }

    public String getMeting_date() {
        return meting_date;
    }

    public void setMeting_date(String meting_date) {
        this.meting_date = meting_date;
    }

    public String getMeting_time() {
        return meting_time;
    }

    public void setMeting_time(String meting_time) {
        this.meting_time = meting_time;
    }

    public String getMeting_place() {
        return meting_place;
    }

    public void setMeting_place(String meting_place) {
        this.meting_place = meting_place;
    }
}
