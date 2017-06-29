package com.example.administrator.myapplication;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Administrator on 2017/6/28.
 */

public class SplashActivity  extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_splash);
        mCountDownTimer.start();
    }
    CountDownTimer mCountDownTimer =new CountDownTimer( 2000,1000) {
        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {
            Intent intent =new Intent(SplashActivity.this,MainActivity.class);
            startActivity(intent);
            finish();

        }
    };
}
