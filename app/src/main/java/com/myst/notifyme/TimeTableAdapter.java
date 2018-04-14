package com.myst.notifyme;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Myst on 1/24/2017.
 */

class TimeTableAdapter {

    TimeTableDatabase database;

    TimeTableAdapter(Context context){
        database = new TimeTableDatabase(context);
    }


    public long insertData(String[] data, String day){
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(database.DayOfWeek,day);

        contentValues.put(database.First,data[0]);
        contentValues.put(database.Second,data[1]);
        contentValues.put(database.Third,data[2]);
        contentValues.put(database.Fourth,data[3]);
        contentValues.put(database.Fifth,data[4]);
        contentValues.put(database.Sixth,data[5]);
        contentValues.put(database.Seventh,data[6]);
        contentValues.put(database.Eighth,data[7]);

        long id = db.insert(database.TABLE_NAME,null,contentValues);
        return id;
    }

    public int update(String[] data, String day){
         SQLiteDatabase db = database.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(database.DayOfWeek,day);

        contentValues.put(database.First,data[0]);
        contentValues.put(database.Second,data[1]);
        contentValues.put(database.Third,data[2]);
        contentValues.put(database.Fourth,data[3]);
        contentValues.put(database.Fifth,data[4]);
        contentValues.put(database.Sixth,data[5]);
        contentValues.put(database.Seventh,data[6]);
        contentValues.put(database.Eighth,data[7]);

        String whereargs[] = {day};

        int count = db.update(database.TABLE_NAME,contentValues,database.DayOfWeek + " =? ",whereargs);
        return count;
    }

    public String[] getAllData(String day){
        SQLiteDatabase db = database.getWritableDatabase();

        String[] columns = {database.DayOfWeek,database.First,database.Second,database.Third,database.Fourth,database.Fifth,
                            database.Sixth,database.Seventh,database.Eighth};

       Cursor cursor =  db.query(database.TABLE_NAME,columns,database.DayOfWeek + " = '"+day+"'",null,null,null,null);

        StringBuffer buffer = new StringBuffer();

        cursor.moveToFirst();

            int index1 = cursor.getColumnIndex(database.DayOfWeek);
            int index2 = cursor.getColumnIndex(database.First);
            int index3 = cursor.getColumnIndex(database.Second);
            int index4 = cursor.getColumnIndex(database.Third);
            int index5 = cursor.getColumnIndex(database.Fourth);
            int index6 = cursor.getColumnIndex(database.Fifth);
            int index7 = cursor.getColumnIndex(database.Sixth);
            int index8 = cursor.getColumnIndex(database.Seventh);
            int index9 = cursor.getColumnIndex(database.Eighth);

            String[] timeTableData = new String[9];

            timeTableData[0] = cursor.getString(index1);
            timeTableData[1] = cursor.getString(index2);
            timeTableData[2] = cursor.getString(index3);
            timeTableData[3] = cursor.getString(index4);
            timeTableData[4] = cursor.getString(index5);
            timeTableData[5] = cursor.getString(index6);
            timeTableData[6] = cursor.getString(index7);
            timeTableData[7] = cursor.getString(index8);
            timeTableData[8] = cursor.getString(index9);

         /*   buffer.append(cursor.getString(index1)+"\n"+cursor.getString(index2)+"\n"+
                    cursor.getString(index3)+"\n"+cursor.getString(index4)+"\n"+cursor.getString(index5)+"\n"+
                    cursor.getString(index6)+"\n"+cursor.getString(index7)+"\n"+cursor.getString(index8)+"\n"+
                    cursor.getString(index9)+"\n");*/

       // buffer.append(cursor.getString(2));

        return timeTableData;
    }

    public class TimeTableDatabase extends SQLiteOpenHelper {

        Context context;

        private static final String DATABASE_NAME = "notifydatabase";
        private static final String TABLE_NAME = "TIME_TABLE";
        private static final int DATABASE_VERSION = 1;
        private static final String UID = "_id";
        private static final String DayOfWeek = "Day";
        private static final String First = "First";
        private static final String Second = "Second";
        private static final String Third = "Third";
        private static final String Fourth = "Fourth";
        private static final String Fifth = "Fifth";
        private static final String Sixth = "Sixth";
        private static final String Seventh = "Seventh";
        private static final String Eighth = "Eighth";

        private static final String Create = "CREATE TABLE " + TABLE_NAME + " (" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + DayOfWeek + " VARCHAR(255), " + First + " VARCHAR(255), " +
                Second + " VARCHAR(255), " + Third + " VARCHAR(255), " + Fourth + " VARCHAR(255), " + Fifth + " VARCHAR(255), " + Sixth + " VARCHAR(255), " +
                Seventh + " VARCHAR(255), " + Eighth + " VARCHAR(255));";

        private static final String Drop = "DROP TABLE IF EXISTS " + TABLE_NAME;

        TimeTableDatabase(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            try {
                sqLiteDatabase.execSQL(Create);
               // Toast.makeText(context, "OnCreate", Toast.LENGTH_LONG).show();
            } catch (SQLException e) {
               // Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
                Log.d("SQL Error", e.toString());
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            try {
                sqLiteDatabase.execSQL(Drop);
                //Toast.makeText(context, "OnUpgrade", Toast.LENGTH_LONG).show();
                onCreate(sqLiteDatabase);
            } catch (SQLException e) {
               // Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
            }
        }

    }
}
