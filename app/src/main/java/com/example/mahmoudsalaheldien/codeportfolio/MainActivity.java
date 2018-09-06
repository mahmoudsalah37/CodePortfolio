package com.example.mahmoudsalaheldien.codeportfolio;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.mahmoudsalaheldien.codeportfolio.Adapter.MainActAdapter;
import com.example.mahmoudsalaheldien.codeportfolio.Classes.RecyclerTouchListener;
import com.example.mahmoudsalaheldien.codeportfolio.SQLite.StoreCodeSQLite;
import com.example.mahmoudsalaheldien.codeportfolio.SQLite.StoreCodeSQLite.StoreCodeEntry;
import com.example.mahmoudsalaheldien.codeportfolio.VarListClass.NoteList;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<NoteList> codeList;
    private RecyclerView viewCodeList_RecView_MainAct;
    private StoreCodeSQLite storeCodeSQLite;
    private MainActAdapter adapter;
    private boolean longPress = false;
    private List<Integer> selectedList;
    private Menu menu;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_menu, menu);
       /* getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);*/
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addMenu_mainAct:
                Intent intentMainAct2InputAct = new Intent(MainActivity.this, InputsActivity.class);
                startActivity(intentMainAct2InputAct);
                return true;
            case R.id.deleteMenu_mainAct:
                for (int deleteSeleltedData : selectedList) {
                    int ID = codeList.get(deleteSeleltedData).ID;
                    String selection = StoreCodeEntry._ID + " = ?";
                    // Specify arguments in placeholder order.
                    String[] selectionArgs = {Integer.toString(ID)};
                    // Issue SQL statement.
                    SQLiteDatabase sqLiteDatabase = storeCodeSQLite.getWritableDatabase();
                    int deletedRows = sqLiteDatabase.delete(StoreCodeEntry.TABLE_NAME, selection, selectionArgs);
                    longPress = false;
                }
                getData();
                item.setVisible(false);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getData();
        viewCodeList_RecView_MainAct.addOnItemTouchListener(new RecyclerTouchListener(this, viewCodeList_RecView_MainAct, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                if (longPress) {
                    selectRows2Delete(view, position);
                }
                else {
                    Intent intentMainAct2EditAct = new Intent(MainActivity.this, EditActivity.class);
                    NoteList noteList = codeList.get(position);
                    intentMainAct2EditAct.putExtra(StoreCodeEntry._ID,noteList.ID);
                    intentMainAct2EditAct.putExtra(StoreCodeEntry.TITLE_CODE,noteList.titleCode);
                    intentMainAct2EditAct.putExtra(StoreCodeEntry.TYPE_CODE,noteList.typeCode);
                    intentMainAct2EditAct.putExtra(StoreCodeEntry.RATING_CODE,noteList.ratingCode);
                    intentMainAct2EditAct.putExtra(StoreCodeEntry.CONTENT_CODE,noteList.contentCode);
                    startActivity(intentMainAct2EditAct);
                }
            }

            @Override
            public void onLongClick(View view, int position) {
                if (!longPress) {
                    longPress = true;
                    MenuItem deleteMenuItem = menu.findItem(R.id.deleteMenu_mainAct);
                    deleteMenuItem.setVisible(true);
                }
                selectRows2Delete(view, position);
            }
        }));
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    private void getData() {
        selectedList = new ArrayList<>();
        storeCodeSQLite = new StoreCodeSQLite(MainActivity.this);
        SQLiteDatabase sqLiteDatabase = storeCodeSQLite.getReadableDatabase();
        String sortOrder =
                StoreCodeEntry._ID + " DESC";
        Cursor cursor = sqLiteDatabase.query(
                StoreCodeEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                sortOrder
        );
        codeList = new ArrayList<>();
        adapter = new MainActAdapter(codeList);
        viewCodeList_RecView_MainAct = findViewById(R.id.viewCodeList_RecView_MainAct);
        viewCodeList_RecView_MainAct.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        viewCodeList_RecView_MainAct.setLayoutManager(linearLayoutManager);
        viewCodeList_RecView_MainAct.setAdapter(adapter);
        while (cursor.moveToNext()) {
            int ID = cursor.getInt(cursor.getColumnIndexOrThrow(StoreCodeEntry._ID));
            String titleCode = cursor.getString(cursor.getColumnIndexOrThrow(StoreCodeEntry.TITLE_CODE));
            String typeCode = cursor.getString(cursor.getColumnIndexOrThrow(StoreCodeEntry.TYPE_CODE));
            int rationCode = cursor.getInt(cursor.getColumnIndexOrThrow(StoreCodeEntry.RATING_CODE));
            String contentCode = cursor.getString(cursor.getColumnIndexOrThrow(StoreCodeEntry.CONTENT_CODE));
            codeList.add(new NoteList(ID, titleCode, typeCode, rationCode, contentCode));
        }
        adapter.notifyDataSetChanged();
        cursor.close();
    }


    @SuppressLint("ResourceAsColor")
    private void selectRows2Delete(View view, int postion) {
        if (longPress)
            if (!selectedList.contains(postion)) {
                view.setBackgroundColor(Color.rgb(153, 153, 153));
                selectedList.add(postion);
            } else if (selectedList.contains(postion)) {
                view.setBackgroundColor(Color.rgb(255, 255, 255));
                selectedList.remove(selectedList.indexOf(postion));
            }
        if (selectedList.isEmpty()){
            longPress = false;
            MenuItem deleteMenuItem = menu.findItem(R.id.deleteMenu_mainAct);
            deleteMenuItem.setVisible(false);
        }
    }
}
