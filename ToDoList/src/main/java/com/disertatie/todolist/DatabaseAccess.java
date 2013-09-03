package com.disertatie.todolist;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Bogdan on 03.09.2013.
 */
public class DatabaseAccess extends SQLiteOpenHelper
{
    public static String DATABASENAME = "todolistsql";
    public static String TABLENAME = "todolist";
    public static String IDCOLNAME = "id";
    public static String NAMECOLNAME = "todoname";
    public static String DESCCOLNAME = "tododesc";
    public static String BYCOLNAME = "todoby";

    Context c;

    public DatabaseAccess(Context context)
    {
        super(context, DATABASENAME, null, 33);
        c = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLENAME + " ( " + IDCOLNAME + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + NAMECOLNAME + " TEXT,"
                + DESCCOLNAME + " TEXT,"
                + BYCOLNAME   + " DATETIME )"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion)
    {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLENAME);
        onCreate(sqLiteDatabase);
    }

    public ArrayList<ToDoModel> GetToDoList() throws ParseException
    {
        ArrayList<ToDoModel> list = new ArrayList<ToDoModel>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLENAME, null);

        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY/mm/dd HH:mm:ss");

        if (cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    ToDoModel item = new ToDoModel(cursor.getInt(cursor.getColumnIndex(IDCOLNAME))
                    , cursor.getString(cursor.getColumnIndex(NAMECOLNAME))
                    , cursor.getString(cursor.getColumnIndex(DESCCOLNAME))
                    , dateFormat.parse(cursor.getString(cursor.getColumnIndex(BYCOLNAME)))
                    );

                   list.add(item);

                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        db.close();
        return list;
    }
}
