package Db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DbHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "billpayment.db";
    private static final String TABLE_REMINDER = "reminder";
    private static final String COLUMN_ID = "_id";
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    public DbHandler(Context ctxt, SQLiteDatabase.CursorFactory factory) {
        super(ctxt, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuilder _query = new StringBuilder();

        _query.append("CREATE TABLE " + TABLE_REMINDER);
        _query.append("(ID INTEGER PRIMARY KEY AUTOINCREMENT,BILL_DATE DATETIME,BILL_TITLE TEXT,");
        _query.append("BILL_CONTENT TEXT NULL,BILL_IS_DISMISSED INTEGER,BILL_CREATED_ON DATETIME NULL);");
        db.execSQL(_query.toString());

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addProduct(IReminder reminder){
        ContentValues values = new ContentValues();
        values.put("BILL_DATE",reminder.datetime.toString());
        values.put("BILL_TITLE",reminder.title.toString());
        values.put("BILL_CONTENT",reminder.content.toString());
        values.put("BILL_IS_DISMISSED",reminder.isread.toString());
        values.put("BILL_CREATED_ON",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

//        for(Field property: IReminder.class.getFields()){
//            values.put(property.getName(),property.get());
//
//        }


        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_REMINDER, null, values);
        db.close();
    }


    public List<IReminder> getProducts(){

        List<IReminder> _returnList=new ArrayList<>();

        SQLiteDatabase db = getWritableDatabase();
        String query = String.format("SELECT * FROM %s WHERE 1",TABLE_REMINDER);

        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();

        while (!c.isAfterLast()) {
            if (c.getString(c.getColumnIndex("ID")) != null) {
                int _id = c.getInt(c.getColumnIndex("ID"));
                String _datetime = c.getString(c.getColumnIndex("BILL_DATE"));
                String _title = c.getString(c.getColumnIndex("BILL_TITLE"));
                String _content = c.getString(c.getColumnIndex("BILL_CONTENT"));
                Integer _isread = c.getInt(c.getColumnIndex("BILL_IS_DISMISSED"));
                String _createdon = c.getString(c.getColumnIndex("BILL_CREATED_ON"));

                LocalDateTime _formatedDate=LocalDateTime.parse(_datetime, formatter);

                _returnList.add(new Reminder(_id,_formatedDate,_title,_content,_isread));

            }
            c.moveToNext();
        }
        db.close();
        return _returnList;
    }



}
