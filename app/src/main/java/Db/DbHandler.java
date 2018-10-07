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

    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "billpayment.db";
    private static final String TABLE_REMINDER = "REMINDER";

    private static final String COLUMN_ID = "BILL_ID";
    private static final String COLUMN_DATE = "BILL_DATE";
    private static final String COLUMN_TITLE = "BILL_TITLE";
    private static final String COLUMN_AMOUNT = "BILL_AMOUNT";
    private static final String COLUMN_CONTENT = "BILL_CONTENT";
    private static final String COLUMN_DISMISSED = "BILL_IS_DISMISSED";
    private static final String COLUMN_PAID = "BILL_IS_PAID";
    private static final String COLUMN_CREATED = "BILL_CREATED_ON";


    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public DbHandler(Context ctxt, SQLiteDatabase.CursorFactory factory) {
        super(ctxt, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuilder _query = new StringBuilder();

        _query.append("CREATE TABLE %s ");
        _query.append("(%s INTEGER PRIMARY KEY AUTOINCREMENT,");
        _query.append("%s DATETIME,");
        _query.append("%s TEXT NULL,");
        _query.append("%s INT,");
        _query.append("%s TEXT NULL,");
        _query.append("%s INTEGER DEFAULT 0,");
        _query.append("%s INTEGER DEFAULT 0,");
        _query.append("%s DATETIME NULL);");

        String _createQuery= String.format(_query.toString(),
                                            TABLE_REMINDER,COLUMN_ID,
                                            COLUMN_DATE,COLUMN_TITLE,COLUMN_AMOUNT,
                                            COLUMN_CONTENT,COLUMN_DISMISSED,COLUMN_PAID,
                                            COLUMN_CREATED);

         db.execSQL(_createQuery);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REMINDER);
        onCreate(db);
    }


    private ContentValues getContentValues(BillContent content){

        ContentValues values = new ContentValues();
        values.put(COLUMN_DATE,content.datetime.toString().replace("T", " "));
        values.put(COLUMN_TITLE,content.title);
        values.put(COLUMN_CONTENT,content.content);
        values.put(COLUMN_AMOUNT,content.amount);
        values.put(COLUMN_PAID,content.ispaid);
        values.put(COLUMN_DISMISSED,content.isread);
        values.put(COLUMN_CREATED,new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

        return values;
    }

    public long addReminder(BillContent content){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values=getContentValues(content);

        long count = db.insert(TABLE_REMINDER, null, values);
        db.close();

        return count;
    }

    public int updateReminder(BillContent content){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values=getContentValues(content);

        return db.update(TABLE_REMINDER,values,String.format("%s = ?",COLUMN_ID),new String[]{String.valueOf(content.id)});
    }

    public int deleteReminder(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_REMINDER,String.format("%s = ?",COLUMN_ID),new String[]{String.valueOf(id)});
    }

    public List<BillContent> getReminders(){

        String query = String.format("SELECT * FROM %s ",TABLE_REMINDER);
        return executeQuery(query);

    }


    public List<SingleSeries> getReport(){
        List<SingleSeries> _singleSeries=new ArrayList<>();

        String query="";
        query += "SELECT %s,";
        query +="sum(case when %s=0 then 1 else 0 end) DueCount,";
        query +="sum(case when %s=1 then 1 else 0 end) PaidCount";
        query +=" FROM %s GROUP BY %s";

        SQLiteDatabase db = getWritableDatabase();
        final Cursor c = db.rawQuery(String.format(query,COLUMN_ID,COLUMN_PAID,COLUMN_PAID,TABLE_REMINDER,COLUMN_ID), null);

        if(c!=null){
            try{
                if(c.moveToFirst()){
                    //int total=c.getInt(c.getColumnIndex("Total"));
                    int dueCount=c.getInt(c.getColumnIndex("DueCount"));
                    int paidCount=c.getInt(c.getColumnIndex("PaidCount"));

                   // _singleSeries.add(new SingleSeries("Total",total));
                    _singleSeries.add(new SingleSeries("Paid",paidCount));
                    _singleSeries.add(new SingleSeries("Due",dueCount));
                }
            }
            finally {
                c.close();

            }
        }
        db.close();

        return _singleSeries;
    }


    private List<BillContent> executeQuery(String query){
        List<BillContent> _returnList=new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();

        while (!c.isAfterLast()) {
            if (c.getString(c.getColumnIndex(COLUMN_ID)) != null) {
                int _id = c.getInt(c.getColumnIndex(COLUMN_ID));
                String _datetime = c.getString(c.getColumnIndex(COLUMN_DATE));
                String _title = c.getString(c.getColumnIndex(COLUMN_TITLE));
                String _content = c.getString(c.getColumnIndex(COLUMN_CONTENT));
                int _amount = c.getInt(c.getColumnIndex(COLUMN_AMOUNT));
                int _isread = c.getInt(c.getColumnIndex(COLUMN_DISMISSED));
                int _ispaid = c.getInt(c.getColumnIndex(COLUMN_PAID));
                String _createdon = c.getString(c.getColumnIndex(COLUMN_CREATED));

                LocalDateTime _formatedDate=LocalDateTime.parse(_datetime, formatter);

                _returnList.add(new BillContent(_id,_formatedDate,_title,_content,_amount,_isread,_ispaid));

            }
            c.moveToNext();
        }
        db.close();
        return _returnList;

    }


}
