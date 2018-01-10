package com.delon.user.crossgameapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button topLeftButton;
    private Button topCenterButton;
    private Button topRightButton;
    private Button midLeftButton;
    private Button midCenterButton;
    private Button midRightButton;
    private Button underLeftButton;
    private Button underCenterButton;
    private Button underRightButton;

    private TextView textView;
    private TextView countTextView;

    private int touchCounter;
    private boolean thisIsPre;

    private int firstCounter;
    private int secondCounter;
    private int thirdCounter;
    private int fourthCounter;
    private int fifthCounter;
    private int sixthCounter;
    private int seventhCounter;
    private int eighthCounter;
    private int ninthCounter;

    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the Mobile Ads SDK.
        MobileAds.initialize(this,"ca-app-pub-9589073381069791~6673429660 ");

        // Gets the ad view defined in layout/ad_fragment.xml with ad unit ID set in
        // values/strings.xml.
        mAdView = (AdView) findViewById(R.id.ad_view);

        // Create an ad request. Check your logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder().build();

        // Start loading the ad in the background.
        mAdView.loadAd(adRequest);

        thisIsPre = true;

        newButton();

        textView = new TextView(this);
        textView = (TextView)findViewById(R.id.textView);

        countTextView = new TextView(this);
        countTextView =(TextView)findViewById(R.id.count_textView);

        AlertDialog.Builder alertdialog = new AlertDialog.Builder(this);
        alertdialog.setTitle("Are you OK?");
        // 内容説明
        alertdialog.setMessage(R.string.details);
        alertdialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                textView.setText("limit : ");
                //三秒タイマーのスタート
                PreGameCountDown preGameCountDown = new PreGameCountDown(5000, 10);
                preGameCountDown.start();
            }
        });
        alertdialog.setCancelable(false);
        alertdialog.show();

    }

    @Override
    public void onClick(View view){
        if (textView.getText().toString().equals("counter : ")) {
            touchCounter++;
            countTextView.setText(String.valueOf(touchCounter));
            thisIsPre = false;
        }
        switch (view.getId()){
            case R.id.top_left_button:
                firstCounter++;
                secondCounter++;
                fourthCounter++;
                reverse(topLeftButton,firstCounter);
                reverse(topCenterButton, secondCounter);
                reverse(midLeftButton, fourthCounter);
                compPanel();
                break;
            case R.id.top_center_button:
                firstCounter++;
                secondCounter++;
                thirdCounter++;
                fifthCounter++;
                reverse(topLeftButton,firstCounter);
                reverse(topCenterButton, secondCounter);
                reverse(topRightButton,thirdCounter);
                reverse(midCenterButton,fifthCounter);
                compPanel();
                break;
            case R.id.top_right_button:
                secondCounter++;
                thirdCounter++;
                sixthCounter++;
                reverse(topCenterButton, secondCounter);
                reverse(topRightButton, thirdCounter);
                reverse(midRightButton, sixthCounter);
                compPanel();
                break;
            case R.id.mid_left_button:
                firstCounter++;
                fourthCounter++;
                fifthCounter++;
                seventhCounter++;
                reverse(topLeftButton, firstCounter);
                reverse(midLeftButton, fourthCounter);
                reverse(midCenterButton, fifthCounter);
                reverse(underLeftButton, seventhCounter);
                compPanel();
                break;
            case R.id.mid_center_button:
                secondCounter++;
                fourthCounter++;
                fifthCounter++;
                sixthCounter++;
                eighthCounter++;
                reverse(topCenterButton, secondCounter);
                reverse(midLeftButton, fourthCounter);
                reverse(midCenterButton, fifthCounter);
                reverse(midRightButton, sixthCounter);
                reverse(underCenterButton, eighthCounter);
                compPanel();
                break;
            case R.id.mid_right_button:
                thirdCounter++;
                fifthCounter++;
                sixthCounter++;
                ninthCounter++;
                reverse(topRightButton, thirdCounter);
                reverse(midCenterButton, fifthCounter);
                reverse(midRightButton, sixthCounter);
                reverse(underRightButton, ninthCounter);
                compPanel();
                break;
            case R.id.under_left_button:
                fourthCounter++;
                seventhCounter++;
                eighthCounter++;
                reverse(midLeftButton,fourthCounter);
                reverse(underLeftButton,seventhCounter);
                reverse(underCenterButton,eighthCounter);
                compPanel();
                break;
            case R.id.under_center_button:
                fifthCounter++;
                seventhCounter++;
                eighthCounter++;
                ninthCounter++;
                reverse(midCenterButton, fifthCounter);
                reverse(underLeftButton, seventhCounter);
                reverse(underCenterButton, eighthCounter);
                reverse(underRightButton, ninthCounter);
                compPanel();
                break;
            case R.id.under_right_button:
                sixthCounter++;
                eighthCounter++;
                ninthCounter++;
                reverse(midRightButton, sixthCounter);
                reverse(underCenterButton, eighthCounter);
                reverse(underRightButton, ninthCounter);
                compPanel();
                break;
        }

    }

    private void compPanel(){
        if (!thisIsPre && firstCounter % 2 == 1 && secondCounter % 2 == 1 && thirdCounter % 2 == 1 &&
                fourthCounter % 2 == 1&& fifthCounter % 2 == 1 && sixthCounter % 2 ==1 &&
                seventhCounter % 2 == 1 && eighthCounter % 2 == 1 && ninthCounter % 2 == 1){
            long startTime = System.currentTimeMillis();
            Date date = new Date(startTime);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM月dd日HH時mm分ss秒 SSS", Locale.JAPAN);

            Intent intent = new Intent(MainActivity.this,ResultActivity.class);
            intent.putExtra("touchCounter", touchCounter);
            intent.putExtra("date", simpleDateFormat.format(date));
            startActivity(intent);
        }
    }

    private void newButton(){
        topLeftButton = new Button(this);
        topLeftButton = (Button)findViewById(R.id.top_left_button);
        topLeftButton.setOnClickListener(this);

        topCenterButton = new Button(this);
        topCenterButton = (Button)findViewById(R.id.top_center_button);
        topCenterButton.setOnClickListener(this);

        topRightButton = new Button(this);
        topRightButton = (Button)findViewById(R.id.top_right_button);
        topRightButton.setOnClickListener(this);

        midLeftButton = new Button(this);
        midLeftButton = (Button)findViewById(R.id.mid_left_button);
        midLeftButton.setOnClickListener(this);

        midCenterButton = new Button(this);
        midCenterButton = (Button)findViewById(R.id.mid_center_button);
        midCenterButton.setOnClickListener(this);

        midRightButton = new Button(this);
        midRightButton = (Button)findViewById(R.id.mid_right_button);
        midRightButton.setOnClickListener(this);

        underLeftButton = new Button(this);
        underLeftButton = (Button)findViewById(R.id.under_left_button);
        underLeftButton.setOnClickListener(this);

        underCenterButton = new Button(this);
        underCenterButton = (Button)findViewById(R.id.under_center_button);
        underCenterButton.setOnClickListener(this);

        underRightButton = new Button(this);
        underRightButton = (Button)findViewById(R.id.under_right_button);
        underRightButton.setOnClickListener(this);
    }

    private void reverse(Button button, int counter){
        if (counter%2 ==1){
            button.setBackgroundResource(android.R.color.holo_orange_light);
        }else if (counter%2 == 0){
            button.setBackgroundResource(android.R.color.holo_blue_bright);
        }
    }

    private class PreGameCountDown extends CountDownTimer {

        PreGameCountDown(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);

        }

        @Override
        public void onFinish() {
            // カウントダウン完了後に呼ばれる
            new AlertDialog.Builder(MainActivity.this)
            .setTitle("Are you ready?")
                    .setPositiveButton("start", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            touchCounter = 0;
                        }
                    });
            countTextView.setText(String.valueOf(touchCounter));
            textView.setText("counter : ");
        }

        @Override
        public void onTick(long millisUntilFinished) {
            // インターバル(countDownInterval)毎に呼ばれる
            countTextView.setText(Long.toString(millisUntilFinished / 1000 % 60) + "." + Long.toString(millisUntilFinished / 100));
        }
    }

}
