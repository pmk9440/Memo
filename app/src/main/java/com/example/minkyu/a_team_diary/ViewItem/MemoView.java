package com.example.minkyu.a_team_diary.ViewItem;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.minkyu.a_team_diary.R;

public class MemoView extends LinearLayout {

    TextView tvMemoTitle;
    TextView tvMemoDate;

    public MemoView(Context context) {
        super(context);

        init(context);
    }

    public MemoView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    public void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.memoitem,this,true);

        tvMemoTitle = findViewById(R.id.tvMemoTitle);
        tvMemoDate = findViewById(R.id.tvMemoDate);
    }

    public void setTvFolderName(String name) {
        tvMemoTitle.setText(name);
    }
    public void setTvMemoDate(String date) {
        tvMemoDate.setText(date);
    }

}
