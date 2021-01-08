package com.example.myapplication.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.myapplication.contracts.ProductContract;

public class ProductLite extends SQLiteOpenHelper {

    public static final String DB_Name = "products.db";
    public static final int DB_Version = 1;

    public ProductLite(@Nullable Context context) {
        super(context, DB_Name, null, DB_Version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQlQuery = "CREATE TABLE " + ProductContract.ProductEntry.TableName + " ("
                + ProductContract.ProductEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ProductContract.ProductEntry.CoulmnNum + " INTEGER NOT NULL, "
                + ProductContract.ProductEntry.CoulmnUserID + " INTEGER NOT NULL, "
                + ProductContract.ProductEntry.CoulmnName + " TEXT NOT NULL, "
                + ProductContract.ProductEntry.CoulmnPrice + " INTEGER NOT NULL, "
                + ProductContract.ProductEntry.CoulmnAmount + " INTEGER NOT NULL, "
                + ProductContract.ProductEntry.CoulmnTime  + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP "
                + ");";

        db.execSQL(SQlQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ProductContract.ProductEntry.TableName);
        onCreate(db);
    }
}
