package com.google.android.gms.samples.vision.ocrreader;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.text.InputType;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class Details extends Activity implements DatePickerDialog.OnDateSetListener,ToolbarFragment.OnFragmentInteractionListener {
    DatePickerDialog datePickerDialog;
    EditText _date;

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        datePickerDialog =new DatePickerDialog(this,Details.this,2018,1,1);

        _date=findViewById(R.id.billdate);
        _date.setInputType(InputType.TYPE_NULL); //for preventing the soft keyboard from getting launched
    }



    public void dateSelect(View v){
            datePickerDialog.show();
    }


    @Override //set selected date
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
      String _dateformat=String.format("%d-%d-%d", dayOfMonth,month,year);
      _date.setText(_dateformat);
    }
}
