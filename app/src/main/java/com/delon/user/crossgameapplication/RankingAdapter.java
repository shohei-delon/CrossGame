package com.delon.user.crossgameapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by User on 2016/07/04.
 */

public class RankingAdapter extends ArrayAdapter<RankingItem> {

    LayoutInflater layoutInflater;

    RankingAdapter(Context context, int textViewResourceId, List<RankingItem> objects){
        super(context, textViewResourceId, objects);
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent){


        //指定行のデータを取得
        RankingItem detail = getItem(position);

        //nullの場合のみ再構成
        if (null == convertView){
            convertView = layoutInflater.inflate(R.layout.rank_list_item,null);
        }

        if (detail != null) {
            //行データを項目へ設定
            TextView textView = (TextView) convertView.findViewById(R.id.date_textView);
            textView.setText(String.valueOf(detail.getResultDate()));

            TextView textView1 = (TextView) convertView.findViewById(R.id.count_textView);
            textView1.setText(String.valueOf(detail.getResultCount()));
        }

        //返却
        return convertView;
    }

}
