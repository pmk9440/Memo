package com.example.minkyu.a_team_diary.ViewItem;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.minkyu.a_team_diary.R;

public class FolderView extends LinearLayout {

    ImageView imgChecked;
    TextView tvFolderName;
    TextView tvMemoDate;

    public FolderView(Context context) {
        super(context);

        init(context);
    }

    public FolderView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    public void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.listitem,this,true);

        imgChecked = findViewById(R.id.imgChecked);
        tvFolderName = findViewById(R.id.tvFolderName);
        tvMemoDate = findViewById(R.id.tvMemoDate);
    }

    public void setImgChecked(int resId) {
        imgChecked.setImageResource(resId);
    }

    public void setTvFolderName(String name) {
        tvFolderName.setText(name);
    }

    public void setTvMemoDate(String date) {
        tvMemoDate.setText(date);
    }

}
