package com.example.minkyu.a_team_diary.ViewItem;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.minkyu.a_team_diary.Activity.MemoWriteActivity;
import com.example.minkyu.a_team_diary.R;

public class MemoWriteView extends LinearLayout {

    EditText edtMemoWrite;

    MemoWriteActivity.MemoWriteAdapter adapter;
    public MemoWriteView(Context context) {
        super(context);

        init(context);
    }

    public MemoWriteView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    public void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.memowriteitem,this,true);

        edtMemoWrite = (EditText) findViewById(R.id.edtMemoWrite);
    }

    public void setTvMemoWrite(String memo) {
        edtMemoWrite.setText(memo);
    }
}
