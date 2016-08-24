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

public class Tb_Foto_Crud {

    private DBHelper dbHelper;

    public Tb_Foto_Crud(Context context){
        dbHelper =new DBHelper(context);
    }

    public int Insert(Tb_Foto foto_item){
        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Tb_Foto.KEY_ID_TANAMAN,foto_item.id_tanaman);
        values.put(Tb_Foto.KEY_LOKASI,foto_item.nama_lokasi);


        // Inserting Row
        long id_foto = db.insert(Tb_Foto.TABLE, null, values);
        db.close(); // Closing database connection
        return (int) id_foto;
    }

    public void Delete(int id_foto){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // It's a good practice to use parameter ?, instead of concatenate string
        db.delete(Tb_Foto.TABLE, Tb_Foto.KEY_ID_FOTO + "= ?", new String[] { String.valueOf(id_foto) });
        db.close(); // Closing database connection

    }


    public Tb_Foto getFotoById(int Id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                Tb_Foto.KEY_ID_TANAMAN + "," +
                Tb_Foto.KEY_ID_FOTO + "," +
                Tb_Foto.KEY_LOKASI +
                " FROM " + Tb_Foto.TABLE
                + " WHERE " +
                Tb_Foto.KEY_ID_TANAMAN + "=?";// It's a good practice to use parameter ?, instead of concatenate string

        int iCount =0;
        Tb_Foto pos = new Tb_Foto();

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(Id) } );

        if (cursor.moveToFirst()) {
            //   do {
            pos.id_foto =cursor.getInt(cursor.getColumnIndex(Tb_Foto.KEY_ID_FOTO));
            pos.id_tanaman =cursor.getInt(cursor.getColumnIndex(Tb_Foto.KEY_ID_TANAMAN));
            pos.nama_lokasi  =cursor.getString(cursor.getColumnIndex(Tb_Foto.KEY_LOKASI));

            //  } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return pos;
    }



    public ArrayList<HashMap<String, String>> getAllFotoById(int Id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT  " +
                Tb_Foto.KEY_ID_TANAMAN + "," +
                Tb_Foto.KEY_ID_FOTO + "," +
                Tb_Foto.KEY_LOKASI +
                " FROM " + Tb_Foto.TABLE
                + " WHERE " +
                Tb_Foto.KEY_ID_TANAMAN + "=?";


        ArrayList<HashMap<String, String>> posList = new ArrayList<HashMap<String, String>>();

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(Id) } );



        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> pos = new HashMap<String, String>();
                pos.put(Tb_Foto.KEY_ID_TANAMAN, cursor.getString(cursor.getColumnIndex(Tb_Foto.KEY_ID_TANAMAN)));
                pos.put(Tb_Foto.KEY_ID_FOTO, cursor.getString(cursor.getColumnIndex(Tb_Foto.KEY_ID_FOTO)));
                pos.put(Tb_Foto.KEY_LOKASI, cursor.getString(cursor.getColumnIndex(Tb_Foto.KEY_LOKASI)));

                posList.add(pos);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return posList;


    }
}
