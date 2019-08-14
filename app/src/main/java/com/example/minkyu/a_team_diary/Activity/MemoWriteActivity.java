package com.example.minkyu.a_team_diary.Activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.minkyu.a_team_diary.DB.Contract;
import com.example.minkyu.a_team_diary.DB.MemoDBHelper;
import com.example.minkyu.a_team_diary.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MemoWriteActivity extends AppCompatActivity {

    FrameLayout frSetting;
    LinearLayout linearFont;
    LinearLayout linearAlign;

    LinearLayout btnFont;
    LinearLayout btnCheck;
    LinearLayout btnAlign;
    LinearLayout btnSaveMemo;

    ImageView imgFont;
    ImageView imgCheck;
    ImageView imgAlign;

    ImageView imgBold;
    ImageView imgUnderline;
    ImageView imgStrike;
    ImageView imgLeft;
    ImageView imgMiddle;
    ImageView imgRight;

    TextView tvFont;
    TextView tvCheck;
    TextView tvAlign;
    TextView tvDate;

    EditText edtTitle;
    EditText edtMemo;

    LinearLayout linear_Write;
    LinearLayout.LayoutParams params;

    String Folder_name;
    String folder_name2;
    String memo_title2;
    String click;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_memo_write);

        linear_Write = (LinearLayout) findViewById(R.id.linear_Write);
        params = (LinearLayout.LayoutParams) linear_Write.getLayoutParams();

        frSetting = (FrameLayout) findViewById(R.id.frSetting);
        linearFont = (LinearLayout) findViewById(R.id.linearFont);
        linearAlign = (LinearLayout) findViewById(R.id.linearAlign);

        imgFont = (ImageView) findViewById(R.id.imgFont);
        imgCheck = (ImageView) findViewById(R.id.imgCheck);
        imgAlign = (ImageView) findViewById(R.id.imgAlign);
        imgBold = (ImageView) findViewById(R.id.imgBold);
        imgUnderline = (ImageView) findViewById(R.id.imgUnderline);
        imgStrike = (ImageView) findViewById(R.id.imgStrike);
        imgLeft = (ImageView) findViewById(R.id.imgLeft);
        imgMiddle = (ImageView) findViewById(R.id.imgMiddle);
        imgRight = (ImageView) findViewById(R.id.imgRight);

        tvFont = (TextView) findViewById(R.id.tvFont);
        tvCheck = (TextView) findViewById(R.id.tvCheck);
        tvAlign = (TextView) findViewById(R.id.tvAlign);
        tvDate = (TextView) findViewById(R.id.tvDate);



        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd", Locale.KOREAN);
        String date = dateFormat.format(new Date());
        tvDate.setText(date);

        edtTitle = (EditText) findViewById(R.id.edtTitle);
        edtMemo = (EditText) findViewById(R.id.edtMemo);
        edtMemo.isTextSelectable();
        edtMemo.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && keyCode == KeyEvent.KEYCODE_ENTER) {

                }
                return false;
            }
        });

        btnFont = (LinearLayout) findViewById(R.id.btnFont);
        btnCheck = (LinearLayout) findViewById(R.id.btnCheck);
        btnAlign = (LinearLayout) findViewById(R.id.btnAlign);
        btnSaveMemo = (LinearLayout) findViewById(R.id.btnSaveMemo);

        btnFont.setOnClickListener(settingClick);
        btnCheck.setOnClickListener(settingClick);
        btnAlign.setOnClickListener(settingClick);
        btnSaveMemo.setOnClickListener(settingClick);

        imgBold.setOnClickListener(fontClick);
        imgUnderline.setOnClickListener(fontClick);
        imgStrike.setOnClickListener(fontClick);

        imgLeft.setOnClickListener(alignClick);
        imgMiddle.setOnClickListener(alignClick);
        imgRight.setOnClickListener(alignClick);

        Intent intent = new Intent(this.getIntent());
        Folder_name = intent.getStringExtra("Folder");
        click = intent.getStringExtra("click");

        if (click.equals("item")) {
            readMemo();
        }
    }

    int checkcount = 0;

    View.OnClickListener settingClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.btnFont:
                    imgFont.setImageResource(R.drawable.setting_b);
                    imgAlign.setImageResource(R.drawable.setting_w);
                    tvFont.setTypeface(null, Typeface.BOLD);
                    tvAlign.setTypeface(null, Typeface.NORMAL);

                    frSetting.setVisibility(View.VISIBLE);
                    linearFont.setVisibility(View.VISIBLE);
                    linearAlign.setVisibility(View.INVISIBLE);

                    params.topMargin = 48;
                    linear_Write.setLayoutParams(params);
                    break;

                case R.id.btnCheck:
                    checkcount++;
                    if (checkcount%2 == 1) {
                        imgCheck.setImageResource(R.drawable.setting_b);
                        tvCheck.setTypeface(null, Typeface.BOLD);
                    }
                    else {
                        imgCheck.setImageResource(R.drawable.setting_w);
                        tvCheck.setTypeface(null, Typeface.NORMAL);
                    }
                    break;

                case R.id.btnAlign:
                    imgFont.setImageResource(R.drawable.setting_w);
                    imgAlign.setImageResource(R.drawable.setting_b);
                    tvFont.setTypeface(null, Typeface.NORMAL);
                    tvAlign.setTypeface(null, Typeface.BOLD);

                    frSetting.setVisibility(View.VISIBLE);
                    linearFont.setVisibility(View.INVISIBLE);
                    linearAlign.setVisibility(View.VISIBLE);

                    params.topMargin = 48;
                    linear_Write.setLayoutParams(params);
                    break;

                case R.id.btnSaveMemo:
                    if (click.equals("item")) {
                        MemoUpdate();
                    }
                    else if (click.equals("write")) {
                        MemoSave();
                    }
                    break;
            }
        }
    };

    int boldCount = 0;
    int underlineCount = 0;
    int strikeCount = 0;

    View.OnClickListener fontClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.imgBold:
                    boldCount++;
                    if (boldCount%2 == 1) {
                        imgBold.setImageResource(R.drawable.bold_p);
                        edtMemo.getText().setSpan(new StyleSpan(Typeface.BOLD), edtMemo.getSelectionStart(), edtMemo.getSelectionEnd(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                    else {
                        imgBold.setImageResource(R.drawable.bold_b);
                        StyleSpan[] styleSpans = edtMemo.getText().getSpans(edtMemo.getSelectionStart(), edtMemo.getSelectionEnd(), StyleSpan.class);
                        if (styleSpans!=null && styleSpans.length>0) {
                            for (int i=0 ; i<styleSpans.length ; i++) {
                                edtMemo.getText().removeSpan(styleSpans[i]);
                            }
                        }
                    }
                    break;

                case R.id.imgUnderline:
                    underlineCount++;
                    String temp = edtMemo.getText().toString();
                    SpannableString spannableString = new SpannableString(edtMemo.getText().toString());
                    spannableString.setSpan(new UnderlineSpan(), 0, spannableString.length(), 0);
                    if (underlineCount%2 == 1) {
                        imgUnderline.setImageResource(R.drawable.underline_p);
                        edtMemo.getText().setSpan(new UnderlineSpan(), edtMemo.getSelectionStart(), edtMemo.getSelectionEnd(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                    else {
                        imgUnderline.setImageResource(R.drawable.underline_b);
                        edtMemo.getText().setSpan(null, edtMemo.getSelectionStart(), edtMemo.getSelectionEnd(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                    break;

                case R.id.imgStrike:
                    strikeCount++;
                    if (strikeCount%2 == 1) {
                        imgStrike.setImageResource(R.drawable.strike_p);
                        edtMemo.getText().setSpan(new StrikethroughSpan(), edtMemo.getSelectionStart(), edtMemo.getSelectionEnd(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                    }
                    else {
                        imgStrike.setImageResource(R.drawable.strike_b);
                    }
                    break;
            }
        }
    };

    int leftCount = 0;
    int middleCount = 0;
    int rightCount = 0;

    View.OnClickListener alignClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.imgLeft:
                    leftCount++;
                    if (leftCount%2 == 1) {
                        imgLeft.setImageResource(R.drawable.left_p);
                        imgMiddle.setImageResource(R.drawable.middle_b);
                        imgRight.setImageResource(R.drawable.right_b);
                        edtMemo.setGravity(Gravity.LEFT);
                    }
                    else {
                        imgLeft.setImageResource(R.drawable.left_b);
                        edtMemo.setGravity(0);
                    }
                    break;

                case R.id.imgMiddle:
                    middleCount++;
                    if (middleCount%2 == 1) {
                        imgLeft.setImageResource(R.drawable.left_b);
                        imgMiddle.setImageResource(R.drawable.middle_p);
                        imgRight.setImageResource(R.drawable.right_b);
                        edtMemo.setGravity(Gravity.CENTER_HORIZONTAL);
                    }
                    else {
                        imgMiddle.setImageResource(R.drawable.middle_b);
                        edtMemo.setGravity(0);
                    }
                    break;

                case R.id.imgRight:
                    rightCount++;
                    if(rightCount%2 == 1) {
                        imgLeft.setImageResource(R.drawable.left_b);
                        imgMiddle.setImageResource(R.drawable.middle_b);
                        imgRight.setImageResource(R.drawable.right_p);
                        edtMemo.setGravity(Gravity.RIGHT);
                    }
                    else {
                        imgRight.setImageResource(R.drawable.right_b);
                        edtMemo.setGravity(0);
                    }
                    break;
            }
        }
    };

    public void MemoSave() {
        MemoActivity memoActivity = new MemoActivity();
        MemoDBHelper dbHelper = new MemoDBHelper(getApplicationContext());
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        String title = edtTitle.getText().toString();
        String memo = edtMemo.getText().toString();
        String date = tvDate.getText().toString();

        dbHelper.addContact(Folder_name, title, memo, date, database);
        dbHelper.close();

        edtTitle.setText("");
        edtMemo.setText("");

        Intent intent = new Intent(getApplicationContext(), MemoActivity.class);
        intent.putExtra("Folder", Folder_name);
        startActivity(intent);
    }

    public void MemoUpdate() {
        MemoDBHelper dbHelper = new MemoDBHelper(getApplicationContext());
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        SQLiteDatabase database1 = dbHelper.getReadableDatabase();
        Intent intent = new Intent(this.getIntent());
        Cursor cursor = dbHelper.readContact(database1);

        String folder_name = intent.getStringExtra("folder_name");
        String old_memo_title = intent.getStringExtra("memo_title");
        String memo_title = edtTitle.getText().toString();
        String memo_contents = edtMemo.getText().toString();

        int id = 1;
        Log.d("old", old_memo_title);
        Log.d("fol", folder_name);
        while (cursor.moveToNext()) {
            Log.d("1", cursor.getString(cursor.getColumnIndex(Contract.ContractEmpty.FOLDER_NAME)));
            Log.d("2", cursor.getString(cursor.getColumnIndex(Contract.ContractEmpty.MEMO_TITLE)));
            if (folder_name.equals(cursor.getString(cursor.getColumnIndex(Contract.ContractEmpty.FOLDER_NAME))) && old_memo_title.equals(cursor.getString(cursor.getColumnIndex(Contract.ContractEmpty.MEMO_TITLE)))) {
                id = cursor.getInt(0);
                Log.d("id", Integer.toString(id));
            }
        }

        dbHelper.UpdateContact(id,memo_title,memo_contents,database);

        Intent intent1 = new Intent(this, MemoActivity.class);
        intent1.putExtra("Folder", folder_name);
        startActivity(intent1);
        dbHelper.close();
    }

    public void readMemo() {
        MemoDBHelper dbHelper = new MemoDBHelper(this);
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        dbHelper.readContact(database);
        Cursor cursor = dbHelper.readContact(database);

        Intent intent = new Intent(this.getIntent());

        String folder_name = intent.getStringExtra("folder_name");
        String memo_title = intent.getStringExtra("memo_title");

        while (cursor.moveToNext()) {
            folder_name2 = cursor.getString(cursor.getColumnIndex(Contract.ContractEmpty.FOLDER_NAME));
            memo_title2 = cursor.getString(cursor.getColumnIndex(Contract.ContractEmpty.MEMO_TITLE));

            if (folder_name.equals(folder_name2) && memo_title.equals(memo_title2)) {
                String memo_contents = cursor.getString(cursor.getColumnIndex(Contract.ContractEmpty.MEMO_CONTENTS));
                Log.d("cc",memo_title2);
                edtTitle.setText(memo_title2);
                edtMemo.setText(memo_contents);
            }
        }

        cursor.close();
        dbHelper.close();
    }

}
