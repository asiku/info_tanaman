package com.sundaya.tanaman.infotanaman;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Userpc on 10/08/2016.
 */

public class Tb_tanaman_Crud {
    private DBHelper dbHelper;

    public Tb_tanaman_Crud(Context context){
        dbHelper =new DBHelper(context);
    }


    public int Insert(Tb_tanaman tanaman_item){
        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        //values.put(Tb_Customer.KEY_ID_CUSTOMER, customer_item.id_customer);

        values.put(Tb_tanaman.KEY_NAMA_LOKAL,tanaman_item.nama_lokal);
        values.put(Tb_tanaman.KEY_NAMA_LATIN,tanaman_item.nama_latin);
        values.put(Tb_tanaman.KEY_KHASIAT, tanaman_item.khasiat);
        values.put(Tb_tanaman.KEY_SENYAWA, tanaman_item.senyawa);


        // Inserting Row
        long id_tanaman = db.insert(Tb_tanaman.TABLE, null, values);
        db.close(); // Closing database connection
        return (int) id_tanaman;
    }

    public void Delete(int id_tanaman){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // It's a good practice to use parameter ?, instead of concatenate string
        db.delete(Tb_tanaman.TABLE, Tb_tanaman.KEY_ID_TANAMAN + "= ?", new String[] { String.valueOf(id_tanaman) });
        db.close(); // Closing database connection

    }


    public Tb_tanaman getTanamanByName(String nama){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT  " +
                Tb_tanaman.KEY_ID_TANAMAN + "," +
                Tb_tanaman.KEY_NAMA_LOKAL + "," +
                Tb_tanaman.KEY_NAMA_LATIN + "," +
                Tb_tanaman.KEY_KHASIAT + "," +
                Tb_tanaman.KEY_SENYAWA +
                " FROM " + Tb_tanaman.TABLE
                + " WHERE " +
                Tb_tanaman.KEY_NAMA_LOKAL + "=?";// It's a good practice to use parameter ?, instead of concatenate string

        int iCount =0;
        Tb_tanaman pos = new Tb_tanaman();

        Cursor cursor = db.rawQuery(selectQuery, new String[] { nama } );

        if (cursor.moveToFirst()) {
            //   do {
            pos.id_tanaman =cursor.getInt(cursor.getColumnIndex(Tb_tanaman.KEY_ID_TANAMAN));
            pos.nama_lokal =cursor.getString(cursor.getColumnIndex(Tb_tanaman.KEY_NAMA_LOKAL));
            pos.nama_latin  =cursor.getString(cursor.getColumnIndex(Tb_tanaman.KEY_NAMA_LATIN));
            pos.khasiat  =cursor.getString(cursor.getColumnIndex(Tb_tanaman.KEY_KHASIAT));
            pos.senyawa  =cursor.getString(cursor.getColumnIndex(Tb_tanaman.KEY_SENYAWA));

            //  } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return pos;
    }

    public  ArrayList<HashMap<String, String>> filterTanamanByName(String name){
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        if (name.length() != 0) {

            name = "%" + name + "%";
        }

        String selectQuery = "SELECT  " +
                Tb_tanaman.KEY_ID_TANAMAN + "," +
                Tb_tanaman.KEY_NAMA_LOKAL + "," +
                Tb_tanaman.KEY_NAMA_LATIN + "," +
                Tb_tanaman.KEY_KHASIAT + "," +
                Tb_tanaman.KEY_SENYAWA +
                " FROM " + Tb_tanaman.TABLE
                + " WHERE " +
                Tb_tanaman.KEY_NAMA_LOKAL + " LIKE '" + name +"'";// It's a good practice to use parameter ?, instead of concatenate string


        ArrayList<HashMap<String, String>> posList = new ArrayList<HashMap<String, String>>();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> pos = new HashMap<String, String>();
                pos.put(Tb_tanaman.KEY_ID_TANAMAN, cursor.getString(cursor.getColumnIndex(Tb_tanaman.KEY_ID_TANAMAN)));
                pos.put(Tb_tanaman.KEY_NAMA_LOKAL, cursor.getString(cursor.getColumnIndex(Tb_tanaman.KEY_NAMA_LOKAL)));
                pos.put(Tb_tanaman.KEY_NAMA_LATIN, cursor.getString(cursor.getColumnIndex(Tb_tanaman.KEY_NAMA_LATIN)));
                pos.put(Tb_tanaman.KEY_KHASIAT, cursor.getString(cursor.getColumnIndex(Tb_tanaman.KEY_KHASIAT)));
                pos.put(Tb_tanaman.KEY_SENYAWA, cursor.getString(cursor.getColumnIndex(Tb_tanaman.KEY_SENYAWA)));
                posList.add(pos);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return posList;

    }

    public ArrayList<HashMap<String, String>> getTanamanById(int Id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT  " +
                Tb_tanaman.KEY_ID_TANAMAN + "," +
                Tb_tanaman.KEY_NAMA_LOKAL + "," +
                Tb_tanaman.KEY_NAMA_LATIN + "," +
                Tb_tanaman.KEY_KHASIAT + "," +
                Tb_tanaman.KEY_SENYAWA +
                " FROM " + Tb_tanaman.TABLE
                + " WHERE " +
                Tb_tanaman.KEY_ID_TANAMAN + "=?";


        ArrayList<HashMap<String, String>> posList = new ArrayList<HashMap<String, String>>();

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(Id) } );



        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> pos = new HashMap<String, String>();
                pos.put(Tb_tanaman.KEY_ID_TANAMAN, cursor.getString(cursor.getColumnIndex(Tb_tanaman.KEY_ID_TANAMAN)));
                pos.put(Tb_tanaman.KEY_NAMA_LOKAL, cursor.getString(cursor.getColumnIndex(Tb_tanaman.KEY_NAMA_LOKAL)));
                pos.put(Tb_tanaman.KEY_NAMA_LATIN, cursor.getString(cursor.getColumnIndex(Tb_tanaman.KEY_NAMA_LATIN)));
                pos.put(Tb_tanaman.KEY_KHASIAT, cursor.getString(cursor.getColumnIndex(Tb_tanaman.KEY_KHASIAT)));
                pos.put(Tb_tanaman.KEY_SENYAWA, cursor.getString(cursor.getColumnIndex(Tb_tanaman.KEY_SENYAWA)));

