package com.yeowheng.simplepasswordmanager;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.nfc.Tag;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper{
    public static final String TABLE_NAME = "Password_Manager_User";

    public static final String _ID = "_id";
    public static final String email_address = "email_address";
    public static final String hashed_mpw = "hashed_mpw";
    public static final String salt = "salt_value";

    public static final String DATA_TABLE_NAME = "Stored_Password";
    public static final String DATA_APPLICATION_NAME = "Application";
    public static final String DATA_PASSWORD = "Password";
    static final String DB_NAME = "PASSWORD_MANAGER.DB";

    static final int DB_VERSION = 3;

    private static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" + _ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + email_address + " TEXT NOT NULL, " + hashed_mpw + " TEXT NOT NULL, " + salt + " TEXT NOT NULL);";

    private static final String CREATE_STORED_DATA = "create table " + DATA_TABLE_NAME + "(" + _ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + DATA_APPLICATION_NAME + " TEXT NOT NULL, " + DATA_PASSWORD + " TEXT NOT NULL );";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
        db.execSQL(CREATE_STORED_DATA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("DbHelper", "Upgrading...");
        if (newVersion > oldVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }

    }
}
