package com.sundaya.tanaman.infotanaman;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;

import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;




public class Fototanaman extends AppCompatActivity {


    private String path;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    public final String APP_TAG = "FotoTanaman";
    public String photoFileName;
    private EditText txt_nama_lokal,txt_nama_latin,txt_nama_khasiat,txt_senyawa;
    private ImageView img_kamera;


    private Tb_tanaman_Crud crud_tanaman;
    private Tb_tanaman tb_tanaman=new Tb_tanaman();

    private Tb_Foto_Crud crud_foto;
    private Tb_Foto tb_foto=new Tb_Foto();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fototanaman);


        crud_tanaman=new Tb_tanaman_Crud(this);
        crud_foto=new Tb_Foto_Crud(this);

        Button btn_save = (Button) findViewById(R.id.btn_save);
        txt_nama_lokal = (EditText) findViewById(R.id.txt_nama_lokal);
        txt_nama_latin = (EditText) findViewById(R.id.txt_nama_latin);
        txt_nama_khasiat = (EditText) findViewById(R.id.txt_nama_khasiat);
        txt_senyawa = (EditText) findViewById(R.id.txt_nama_senyawa);

        img_kamera = (ImageView) findViewById(R.id.img_kamera);

        txt_nama_lokal.addTextChangedListener(new Validinput(txt_nama_lokal));
        txt_nama_latin.addTextChangedListener(new Validinput(txt_nama_latin));
        txt_nama_khasiat.addTextChangedListener(new Validinput(txt_nama_khasiat));

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                photoFileName = txt_nama_lokal.getText().toString();
//
//                Toast.makeText(getApplicationContext(), photoFileName, Toast.LENGTH_LONG).show();

               saveData();
            }
        });


        img_kamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });

    }

    private void saveData(){
        if (validlokal()&&validlatin()&&validkhasiat()){
//            Toast.makeText(getApplicationContext(), "OK", Toast.LENGTH_LONG).show();
         if(!path.isEmpty()) {

            try {
                tb_tanaman.nama_lokal = txt_nama_lokal.getText().toString();
                tb_tanaman.nama_latin = txt_nama_latin.getText().toString();
                tb_tanaman.khasiat = txt_nama_latin.getText().toString();
                tb_tanaman.senyawa = txt_senyawa.getText().toString();

                int idtanaman = crud_tanaman.Insert(tb_tanaman);

                tb_foto.id_tanaman = idtanaman;
                tb_foto.nama_lokasi = path;
                crud_foto.Insert(tb_foto);
                Toast.makeText(getApplicationContext(), "Data Tersimpan", Toast.LENGTH_LONG).show();
            }
            catch (Exception e){
                Toast.makeText(getApplicationContext(), "Gagal simpan :"+e.getMessage(), Toast.LENGTH_LONG).show();
            }



         }
        else {
             Toast.makeText(getApplicationContext(), "Gambar Foto Belum di ambil", Toast.LENGTH_LONG).show();
         }
        }
    }

    private boolean validlatin(){
        if(txt_nama_latin.getText().toString().trim().isEmpty()){
            txt_nama_latin.setError("Text Nama Latin tidak boleh Kosong");
            txt_nama_latin.requestFocus();
            return false;

        }
        return true;
    }

    private boolean validkhasiat(){
        if(txt_nama_khasiat.getText().toString().trim().isEmpty()){
            txt_nama_khasiat.setError("Text Nama Khasiat tidak boleh Kosong");
            txt_nama_khasiat.requestFocus();
            return false;

        }


        return true;
    }
    private boolean validlokal(){

        if(txt_nama_lokal.getText().toString().trim().isEmpty()){
            txt_nama_lokal.setError("Text Nama Lokal tidak boleh Kosong");
            txt_nama_lokal.requestFocus();
            return false;

        }
        else{

            photoFileName = txt_nama_lokal.getText().toString();

        }



        return true;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_utama, menu);

        return true;
    }

    private void dispatchTakePictureIntent() {

        if (photoFileName != null) {

            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, getPhotoFileUri(photoFileName));

            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

            }
        } else {
            Toast.makeText(getApplicationContext(), "Isi Nama Lokal Terlebih Dahulu", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {


//            Intent intent = new Intent(Fototanaman.this,Fototanaman.class);
//            startActivity(intent);
        } else {
            Toast.makeText(this, "Picture wasn't taken!",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public Uri getPhotoFileUri(String fileName) {

        File mediaStorageDir = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                APP_TAG);

        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
            Log.d(APP_TAG, "failed to create directory");
        }

        path=mediaStorageDir.getPath() + File.separator
                + fileName + setdate() + ".jpg";
        return Uri.fromFile(new File(path));
    }

    public String setdate() {

        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yy HH:mm:ss",
                java.util.Locale.getDefault());

        Date today = Calendar.getInstance().getTime();

        String reportDate = df.format(today);
        String Str = new String(reportDate);

        String nw = Str.replace(':', '_');

        return nw.replace('/', '_');
    }


    private class Validinput implements TextWatcher{

private View view;

        private Validinput(View view){

            this.view=view;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
switch (view.getId()) {
    case R.id.txt_nama_lokal:
        validlokal();
        break;
    case R.id.txt_nama_latin:
        validlatin();
        break;
    case R.id.txt_nama_khasiat:
        validkhasiat();
        break;
}
        }
    }

}
