package Db;

import android.support.annotation.Nullable;

import java.time.LocalDateTime;
import java.util.List;

public interface IReminder{

    boolean AddReminder();
    List<BillContent> GetReminders();
}
