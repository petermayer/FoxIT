package com.bp;


import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * device and user specific content is retrieved and saved in database
 * including installed apps, respective permissions & settings.
 */
public class FoxItActivity extends AppCompatActivity {

    private static Context context;


    @Override
    public void onStart()
    {
        super.onResume();
        FoxItActivity.context = getApplicationContext();
        ValueKeeper v=ValueKeeper.getInstance();
        FoxItApplication myApp = (FoxItApplication) this.getApplication();
        if(v.getFreshlyStartet()){

           // v.reviveInstance();
            new reviveValueTask().execute();
            v.setTimeOfFirstAccess(System.currentTimeMillis());
        }


        if (myApp.wasInBackground||v.getFreshlyStartet())
        {
            Log.d("MyApp","Was in Background");
            v.setFreshlyStartet(false);
            v.setTimeOfLastAccess(System.currentTimeMillis());
        }

        myApp.stopActivityTransitionTimer();
    }

    @Override
    public void onPause()
    {
        super.onPause();
        ValueKeeper v=ValueKeeper.getInstance();
        v.fillApplicationAccessAndDuration(System.currentTimeMillis());
        v.fillApplicationStartAndDuration(System.currentTimeMillis());
        v.fillApplicationStartAndActiveCDuration(System.currentTimeMillis());
        ((FoxItApplication) this.getApplication()).startActivityTransitionTimer();
        new SavaValueTask().execute();
    }

    public static Context getAppContext() {
        return FoxItActivity.context;
    }

}
