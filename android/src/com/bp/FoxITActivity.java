package com.bp;


import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * device and user specific content is retrieved and saved in database
 * including installed apps, respective permissions & settings.
 */
public class FoxITActivity extends AppCompatActivity {

    @Override
    public void onStart()
    {
        super.onResume();
        ValueKeeper v=ValueKeeper.getInstance();
        FoxITApplication myApp = (FoxITApplication) this.getApplication();
        if(v.getFreshlyStartet()){
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
        ((FoxITApplication) this.getApplication()).startActivityTransitionTimer();
    }

}