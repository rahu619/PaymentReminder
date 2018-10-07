package Db;

import android.support.annotation.Nullable;

import com.google.android.gms.samples.vision.ocrreader.SingleSeries;

import java.time.LocalDateTime;
import java.util.List;

public interface IReminder{


    List<BillContent> GetReminders();
    List<SingleSeries> GetReport();

    boolean AddReminder();
    boolean UpdateReminder();
    boolean DeleteReminder();
}
