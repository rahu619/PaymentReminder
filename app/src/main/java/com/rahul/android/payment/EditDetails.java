package com.rahul.android.payment;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import Db.BillContent;
import Db.IReminder;
import Db.Reminder;

public class EditDetails extends Activity {

    int _billID;
    EditText _billdate,_billamount,_billcontent, _billTitle;
    CheckBox _billpaid;
    boolean _isChecked;
    ImageView _ivback;
    Button _submit;
    IReminder _reminder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_details);

        _billdate = findViewById(R.id.billdate);
        _billTitle = findViewById(R.id.billtitle);
        _billcontent=findViewById(R.id.billcontent);
        _billamount = findViewById(R.id.billamount);
        _ivback =findViewById(R.id.ivback);
        _billpaid=findViewById(R.id.chkpaid);

        LoadContent();

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    public void NavigateBack(View v){
        finish();
    }

    private void LoadContent(){
        Intent _intent = getIntent();

        _billID=_intent.getIntExtra("BILL_ID",0);
        _billdate.setText(_intent.getStringExtra("BILL_DATE").replace("T"," "));
        _billTitle.setText(_intent.getStringExtra("BILL_TITLE"));
        _billamount.setText(_intent.getStringExtra("BILL_AMOUNT"));
        _billcontent.setText(_intent.getStringExtra("BILL_CONTENT"));
        _isChecked =_intent.getBooleanExtra("BILL_PAID",false);

        if(_isChecked)
            _billpaid.setChecked(true);

    }

    public void Save(View v){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d H:m");
        LocalDateTime _date=LocalDateTime.parse(_billdate.getText().toString(),formatter);

        String _title=_billTitle.getText().toString();
        int _amount= Integer.valueOf(_billamount.getText().toString());
        int _isPaid=_billpaid.isChecked()?1:0;
        String _content=_billcontent.getText().toString();

        BillContent _cntnt=new BillContent(_billID,_date,_title,_content,_amount,0,_isPaid);

        _reminder =new Reminder(getApplicationContext(),_cntnt);
        boolean _status= _reminder.UpdateReminder();

        if(_status)
            Toast.makeText(this,"Updated successfully!",Toast.LENGTH_LONG).show();



    }

    public void Delete(View v){
        BillContent _content=new BillContent(_billID);
        _reminder =new Reminder(getApplicationContext(),_content);
        _reminder.DeleteReminder();
    }

}
