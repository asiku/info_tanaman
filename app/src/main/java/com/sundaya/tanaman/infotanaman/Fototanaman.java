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

import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.text.InputFilter;


public class Fototanaman extends AppCompatActivity {


    private String path;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    public final String APP_TAG = "FotoTanaman";
    public String photoFileName;
    private EditText txt_nama_lokal,txt_nama_latin,txt_nama_khasiat,txt_senyawa,txt_foto;
    private ImageView img_kamera;



    private Tb_tanaman_Crud crud_tanaman;
    private Tb_tanaman tb_tanaman=new Tb_tanaman();

    private Tb_Foto_Crud crud_foto;
    private Tb_Foto tb_foto=new Tb_Foto();

    ArrayList<String> lst=new ArrayList<String>();

    Bundle c=new Bundle();
    Bundle b=new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fototanaman);

        Intent intent=getIntent();
        b=intent.getExtras();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Button btn_save = (Button) findViewById(R.id.btn_save);
        txt_nama_lokal = (EditText) findViewById(R.id.txt_nama_lokal);

        txt_nama_lokal.setFilters(new InputFilter[]{new InputFilter.AllCaps()});;

        txt_nama_latin = (EditText) findViewById(R.id.txt_nama_latin);
        txt_nama_khasiat = (EditText) findViewById(R.id.txt_nama_khasiat);
        txt_senyawa = (EditText) findViewById(R.id.txt_nama_senyawa);
        txt_foto = (EditText) findViewById(R.id.txt_foto);

//        img_kamera = (ImageView) findViewById(R.id.img_kamera);

        txt_nama_lokal.addTextChangedListener(new Validinput(txt_nama_lokal));
        txt_nama_latin.addTextChangedListener(new Validinput(txt_nama_latin));
        txt_nama_khasiat.addTextChangedListener(new Validinput(txt_nama_khasiat));

        txt_foto.setHint("Foto");

        if(b!=null) {
            lst = b.getStringArrayList("listfoto");
            if(b.getString(tb_tanaman.KEY_NAMA_LOKAL)!=null&&b.getString(tb_tanaman.KEY_NAMA_LATIN)!=null&&b.getString(tb_tanaman.KEY_KHASIAT)!=null){
                txt_nama_lokal.setText(b.getString(tb_tanaman.KEY_NAMA_LOKAL));
                txt_nama_latin.setText(b.getString(tb_tanaman.KEY_NAMA_LATIN));
                txt_nama_khasiat.setText(b.getString(tb_tanaman.KEY_KHASIAT));
            }

            if(b.getString(tb_tanaman.KEY_SENYAWA)!=null){
                txt_senyawa.setText(b.getString(tb_tanaman.KEY_SENYAWA));
            }

            if(b.getString(tb_foto.KEY_LOKASI)!=null){
                txt_foto.setText(b.getString(tb_foto.KEY_LOKASI));
            }


        }


        if(lst!=null){
            txt_foto.setText(lst.size()+" Files");
        }
        else {
            txt_foto.setText("0 Files");
        }



        txt_foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validlokal()&&validlatin()&&validkhasiat()) {
                    c.putString(tb_tanaman.KEY_NAMA_LOKAL, txt_nama_lokal.getText().toString());
                    c.putString(tb_tanaman.KEY_NAMA_LATIN, txt_nama_latin.getText().toString());
                    c.putString(tb_tanaman.KEY_KHASIAT, txt_nama_khasiat.getText().toString());
                    c.putString(tb_tanaman.KEY_SENYAWA, txt_senyawa.getText().toString());
                    c.putString(tb_foto.KEY_LOKASI, txt_foto.getText().toString());

                    if(lst!=null) {
                        c.putStringArrayList("listfoto", lst);
                    }

                    Intent intent = new Intent(Fototanaman.this, List_tanaman.class);
                    intent.putExtras(c);
                    startActivity(intent);
                    finish();
                    //txt_foto.setHint("4 Files");
                }
                else{
                   // txt_foto.setError("Text Nama Lokal, atau Text Nama Latin atau Text Khasiat Harus Diisi!");
                    txt_nama_lokal.requestFocus();
                  Toast.makeText(getApplicationContext(), "Text Nama Lokal, atau Text Nama Latin atau Text Khasiat Harus Diisi!", Toast.LENGTH_LONG).show();
                }
            }
        });


        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                photoFileName = txt_nama_lokal.getText().toString();
//
//                Toast.makeText(getApplicationContext(), photoFileName, Toast.LENGTH_LONG).show();

             if(!txt_foto.getText().toString().equals("0 Files")) {
                 saveData();
             }
                else {
                 Toast.makeText(getApplicationContext(), "Foto Belum ada", Toast.LENGTH_LONG).show();
             }
            }
        });


//        img_kamera.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dispatchTakePictureIntent();
//            }
//        });

    }

    private void saveData(){
        if (validlokal()&&validlatin()&&validkhasiat()){
//         Toast.makeText(getApplicationContext(), path, Toast.LENGTH_LONG).show();

//            Log.d("Path",""+path);

         if(lst!=null) {

            try {

                crud_tanaman=new Tb_tanaman_Crud(this);
                crud_foto=new Tb_Foto_Crud(this);


                Tb_tanaman cari=new Tb_tanaman();

                cari=crud_tanaman.getTanamanByName(txt_nama_lokal.getText().toString());

Log.d("Car tanaman",""+cari.nama_lokal);

//                Toast.makeText(getApplicationContext(), "Nama Lokal : " + cari.nama_lokal.equalsIgnoreCase(txt_nama_lokal.getText().toString()) + " Sudah Ada!", Toast.LENGTH_LONG).show();

                if (cari.nama_lokal!=null) {
                    Toast.makeText(getApplicationContext(), "Nama Lokal : " + txt_nama_lokal.getText().toString() + " Sudah Ada!", Toast.LENGTH_LONG).show();
                    txt_nama_lokal.requestFocus();
                    txt_nama_lokal.setText("");
                } else {



                      tb_tanaman.nama_lokal = txt_nama_lokal.getText().toString();
                      tb_tanaman.nama_latin = txt_nama_latin.getText().toString();
                      tb_tanaman.khasiat = txt_nama_khasiat.getText().toString();
                      tb_tanaman.senyawa = txt_senyawa.getText().toString();

                      int idtanaman = crud_tanaman.Insert(tb_tanaman);

                      for (int i = 0; i < lst.size(); i++) {
                          tb_foto.id_tanaman = idtanaman;
                          tb_foto.nama_lokasi = lst.get(i);
                          crud_foto.Insert(tb_foto);
                      }
                      Toast.makeText(getApplicationContext(), "Data Tersimpan", Toast.LENGTH_LONG).show();



//                    txt_nama_lokal.setText("");
//                    txt_nama_latin.setText("");
//                    txt_nama_khasiat.setText("");
//                    txt_senyawa.setText("");

                }



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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_bersihin_teks:
                txt_nama_lokal.setText("");
                   txt_nama_latin.setText("");
                    txt_nama_khasiat.setText("");
                    txt_senyawa.setText("");
                txt_foto.setText("0 Files");
                return true;
            case android.R.id.home:
//                if(b!=null) {
//                    Toast.makeText(getApplicationContext(), "Maaf tidak Bisa Exit karena ada Inputan yang Sudah di Proses!", Toast.LENGTH_LONG).show();
//
//                }
//                else{
                    finish();
//                }
                return true;
            default:

                return super.onOptionsItemSelected(item);

        }
    }

}
