package Db;

import android.support.annotation.Nullable;

import java.time.LocalDateTime;

public class BillContent {
    @Nullable
    public LocalDateTime datetime = LocalDateTime.MIN;
    public int id=0;
    public int amount=0;
    @Nullable
    public String title=null;
    @Nullable
    public String content=null;
    public Integer isread=0;
    public Integer ispaid=0;


    public BillContent(int id){
        this.id=id;
    }


    public BillContent(int id,LocalDateTime datetime,String title,String content,int amount,int isread,int ispaid){
        this.id=id;
        this.datetime=datetime;
        this.title=title;
        this.content=content;
        this.amount=amount;
        this.isread=isread;
        this.ispaid=ispaid;

    }


}
