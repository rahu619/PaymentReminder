package com.rahul.android.payment;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import Db.BillContent;
import Db.IReminder;
import Db.Reminder;
import Notification.AlarmBroadcastReceiver;

public class HomeActivity extends Fragment {

    private static int SCREEN_HEIGHT;
    private static int SCREEN_WIDTH;
    TableLayout billsTable;
    IReminder _reminder;

    String[] _categories={"All","Paid","Due"};

    public HomeActivity() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        _reminder =new Reminder(getContext().getApplicationContext(),null);

        super.onCreate(savedInstanceState);
        getScreenDimension();
        View rootView = inflater.inflate(R.layout.activity_home, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        billsTable = getView().findViewById(R.id.billsTable);

        SpinnerInitialize();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

    }

    private void SpinnerInitialize(){
        Spinner spin = getView().findViewById(R.id.billCategory);
        ArrayAdapter aa = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_dropdown_item,_categories);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);

        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
                ((TextView) parent.getChildAt(0)).setTextSize(20);

                selectReminders(_categories[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public void onResume()
    {
        super.onResume();
        selectReminders("");

    }

    void selectReminders(String value){
       billsTable.removeAllViews();
       List<BillContent> _content = _reminder.GetReminders(value);

       for(BillContent _cntnt: _content){
           addBillInfoRowToBillTable(_cntnt.id,_cntnt.datetime,_cntnt.title,_cntnt.content,_cntnt.amount,_cntnt.ispaid);
        }
    }




    private void addBillInfoRowToBillTable(int billID,LocalDateTime billDate,String billTitle,String billInfo,int billAmount,int billPaid){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM, HH:mm");

        TableRow tableRow= new TableRow(getContext());

        tableRow = (TableRow)LayoutInflater.from(getContext()).inflate(R.layout.table_row, null);
        ((TextView)tableRow.findViewById(R.id.title)).setText(billInfo);
        ((TextView)tableRow.findViewById(R.id.date)).setText(billDate.format(formatter));
        ((TextView)tableRow.findViewById(R.id.amount)).setText(String.valueOf(billAmount)+" NZD");

        tableRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getContext(), EditDetails.class);

                in.putExtra("BILL_ID",billID);
                in.putExtra("BILL_DATE", String.valueOf(billDate));
                in.putExtra("BILL_TITLE", billTitle);
                in.putExtra("BILL_CONTENT",billInfo);
                in.putExtra("BILL_AMOUNT", String.valueOf(billAmount));
                in.putExtra("BILL_PAID",billPaid>0);
                startActivity(in);
            }
        });

        int _array= billPaid==1?R.array.paidcolors:R.array.duecolors;
        int[] _randomColors = getResources().getIntArray(_array);
        int _rowColor = _randomColors[new Random().nextInt(_randomColors.length)];

        tableRow.setBackgroundColor(_rowColor);
        tableRow.setPadding(1,0,1,2);


        billsTable.addView(tableRow);
    }

    private void getScreenDimension(){
        WindowManager wm= (WindowManager) getContext().getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        SCREEN_WIDTH= size.x;
        SCREEN_HEIGHT = size.y;
    }

}
