package Db;

import android.support.annotation.Nullable;

import com.rahul.android.payment.SingleSeries;

import java.time.LocalDateTime;
import java.util.List;

public interface IReminder{


    List<BillContent> GetReminders(String condition);
    List<SingleSeries> GetReport();

    boolean AddReminder();
    boolean UpdateReminder();
    boolean DeleteReminder();
}
