package com.opengoogle.nikhilchauhan.reminder4;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;

public class RingtonePlayingService extends Service {
    MediaPlayer mediaPlayer;
    int startId=0;
    boolean isRunning;
    @Override
    public IBinder onBind(Intent intent) {
        // We don't provide binding, so return null
        return null;
    }



    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String noti_name,noti_desc;
        DataBaseHelper myDb;

        myDb = new DataBaseHelper(this);






        String state=intent.getExtras().getString("extra");
        noti_name=intent.getExtras().getString("name");
        noti_desc=intent.getExtras().getString("desc");



        assert state!=null;
        switch (state) {
            case "alarm on":
                startId = 1;
                break;
            case "alarm off":
                startId = 0;
                break;
            default:
                startId = 0;
                break;
        }
        if(!this.isRunning && startId==1)
        {
            Log.e("there is music","and you want to start");

            mediaPlayer=MediaPlayer.create(this,R.raw.wake_me_up);
            //starty the ringtone
            mediaPlayer.start();

            this.isRunning=true;
            this.startId=0;


            //set up notification service
            NotificationManager notify_manager=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
            //set up an intent that goes to the main activity
            Intent intent_main_activity=new Intent(this.getApplicationContext(),AddReminder.class);

            // set up a pending intent
            PendingIntent pending_intent_main_activity=PendingIntent.getActivity(this,0,intent_main_activity,0);




            //make the notifiaction parameters
            Notification notification_popup=new Notification.Builder(this)
                    .setContentTitle(noti_name)
                    .setContentText(noti_desc)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentIntent(pending_intent_main_activity)
                    .setAutoCancel(true)
                    .build();


            notify_manager.notify(0,notification_popup);



        }
        else if(this.isRunning && startId==0)
        {
            Log.e("there is music","and you want to end");
            //stop the ringtone
            mediaPlayer.stop();
            mediaPlayer.reset();

            this.isRunning=false;
            startId=0;


        }
        else if(!this.isRunning && startId==0)
        {
            Log.e("there is  no music","and you want to end");
            this.isRunning=false;
            this.startId=0;

        }
        else if(this.isRunning && startId==0)
        {
            Log.e("there is music","and you want to start");
            this.isRunning=true;
            this.startId=1;

        }
        else
        {
            Log.e("else","oh you reached this!");

        }





        // For each start request, send a message to start a job and deliver the
        // start ID so we know which request we're stopping when we finish the job

        // If we get killed, after returning from here, restart

        return START_NOT_STICKY;
    }



    @Override
    public void onDestroy() {

        Log.e("on destroy called","yeah");
        this.isRunning=false;
        super.onDestroy();
    }

}
