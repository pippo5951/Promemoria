package com.opengoogle.nikhilchauhan.reminder4;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String get_string = intent.getExtras().getString("extra");
        String name=intent.getExtras().getString("name");
        String desc_text=intent.getExtras().getString("desc");
        Log.e("MyActivity", "Nel ricevitore con " + get_string);

        Intent serviceIntent = new Intent(context,RingtonePlayingService.class);
        serviceIntent.putExtra("extra", get_string);
        serviceIntent.putExtra("name",name);
        serviceIntent.putExtra("desc",desc_text);



        context.startService(serviceIntent);

    }
}
