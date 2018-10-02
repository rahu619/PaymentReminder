package Db;

import android.support.annotation.Nullable;

import java.time.LocalDateTime;

public class BillContent {
    @Nullable
    public LocalDateTime datetime = LocalDateTime.MIN;
    @Nullable
    int id=0;
    @Nullable
    int amount=0;
    public String title=null;
    @Nullable
    public String content=null;
    public Integer isread=0;

    public BillContent(int id,LocalDateTime datetime,String title,String content,int amount,int isread){
        this.id=id;
        this.datetime=datetime;
        this.title=title;
        this.content=content;
        this.amount=amount;
        this.isread=isread;

    }


}
