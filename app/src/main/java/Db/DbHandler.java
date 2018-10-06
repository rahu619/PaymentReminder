package Db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.android.gms.samples.vision.ocrreader.SingleSeries;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DbHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "billpayment.db";
    private static final String TABLE_REMINDER = "REMINDER";

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public DbHandler(Context ctxt, SQLiteDatabase.CursorFactory factory) {
        super(ctxt, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuilder _query = new StringBuilder();

        _query.append("CREATE TABLE " + TABLE_REMINDER);
        _query.append("(ID INTEGER PRIMARY KEY AUTOINCREMENT,BILL_DATE DATETIME,BILL_TITLE TEXT NULL,");
        _query.append("BILL_AMOUNT INT,BILL_CONTENT TEXT NULL,BILL_IS_DISMISSED INTEGER,BILL_CREATED_ON DATETIME NULL);");
         db.execSQL(_query.toString());

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REMINDER);
        onCreate(db);
    }

    public long addReminder(BillContent content){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("BILL_DATE",content.datetime.toString().replace("T", " "));
        values.put("BILL_TITLE",content.title);
        values.put("BILL_CONTENT",content.content);
        values.put("BILL_AMOUNT",content.amount);
        values.put("BILL_IS_DISMISSED",content.isread);
        values.put("BILL_CREATED_ON",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));


        long count = db.insert(TABLE_REMINDER, null, values);
        db.close();

        return count;
    }


    public List<BillContent> getReminders(){

        String query = String.format("SELECT * FROM %s ",TABLE_REMINDER);
        return executeQuery(query);

    }


    public List<SingleSeries> getReport(){
        List<SingleSeries> _singleSeries=new ArrayList<>();

        String query="";
        query += "SELECT ID, count(*) as Total,";
        query +="sum(case when BILL_IS_DISMISSED=0 then 1 else 0 end) DueCount,";
        query +="sum(case when BILL_IS_DISMISSED=1 then 1 else 0 end) PaidCount";
        query +=" FROM %s GROUP BY ID";

        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery(String.format(query,TABLE_REMINDER), null);
        c.moveToFirst();

        while (!c.isAfterLast()) {
            int total=c.getInt(c.getColumnIndex("Total"));
            int dueCount=c.getInt(c.getColumnIndex("DueCount"));
            int paidCount=c.getInt(c.getColumnIndex("PaidCount"));

            _singleSeries.add(new SingleSeries("Total",total));
            _singleSeries.add(new SingleSeries("Paid",paidCount));
            _singleSeries.add(new SingleSeries("Due",dueCount));
        }

        return _singleSeries;
    }


    private List<BillContent> executeQuery(String query){
        List<BillContent> _returnList=new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();

        while (!c.isAfterLast()) {
            if (c.getString(c.getColumnIndex("ID")) != null) {
                int _id = c.getInt(c.getColumnIndex("ID"));
                String _datetime = c.getString(c.getColumnIndex("BILL_DATE"));
                String _title = c.getString(c.getColumnIndex("BILL_TITLE"));
                String _content = c.getString(c.getColumnIndex("BILL_CONTENT"));
                int _amount = c.getInt(c.getColumnIndex("BILL_AMOUNT"));
                int _isread = c.getInt(c.getColumnIndex("BILL_IS_DISMISSED"));
                String _createdon = c.getString(c.getColumnIndex("BILL_CREATED_ON"));

                LocalDateTime _formatedDate=LocalDateTime.parse(_datetime, formatter);

                _returnList.add(new BillContent(_id,_formatedDate,_title,_content,_amount,_isread));

            }
            c.moveToNext();
        }
        db.close();
        return _returnList;

    }


}
