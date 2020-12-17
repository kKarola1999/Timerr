package com.example.timer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private int seconds;
    private int period;
    private int periodView;
    private int periodTime;
    private double mass;
    private boolean running;
    private boolean wasRunning;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
            mass = savedInstanceState.getDouble("mass");
            period = savedInstanceState.getInt("period");
            periodTime = savedInstanceState.getInt("periodTime");
            periodView = savedInstanceState.getInt("periodView");


        }
        runTimer();

    }

    private void runTimer() {
        final TextView timeView = (TextView) findViewById(R.id.tvTime);
        final TextView MassView = (TextView) findViewById(R.id.tvMass);
        final TextView PeriodView = (TextView) findViewById(R.id.tvPeriod);

        final Handler handler = new Handler(Looper.getMainLooper());

        handler.post(new Runnable() {

                         @Override
                         public void run() {
                             int hours = seconds / 3600;
                             int minutes = (seconds % 3600) / 60;
                             int secs = seconds % 60;


                             String time = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs);
                             timeView.setText("Time " + time);

                             MassView.setText("Mass " + String.valueOf(mass));
                             PeriodView.setText("Period " + String.valueOf(periodView));


                             Log.d("licznik", String.valueOf(seconds));
                             if (running)
                                 seconds++;
                             handler.postDelayed(this, 1000);

                             if (seconds == period && running) {

                                 mass /= 2;
                                 periodView++;
                                 period = seconds + periodTime;
                             }


                         }
                     }

        );

    }

    public void onClickStart(View view) {

        running = true;
        if (wasRunning){
            mass=mass;
            period=period;

        }
        else{
        EditText halfLive = (EditText) findViewById(R.id.halfLive);
        EditText initialMass = (EditText) findViewById(R.id.initialMass);

        mass = Double.valueOf(initialMass.getText().toString());
        period = Integer.valueOf(halfLive.getText().toString());
        periodTime = Integer.valueOf(halfLive.getText().toString());
        periodView = 0;


        }

    }

    public void onClickStop(View view) {
        running = false;
    }

    public void onClickReset(View view) {
        running = false;
        seconds = 0;
    }

    @Override
    protected void onStart() {
        super.onStart();
        wasRunning = running;
        //running = false;

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (wasRunning)
            running = true;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("seconds", seconds);
        savedInstanceState.putBoolean("running", running);
        savedInstanceState.putBoolean("wasRunning", wasRunning);
        savedInstanceState.putInt("period",period);
        savedInstanceState.putInt("periodTime",periodTime);
        savedInstanceState.putInt("periodView",periodView);
        savedInstanceState.putDouble("mass",mass);
    }
}