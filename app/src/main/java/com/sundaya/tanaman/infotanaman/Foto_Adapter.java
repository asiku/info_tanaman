package com.sundaya.tanaman.infotanaman;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

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
    public void onBindViewHolder(Foto tanamanViewHolder, int i) {
        tanamanViewHolder.namalokal.setText(tanaman.get(i).nama_lokal);
        tanamanViewHolder.namalatin.setText(tanaman.get(i).nama_latin);

        Picasso.with(mContext).load("file:///"+tanaman.get(i).path).into(tanamanViewHolder.fototanaman);

//        tanamanViewHolder.cv.setCardBackgroundColor(tanaman.(i).isSelected() ? Color.LTGRAY : Color.WHITE);

       // tanamanViewHolder.fototanaman.setImageResource(tanaman.get(i).fototanaman);
    }

    @Override
    public int getItemCount() {
        return tanaman.size();
    }

}
