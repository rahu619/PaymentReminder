package Notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.rahul.android.payment.R;


public class Notify {

 Context context;
 public Notify(Context _context){
 this.context=_context;

 }

 public void Set(String title,String body){

     NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

     int notificationId = 1;
     String channelId = "billpay-01";
     String channelName = "BillpaymentReminder";
     int importance = NotificationManager.IMPORTANCE_HIGH;

     if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
         NotificationChannel mChannel = new NotificationChannel(channelId, channelName, importance);
         notificationManager.createNotificationChannel(mChannel);
     }

     NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, channelId)
             .setSmallIcon(R.drawable.billremind)
             .setContentTitle(title)
             .setContentText(body);

     notificationManager.notify(notificationId, mBuilder.build());

 }

    public static void cancelNotification(Context ctx, int notifyId) {
        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager nMgr = (NotificationManager) ctx.getSystemService(ns);
        nMgr.cancel(notifyId);

//        NotificationManager notifManager= (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//        notifManager.cancelAll();
    }



}
