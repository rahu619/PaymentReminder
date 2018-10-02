package Notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AlarmBroadcastReceiver extends BroadcastReceiver {
    public static final String ACTION_ALARM = "BillPayment.Reminder";

    public AlarmBroadcastReceiver(){

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (ACTION_ALARM.equals(intent.getAction())) {
            //Toast.makeText(context, ACTION_ALARM, Toast.LENGTH_SHORT).show();
            new Notify(context).Set("Test");
        }
    }


}
