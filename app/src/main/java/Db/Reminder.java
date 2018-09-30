package Db;

import java.time.LocalDateTime;

public class Reminder implements IReminder {

    public int id;
    public LocalDateTime datetime;
    public String title,content;
    public Integer isread;

    public Reminder(int id,LocalDateTime datetime,String title,String content,Integer isread){

        this.id=id;
        this.datetime=datetime;
        this.title=title;
        this.content=content;
        this.isread=isread;

    }

    //


}


