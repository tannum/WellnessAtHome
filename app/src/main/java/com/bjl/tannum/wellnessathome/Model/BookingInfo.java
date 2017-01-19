package com.bjl.tannum.wellnessathome.Model;

/**
 * Created by Tannum on 19/01/17.
 */

public class BookingInfo {
    int office_image_id;
    String officer_name;
    String reserved_date;
    String reserved_time;

    public BookingInfo(int office_image_id,String officer_name, String reserved_date, String reserved_time) {
        this.office_image_id = office_image_id;
        this.officer_name = officer_name;
        this.reserved_date = reserved_date;
        this.reserved_time = reserved_time;
    }

    public int getOffice_image_id() {
        return office_image_id;
    }

    public void setOffice_image_id(int office_image_id) {
        this.office_image_id = office_image_id;
    }

    public String getOfficer_name() {
        return officer_name;
    }

    public void setOfficer_name(String officer_name) {
        this.officer_name = officer_name;
    }

    public String getReserved_date() {
        return reserved_date;
    }

    public void setReserved_date(String reserved_date) {
        this.reserved_date = reserved_date;
    }

    public String getReserved_time() {
        return reserved_time;
    }

    public void setReserved_time(String reserved_time) {
        this.reserved_time = reserved_time;
    }
}
