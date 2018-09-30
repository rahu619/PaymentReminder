package com.google.android.gms.samples.vision.ocrreader;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.text.InputType;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

public class DetailsActivity extends Activity implements DatePickerDialog.OnDateSetListener,TimePickerDialog.OnTimeSetListener,ToolbarFragment.OnFragmentInteractionListener {
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    Calendar myCalendar;
    EditText _date,_time;
    String date_time;
    int mHour;
    int mMinute;

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        datePickerDialog =new DatePickerDialog(this,DetailsActivity.this,2018,1,1);

//        int hour = myCalendar.get(Calendar.HOUR_OF_DAY);
//        int minute = myCalendar.get(Calendar.MINUTE);
        timePickerDialog= new TimePickerDialog(getApplicationContext(), this, 1, 0, false);


        _date=findViewById(R.id.billdate);
        _time=findViewById(R.id.billtime);
        _date.setInputType(InputType.TYPE_NULL); //for preventing the soft keyboard from getting launched
        _time.setInputType(InputType.TYPE_NULL);
    }



    public void dateSelect(View v){
        datePickerDialog.show();
    }

    public void timeSelect(View v){
        timePickerDialog.show();
    }

    @Override //set selected date
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
      String _dateformat=String.format("%d-%d-%d", year,month,dayOfMonth);
      date_time=_dateformat;
      timePicker();
      //_date.setText(_dateformat);
    }


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        _time.setText(hourOfDay+":"+minute);
    }



    private void timePicker(){
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,int minute) {

                        mHour = hourOfDay;
                        mMinute = minute;

                        _time.setText(date_time+" "+hourOfDay + ":" + minute);
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }
}
