package com.delon.user.crossgameapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.List;

public class ResultActivity extends AppCompatActivity {

    private TextView newTextView;
    private TextView countTextView;

    private int newCount;
    private String date;

    private ResultData resultData;
    private List<RankingItem> countList = new ArrayList<>();
    private List<RankingItem> dateList = new ArrayList<>();
    private RankingAdapter countRankingAdapter;
    private RankingAdapter dateRankingAdapter;
    private ListView countListView;
    private ListView dateListView;

    private int oldRecord;

    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

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

        Intent intent = getIntent();
        newCount = intent.getIntExtra("touchCounter",9999);
        date = intent.getStringExtra("date");

        countTextView = new TextView(this);
        countTextView = (TextView)findViewById(R.id.result_count_textView);
        countTextView.setText(String.valueOf(newCount));

        resultData= new ResultData(this);

        newTextView = new TextView(this);
        newTextView = (TextView)findViewById(R.id.new_record_textView);
        newTextView.setText(newOrOld(newCount));

        //touchCountのランキングのlistView
        countRankingAdapter = new RankingAdapter(this,android.R.layout.simple_list_item_1,countList);
        countListView = new ListView(this);
        countListView = (ListView)findViewById(R.id.ranking_count_listView);
        countListView.setAdapter(countRankingAdapter);

        //日付順のランキングのlistView
        dateRankingAdapter = new RankingAdapter(this,android.R.layout.simple_list_item_1,dateList);
        dateListView = new ListView(this);
        dateListView = (ListView)findViewById(R.id.ranking_date_listView);
        dateListView.setAdapter(dateRankingAdapter);

        //Sqliteに書き込む
        resultData.insert(date,newCount);



        Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(ResultActivity.this,MainActivity.class);
                startActivity(intent1);
            }
        });
    }

    private CharSequence newOrOld(int newRecord){
        Cursor cursor = resultData.maxRecord();
        if (cursor.moveToFirst()){
            do {
                oldRecord = cursor.getInt(cursor.getColumnIndex(SqlOpenHelper.RESULT_COUNT));
            }while (cursor.moveToNext());
        }
        if (newRecord < oldRecord){
            //新記録が出たら
            return "New Record!!";
        }else if (newRecord >= oldRecord){
            return "Don't worry...";
        }
        return "OMG!!";
    }

    private void loadCountList(){
        countList.clear();

        Cursor cursor = resultData.countSort();

        if (cursor.moveToFirst()){
            do {
                RankingItem countRankItem = new RankingItem(
                        cursor.getInt(cursor.getColumnIndex(SqlOpenHelper.RESULT_ID)),
                        cursor.getString(cursor.getColumnIndex(SqlOpenHelper.RESULT_DATE)),
                        cursor.getInt(cursor.getColumnIndex(SqlOpenHelper.RESULT_COUNT))
                );
                countList.add(countRankItem);
            }while (cursor.moveToNext());
        }
        countRankingAdapter.notifyDataSetChanged();
    }

    private void loadDateList(){
        dateList.clear();

        Cursor cursor = resultData.idSort();

        if (cursor.moveToFirst()){
            do {
                RankingItem dateRankingItem = new RankingItem(
                        cursor.getInt(cursor.getColumnIndex(SqlOpenHelper.RESULT_ID)),
                        cursor.getString(cursor.getColumnIndex(SqlOpenHelper.RESULT_DATE)),
                        cursor.getInt(cursor.getColumnIndex(SqlOpenHelper.RESULT_COUNT))
                );
                dateList.add(dateRankingItem);
            }while (cursor.moveToNext());
        }
        dateRankingAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume(){
        super.onResume();
        loadCountList();
        loadDateList();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            Intent intent = new Intent(ResultActivity.this,MainActivity.class);
            startActivity(intent);
            return false;
        }else{
            return super.onKeyDown(keyCode, event);
        }
    }
}
