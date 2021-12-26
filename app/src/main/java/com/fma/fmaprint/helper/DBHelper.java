package com.fma.fmaprint.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.fma.fmaprint.model.ModelSetting;

/**
 * Created by fmanda on 08/01/17.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static String DATABASE_NAME = "fmaprint";
    private static final int DATABASE_VERSION = 1;

    private static DBHelper mInstance;

    public static synchronized DBHelper getInstance(Context context) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (mInstance == null) {
            mInstance = new DBHelper(context.getApplicationContext());
        }
        return mInstance;
    }

    public DBHelper(Context context, String name) {
        super(context, name, null, 0);
        this.DATABASE_NAME = name;
    }

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        db.execSQL(new ModelProduct().generateMetaData());
//        db.execSQL(new ModelCustomer().generateMetaData());
//        db.execSQL(new ModelModifier().generateMetaData());
//        db.execSQL(new ModelOrder().generateMetaData());
//        db.execSQL(new ModelOrderItem().generateMetaData());
//        db.execSQL(new ModelOrderModifier().generateMetaData());
        db.execSQL(new ModelSetting().generateMetaData());
//        db.execSQL(new ModelMoneyPreset(0).generateMetaData());
//        db.execSQL(new ModelOrderPreset().generateMetaData());
//        db.execSQL(new ModelOrderCategory().generateMetaData());
//        db.execSQL(new ModelCashTrans().generateMetaData());
//        db.execSQL(new ModelReconcile().generateMetaData());

        //init
        ModelSetting.initMetaData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        resetDatabase(db);
    }


    public void dropAllTables(SQLiteDatabase db){
        db.execSQL(new ModelSetting().generateDropMetaData());
    }

    public void resetDatabase(SQLiteDatabase db){
        dropAllTables(db);
        onCreate(db);
    }


}
