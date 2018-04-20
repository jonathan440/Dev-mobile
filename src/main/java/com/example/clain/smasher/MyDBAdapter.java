package com.example.clain.smasher;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CLAIN on 18/04/2018.
 */



public class MyDBAdapter {
    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "clients_database.db";
    private static final String TABLE_CLIENTS = "table_clients";
    public static final String COL_ID = "_id";
    public static final String COL_NAME = "name";
    public static final String COL_ADDR = "address";
    private static final String CREATE_DB =
            "create table " + TABLE_CLIENTS + " ("
                    + COL_ID + " integer primary key autoincrement, "
                    + COL_NAME + " text not null, "
                    + COL_ADDR + " text not null);";
    private SQLiteDatabase mDB;
    private MyOpenHelper mOpenHelper; // MyOpenHelper : voir plus bas

    public MyDBAdapter(Context context) {
        mOpenHelper = new MyOpenHelper(context, DB_NAME,
                null, DB_VERSION);
    }
    public void open() {
        mDB = mOpenHelper.getWritableDatabase();
    }
    public void close() {
        mDB.close();
    }
    public DataScore getClient(long id) throws SQLException {
        DataScore cl = null;
        Cursor c = mDB.query(TABLE_CLIENTS,
                new String [] {COL_ID, COL_NAME, COL_ADDR},
                COL_ID + " = " + id, null, null, null, null);
        if (c.getCount() > 0) {
            c.moveToFirst();
            cl = new DataScore(c.getLong(0), c.getInt(1), c.getString(2),c.getString(3));
        }
        c.close();
        return cl;
    }

    public List<DataScore> getAllClients() {
        List<DataScore> clients = new ArrayList<DataScore>();
        Cursor c = mDB.query(TABLE_CLIENTS,
                new String[] {COL_ID, COL_NAME, COL_ADDR},
                null, null, null, null, null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            clients.add(new DataScore(
                    c.getLong(0), c.getInt(1), c.getString(2),c.getString(3)));
            c.moveToNext();
        }
        c.close();
        return clients;
    }

    public long insertClient(String name, String address) {
        ContentValues values = new ContentValues();
        values.put(COL_NAME, name);
        values.put(COL_ADDR, address);
        return mDB.insert(TABLE_CLIENTS, null, values);
    }
    public int updateClient(long id, String name, String address) {
        ContentValues values = new ContentValues();
        values.put(COL_NAME, name);
        values.put(COL_ADDR, address);
        return mDB.update(TABLE_CLIENTS, values, COL_ID + "=" + id, null);
    }
    public int removeClient(long id) {
        return mDB.delete(TABLE_CLIENTS, COL_ID + " = " + id, null);
    }

    private class MyOpenHelper extends SQLiteOpenHelper {
        public MyOpenHelper(Context context, String name,
                            SQLiteDatabase.CursorFactory cursorFactory, int version) {
            super(context, name, cursorFactory, version);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_DB);
        }
        @Override
        public void onUpgrade(SQLiteDatabase db,
                              int oldVersion, int newVersion) {
            db.execSQL("drop table " + TABLE_CLIENTS + ";");
            onCreate(db);
        }
    }
} // fin de la classe MyDBAdapter