                posList.add(pos);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return posList;


    }

    public ArrayList<HashMap<String, String>> getAllTanaman(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT  " +
                Tb_tanaman.KEY_ID_TANAMAN + "," +
                Tb_tanaman.KEY_NAMA_LOKAL + "," +
                Tb_tanaman.KEY_NAMA_LATIN + "," +
                Tb_tanaman.KEY_KHASIAT + "," +
                Tb_tanaman.KEY_SENYAWA +
                " FROM " + Tb_tanaman.TABLE;


        ArrayList<HashMap<String, String>> posList = new ArrayList<HashMap<String, String>>();

        Cursor cursor = db.rawQuery(selectQuery, null );



        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> pos = new HashMap<String, String>();
                pos.put(Tb_tanaman.KEY_ID_TANAMAN, cursor.getString(cursor.getColumnIndex(Tb_tanaman.KEY_ID_TANAMAN)));
                pos.put(Tb_tanaman.KEY_NAMA_LOKAL, cursor.getString(cursor.getColumnIndex(Tb_tanaman.KEY_NAMA_LOKAL)));
                pos.put(Tb_tanaman.KEY_NAMA_LATIN, cursor.getString(cursor.getColumnIndex(Tb_tanaman.KEY_NAMA_LATIN)));
                pos.put(Tb_tanaman.KEY_KHASIAT, cursor.getString(cursor.getColumnIndex(Tb_tanaman.KEY_KHASIAT)));
                pos.put(Tb_tanaman.KEY_SENYAWA, cursor.getString(cursor.getColumnIndex(Tb_tanaman.KEY_SENYAWA)));

                posList.add(pos);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return posList;


    }





}
