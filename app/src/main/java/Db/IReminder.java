package Db;

import android.support.annotation.Nullable;

import java.time.LocalDateTime;

public interface IReminder{

    public LocalDateTime datetime = LocalDateTime.MIN;
    @Nullable
    int id=0;
    public String title=null;
    @Nullable
    public String content=null;
    public Integer isread=0;
}
