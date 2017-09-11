package com.xu.myandroidtest.dict;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.Settings;

/**
 * Created by Administrator on 2017/9/12 0012.
 */

public class MyDataBaseHelper extends SQLiteOpenHelper{

    final String CREATE_TABLE_SQL = "CREATE TABLE dict(_id integer primary" +
            "key autoincrement ,word,detail)";

    public MyDataBaseHelper(Context context, String name, int version) {
        super(context, name,null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        System.out.println("--------------Database Update Called--------------");
        System.out.println(oldVersion + "-->" +newVersion);
    }
}
