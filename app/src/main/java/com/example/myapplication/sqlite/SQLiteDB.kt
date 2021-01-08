package com.example.myapplication.sqlite

import android.content.Context
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Build
import androidx.annotation.RequiresApi
import java.util.ArrayList

class SQLiteDB(context: Context) : SQLiteOpenHelper(context, DBName, null, 1) {


    val _Email: String
        get() {
            val Email: String
            val db = this.readableDatabase
            val mCursor = db.rawQuery("select email FROM my_table", null)
            mCursor.moveToFirst()
            Email = mCursor.getString(mCursor.getColumnIndex("email"))
            return Email
        }

    val _Password: String
        get() {
            val Password: String
            val db = this.readableDatabase
            val mCursor = db.rawQuery("select password FROM my_table", null)
            mCursor.moveToFirst()
            Password = mCursor.getString(mCursor.getColumnIndex("password"))
            return Password
        }

    val _Check: String
        get() {
            val Check: String
            val db = this.readableDatabase
            val mCursor = db.rawQuery("select save_data FROM my_table", null)
            mCursor.moveToFirst()
            Check = mCursor.getString(mCursor.getColumnIndex("save_data"))
            return Check
        }

    override fun onCreate(db: SQLiteDatabase) {

        db.execSQL("create table my_table (id INTEGER PRIMARY KEY AUTOINCREMENT , email TEXT , password TEXT , save_data TEXT)")
        val mContentValues = ContentValues()
        mContentValues.put("email", "null")
        mContentValues.put("password", "null")
        mContentValues.put("save_data", "0")

        db.insert("my_table", null, mContentValues)

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

        db.execSQL("DROP TABLE IF EXISTS my_table")
        onCreate(db)
    }


    fun update_Data_Login(email: String, password: String): Boolean {
        val db = this.writableDatabase
        val mContentValues = ContentValues()
        mContentValues.put("email", email)
        mContentValues.put("password", password)

        db.update("my_table", mContentValues, "id= ?", arrayOf("1"))
        return true
    }

    fun update_Data_Logout(email: String, password: String, check_save_data: String): Boolean {
        val db = this.writableDatabase
        val mContentValues = ContentValues()
        mContentValues.put("email", "null")
        mContentValues.put("password", "null")
        mContentValues.put("save_data", "0")

        db.update("my_table", mContentValues, "id= ?", arrayOf("1"))
        return true
    }


    fun update_Data_Req(email: String, password: String, check_save_data: String): Boolean {
        val db = this.writableDatabase
        val mContentValues = ContentValues()
        mContentValues.put("email", email)
        mContentValues.put("password", password)
        mContentValues.put("save_data", check_save_data)

        db.update("my_table", mContentValues, "id= ?", arrayOf("1"))
        return true
    }

    companion object {

        val DBName = "mdatabase.db"
    }
}