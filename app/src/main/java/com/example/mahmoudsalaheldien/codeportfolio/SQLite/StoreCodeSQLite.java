package com.example.mahmoudsalaheldien.codeportfolio.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class StoreCodeSQLite extends SQLiteOpenHelper {
    public static class StoreCodeEntry implements BaseColumns {
        public static final String TABLE_NAME = "entry";
        public static final String TITLE_CODE = "title_code";
        public static final String TYPE_CODE = "type_code";
        public static final String RATING_CODE = "rationg_code";
        public static final String CONTENT_CODE = "content_code";
    }
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "StoreCode.db";
    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE "+ StoreCodeEntry.TABLE_NAME+" ("+
            StoreCodeEntry._ID+ " INTEGER PRIMARY KEY,"
            +StoreCodeEntry.TITLE_CODE+" TEXT,"
            +StoreCodeEntry.TYPE_CODE +" TEXT,"
            +StoreCodeEntry.RATING_CODE+" INTEGER,"
            +StoreCodeEntry.CONTENT_CODE+" TEXT"+
            " )";

    private static final String SQL_DELETE_ENTRIES = "";
    public StoreCodeSQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
