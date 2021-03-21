package com.example.mpacoursework;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class TimerActivity extends AppCompatActivity {
    //time left in MILLISECONDS
    long timeRemaining = 0;
    boolean isActive = false;
    CountDownTimer timer;
    //max time is 99:59
    final long MAX_TIME = 5999000;

    ImageButton upTenMin;
    ImageButton upOneMin;
    ImageButton upTenSec;
    ImageButton upOneSec;
    ImageButton downTenMin;
    ImageButton downOneMin;
    ImageButton downTenSec;
    ImageButton downOneSec;

    Button startButton;
    Button stopButton;
    TextView timerText;

    ImageButton backButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        upTenMin = findViewById(R.id.upMinTen);
        upOneMin = findViewById(R.id.upMinOne);
        upTenSec = findViewById(R.id.upSecTen);
        upOneSec = findViewById(R.id.upSecOne);
        downTenMin = findViewById(R.id.downMinTen);
        downOneMin = findViewById(R.id.downMinOne);
        downTenSec = findViewById(R.id.downSecTen);
        downOneSec = findViewById(R.id.downSecOne);

        startButton = findViewById(R.id.startButton);
        stopButton = findViewById(R.id.stopButton);
        timerText = findViewById(R.id.TimerText);

        backButton = findViewById(R.id.timerBackButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TimerActivity.this, cookActivityMain.class);
                startActivity(intent);
            }
        });

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isActive && timeRemaining > 999){
                    startTimer();
                }
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isActive){
                    stopTimer();
                }
            }
        });

        upTenMin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementTimer(600000);
                updateTimer();
            }
        });

        upOneMin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementTimer(60000);
                updateTimer();
            }
        });

        upTenSec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementTimer(10000);
                updateTimer();
            }
        });

        upOneSec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementTimer(1000);
                updateTimer();
            }
        });

        downTenMin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementTimer(-600000);
                updateTimer();
            }
        });

        downOneMin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementTimer(-60000);
                updateTimer();
            }
        });

        downTenSec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementTimer(-10000);
                updateTimer();
            }
        });

        downOneSec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementTimer(-1000);
                updateTimer();
            }
        });





    }

    void startTimer() {
        timer = new CountDownTimer(timeRemaining,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeRemaining = millisUntilFinished;
                updateTimer();
                Log.i("Time", String.valueOf(timeRemaining));
            }

            @Override
            public void onFinish() {

            }
        }.start();

        isActive = true;
    }

    void stopTimer() {
        if(timer != null){
            timer.cancel();
            isActive = false;
        }
    }

    void updateTimer(){
        int minutes = (int) timeRemaining/ 60000;
        int seconds = (int) (timeRemaining % 60000) / 1000;
        String timeText = "";
        if(minutes < 10){
            timeText += '0';
        }
        timeText += String.valueOf(minutes) + ':';
        //make sure seconds is displayed as 0X if less than 10 i.e. 00:09
        if(seconds < 10){
            timeText += '0';
        }
        timeText += String.valueOf(seconds);
        timerText.setText(timeText);

    }

    void incrementTimer(long time){
        timeRemaining += time;
        if(timeRemaining < 0){
            timeRemaining = 0;
        }
        else if(timeRemaining >= MAX_TIME){
            timeRemaining = MAX_TIME;
        }
    }

}