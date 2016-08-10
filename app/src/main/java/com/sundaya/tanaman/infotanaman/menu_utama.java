package com.sundaya.tanaman.infotanaman;

import android.content.Intent;

import android.os.Bundle;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

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
import java.util.ArrayList;
import java.util.List;


public class menu_utama extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {



    private boolean toggle;
    private Menu menu;


    //Recyclerview Variabel
    private List<Foto_data> persons;
    private RecyclerView rv;
    private StaggeredGridLayoutManager mStaggeredLayoutManager;

    private void initializeData(){
        persons = new ArrayList<>();
        persons.add(new Foto_data("Emma Wilson", "23 years old",R.drawable.plant));
        persons.add(new Foto_data("Lavery Maiss", "25 years old",R.drawable.user));
        persons.add(new Foto_data("Lillie Watts", "35 years old",R.drawable.tanamanlogo));
        persons.add(new Foto_data("Emma Wilson", "23 years old",R.drawable.plant));
        persons.add(new Foto_data("Lavery Maiss", "25 years old",R.drawable.user));
        persons.add(new Foto_data("Lillie Watts", "35 years old",R.drawable.tanamanlogo));
        persons.add(new Foto_data("Emma Wilson", "23 years old",R.drawable.plant));
        persons.add(new Foto_data("Lavery Maiss", "25 years old",R.drawable.user));
        persons.add(new Foto_data("Lillie Watts", "35 years old",R.drawable.tanamanlogo));
        persons.add(new Foto_data("Emma Wilson", "23 years old",R.drawable.plant));
        persons.add(new Foto_data("Lavery Maiss", "25 years old",R.drawable.user));
        persons.add(new Foto_data("Lillie Watts", "35 years old",R.drawable.tanamanlogo));
    }

    private void initializeAdapter(){
        Foto_Adapter adapter = new Foto_Adapter(persons);
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
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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

        initializeData();
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

