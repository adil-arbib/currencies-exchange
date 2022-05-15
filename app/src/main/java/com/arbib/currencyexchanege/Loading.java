package com.arbib.currencyexchanege;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

public class Loading extends AppCompatActivity {
    private final int TIME_OUT = 500;
    private ProgressBar progressBar;
    private CountDownTimer mCountDownTimer;
    private int timePassed;
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        progressBar = findViewById(R.id.progressBar);

        progressBar.getProgressDrawable().setColorFilter(
                Color.WHITE, android.graphics.PorterDuff.Mode.SRC_IN);

        progressBar.setMax(TIME_OUT);
        timePassed = 0;
        progressBar.setProgress(timePassed);

        mCountDownTimer=new CountDownTimer(TIME_OUT,50) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.v("Log_tag", "Tick of Progress"+ timePassed+ millisUntilFinished);
                timePassed+=50;
                progressBar.setProgress(timePassed);
            }

            @Override
            public void onFinish() {
                //Do what you want
                progressBar.setProgress(TIME_OUT);
            }
        };
        mCountDownTimer.start();

        new Handler().postDelayed(new Runnable() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void run() {
                if(checkConnection()){

                    Intent i = new Intent(Loading.this, MainActivity.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                }
                else {
                    Toast.makeText(Loading.this, "No internet", Toast.LENGTH_SHORT).show();
                }

            }
        }, TIME_OUT);

    }
    private boolean checkConnection(){
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED;
    }

}