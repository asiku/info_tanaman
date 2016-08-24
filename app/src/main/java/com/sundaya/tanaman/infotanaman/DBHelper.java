package com.sundaya.tanaman.infotanaman;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.sundaya.tanaman.infotanaman.Tb_tanaman.TABLE;

/**
 * Created by Userpc on 10/08/2016.
 */




public class DBHelper extends SQLiteOpenHelper {

    //edit ke-1
    private static final int DATABASE_VERSION = 6;

    // Database Name
    private static final String DATABASE_NAME = "tanaman.db";

    public DBHelper(Context context ) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE_TANAMAN = "CREATE TABLE " + Tb_tanaman.TABLE + "("
                + Tb_tanaman.KEY_ID_TANAMAN + " integer primary key autoincrement,"
                + Tb_tanaman.KEY_NAMA_LOKAL + " TEXT NOT NULL, "
                + Tb_tanaman.KEY_NAMA_LATIN + " TEXT, "
                + Tb_tanaman.KEY_KHASIAT + " TEXT NOT NULL, "
                + Tb_tanaman.KEY_SENYAWA + " TEXT )";

        db.execSQL(CREATE_TABLE_TANAMAN);



        String CREATE_TABLE_FOTO = "CREATE TABLE " + Tb_Foto.TABLE + "("
                + Tb_Foto.KEY_ID_FOTO + " integer primary key autoincrement,"
                + Tb_Foto.KEY_ID_TANAMAN + " INTEGER NOT NULL, "
                + Tb_Foto.KEY_LOKASI + " TEXT NOT NULL)";

        db.execSQL(CREATE_TABLE_FOTO);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + Tb_tanaman.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + Tb_Foto.TABLE);

        onCreate(db);

    }

}
