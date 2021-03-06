package com.foxyourprivacy.f0x1t;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.foxyourprivacy.f0x1t.activities.Home;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Background Service run originally to check on app installs and notify about evaluations and daily lessons
 * now it is only used for notifications on dailies and planned for news updates given on the server
 * Created by Tim on 13.11.2016.
 */

public class BackgroundService extends Service {

    List<ApplicationInfo> apps;
    private Context context;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        context = getApplicationContext();
        Timer timer = new Timer();
        timer.schedule(new timedTask(), 0, 1800000);


        return Service.START_NOT_STICKY;

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public boolean shouldEvaluationBeDisplayed() {
        return false; //dummy, maybe we want to use this later again
    }


    class timedTask extends TimerTask {
        public void run() {
            ValueKeeper v = ValueKeeper.getInstance();

            //Ability to check for Evaluations and notify about them
           /* if (shouldEvaluationBeDisplayed() && v.notDisplayed) {//v.isEvaluationOutstanding==false&&shouldEvaluationBeDisplayed()){
                v.isEvaluationOutstanding = true;
                v.notDisplayed = false;
                NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(context)
                                .setSmallIcon(R.drawable.paw)
                                .setContentTitle("Neue Evaluation verfügbar!")
                                .setContentText("");
                Intent resultIntent = new Intent(context, Home.class);

                // The stack builder object will contain an artificial back stack for the
                // started Activity.
                // This ensures that navigating backward from the Activity leads out of
                // your application to the Home screen.
                TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
                // Adds the back stack for the Intent (but not the Intent itself)
                stackBuilder.addParentStack(Home.class);
                // Adds the Intent that starts the Activity to the top of the stack
                stackBuilder.addNextIntent(resultIntent);
                PendingIntent resultPendingIntent =
                        stackBuilder.getPendingIntent(
                                0,
                                PendingIntent.FLAG_UPDATE_CURRENT
                        );
                mBuilder.setContentIntent(resultPendingIntent);
                NotificationManager mNotificationManager =
                        (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                // mId allows you to update the notification later on.
                mNotificationManager.notify(15, mBuilder.build());


            } */



                Long firstTime = v.getTimeOfFirstStart();
                Long currentTime = System.currentTimeMillis();
                Long result = (currentTime - firstTime) / 86400000;

            if (result > v.dailyLessonsUnlocked && v.dailyLessonsUnlocked < 15 && v.valueKeeperAlreadyRefreshed) {
                DBHandler db = new DBHandler(context);
                String lessonName = "blabla";
                //db.unlockDaily();
                //TODO unlockdaily fixen
                v.increaseDailyLessonsUnlocked();

                    NotificationCompat.Builder mBuilder =
                            new NotificationCompat.Builder(context, "15")
                                    .setSmallIcon(R.mipmap.literature)
                                    .setContentTitle(getString(R.string.newLessonAvailable))
                                    .setContentText(lessonName);

                    Intent resultIntent = new Intent(context, Home.class);

                    // The stack builder object will contain an artificial back stack for the
                    // started Activity.
                    // This ensures that navigating backward from the Activity leads out of
                    // your application to the Home screen.
                    TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
                    // Adds the back stack for the Intent (but not the Intent itself)
                    stackBuilder.addParentStack(Home.class);
                    // Adds the Intent that starts the Activity to the top of the stack
                    stackBuilder.addNextIntent(resultIntent);
                    PendingIntent resultPendingIntent =
                            stackBuilder.getPendingIntent(
                                    0,
                                    PendingIntent.FLAG_UPDATE_CURRENT
                            );
                    mBuilder.setContentIntent(resultPendingIntent);
                NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    // mId allows you to update the notification later on.
                if (mNotificationManager != null) mNotificationManager.notify(15, mBuilder.build());
                else Log.d("Notification", "NotifactionManager is null");

            }
        }


    }


}
