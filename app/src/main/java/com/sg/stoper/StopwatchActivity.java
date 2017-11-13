package com.sg.stoper;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;

public class StopwatchActivity extends AppCompatActivity {

    private int miliseconds = 0;
    private boolean running;
    private boolean wasrunning;

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        savedInstanceState.putInt("miliseconds", miliseconds);
        savedInstanceState.putBoolean("running",running);
        savedInstanceState.putBoolean("wasrunning",wasrunning);
    }

    @Override
    protected void onPause(){
        super.onPause();
        wasrunning = running;
        running = false;
    }

    @Override
    protected void onResume(){
        super.onResume();
        if(wasrunning)
        running = true;
        animateOrange();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);
        runTimer();
        if(savedInstanceState != null){
            miliseconds = savedInstanceState.getInt("miliseconds");
            running = savedInstanceState.getBoolean("running");
            wasrunning = savedInstanceState.getBoolean("wasrunning");
        }
    }

    public void onClickStart(View view) {
        running = true;
        animateOrange();
    }

    public void onClickStop(View view) {
        running = false;
        animateOrange();
    }

    public void onClickReset(View view) {
        running = false;
        miliseconds = 0;
        animateOrange();
    }
    private void animateOrange() {
        ImageView orange = (ImageView) findViewById(R.id.orange_logo);
        Animation spin = new RotateAnimation(0.0f,360.0f,
                RotateAnimation.RELATIVE_TO_SELF,0.5f,RotateAnimation.RELATIVE_TO_SELF,0.5f);
        spin.setDuration(1000);
        spin.setRepeatCount(-1);
        spin.setFillAfter(true);
        orange.setAnimation(spin);
        if(running) spin.start();
        else{
            spin.cancel();
            spin.reset();
        }
        //orange.setColorFilter(R.color.orange3);
    }

    private void runTimer() {
        final TextView timeView = (TextView)findViewById(R.id.time_view);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                String time;
                int hours = miliseconds /216000;
                int minutes = (miliseconds % 216000) / 3600;
                int seconds = (miliseconds % 3600) / 60;
                int milis = miliseconds % 60;
                if(miliseconds>216000) time = String.format("%02d:%02d:%02d:%d",hours, minutes, seconds, milis);
                else time = String.format("%02d:%02d:%02d", minutes, seconds, milis);
                timeView.setText(time);
                if (running){
                    miliseconds++;
                }
                handler.postDelayed(this, 1);
            }
        });
    }

}