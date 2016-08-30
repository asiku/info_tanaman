package com.sundaya.tanaman.infotanaman;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.name;

/**
 * Created by Userpc on 10/08/2016.
 */

public class Foto_data {

    String nama_lokal;
    String nama_latin;
    String khasiat;
    String senyawa;
    String path;
    int id_tanaman;

    Foto_data(String nama_lokal, String nama_latin, String khasiat, String senyawa,int id_tanaman,String path) {
        this.nama_lokal = nama_lokal;
        this.nama_latin = nama_latin;
        this.khasiat = khasiat;
        this.senyawa = senyawa;
        this.id_tanaman = id_tanaman;
        this.path=path;
    }



}
