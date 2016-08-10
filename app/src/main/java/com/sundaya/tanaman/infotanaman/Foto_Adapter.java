package com.sundaya.tanaman.infotanaman;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Userpc on 10/08/2016.
 */

public class Foto_Adapter extends RecyclerView.Adapter<Foto_Adapter.Foto>{



    public  class Foto extends RecyclerView.ViewHolder{
        CardView cv;
        TextView personName;
        TextView personAge;
        ImageView personPhoto;

        Foto(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            personName = (TextView)itemView.findViewById(R.id.person_name);
            personAge = (TextView)itemView.findViewById(R.id.person_age);
            personPhoto = (ImageView)itemView.findViewById(R.id.person_photo);
        }
    }

    List<Foto_data> persons;

    Foto_Adapter(List<Foto_data> persons){
        this.persons = persons;
    }



    @Override
    public Foto onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_foto, viewGroup, false);
        Foto pvh = new Foto(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(Foto personViewHolder, int i) {
        personViewHolder.personName.setText(persons.get(i).name);
        personViewHolder.personAge.setText(persons.get(i).age);
        personViewHolder.personPhoto.setImageResource(persons.get(i).photoId);
    }

    @Override
    public int getItemCount() {
        return persons.size();
    }

}
