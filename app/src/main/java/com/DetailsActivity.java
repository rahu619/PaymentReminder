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
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
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

public class DetailsActivity extends Fragment implements DatePickerDialog.OnDateSetListener,TimePickerDialog.OnTimeSetListener, View.OnClickListener {
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    EditText _date,_billamount,_billcontent, _billTitle;
    ImageView _imgcategory;
    Button _submit;
    String date_time;
    int mHour;
    int mMinute;
    DbHandler db;


    public DetailsActivity() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        datePickerDialog =new DatePickerDialog(getContext(),DetailsActivity.this,mYear,mMonth,mDay);
        timePickerDialog= new TimePickerDialog(getContext().getApplicationContext(), this, 1, 0, false);

        View rootView = inflater.inflate(R.layout.activity_details, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialize();
        setOnClickListeners();
        _date.setInputType(InputType.TYPE_NULL); //for preventing the soft keyboard from getting launched
        db = new DbHandler(getContext(), null);
    }


    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }


    private void initialize(){
        _date = getView().findViewById(R.id.billdate);
        _billTitle = getView().findViewById(R.id.billtitle);
        _billamount = getView().findViewById(R.id.billamount);
        _billcontent = getView().findViewById(R.id.billcontent);
        _submit = getView().findViewById(R.id.submit);
        _imgcategory=getView().findViewById(R.id.imgcategory);

        final String[] data= {"Electricity","Shopping","Car","Rent"};
        final Spinner spinner = (Spinner) getView().findViewById(R.id.spinnerCategory);
        spinner.setPrompt("Category");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,data);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                _billTitle.setText(data[position]);

                switch(position){
                    case 0:_imgcategory.setBackgroundResource(R.drawable.category_bulb);break;
                    case 1:_imgcategory.setBackgroundResource(R.drawable.category_cart);break;
                    case 2:_imgcategory.setBackgroundResource(R.drawable.category_car);break;
                    case 3:_imgcategory.setBackgroundResource(R.drawable.category_rent);break;

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        _billTitle.setText("");

    }


    public void SetValue(String _value){
        _billcontent.setText(_value);
    }

    private void setOnClickListeners() {
        _submit.setOnClickListener(this);
        _date.setOnClickListener(this);
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
        this.getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private void timePicker(){
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
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

            int _billamt=Integer.valueOf(_billamount.getText().toString());
            String _billctnt=_billcontent.getText().toString();
            String _billtitle=_billTitle.getText().toString();

            LocalDateTime _billdate=LocalDateTime.parse(_date.getText().toString(),formatter);
            BillContent _content=new BillContent(0, _billdate,_billtitle,_billctnt,_billamt ,0,0);
            IReminder _reminder= new Reminder(getContext().getApplicationContext(),_content);


            if(_reminder.AddReminder()){
                _date.setText("");
                _billamount.setText("");
                _billcontent.setText("");

                Calendar c = Calendar.getInstance();
                c.add(Calendar.MINUTE, (int)ChronoUnit.MINUTES.between(LocalDateTime.now(),_billdate));
                setAlarm(_billtitle,_billamt,_billctnt,c.getTimeInMillis());
                _msg="Saved";
            }
            else
                _msg="Uh oh!";
        }
        else
            _msg="Left out any field?";

            Toast.makeText(getActivity(),_msg,Toast.LENGTH_SHORT).show();
    }

    public void setAlarm(String title,int amount,String content,long time) {

        Intent intentToFire = new Intent(getContext().getApplicationContext(), AlarmBroadcastReceiver.class);
        intentToFire.putExtra("Title",title);
        intentToFire.putExtra("Amount",amount);
        intentToFire.putExtra("Content",content);
        intentToFire.setAction(AlarmBroadcastReceiver.ACTION_ALARM);

        PendingIntent alarmIntent = PendingIntent.getBroadcast(getContext().getApplicationContext(),0, intentToFire, 0);
        AlarmManager alarmManager = (AlarmManager)getContext().getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, time, alarmIntent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit:
                SaveDetails(v);
                break;
            case R.id.billdate:
                dateSelect(v);
        }
    }
}
