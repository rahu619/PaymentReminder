package com.google.android.gms.samples.vision.ocrreader;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.view.Display;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Random;

public class HomeActivity extends Activity implements ToolbarFragment.OnFragmentInteractionListener {

    private static int SCREEN_HEIGHT;
    private static int SCREEN_WIDTH;
    TableLayout billsTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getScreenDimension();
        billsTable = findViewById(R.id.billsTable);
        addBillInfoRowToBillTable("Bill 1");
        addBillInfoRowToBillTable("Bill 2");
        addBillInfoRowToBillTable("Bill 3");
    }

    private void addBillInfoRowToBillTable(String billInfo){
        TableRow tableRow= new TableRow(getApplicationContext());
        TextView billLabel = new TextView(getApplicationContext());
        billLabel.setText(billInfo);
        billLabel.setTextColor(Color.WHITE);
        tableRow.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL);
        tableRow.addView(billLabel);
        tableRow.setPadding(20,20,20,20);
        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        tableRow.setBackgroundColor(color);
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
