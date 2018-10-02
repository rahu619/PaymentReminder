package com.google.android.gms.samples.vision.ocrreader;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.text.InputType;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;

import Db.BillContent;
import Db.DbHandler;
import Db.IReminder;
import Db.Reminder;
import Notification.AlarmBroadcastReceiver;

public class DetailsActivity extends Activity implements DatePickerDialog.OnDateSetListener,TimePickerDialog.OnTimeSetListener,ToolbarFragment.OnFragmentInteractionListener {
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    EditText _date,_billamount,_billcontent;
    String date_time;
    int mHour;
    int mMinute;
    DbHandler db;

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        setContentView(R.layout.activity_details);
        datePickerDialog =new DatePickerDialog(this,DetailsActivity.this,mYear,mMonth,mDay);
        timePickerDialog= new TimePickerDialog(getApplicationContext(), this, 1, 0, false);

         initialize();
        _date.setInputType(InputType.TYPE_NULL); //for preventing the soft keyboard from getting launched

         db = new DbHandler(this, null);

    }

   private void initialize(){
       _date=findViewById(R.id.billdate);
       _billamount=findViewById(R.id.billamount);
       _billcontent=findViewById(R.id.billcontent);

   }

   private View.OnClickListener _commonClick= new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            hide();
        }
    };

    public void dateSelect(View v){
        datePickerDialog.show();
    }

    public void timeSelect(View v){
        timePickerDialog.show();
    }

    @Override //set selected date
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
      String _dateformat=String.format("%d-%d-%d", year,month+1,dayOfMonth);
      date_time=_dateformat;
      timePicker();
    }


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

    }

    private void hide(){
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
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
                        String _output=  String.format("%s %d:%d",date_time,mHour,mMinute);
                        _date.setText(_output);

                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }


    public boolean Validate(EditText et){
        return et != null && !et.getText().toString().isEmpty();
    }

    public void SaveDetails(View v){
        String _msg=null;
        if(Validate(_date) && Validate(_billamount) && Validate(_billcontent)){

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d H:m");

            LocalDateTime _billdate=LocalDateTime.parse(_date.getText().toString(),formatter);
            BillContent _content=new BillContent(0, _billdate,null,_billcontent.getText().toString(), Integer.valueOf(_billamount.getText().toString()),0);
            IReminder _reminder= new Reminder(getApplicationContext(),_content);


            if(_reminder.AddReminder()){
                _date.setText("");
                _billamount.setText("");
                _billcontent.setText("");

                Calendar c = Calendar.getInstance();
                c.add(Calendar.MINUTE, (int)ChronoUnit.MINUTES.between(LocalDateTime.now(),_billdate));
                setAlarm(c.getTimeInMillis());
                _msg="Saved";
            }
            else
                _msg="Uh oh!";
        }
        else
            _msg="Left out any field?";

            Toast.makeText(this,_msg,Toast.LENGTH_SHORT).show();
    }


    public void setAlarm(long time) {

        Intent intentToFire = new Intent(getApplicationContext(), AlarmBroadcastReceiver.class);
        intentToFire.setAction(AlarmBroadcastReceiver.ACTION_ALARM);

        PendingIntent alarmIntent = PendingIntent.getBroadcast(getApplicationContext(),0, intentToFire, 0);
        AlarmManager alarmManager = (AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, time, alarmIntent);
    }




}
