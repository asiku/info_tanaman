package com.sundaya.tanaman.infotanaman;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Userpc on 20/08/2016.
 */

public class List_Adapter extends RecyclerView.Adapter<List_Adapter.List_foto> {
    Context mContext;



    public class List_foto extends RecyclerView.ViewHolder{
        CardView cv;
        ImageView img_list_foto;
        ImageView img_remove;

        List_foto(View itemView) {
            super(itemView);
            cv=(CardView) itemView.findViewById(R.id.cv_list);
            img_list_foto=(ImageView) itemView.findViewById(R.id.img_row_list_tanaman);
            img_remove=(ImageView) itemView.findViewById(R.id.img_row_remove_tanaman);
        }


    }


    List<List_data> list_foto;

    List_Adapter(List<List_data> list_foto,Context context){
        this.mContext=context;
        this.list_foto=list_foto;
    }

    @Override
    public List_foto onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_list_foto, viewGroup, false);
        List_foto pvh=new List_foto(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(List_foto listViewholder, final int i) {

        Picasso.with(mContext).load("file:///"+list_foto.get(i).path).into( listViewholder.img_list_foto);

        listViewholder.img_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list_foto.remove(i);
                notifyItemRemoved(i);
                notifyItemRangeChanged(i,list_foto.size());

            }
        });


    }




    @Override
    public int getItemCount() {
        return list_foto.size();
    }
}
