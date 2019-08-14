package com.example.minkyu.a_team_diary.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.minkyu.a_team_diary.ViewItem.FolderView;
import com.example.minkyu.a_team_diary.DB.MemoDBHelper;
import com.example.minkyu.a_team_diary.ViewItem.MemoNameItem;
import com.example.minkyu.a_team_diary.R;

import java.util.ArrayList;

public class MemoActivity extends AppCompatActivity {

    Button btnedit2;
    Button btnWrite;
    Button btnCancel2;
    Button btnAllDelete2;
    Button btnDelete2;
    Button btnCancel_alldeletefolder;
    Button btnDelete_alldeletefolder;
    Button btnCancel_selectdeletefolder;
    Button btnDelete_selectdeletefolder;

    TextView tvMemoName;

    ListView listMemo;
    ListView listMemo_delete;

    MemonameAdapter adapter;
    MemonameAdapter adapter_delete;

    String Folder;
    Intent getintent;

    ArrayList count;

    String folder_name;
    String memo_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo);

        btnWrite = (Button) findViewById(R.id.btnWrite);
        btnedit2 = (Button) findViewById(R.id.btnEdit2);
        btnCancel2 = (Button) findViewById(R.id.btnCancel2);
        btnAllDelete2 = (Button) findViewById(R.id.btnAllDelete2);
        btnDelete2 = (Button) findViewById(R.id.btnDelete2);

        tvMemoName = (TextView) findViewById(R.id.tvMemoName);

        getintent = new Intent(this.getIntent());
        Folder = getintent.getStringExtra("Folder");
        tvMemoName.setText(Folder);

        listMemo = (ListView) findViewById(R.id.listMemo);
        adapter = new MemonameAdapter();
        adapter.readTitle();
        listMemo.setAdapter(adapter);
        listMemo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MemoNameItem memoNameItem = (MemoNameItem) adapter.getItem(position);

                folder_name = tvMemoName.getText().toString();
                memo_title = memoNameItem.getTitle();

                Intent intent = new Intent(getApplicationContext(), MemoWriteActivity.class);
                intent.putExtra("folder_name", folder_name);
                intent.putExtra("memo_title", memo_title);
                intent.putExtra("click", "item");
                startActivity(intent);
            }
        });

        count = new ArrayList();

        listMemo_delete = (ListView) findViewById(R.id.listMemo_delete);
        adapter_delete = new MemonameAdapter();
        adapter_delete.readTitle_delete();
        listMemo_delete.setAdapter(adapter_delete);
        listMemo_delete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MemoNameItem memoNameItem = (MemoNameItem) adapter_delete.getItem(position);

                if (count.get(position).equals("1")) {
                    count.set(position, "0");
                }
                else {
                    count.set(position, "1");
                }

                if(count.get(position).equals("1")) {
                    memoNameItem.setResId(R.drawable.checked_b);
                    adapter_delete.onClick(position, memoNameItem);
                }
                else if(count.get(position).equals("0")){
                    memoNameItem.setResId(R.drawable.unchecked_b);
                    adapter_delete.onClick(position, memoNameItem);
                }

                for(int i=0 ; i<count.size() ; i++) {
                    if (count.get(i).equals("1")) {
                        btnAllDelete2.setVisibility(View.INVISIBLE);
                        btnDelete2.setVisibility(View.VISIBLE);
                        break;
                    }
                    else {
                        btnAllDelete2.setVisibility(View.VISIBLE);
                        btnDelete2.setVisibility(View.INVISIBLE);
                    }
                }
                adapter_delete.notifyDataSetChanged();
            }
        });

        btnWrite.setOnClickListener(Click);
        btnedit2.setOnClickListener(Click);
        btnCancel2.setOnClickListener(Click);
        btnAllDelete2.setOnClickListener(Click);
        btnDelete2.setOnClickListener(Click);
    }

    class MemonameAdapter extends BaseAdapter {
        ArrayList<MemoNameItem> memos = new ArrayList<MemoNameItem>();

        @Override
        public int getCount() {
            return memos.size();
        }

        @Override
        public Object getItem(int position) {
            return memos.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            FolderView view = new FolderView(getApplicationContext());

            MemoNameItem item = memos.get(position);
            view.setImgChecked(item.getResId());
            view.setTvFolderName(item.getTitle());
            view.setTvMemoDate(item.getDate());

            return view;
        }

        public void onClick(int position, MemoNameItem memoNameItem) {
            memos.set(position, memoNameItem);
        }

        public void addTitle(MemoNameItem memoNameItem) {
            memos.add(memoNameItem);
        }

        public void readTitle() {
            MemoNameItem memoNameItem;

            MemoDBHelper dbHelper = new MemoDBHelper(getApplication());
            SQLiteDatabase database = dbHelper.getReadableDatabase();

            Cursor cursor = dbHelper.readContact(database);


            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                String folder_name = cursor.getString(1);

                if (folder_name != null) {
                    if (folder_name.equals(tvMemoName.getText().toString())) {
                        memoNameItem = new MemoNameItem(cursor.getString(2), R.drawable.checked_b, cursor.getString(4));
                        Log.d("memotitle", memoNameItem.getTitle());

                        Log.d("folder_name", folder_name);
                        addTitle(memoNameItem);
                    }
                }
            }

            cursor.close();
            dbHelper.close();

        }

        public void readTitle_delete() {
            MemoNameItem memoNameItem;

            MemoDBHelper dbHelper = new MemoDBHelper(getApplicationContext());
            SQLiteDatabase database = dbHelper.getReadableDatabase();

            Cursor cursor = dbHelper.readContact(database);


            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                String folder_name = cursor.getString(1);

                if (folder_name != null) {
                    if (folder_name.equals(tvMemoName.getText().toString())) {
                        memoNameItem = new MemoNameItem(cursor.getString(2), R.drawable.unchecked_b, cursor.getString(4));

                        addTitle(memoNameItem);
                    }
                }
            }

            cursor.close();
            dbHelper.close();

        }

        public void AllDelete() {
            MemoDBHelper dbHelper = new MemoDBHelper(getApplicationContext());
            SQLiteDatabase database = dbHelper.getWritableDatabase();
            String deletename = tvMemoName.getText().toString();

            for (int i=0 ; i<count.size() ; i++) {
                dbHelper.AllDelete(deletename, database);
            }
            memos.clear();

            dbHelper.close();
        }

        public void selectdeleteContact() {
            MemoDBHelper dbHelper = new MemoDBHelper(getApplicationContext());
            SQLiteDatabase database = dbHelper.getWritableDatabase();
            String deletename = tvMemoName.getText().toString();


            for (int i=getCount()-1 ; i>=0 ; i--) {
                if (count.get(i).equals("1")) {
                    MemoNameItem delete = (MemoNameItem) adapter.getItem(i);
                    String memo_title = delete.getTitle();
                    dbHelper.SelectDelete(deletename, memo_title, database);
                    Log.d("Get", Integer.toString(i));
                    memos.remove(i);
                }
            }

            dbHelper.close();
        }
    }

    public void AlldeletefolderMessage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.alldeletefolder,null);
        builder.setView(view);

        btnCancel_alldeletefolder = (Button) view.findViewById(R.id.btnCancel_alldeletefolder);
        btnDelete_alldeletefolder = (Button) view.findViewById(R.id.btnDelete_alldeletefolder);

        final AlertDialog dialog = builder.create();

        btnCancel_alldeletefolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnDelete_alldeletefolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter_delete.AllDelete();
                adapter.AllDelete();

                adapter_delete.notifyDataSetChanged();
                adapter.notifyDataSetChanged();

                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void SelectdeletefolderMessage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.selectdeletefolder,null);
        builder.setView(view);

        btnCancel_selectdeletefolder = (Button) view.findViewById(R.id.btnCancel_selectdeletefolder);
        btnDelete_selectdeletefolder = (Button) view.findViewById(R.id.btnDelete_selectdeletefolder);

        final AlertDialog dialog = builder.create();

        btnCancel_selectdeletefolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnDelete_selectdeletefolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter_delete.selectdeleteContact();
                adapter.selectdeleteContact();

                adapter_delete.notifyDataSetChanged();
                adapter.notifyDataSetChanged();

                btnWrite.setVisibility(View.VISIBLE);
                btnCancel2.setVisibility(View.INVISIBLE);
                btnedit2.setVisibility(View.VISIBLE);
                btnAllDelete2.setVisibility(View.INVISIBLE);
                btnDelete2.setVisibility(View.INVISIBLE);

                listMemo.setVisibility(View.VISIBLE);
                listMemo_delete.setVisibility(View.INVISIBLE);

                dialog.dismiss();
            }
        });

        dialog.show();
    }

    View.OnClickListener Click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnWrite:
                    Intent intent = new Intent(getApplicationContext(), MemoWriteActivity.class);
                    intent.putExtra("Folder", tvMemoName.getText().toString());
                    intent.putExtra("click", "write");
                    startActivity(intent);
                    break;

                case R.id.btnEdit2:
                    btnWrite.setVisibility(View.INVISIBLE);
                    btnedit2.setVisibility(View.INVISIBLE);
                    btnAllDelete2.setVisibility(View.VISIBLE);
                    btnCancel2.setVisibility(View.VISIBLE);

                    listMemo.setVisibility(View.INVISIBLE);
                    listMemo_delete.setVisibility(View.VISIBLE);

                    for(int i =0; i<adapter.getCount() ; i++) {
                        count.add(i, "0");
                    }
                    break;

                case R.id.btnCancel2:
                    btnWrite.setVisibility(View.VISIBLE);
                    btnedit2.setVisibility(View.VISIBLE);
                    btnAllDelete2.setVisibility(View.INVISIBLE);
                    btnCancel2.setVisibility(View.INVISIBLE);
                    btnDelete2.setVisibility(View.INVISIBLE);

                    listMemo.setVisibility(View.VISIBLE);
                    listMemo_delete.setVisibility(View.INVISIBLE);
                    break;

                case R.id.btnAllDelete2:
                    AlldeletefolderMessage();
                    break;

                case R.id.btnDelete2:
                    SelectdeletefolderMessage();
                    break;
            }
        }
    };
}
