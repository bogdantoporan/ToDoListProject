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
 * Created by Dan Petran on 03.09.2013.
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
                + BYCOLNAME   + " TEXT )"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion)
    {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLENAME);
        onCreate(sqLiteDatabase);
    }

    public int GetNbOfToDoItems()
    {
        int nbOfItems = 0;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select count(*) as count from " + TABLENAME, null);

        //SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY/mm/dd HH:mm:ss");

        if (cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    nbOfItems = Integer.parseInt(cursor.getString(cursor.getColumnIndex("count")));
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        db.close();

        return nbOfItems;
    }
    public ArrayList<ToDoModel> GetToDoList() throws ParseException
    {
        ArrayList<ToDoModel> list = new ArrayList<ToDoModel>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLENAME, null);

        //SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY/mm/dd HH:mm:ss");

        if (cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    ToDoModel item = new ToDoModel(cursor.getInt(cursor.getColumnIndex(IDCOLNAME))
                    , cursor.getString(cursor.getColumnIndex(NAMECOLNAME))
                    , cursor.getString(cursor.getColumnIndex(DESCCOLNAME))
                    , cursor.getString(cursor.getColumnIndex(BYCOLNAME))
                    );

                   list.add(item);

                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        db.close();
        return list;
    }

    public void addProduct(ToDoModel toDoModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAMECOLNAME, toDoModel.GetToDoName());
        contentValues.put(DESCCOLNAME, toDoModel.GetToDoDescription());
        contentValues.put(BYCOLNAME, String.valueOf(toDoModel.GetToDoBy()));
        db.insert(TABLENAME, null, contentValues);
        db.close();
    }

    public ToDoModel GetToDoListItem(String itemId) throws ParseException
    {
       ToDoModel item = new ToDoModel(0,"","","") ;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLENAME + " where " + NAMECOLNAME + "='" + itemId + "'", null);

        if (cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                     item = new ToDoModel(cursor.getInt(cursor.getColumnIndex(IDCOLNAME))
                            , cursor.getString(cursor.getColumnIndex(NAMECOLNAME))
                            , cursor.getString(cursor.getColumnIndex(DESCCOLNAME))
                            , cursor.getString(cursor.getColumnIndex(BYCOLNAME))
                    );
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        db.close();
       return item;
    }

    public void DeleteToDoItem(String itemId)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLENAME, NAMECOLNAME + " = ?", new String[] { itemId });
        db.close();
    }

    public void UpdateToDo(ToDoModel toDo)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAMECOLNAME, toDo.GetToDoName());
        contentValues.put(DESCCOLNAME, toDo.GetToDoDescription());
        contentValues.put(BYCOLNAME, toDo.GetToDoBy());

        db.update(TABLENAME, contentValues, NAMECOLNAME + " = ?", new String[] {toDo.GetToDoName() });

        db.close();
    }
}
