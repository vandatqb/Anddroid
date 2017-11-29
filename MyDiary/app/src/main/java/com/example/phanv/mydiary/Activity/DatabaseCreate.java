package com.example.phanv.mydiary.Activity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.phanv.mydiary.Model.Diary;

import java.util.ArrayList;

/**
 * Created by phanv on 01-Nov-17.
 */

public class DatabaseCreate extends SQLiteOpenHelper{
    public static final String DATABASE_NAME = "DIARY";

    /**
     * table note contain id, title, content
     */
    public static final String TABLE_DIARY = "DIARY";
    public static final String KEY_ID_DIARY = "ID";
    public static final String KEY_DATE_DIARY = "DATE";
    public static final String KEY_TITLE_DIARY = "TITLE";
    public static final String KEY_DETAIL_DIARY = "DETAIL";
    public static final String KEY_LIKE_DIARY = "LIKE";
    public static final String KEY_URL_DIARY = "URL";

    /**
     * string for create table note
     */
    public static final String CREATE_TABLE_DIARY =
            "CREATE TABLE " + TABLE_DIARY + "(" +
                    KEY_ID_DIARY + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL" +
                    ", " + KEY_DATE_DIARY + " TEXT NOT NULL" +
                    "," + KEY_TITLE_DIARY + " TEXT NOT NULL" +
                    "," + KEY_DETAIL_DIARY + " TEXT NOT NULL" +
                    "," + KEY_LIKE_DIARY + " REAL NOT NULL" +
                    "," + KEY_URL_DIARY + " TEXT " +
                    ")";
    /**
     * value for update database
     */
    public static final int DATA_VERSION = 1;

    /**
     * Sqlite database
     */
    private SQLiteDatabase db;

    public DatabaseCreate(Context context) {
        super(context, DATABASE_NAME, null, DATA_VERSION);
    }

    /**
     * create db when app start, and only call when database don't create
     * When database created, it will not call
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_TABLE_DIARY);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * call when change DATA_VERSION
     * help we update database
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * open database
     */
    public void open() {
        try {
            db = getWritableDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * close database
     */
    public void close() {
        if (db != null && db.isOpen()) {
            try {
                db.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /************************* method work with database *******************/

    /**
     * get all row of table with sql command then return cursor
     * cursor move to frist to redy for get data
     */
    public Cursor getAll(String sql) {
        open();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        close();
        return cursor;
    }

    /**
     * insert contentvaluse to table
     *
     * @param values value of data want insert
     * @return index row insert
     */
    public long insert(String table, ContentValues values) {
        open();
        long index = db.insert(table, null, values);
        close();
        return index;
    }

    /**
     * update values to table
     *
     * @return index row update
     */
    public boolean update(String table, ContentValues values, String where) {
        open();
        long index = db.update(table, values, where, null);
        close();
        return index > 0;
    }

    /**
     * delete id row of table
     */
    public boolean delete(String table, String where) {
        open();
        long index = db.delete(table, where, null);
        close();
        return index > 0;
    }


    /************************* end of method work with database *******************/

    /************************* method work with note table *******************/
    /**
     * get Note by sql command
     *
     * @param sql sql to get note
     */
    public Diary getDiary(String sql) {
        Diary diary = null;
        Cursor cursor = getAll(sql);
        if (cursor.getCount()>0) {
            diary = cursorToDiary(cursor);
            cursor.close();
        }
        return diary;
    }

    /**
     * @param sql get all notes with sql command
     * @return arraylist of note
     */
    public ArrayList<Diary> getListDiary(String sql) {
        ArrayList<Diary> list = new ArrayList<>();
        Cursor cursor = getAll(sql);

        while (!cursor.isAfterLast()) {
            list.add(cursorToDiary(cursor));
            cursor.moveToNext();
        }
        cursor.close();

        return list;
    }

    /**
     * insert note to table
     *
     * @param diary note to insert
     * @return id of note
     */
    public long insertDiary(Diary diary) {
        return insert(TABLE_DIARY, diaryToValues(diary));
    }

    /**
     * @param diary note to update
     * @return id of note update
     */
    public boolean updateDiary(Diary diary) {
        return update(TABLE_DIARY, diaryToValues(diary), KEY_ID_DIARY + " = " + diary.getId());
    }

    /**
     * delete id row of table
     */
    public boolean deleteDiary(String where) {
        return delete(TABLE_DIARY, where);
    }

    /**
     * convert note to contentvalues
     * don't put id of note because
     * when insert id will auto create
     * when update we don't update id
     */
    private ContentValues diaryToValues(Diary diary) {
        ContentValues values = new ContentValues();
        values.put(KEY_DATE_DIARY, diary.getDate());
        values.put(KEY_TITLE_DIARY, diary.getTitle());
        values.put(KEY_DETAIL_DIARY, diary.getDetail());
        values.put(KEY_LIKE_DIARY, diary.getLike());
        values.put(KEY_URL_DIARY, diary.getUrl());
        return values;
    }

    /**
     * convert cursor to note
     */
    private Diary cursorToDiary(Cursor cursor) {
        Diary diary = new Diary(
                cursor.getInt(cursor.getColumnIndex(KEY_ID_DIARY)),cursor.getString(cursor.getColumnIndex(KEY_DATE_DIARY)),
                cursor.getString(cursor.getColumnIndex(KEY_TITLE_DIARY)),
                cursor.getString(cursor.getColumnIndex(KEY_DETAIL_DIARY)),
                cursor.getInt(cursor.getColumnIndex(KEY_LIKE_DIARY)),
                cursor.getString(cursor.getColumnIndex(KEY_URL_DIARY))
                );

        return diary;
    }
}
