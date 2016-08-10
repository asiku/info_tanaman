package com.sundaya.tanaman.infotanaman;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;

import static com.sundaya.tanaman.infotanaman.R.id.txt_nama_lokal;


public class Fototanaman extends AppCompatActivity {


    static final int REQUEST_IMAGE_CAPTURE = 1;
    public final String APP_TAG = "FotoTanaman";
    public String photoFileName;
    private EditText txt_nama_lokal;
    private ImageView img_kamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fototanaman);


        Button btn_save = (Button) findViewById(R.id.btn_save);
        txt_nama_lokal = (EditText) findViewById(R.id.txt_nama_lokal);

        img_kamera = (ImageView) findViewById(R.id.img_kamera);


        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                photoFileName = txt_nama_lokal.getText().toString();

                Toast.makeText(getApplicationContext(), photoFileName, Toast.LENGTH_LONG).show();
            }
        });


        img_kamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });

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
            Toast.makeText(getApplicationContext(), "Nama", Toast.LENGTH_LONG).show();
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

        return Uri.fromFile(new File(mediaStorageDir.getPath() + File.separator
                + fileName + setdate() + ".jpg"));
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
