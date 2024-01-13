package com.yeowheng.simplepasswordmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;

import java.util.HashMap;
import java.util.Map;

public class DBManager {
    private DatabaseHelper dbHelper;
    private Context context;
    private SQLiteDatabase database;

    public DBManager (Context c) {
        context = c;
    }

    public DBManager open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dbHelper.close();
    }

    public long insert(String email_address, String hashed_mpassword, String salt) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.email_address, email_address);
        contentValue.put(DatabaseHelper.hashed_mpw, hashed_mpassword);
        contentValue.put(DatabaseHelper.salt, salt);

        return database.insert(DatabaseHelper.TABLE_NAME, null, contentValue);
    }

    public String[] fetch(String master_pw) {
        String[] columns = new String[] {DatabaseHelper.hashed_mpw, DatabaseHelper.salt};

        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, columns, null, null, null, null, null);
        if (cursor == null) return null;

        if (cursor.moveToFirst()) {
            int masterPassword_Index = cursor.getColumnIndex(DatabaseHelper.hashed_mpw);
            int salt_Index = cursor.getColumnIndex(DatabaseHelper.salt);
            return new String[]{cursor.getString(masterPassword_Index), cursor.getString(salt_Index)};
        }
        return null;
    }

    public int update(long _id, String email_address, String hashed_mpassword) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.email_address, email_address);
        contentValues.put(DatabaseHelper.hashed_mpw, hashed_mpassword);
        int i = database.update(DatabaseHelper.TABLE_NAME, contentValues, DatabaseHelper._ID + " = " + _id, null);
        return i;
    }

    public long InsertData(String application_name, String password) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.DATA_APPLICATION_NAME, application_name);
        contentValues.put(DatabaseHelper.DATA_PASSWORD, password);

        return database.insert(DatabaseHelper.DATA_TABLE_NAME, null, contentValues);
    }

    public Map<String, String> RetrieveData() {
        String[] columns = new String[] {DatabaseHelper.DATA_APPLICATION_NAME, DatabaseHelper.DATA_PASSWORD};
        Map<String, String> map = new HashMap<>();

        Cursor cursor = database.query(DatabaseHelper.DATA_TABLE_NAME, columns, null, null, null, null, null);

        if (!cursor.moveToNext()){
            return null;
        }


        do{
            int applicationIndex = cursor.getColumnIndex(DatabaseHelper.DATA_APPLICATION_NAME);
            int passwordIndex = cursor.getColumnIndex(DatabaseHelper.DATA_PASSWORD);
            String application = cursor.getString(applicationIndex).toString();
            String password = cursor.getString(passwordIndex).toString();

            map.put(application, password);
        } while (cursor.moveToNext());


        return map;
    }

    public Boolean CheckRegistered() {
        String[] columns = new String[] {DatabaseHelper.hashed_mpw, DatabaseHelper.salt};
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, columns, null, null, null, null, null);

        return cursor.moveToNext();

    }


    //not using for now..
//    public void delete(long _id) {
//        database.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper._ID + "=" + _id, null);
//    }


}
