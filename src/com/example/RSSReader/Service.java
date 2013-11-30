package com.example.RSSReader;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.widget.Toast;

import java.net.URL;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 27.11.13
 * Time: 2:30
 * To change this template use File | Settings | File Templates.
 */
public class Service extends android.app.Service {
    public static final String log = "createLog";
    NotificationManager mNotifyMgr;
    String chekingPubDate="";
    URL url=null;
    ArrayList<RssTags> arrayList = null;

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public ArrayList<RssTags> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<RssTags> arrayList) {
        this.arrayList = arrayList;
    }

    public String getChekingPubDate() {
        return chekingPubDate;
    }

    public void setChekingPubDate(String chekingPubDate) {
        this.chekingPubDate = chekingPubDate;
    }


    public class LocalBinder extends Binder {
        Service getService() {
            return Service.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;  //To change body of implemented methods use File | Settings | File Templates.
    }
    private final IBinder mBinder = new LocalBinder();



    public void onCreate() {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_stat_av_add_to_queue)
                        .setContentTitle("Service RSS Reader")
                        .setContentText("is started(works)");

        Intent resultIntent = new Intent(this, SimpleActivity.class);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this,0,resultIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MyActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntentBuilder =stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder.setContentIntent(resultPendingIntent);
        int mNotificationId = 001;

        mNotifyMgr =(NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        mNotifyMgr.notify(mNotificationId, mBuilder.build());



    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("LocalService", "Received start id " + startId + ": " + intent);
        Toast.makeText(this, "My Service Started", Toast.LENGTH_LONG).show();
        // TODO Запустить поток в фоновом режиме для обработки.


        return android.app.Service.START_STICKY;
    }
}
