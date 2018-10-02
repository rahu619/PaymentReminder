package com.google.android.gms.samples.vision.ocrreader;

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
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import Db.BillContent;
import Db.IReminder;
import Db.Reminder;
import Notification.AlarmBroadcastReceiver;

public class HomeActivity extends Activity implements ToolbarFragment.OnFragmentInteractionListener {

    private static int SCREEN_HEIGHT;
    private static int SCREEN_WIDTH;
    TableLayout billsTable;
    IReminder _reminder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        _reminder =new Reminder(getApplicationContext(),null);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getScreenDimension();
        billsTable = findViewById(R.id.billsTable);

//        dummyData();

        selectReminders();
    }


//    void dummyData(){
//        addBillInfoRowToBillTable("Bill 1");
//        addBillInfoRowToBillTable("Bill 2");
//        addBillInfoRowToBillTable("Bill 3");
//        addBillInfoRowToBillTable("Bill 1");
//        addBillInfoRowToBillTable("Bill 2");
//        addBillInfoRowToBillTable("Bill 3");
//        addBillInfoRowToBillTable("Bill 1");
//        addBillInfoRowToBillTable("Bill 2");
//        addBillInfoRowToBillTable("Bill 3");
//        addBillInfoRowToBillTable("Bill 1");
//        addBillInfoRowToBillTable("Bill 2");
//        addBillInfoRowToBillTable("Bill 3");
//        addBillInfoRowToBillTable("Bill 1");
//        addBillInfoRowToBillTable("Bill 2");
//        addBillInfoRowToBillTable("Bill 3");
//        addBillInfoRowToBillTable("Bill 1");
//        addBillInfoRowToBillTable("Bill 2");
//        addBillInfoRowToBillTable("Bill 3");
//
//        addBillInfoRowToBillTable("Bill 3");
//        addBillInfoRowToBillTable("Bill 1");
//        addBillInfoRowToBillTable("Bill 2");
//        addBillInfoRowToBillTable("Bill 3");
//        addBillInfoRowToBillTable("Bill 3");
//        addBillInfoRowToBillTable("Bill 1");
//        addBillInfoRowToBillTable("Bill 2");
//        addBillInfoRowToBillTable("Bill 3");
//        addBillInfoRowToBillTable("Bill 1");
//        addBillInfoRowToBillTable("Bill 2");
//        addBillInfoRowToBillTable("Bill 3");
//        addBillInfoRowToBillTable("Bill 1");
//        addBillInfoRowToBillTable("Bill 2");
//        addBillInfoRowToBillTable("Bill 3");
//
//    }

    void selectReminders(){
       List<BillContent> _content = _reminder.GetReminders();
       for(BillContent _cntnt: _content){
           addBillInfoRowToBillTable(_cntnt.datetime,_cntnt.content);
        }
    }


    private void addBillInfoRowToBillTable(LocalDateTime billDate, String billInfo){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd-MMM");

        TableRow tableRow= new TableRow(this);
        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
        tableRow.setLayoutParams(lp);

        TextView _billDate=new TextView(this);
        _billDate.setGravity(Gravity.LEFT);
        _billDate.setPadding(0,0,50,0);

        TextView _billContent = new TextView(this);

        _billDate.setText(billDate.format(formatter));
        _billDate.setTextColor(Color.BLACK);

        _billContent.setText(billInfo);
        _billContent.setGravity(Gravity.RIGHT);

        _billContent.setTypeface(null, Typeface.BOLD);
        _billContent.setTextColor(Color.WHITE);
        tableRow.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL);

        tableRow.addView(_billDate);
        tableRow.addView(_billContent);
        tableRow.setMinimumHeight(50);
        tableRow.setPadding(20,20,20,20);

        int[] _randomColors = getResources().getIntArray(R.array.tablecolors);
        int _rowColor = _randomColors[new Random().nextInt(_randomColors.length)];

//        Random rnd = new Random();
//        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        tableRow.setBackgroundColor(_rowColor);
        billsTable.addView(tableRow);
    }

    private void getScreenDimension(){
        WindowManager wm= (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        SCREEN_WIDTH= size.x;
        SCREEN_HEIGHT = size.y;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


}
