package com.example.renesotolira.alarmauno;

import java.util.GregorianCalendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;


public class MainActivity extends Activity {
    MediaPlayer reproductor;
    Vibrator mVibrator;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        // Get instance of Vibrator from current Context
        mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        // Start immediately
        // Vibrate for 200 milliseconds
        // Sleep for 0 milliseconds
        mVibrator.vibrate(new long[] { 0, 200, 0 }, 0);

       /* Vibrator vibrator;
        vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(5000); // vibrate for 5 seconds (e.g 3000 milliseconds)
        */


         reproductor = MediaPlayer.create(this, R.raw.audio);
        reproductor.start();
        reproductor.setLooping(true);
       // reproductor.start();


        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
        r.play();


        OnClickListener setClickListener = new OnClickListener() {

            @Override
            public void onClick(View v) {
                reproductor.setLooping(false);
                mVibrator.cancel();
//                reproductor.stop();
                /** This intent invokes the activity DemoActivity, which in turn opens the AlertDialog window */
                Intent i = new Intent("com.example.renesotolira.alarmauno.DemoActivity");

                /** Creating a Pending Intent */
                PendingIntent operation = PendingIntent.getActivity(getBaseContext(), 0, i, Intent.FLAG_ACTIVITY_NEW_TASK);

                /** Getting a reference to the System Service ALARM_SERVICE */
                AlarmManager alarmManager = (AlarmManager) getBaseContext().getSystemService(ALARM_SERVICE);

                /** Getting a reference to DatePicker object available in the MainActivity */
                DatePicker dpDate = (DatePicker) findViewById(R.id.dp_date);

                /** Getting a reference to TimePicker object available in the MainActivity */
                TimePicker tpTime = (TimePicker) findViewById(R.id.tp_time);

                int year = dpDate.getYear();
                int month = dpDate.getMonth();
                int day = dpDate.getDayOfMonth();
                int hour = tpTime.getCurrentHour();
                int minute = 55;

                /** Creating a calendar object corresponding to the date and time set by the user */
                GregorianCalendar calendar = new GregorianCalendar(year,month,day, hour, minute);

                /** Converting the date and time in to milliseconds elapsed since epoch */
                long alarm_time = calendar.getTimeInMillis();

                /** Setting an alarm, which invokes the operation at alart_time */
                alarmManager.set(AlarmManager.RTC_WAKEUP  , alarm_time , operation);

                /** Alert is set successfully */
                Toast.makeText(getBaseContext(), "Alarm is set successfully",Toast.LENGTH_SHORT).show();
            }
        };

        OnClickListener quitClickListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        };

        Button btnSetAlarm = ( Button ) findViewById(R.id.btn_set_alarm);
        btnSetAlarm.setOnClickListener(setClickListener);

        Button btnQuitAlarm = ( Button ) findViewById(R.id.btn_quit_alarm);
        btnQuitAlarm.setOnClickListener(quitClickListener);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}