package com.bjl.tannum.wellnessathome.Controller.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bjl.tannum.wellnessathome.R;
import com.philliphsu.bottomsheetpickers.date.BottomSheetDatePickerDialog;
import com.philliphsu.bottomsheetpickers.date.DatePickerDialog;
import com.philliphsu.bottomsheetpickers.time.BottomSheetTimePickerDialog;
import com.philliphsu.bottomsheetpickers.time.grid.GridTimePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddEventActivity extends AppCompatActivity
        implements View.OnClickListener ,
        DatePickerDialog.OnDateSetListener,
        BottomSheetTimePickerDialog.OnTimeSetListener{
    Button btnAddBookingDate;
    Button btnAddBookingTime;
    Button btnCancelBooking;
    Button btnSaveBooking;
    EditText editTextAddTitle;
    RadioGroup radReminderGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        //Blinding layout view
        btnAddBookingDate = (Button)findViewById(R.id.btnBookingDate);
        btnAddBookingTime = (Button)findViewById(R.id.btnBookingTime);
        btnCancelBooking = (Button)findViewById(R.id.btnCancelBooking);
        btnSaveBooking = (Button)findViewById(R.id.btnSaveBooking);
        editTextAddTitle = (EditText)findViewById(R.id.event_title);
        radReminderGroup = (RadioGroup)findViewById(R.id.reminderRadioGroup);

        //Implement Event Listener
        btnAddBookingDate.setOnClickListener(this);
        btnAddBookingTime.setOnClickListener(this);
        btnCancelBooking.setOnClickListener(this);
        btnSaveBooking.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v == btnAddBookingDate){
            Calendar now = Calendar.getInstance();
            BottomSheetDatePickerDialog date = BottomSheetDatePickerDialog.newInstance(
                    AddEventActivity.this,
                    now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH),
                    now.get(Calendar.DAY_OF_MONTH));
            date.setThemeDark(false);
            date.show(getSupportFragmentManager(), "AddEventActivity");
        }
        else if(v == btnAddBookingTime){
            Calendar now = Calendar.getInstance();
            GridTimePickerDialog dialog = GridTimePickerDialog.newInstance(
                    this,
                    now.get(Calendar.HOUR_OF_DAY),
                    now.get(Calendar.MINUTE),
                    DateFormat.is24HourFormat(this));
            dialog.setThemeDark(false);
            dialog.show(getSupportFragmentManager(), "AddEventActivity");
        }
        else if(v == btnCancelBooking){
            finish();
        }
        else if(v == btnSaveBooking){
            String title = editTextAddTitle.getText().toString();
            String booking_time = btnAddBookingTime.getText().toString();
            int reminderId = radReminderGroup.getCheckedRadioButtonId();
            Intent intent = new Intent();
            intent.putExtra("keyBookingTitle",title);
            intent.putExtra("keyBookingTime",booking_time);
            intent.putExtra("keyReminderId",reminderId);
            setResult(RESULT_OK,intent);
            finish();
        }
    }


    @Override
    public void onDateSet(DatePickerDialog dialog, int year, int monthOfYear, int dayOfMonth) {

        Calendar cal = Calendar.getInstance(Locale.getDefault());
        cal.set(year,monthOfYear,dayOfMonth);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyy");
        String now = sdf.format(cal.getTime());
        btnAddBookingDate.setText(now);


    }

    @Override
    public void onTimeSet(ViewGroup viewGroup, int hourOfDay, int minute) {
        Calendar cal = new java.util.GregorianCalendar();
        cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
        cal.set(Calendar.MINUTE, minute);
        btnAddBookingTime.setText(DateFormat.getTimeFormat(this).format(cal.getTime()));
        Log.d("debug","Time set: " + DateFormat.getTimeFormat(this).format(cal.getTime()));

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
