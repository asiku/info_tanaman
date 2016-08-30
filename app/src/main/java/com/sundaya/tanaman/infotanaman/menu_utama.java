package com.sundaya.tanaman.infotanaman;

import android.content.Intent;

import android.os.Bundle;

import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.support.v7.widget.SearchView;

public class menu_utama extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener  {


    private boolean toggle=true;
    private Menu menu;
    public final String APP_TAG = "FotoTanaman";

    //Recyclerview Variabel
    private List<Foto_data> tanaman;
    private RecyclerView rv;
    private StaggeredGridLayoutManager mStaggeredLayoutManager;

    //db con
    Tb_tanaman_Crud crud_tanaman;
    Tb_Foto_Crud crud_foto;

    Tb_tanaman tb_tanaman=new Tb_tanaman();
    Tb_Foto tb_foto=new Tb_Foto();


    private void initializeData(int flag,String txt){

        File mediaStorageDir = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                APP_TAG);

        crud_tanaman=new Tb_tanaman_Crud(this);

        crud_foto=new Tb_Foto_Crud(this);

        ArrayList<HashMap<String, String>> getTanaman=new   ArrayList<HashMap<String, String>>();

        if(flag==0) {
            getTanaman = crud_tanaman.getAllTanaman();
        }
        else{
            getTanaman = crud_tanaman.filterTanamanByName(txt);
        }

        tanaman = new ArrayList<>();

        Log.d("Ukuran : ",String.valueOf(getTanaman.size()));

        for(int i=0;i<getTanaman.size();i++){


            tb_foto=crud_foto.getFotoById(Integer.valueOf(getTanaman.get(i).get(tb_foto.KEY_ID_TANAMAN)));

            Log.d("nama lokasi  ",""+getTanaman.get(i).get(tb_foto.KEY_LOKASI));

            tanaman.add(new Foto_data(getTanaman.get(i).get(tb_tanaman.KEY_NAMA_LOKAL),getTanaman.get(i).get(tb_tanaman.KEY_NAMA_LATIN),getTanaman.get(i).get(tb_tanaman.KEY_KHASIAT)
                    ,getTanaman.get(i).get(tb_tanaman.KEY_SENYAWA),Integer.valueOf(getTanaman.get(i).get(tb_tanaman.KEY_ID_TANAMAN)),tb_foto.nama_lokasi));
        }



    }

    private void initializeAdapter(){
        Foto_Adapter adapter = new Foto_Adapter(tanaman,this);
        rv.setAdapter(adapter);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_utama);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Ambil Foto", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                Intent intent = new Intent(menu_utama.this,List_tanaman.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        rv=(RecyclerView)findViewById(R.id.Rvfoto);

        mStaggeredLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
//        LinearLayoutManager llm = new LinearLayoutManager(this);
//        rv.setLayoutManager(llm);

        rv.setLayoutManager(mStaggeredLayoutManager);
        rv.setHasFixedSize(true);

        initializeData(0,"");
        initializeAdapter();


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_foto, menu);

        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mStaggeredLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
//        LinearLayoutManager llm = new LinearLayoutManager(this);
//        rv.setLayoutManager(llm);

                rv.setLayoutManager(mStaggeredLayoutManager);
                rv.setHasFixedSize(true);

                if(newText.isEmpty()){
                    initializeData(0,newText.toUpperCase());
                }else{
                    initializeData(1,newText.toUpperCase());
                }

                initializeAdapter();
                return true;
            }
        });
        this.menu = menu;
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_toggle) {
            toggle();
            return true;
        }
        else if (id == R.id.action_refresh) {



            mStaggeredLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
//        LinearLayoutManager llm = new LinearLayoutManager(this);
//        rv.setLayoutManager(llm);

            rv.setLayoutManager(mStaggeredLayoutManager);
            rv.setHasFixedSize(true);

            initializeData(0,"");
            initializeAdapter();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void toggle() {
        MenuItem item = menu.findItem(R.id.action_toggle);
        if (toggle) {
            mStaggeredLayoutManager.setSpanCount(2);
            item.setIcon(R.drawable.ic_action_list);
            item.setTitle("Show as list");
            toggle = false;
        } else {
            mStaggeredLayoutManager.setSpanCount(1);
            item.setIcon(R.drawable.ic_action_grid);
            item.setTitle("Show as grid");
            toggle = true;
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {

            Intent intent = new Intent(menu_utama.this,Fototanaman.class);
            startActivity(intent);

        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}

