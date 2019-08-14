package com.example.minkyu.a_team_diary.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.minkyu.a_team_diary.DB.ContactDbHelper;
import com.example.minkyu.a_team_diary.DB.Contract;
import com.example.minkyu.a_team_diary.DB.MemoDBHelper;
import com.example.minkyu.a_team_diary.ViewItem.FolderView;
import com.example.minkyu.a_team_diary.ViewItem.FolerNameItem;
import com.example.minkyu.a_team_diary.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnNewFolder;
    Button btnCancel;
    Button btnEdit;
    Button btnAllDelete;
    Button btnDelete;
    Button btnWrite;

    EditText edtNewFolder;
    Button btnCancel_newfolder;
    Button btnSave_newfolder;

    Button btnCancel_alldeletefolder;
    Button btnDelete_alldeletefolder;

    Button btnCancel_selectdeletefolder;
    Button btnDelete_selectdeletefolder;

    LinearLayout linear_Main;
    TextView tvMainFolder;

    ListView listMain;
    ListView listDelete;
    FolderNameAdapter adapter;
    FolderNameAdapter adapter_delete;

    ArrayList count;

    String temp;

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, LoadingActivity.class);
        startActivity(intent);

        btnNewFolder = (Button) findViewById(R.id.btnNewFolder);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnEdit = (Button) findViewById(R.id.btnEdit);
        btnAllDelete = (Button) findViewById(R.id.btnAllDelete);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnWrite = (Button) findViewById(R.id.btnWrite);

        linear_Main = (LinearLayout) findViewById(R.id.linear_Main);
        tvMainFolder = (TextView) findViewById(R.id.tvMainFolder);

        listMain = (ListView) findViewById(R.id.listMain);
        adapter = new FolderNameAdapter();
        adapter.readContact();
        listMain.setAdapter(adapter);
        listMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FolerNameItem folerNameItem = (FolerNameItem) adapter.getItem(position);
                temp = folerNameItem.getFolderName();
                setTemp(temp);

                Intent intent = new Intent(getApplicationContext(), MemoActivity.class);
                intent.putExtra("Folder", folerNameItem.getFolderName());
                startActivity(intent);
                }
        });

        listDelete = (ListView) findViewById(R.id.listMain_delete);
        adapter_delete = new FolderNameAdapter();
        adapter_delete.readContact_delete();
        listDelete.setAdapter(adapter_delete);

        count = new ArrayList();

        listDelete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FolerNameItem folerNameItem = (FolerNameItem) adapter_delete.getItem(position);

                if (count.get(position).equals("1")) {
                    count.set(position, "0");
                }
                else {
                    count.set(position, "1");
                }

                if(count.get(position).equals("1")) {
                    folerNameItem.setResId(R.drawable.checked_p);
                    adapter_delete.onClick(position, folerNameItem);
                }
                else if(count.get(position).equals("0")){
                    folerNameItem.setResId(R.drawable.unchecked_p);
                    adapter_delete.onClick(position, folerNameItem);
                }

                for(int i=0 ; i<count.size() ; i++) {
                    if (count.get(i).equals("1")) {
                        btnAllDelete.setVisibility(View.INVISIBLE);
                        btnDelete.setVisibility(View.VISIBLE);
                        break;
                    }
                    else {
                        btnAllDelete.setVisibility(View.VISIBLE);
                        btnDelete.setVisibility(View.INVISIBLE);
                    }
                }
                adapter_delete.notifyDataSetChanged();
                Click_Delete();
            }
        });

        btnNewFolder.setOnClickListener(Click);
        btnEdit.setOnClickListener(Click);
        btnCancel.setOnClickListener(Click);
        btnAllDelete.setOnClickListener(Click);
        btnDelete.setOnClickListener(Click);

    }

    public void newfolderMessage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.newfolder,null);
        builder.setView(view);

        edtNewFolder = (EditText) view.findViewById(R.id.edtNewFolder);
        btnCancel_newfolder = (Button) view.findViewById(R.id.btnCancel_newfolder);
        btnSave_newfolder = (Button) view.findViewById(R.id.btnSave_newfolder);

        edtNewFolder.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && keyCode == KeyEvent.KEYCODE_ENTER) {
                    btnSave_newfolder.performClick();
                }
                return false;
            }
        });

        final AlertDialog dialog = builder.create();

        btnCancel_newfolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnSave_newfolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                adapter.addFolder(new FolerNameItem(edtNewFolder.getText().toString(), R.drawable.checked_p));
                adapter_delete.addFolder(new FolerNameItem(edtNewFolder.getText().toString(), R.drawable.unchecked_p));
                adapter.addContact(new FolerNameItem(edtNewFolder.getText().toString(), R.drawable.checked_p));

                adapter.notifyDataSetChanged();
                adapter_delete.notifyDataSetChanged();


                dialog.dismiss();
            }
        });

        dialog.show();
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
                adapter_delete.alldeleteContact();
                adapter.alldeleteContact();

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

                btnNewFolder.setVisibility(View.VISIBLE);
                btnCancel.setVisibility(View.INVISIBLE);
                btnEdit.setVisibility(View.VISIBLE);
                btnAllDelete.setVisibility(View.INVISIBLE);
                btnDelete.setVisibility(View.INVISIBLE);

                listMain.setVisibility(View.VISIBLE);
                listDelete.setVisibility(View.INVISIBLE);

                dialog.dismiss();
            }
        });

        dialog.show();
    }

    class FolderNameAdapter extends BaseAdapter {

        ArrayList<FolerNameItem> folders = new ArrayList<FolerNameItem>();

        @Override
        public int getCount() {
            return folders.size();
        }

        @Override
        public Object getItem(int position) {
            return folders.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            FolderView view = new FolderView(getApplicationContext());

            FolerNameItem item = folders.get(position);
            view.setImgChecked(item.getResId());
            view.setTvFolderName(item.getFolderName());

            return view;
        }

        public void onClick(int position, FolerNameItem folerNameItem) {
            folders.set(position, folerNameItem);
        }

        public void addFolder(FolerNameItem foldername) {
            folders.add(foldername);
        }

        public void addContact(FolerNameItem foldername) {
            ContactDbHelper dbHelper = new ContactDbHelper(getApplicationContext());
            SQLiteDatabase database = dbHelper.getWritableDatabase();
            dbHelper.addContact(foldername.getFolderName(), database);
            dbHelper.close();
        }

        public void readContact() {
            FolerNameItem folerNameItem;

            ContactDbHelper dbHelper = new ContactDbHelper(getApplicationContext());
            SQLiteDatabase database = dbHelper.getReadableDatabase();

            Cursor cursor = dbHelper.readContact(database);

            while (cursor.moveToNext()) {

                folerNameItem = new FolerNameItem(cursor.getString(cursor.getColumnIndex(Contract.ContractEmpty.NAME))
                        , R.drawable.checked_p);

                addFolder(folerNameItem);

            }

            cursor.close();
            dbHelper.close();
        }

        public void readContact_delete() {
            FolerNameItem folerNameItem;

            ContactDbHelper dbHelper = new ContactDbHelper(getApplicationContext());
            SQLiteDatabase database = dbHelper.getReadableDatabase();

            Cursor cursor = dbHelper.readContact(database);

            while (cursor.moveToNext()) {

                folerNameItem = new FolerNameItem(cursor.getString(cursor.getColumnIndex(Contract.ContractEmpty.NAME))
                        , R.drawable.unchecked_p);

                addFolder(folerNameItem);

            }

            cursor.close();
            dbHelper.close();
        }

        public void alldeleteContact() {
            folders.clear();

            ContactDbHelper contactDbHelper = new ContactDbHelper(getApplicationContext());
            SQLiteDatabase contact_database = contactDbHelper.getWritableDatabase();

            MemoDBHelper memoDBHelper = new MemoDBHelper(getApplicationContext());
            SQLiteDatabase memo_database = memoDBHelper.getWritableDatabase();

            contactDbHelper.alldeleteContact(contact_database);
            memoDBHelper.RealAllDelete(memo_database);

            contactDbHelper.close();
            memoDBHelper.close();
        }

        public void selectdeleteContact() {
            ContactDbHelper contactDbHelper = new ContactDbHelper(getApplicationContext());
            SQLiteDatabase contact_database = contactDbHelper.getWritableDatabase();

            MemoDBHelper memoDBHelper = new MemoDBHelper(getApplicationContext());
            SQLiteDatabase memo_database = memoDBHelper.getWritableDatabase();

            for (int i=0 ; i<count.size() ; i++) {
                if (count.get(i).equals("1")) {
                    FolerNameItem delete = (FolerNameItem) adapter.getItem(i);
                    String deletename = delete.getFolderName();
                    contactDbHelper.selectdeleteContact(deletename, contact_database);
                    memoDBHelper.AllDelete(deletename, memo_database);
                    folders.remove(i);
                }
            }

            contact_database.close();
            memoDBHelper.close();
        }

    }

    public void Click_Delete() {

    }

    //버튼클릭
    View.OnClickListener Click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnNewFolder:
                    newfolderMessage();
                    break;

                case R.id.btnEdit:
                    btnNewFolder.setVisibility(View.INVISIBLE);
                    btnCancel.setVisibility(View.VISIBLE);
                    btnEdit.setVisibility(View.INVISIBLE);
                    btnAllDelete.setVisibility(View.VISIBLE);

                    listMain.setVisibility(View.INVISIBLE);
                    listDelete.setVisibility(View.VISIBLE);

                    for(int i =0; i<adapter.getCount() ; i++) {
                        count.add(i, "0");
                    }
                    break;

                case R.id.btnCancel:
                    btnNewFolder.setVisibility(View.VISIBLE);
                    btnCancel.setVisibility(View.INVISIBLE);
                    btnEdit.setVisibility(View.VISIBLE);
                    btnAllDelete.setVisibility(View.INVISIBLE);
                    btnDelete.setVisibility(View.INVISIBLE);

                    listMain.setVisibility(View.VISIBLE);
                    listDelete.setVisibility(View.INVISIBLE);

                    for(int position = 0; position<adapter_delete.getCount() ; position++) {
                        FolerNameItem folerNameItem = (FolerNameItem) adapter_delete.getItem(position);
                        folerNameItem.setResId(R.drawable.unchecked_p);
                        adapter_delete.onClick(position, folerNameItem);
                    }
                    adapter_delete.notifyDataSetInvalidated();

                    break;

                case R.id.btnAllDelete:
                    AlldeletefolderMessage();
                    break;

                case R.id.btnDelete:
                    SelectdeletefolderMessage();
                    break;

            }
        }
    };
}
