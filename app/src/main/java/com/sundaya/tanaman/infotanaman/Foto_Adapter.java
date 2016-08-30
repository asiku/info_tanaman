package com.sundaya.tanaman.infotanaman;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Userpc on 10/08/2016.
 */

public class Foto_Adapter extends RecyclerView.Adapter<Foto_Adapter.Foto>{


    Context mContext;


    public  class Foto extends RecyclerView.ViewHolder{
        CardView cv;
        TextView namalokal;
        TextView namalatin;
        ImageView fototanaman;

        Foto(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            namalokal = (TextView)itemView.findViewById(R.id.txt_row_nama_lokal);
            namalatin = (TextView)itemView.findViewById(R.id.txt_row_nama_latin);
            fototanaman = (ImageView)itemView.findViewById(R.id.img_row_photo_tanaman);
        }
    }

    List<Foto_data> tanaman;

    Foto_Adapter(List<Foto_data> tanaman, Context context){

        this.mContext=context;
        this.tanaman = tanaman;
    }



    @Override
    public Foto onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_foto, viewGroup, false);
        Foto pvh = new Foto(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(Foto tanamanViewHolder, final int i) {
        tanamanViewHolder.namalokal.setText(tanaman.get(i).nama_lokal);
        tanamanViewHolder.namalatin.setText(tanaman.get(i).nama_latin);

        Typeface face= Typeface.createFromAsset(mContext.getAssets(), "fonts/Slabo27px-Regular.ttf");
        tanamanViewHolder.namalokal.setTypeface(face);

        Typeface face2= Typeface.createFromAsset(mContext.getAssets(), "fonts/Roboto-Regular.ttf");
        tanamanViewHolder.namalatin.setTypeface(face2);

        Picasso.with(mContext).load("file:///"+tanaman.get(i).path) .fit()
                .centerCrop().into(tanamanViewHolder.fototanaman);

//        tanamanViewHolder.cv.setCardBackgroundColor(tanaman.(i).isSelected() ? Color.LTGRAY : Color.WHITE);

       // tanamanViewHolder.fototanaman.setImageResource(tanaman.get(i).fototanaman);
        tanamanViewHolder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle b=new Bundle();

                Tb_Foto_Crud crud_foto=new Tb_Foto_Crud(mContext);


                ArrayList<HashMap<String, String>> getTanaman = crud_foto.getAllFotoById(tanaman.get(i).id_tanaman);

                ArrayList<String> lst = new ArrayList<String>();

                for(int i=0;i<getTanaman.size();i++){

                    lst.add(i,getTanaman.get(i).get(Tb_Foto.KEY_LOKASI));


                }

                b.putString(Tb_tanaman.KEY_NAMA_LOKAL,tanaman.get(i).nama_lokal);
                b.putString(Tb_tanaman.KEY_NAMA_LATIN,tanaman.get(i).nama_latin);
                b.putString(Tb_tanaman.KEY_KHASIAT,tanaman.get(i).khasiat);
                b.putString(Tb_tanaman.KEY_SENYAWA,tanaman.get(i).senyawa);

                b.putStringArrayList("editlist",lst);

                Intent intent = new Intent(mContext, Edit_list_Activity.class);
                intent.putExtras(b);

                mContext.startActivity(intent);


            }
        });
    }

    @Override
    public int getItemCount() {
        return tanaman.size();
    }

}
