package com.example.ders_takibi;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;

import java.util.ArrayList;

public class liste4odev_goruntule_adapter extends RecyclerView.Adapter<liste4odev_goruntule_adapter.tanimla> {
    Context context;

    private static int VIEW_TYPE_ITEM=0;
    private static  int VIEW_TYPE_DIVIDER=1;
    ArrayList <ogrenci_model> data_arr;
    dbhelper helper;
    int[] color_arr;
    public liste4odev_goruntule_adapter(Context context,ArrayList<ogrenci_model> data_arr){
        this.context=context;
        this.data_arr=data_arr;
        helper=new dbhelper(context);
        color_arr= new int[]{Color.parseColor("#eb4034"),Color.parseColor("#4831D4"),Color.parseColor("#fad744"),
                Color.parseColor("#f0a07c"),Color.parseColor("#2b3252"),Color.parseColor("#ccf381"),Color.parseColor("#1d1b1b"),Color.parseColor("#F96167")};
    }
    @NonNull
    @Override
    public liste4odev_goruntule_adapter.tanimla onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.example_item2 , parent, false);
        return new tanimla(view,viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull final liste4odev_goruntule_adapter.tanimla holder, int position) {
            final String foto=data_arr.get(position).getIsim().substring(0,1).toUpperCase();
            final int fotocolor=color_arr[position%8];
        TextDrawable drawable = TextDrawable.builder()
                .beginConfig()
                .width(170)  // width in px
                .height(170) // height in px
                .endConfig()
                .buildRound(foto, fotocolor);
     //   ArrayList <ogrenci_kisisel_model> odev_son=helper.odev_al();
      //  Toast.makeText(context,odev_son.get(0).getTarih().toString(),Toast.LENGTH_LONG).show();


        //BURASI
//        ArrayList<ogrenci_kisisel_model> arrlist=helper.son_odev2("stdtable"+(data_arr.get(position).getId()));
//        for (int i=0;i<arrlist.size();i++){
//            System.out.println("ROW1"+arrlist.get(i).getTarih());
//        }
//
//        ogrenci_kisisel_model okm=arrlist.get(arrlist.size()-1);
//        String tarih=okm.getTarih();


        final String expression=helper.son_odev("stdtable"+(data_arr.get(position).getId()));
//        if (tarih.equals("")){
//            holder.son_odev2.setText("Ödev bulunamadı!");
//        }
//        else{
//            holder.son_odev2.setText("Son ödev "+tarih);
//        }
        holder.son_odev2.setText(expression);
        holder.rehber_im.setImageDrawable(drawable);
        holder.saattxt.setText(data_arr.get(position).getSaat());
       // Toast.makeText(context,data_arr.get(position).getSaat(),Toast.LENGTH_LONG).show();
       // holder.saattxt.setText("12:12");
        holder.isim.setText(data_arr.get(position).getIsim().substring(0,1).toUpperCase()+data_arr.get(position).getIsim().substring(1));
        holder.soyisim.setText(data_arr.get(position).getSoyisim().substring(0,1).toUpperCase()+data_arr.get(position).getSoyisim().substring(1));
        holder.layout_click.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if (motionEvent.getAction()== MotionEvent.ACTION_HOVER_EXIT | motionEvent.getAction()==MotionEvent.ACTION_HOVER_MOVE | motionEvent.getAction()==MotionEvent.ACTION_MOVE | motionEvent.getAction()==MotionEvent.ACTION_SCROLL) {
                    view.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    holder.layout_click.setAlpha(1);
                    holder.layout_click.setBackgroundColor(Color.parseColor("#FFFFFF"));
                   // holder.layout_click.setAlpha((float) 0.5);
                    holder.isim.setTextColor(Color.BLACK);
                    holder.son_odev2.setTextColor(Color.GRAY);
                    holder.soyisim.setTextColor(Color.BLACK);

                }
                else if (motionEvent.getAction() == android.view.MotionEvent.ACTION_DOWN) {




                    view.setBackgroundColor(Color.parseColor("#a0a0a0"));
                    holder.layout_click.setBackgroundColor(Color.parseColor("#a0a0a0"));
                    holder.layout_click.setAlpha((float) 0.9);
                    holder.isim.setTextColor(Color.parseColor("white"));
                    holder.son_odev2.setTextColor(Color.WHITE);
                    holder.soyisim.setTextColor(Color.parseColor("white"));
                }
                else{
                    view.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    holder.layout_click.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    holder.layout_click.setAlpha(1);
                   // holder.layout_click.setAlpha((float) 0.5);
                    holder.isim.setTextColor(Color.BLACK);
                    holder.son_odev2.setTextColor(Color.GRAY);
                    holder.soyisim.setTextColor(Color.BLACK);
                }
                if (motionEvent.getAction() == android.view.MotionEvent.ACTION_UP | motionEvent.getAction()== MotionEvent.ACTION_BUTTON_PRESS){

                    int pos = holder.getAdapterPosition();
                    Intent intent = new Intent(context, Ogrenci_Durumu.class);
                    intent.putExtra("isim",data_arr.get(pos).getIsim().substring(0,1).toUpperCase()+data_arr.get(pos).getIsim().substring(1)+" "+data_arr.get(pos).getSoyisim().substring(0,1).toUpperCase()+data_arr.get(pos).getSoyisim().substring(1));

                    holder.layout_click.setAlpha(1);
                    intent.putExtra("id",data_arr.get(pos).getId());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }


                return false;
            }


        });
        holder.layout_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = holder.getAdapterPosition();
                Intent intent = new Intent(context, Ogrenci_Durumu.class);

                intent.putExtra("isim",data_arr.get(pos).getIsim().substring(0,1).toUpperCase()+data_arr.get(pos).getIsim().substring(1)+" "+data_arr.get(pos).getSoyisim().substring(0,1).toUpperCase()+data_arr.get(pos).getSoyisim().substring(1));
                intent.putExtra("id",data_arr.get(pos).getId());
                //intent.putExtra("son", expression);
                intent.putExtra("fotocolor",fotocolor);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return data_arr.size();
    }



    public class tanimla extends RecyclerView.ViewHolder {
        TextView isim, soyisim,son_odev2,saattxt;
        ImageView rehber_im;
        CardView layout_click;

        public tanimla(@NonNull View itemView,int viewType) {
            super(itemView);
            isim = itemView.findViewById(R.id.isimex3);
            soyisim = itemView.findViewById(R.id.soyisimex3);
            rehber_im=(ImageView) itemView.findViewById(R.id.rehber_txt);
            son_odev2=(TextView) itemView.findViewById(R.id.odevtxt) ;
            layout_click=itemView.findViewById(R.id.layout_exitem3);
            saattxt=itemView.findViewById(R.id.saat_txt2);


        }
    }
}
