package Db;

import android.content.Context;
import android.telecom.Call;

import com.rahul.android.payment.DetailsActivity;
import com.rahul.android.payment.SingleSeries;

import java.time.LocalDateTime;
import java.util.List;

public class Reminder implements IReminder {

   private BillContent _content;
   private DbHandler _DB;

    public Reminder(Context _cntxt,BillContent _cntent){

        if(_cntent!=null)
        _content=new BillContent(_cntent.id,_cntent.datetime,_cntent.title,_cntent.content,_cntent.amount,_cntent.isread,_cntent.ispaid);

        _DB = new DbHandler(_cntxt, null);

    }

    public List<BillContent> GetReminders(String condition){
        return _DB.getReminders(condition);
    }


    public List<SingleSeries> GetReport(){
        return _DB.getReport();
    }

    public boolean AddReminder(){
        return _DB.addReminder(_content) > 0;
    }

    public boolean UpdateReminder(){
        return _DB.updateReminder(_content) > 0;
    }

    public boolean DeleteReminder(){
        return _DB.deleteReminder(_content.id) > 0;
    }


    //


}


