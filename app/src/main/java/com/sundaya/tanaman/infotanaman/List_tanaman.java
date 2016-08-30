package com.sundaya.tanaman.infotanaman;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import android.database.Cursor;
import java.util.List;

public class List_tanaman extends AppCompatActivity {

    private boolean cekcamera = false;

    private String path;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    private static int RESULT_GALERY_IMAGE = 2;


    public final String APP_TAG = "FotoTanaman";
    public String photoFileName = "tmp";

    //Recyclerview Variabel
    List<List_data> list_data;
    private RecyclerView rv;
    private StaggeredGridLayoutManager mStaggeredLayoutManager;

    List_Adapter adapter;

    Bundle b = new Bundle();
    Bundle c;


    ArrayList<String> lst = new ArrayList<String>();
    ArrayList<String> lstbundle = new ArrayList<String>();


    private void initializeAdapter() {
        adapter = new List_Adapter(list_data, this);
        rv.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_tanaman);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Intent intent = getIntent();
        c = intent.getExtras();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Ambil Foto", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                dispatchTakePictureIntent();
                cekcamera = true;
            }
        });

        list_data = new ArrayList<>();

        //add item
        rv = (RecyclerView) findViewById(R.id.Rvlistfoto);

        mStaggeredLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);

        rv.setLayoutManager(mStaggeredLayoutManager);
        rv.setHasFixedSize(true);

        //    initializeData();
        //  initializeAdapter();

        if (c != null) {


            lstbundle = c.getStringArrayList("listfoto");

            for (int i = 0; i < lstbundle.size(); i++) {

                list_data.add(new List_data(lstbundle.get(i)));

                //posisi edit 23
                //  lst.add(lstbundle.get(i));
            }


            initializeAdapter();

        } else {


            dispatchTakePictureIntent();
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_list_tanaman, menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        if (id == R.id.action_camera_roll) {
//
//            return true;
//        }
//        return super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.action_camera_roll:

                if (c != null) {
                    if (c.getString(Tb_tanaman.KEY_NAMA_LOKAL) != null && c.getString(Tb_tanaman.KEY_NAMA_LATIN) != null && c.getString(Tb_tanaman.KEY_KHASIAT) != null) {

                        b.putString(Tb_tanaman.KEY_NAMA_LOKAL, c.getString(Tb_tanaman.KEY_NAMA_LOKAL));
                        b.putString(Tb_tanaman.KEY_NAMA_LATIN, c.getString(Tb_tanaman.KEY_NAMA_LATIN));
                        b.putString(Tb_tanaman.KEY_KHASIAT, c.getString(Tb_tanaman.KEY_KHASIAT));


                    }
                    if (c.getString(Tb_tanaman.KEY_SENYAWA) != null) {
                        b.putString(Tb_tanaman.KEY_SENYAWA, c.getString(Tb_tanaman.KEY_SENYAWA));
                    }
                    if (c.getString(Tb_Foto.KEY_LOKASI) != null) {
                        b.putString(Tb_Foto.KEY_LOKASI, c.getString(Tb_Foto.KEY_LOKASI));
                    }


                    //b.putStringArrayList("listfoto", lst);

                    //ubah disini
                    for (int i = 0; i < adapter.list_foto.size(); i++) {
                        lst.add(i, adapter.list_foto.get(i).path);
                    }
                    b.putStringArrayList("listfoto", lst);
                    //

                    Log.d("kampret3", " " + lst.size());

                    Log.d("kampret4", " " + adapter.list_foto.size());

                } else {
                    for (int i = 0; i < adapter.list_foto.size(); i++) {
                        lst.add(i, adapter.list_foto.get(i).path);
                    }
                    b.putStringArrayList("listfoto", lst);
                }

                Intent intent = new Intent(List_tanaman.this, Fototanaman.class);
                intent.putExtras(b);
                startActivity(intent);

                finish();

                return true;
            case R.id.action_select_all:

                Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_GALERY_IMAGE);

                return true;

            case android.R.id.home:
                if(b!=null) {
                    Toast.makeText(getApplicationContext(), "Maaf tidak Bisa Exit karena ada Foto yang Sudah di Proses, Anda Harus Tap Icon Checklist untuk keluar!", Toast.LENGTH_LONG).show();

                }
                else{
                    finish();
                }
                return true;
            default:

                return super.onOptionsItemSelected(item);

        }
    }


    private void dispatchTakePictureIntent() {

        //  if (photoFileName != null) {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, getPhotoFileUri(photoFileName));

        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
//            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//
//            }
//        } else {
//            Toast.makeText(getApplicationContext(), "Isi Nama Lokal Terlebih Dahulu", Toast.LENGTH_LONG).show();
//        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE) {

            if (resultCode == RESULT_OK) {
                if (path != null) {


                    if (cekcamera) {
                        int position = list_data.size();
                        Log.d("jml ", "" + position + " " + path);
                        list_data.add(position, new List_data(path));

                        adapter.notifyItemInserted(position);

                        //posisi edit 23
                        //lst.add(path);


                    } else {

                        list_data.add(new List_data(path));
                        initializeAdapter();

                        //posisi edit 23
                        //lst.add(path);
                    }

                    //posisi edit 23
                    //b.putStringArrayList("listfoto", lst);

                }
            } else {

                Toast.makeText(this, "Foto Gagal di Ambil!",
                        Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == RESULT_GALERY_IMAGE) {

            if (resultCode == RESULT_OK) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

               for(int i=0;i<filePathColumn.length;i++) {

                   int columnIndex = cursor.getColumnIndex(filePathColumn[i]);
                   String picturePath = cursor.getString(columnIndex);

                   int position = list_data.size();
                   Log.d("jml ", "" + position + " " + path);
                   list_data.add(position, new List_data(picturePath));

                   adapter.notifyItemInserted(position);

                   cursor.close();
               }
            }
            else{
                Toast.makeText(this, "Foto Gagal di Ambil!",
                        Toast.LENGTH_SHORT).show();
            }


        } else {
            Toast.makeText(this, "Foto Gagal di Ambil, Ambil ulang Lagi!",
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

        path = mediaStorageDir.getPath() + File.separator
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


}
