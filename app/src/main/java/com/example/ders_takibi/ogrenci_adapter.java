package com.example.ders_takibi;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;

import java.util.ArrayList;
import java.util.Random;

import cn.pedant.SweetAlert.SweetAlertDialog;
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

//String idd="stdtable"+s;
// OGRENCİ LİSTESİ İÇİN SADECE
public class ogrenci_adapter extends RecyclerView.Adapter<ogrenci_adapter.tanimla> {
    Context context;
    ArrayList<ogrenci_model> data_arr;
    dbhelper dbhelp;
    int[] color_arr;
    Random r;
    CreateAlert createAlert;
    public ogrenci_adapter(Context context, ArrayList<ogrenci_model> data_arr) {
        this.context = context;
        this.data_arr = data_arr;
        dbhelp = new dbhelper(context);

         r =new Random();

        color_arr= new int[]{Color.parseColor("#eb4034"),Color.parseColor("#4831D4"),Color.parseColor("#fad744"),
                Color.parseColor("#f0a07c"),Color.parseColor("#2b3252"),Color.parseColor("#ccf381"),Color.parseColor("#1d1b1b"),Color.parseColor("#F96167")};
    }

    @NonNull
    @Override
    public ogrenci_adapter.tanimla onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.example_item, parent, false);

        // set the Context here
        context = parent.getContext();
        createAlert=new CreateAlert(context);
        return new tanimla(view);
    }

    @Override

    public void onBindViewHolder(@NonNull final ogrenci_adapter.tanimla holder, final int position) {
        TextDrawable drawable = TextDrawable.builder()
                .beginConfig()
                .width(170)  // width in px
                .height(170) // height in px
                .endConfig()
                .buildRound(data_arr.get(position).getIsim().substring(0,1).toUpperCase(), color_arr[holder.getAdapterPosition()%8]);
        String exp=dbhelp.son_odev("stdtable"+data_arr.get(position).getId());
        holder.son_odev.setText(exp);
        holder.photo.setImageDrawable(drawable);
        holder.isim.setText(data_arr.get(position).getIsim().substring(0,1).toUpperCase()+data_arr.get(position).getIsim().substring(1));
        holder.soyisim.setText(data_arr.get(position).getSoyisim().substring(0,1).toUpperCase()+data_arr.get(position).getSoyisim().substring(1));
        holder.saat.setText(data_arr.get(position).getSaat());

    /*    holder.editle_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vieww) {
                Toast.makeText(context, "editle butonuna tiklandi", Toast.LENGTH_LONG).show();
                int pos = holder.getAdapterPosition();
                Intent intent = new Intent(context, Ogrenci_Duzenle.class);
                String isim, soyisim, adres, velitel, veliad, tc;
                isim = data_arr.get(pos).getIsim();
                soyisim = data_arr.get(pos).getSoyisim();
                adres = data_arr.get(pos).getAdres();
                velitel = data_arr.get(pos).getVeli_tel();
                veliad = data_arr.get(pos).getVeli_isim();
                tc = data_arr.get(pos).getTc();


                intent.putExtra("ogrmodel_satir", new String[]{String.valueOf(data_arr.get(pos).getId()), isim, soyisim, tc, veliad, velitel, adres});
                //   Toast.makeText(view.getContext(),String.valueOf(pos)+"POSITIONNNNN",Toast.LENGTH_LONG).show();
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });*/
  /*      holder.sil_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos4delete = holder.getAdapterPosition();
                Boolean sonuc = dbhelp.delete_student(data_arr.get(pos4delete).getId());
                // BURAYA EMİN MİSİNİZ GELECEK
                if (sonuc) {
                    Toast.makeText(context, data_arr.get(pos4delete).getIsim() + " Basariyla Silindi", Toast.LENGTH_LONG).show();
                    data_arr.remove(pos4delete);
                    notifyItemRemoved(pos4delete);

                } else {
                    Toast.makeText(context, data_arr.get(pos4delete).getIsim() + " Silinemedi.Tekrar Deneyin", Toast.LENGTH_LONG).show();
                }
                //Toast.makeText(context,"sil butonuna tiklandi",Toast.LENGTH_LONG).show();
            }
        });*/
  holder.layout_click.setOnTouchListener(new View.OnTouchListener() {
      @Override
      public boolean onTouch(View view, MotionEvent motionEvent) {
            int action=motionEvent.getAction();
            if ( motionEvent.getAction()== MotionEvent.ACTION_HOVER_EXIT | motionEvent.getAction()==MotionEvent.ACTION_HOVER_MOVE | motionEvent.getAction()==MotionEvent.ACTION_MOVE | motionEvent.getAction()==MotionEvent.ACTION_SCROLL  ) {
                view.setBackgroundColor(Color.parseColor("#FFFFFF"));
                holder.layout_click.setAlpha((float) 1);
                holder.layout_click.setBackgroundColor(Color.parseColor("#FFFFFF"));
                holder.son_odev.setTextColor(Color.GRAY);
                holder.isim.setTextColor(Color.BLACK);
                holder.soyisim.setTextColor(Color.BLACK);



          }
            else if (motionEvent.getAction() == android.view.MotionEvent.ACTION_DOWN ) {




                view.setBackgroundColor(Color.parseColor("#a0a0a0"));
                holder.layout_click.setAlpha((float) 0.9);
                holder.layout_click.setBackgroundColor(Color.parseColor("#a0a0a0"));
                holder.son_odev.setTextColor(Color.WHITE);
                holder.isim.setTextColor(Color.parseColor("white"));
                holder.soyisim.setTextColor(Color.parseColor("white"));
            }
            else{
                view.setBackgroundColor(Color.parseColor("#FFFFFF"));
                holder.layout_click.setAlpha((float) 1);
                holder.layout_click.setBackgroundColor(Color.parseColor("#FFFFFF"));
                holder.isim.setTextColor(Color.BLACK);
                holder.son_odev.setTextColor(Color.GRAY);
                holder.soyisim.setTextColor(Color.BLACK);
            }
            if (motionEvent.getAction() == android.view.MotionEvent.ACTION_UP | motionEvent.getAction()== MotionEvent.ACTION_BUTTON_PRESS){
                view.setBackgroundColor(Color.parseColor("#FFFFFF"));
                holder.layout_click.setAlpha((float) 1);
                holder.layout_click.setBackgroundColor(Color.parseColor("#FFFFFF"));
                holder.isim.setTextColor(Color.BLACK);
                holder.son_odev.setTextColor(Color.GRAY);
                holder.soyisim.setTextColor(Color.BLACK);
                int pos = holder.getAdapterPosition();
                Intent intent = new Intent(context, Ogrenci_Duzenle.class);
                String isim, soyisim, adres, velitel, veliad, tc;
                isim = data_arr.get(pos).getIsim();
                soyisim = data_arr.get(pos).getSoyisim();
                adres = data_arr.get(pos).getAdres();
                velitel = data_arr.get(pos).getVeli_tel();
                veliad = data_arr.get(pos).getVeli_isim();
                tc = data_arr.get(pos).getTc();
                intent.putExtra("ogrmodel_satir", new String[]{String.valueOf(data_arr.get(pos).getId()), isim, soyisim, tc, veliad, velitel, adres});
                //   Toast.makeText(view.getContext(),String.valueOf(pos)+"POSITIONNNNN",Toast.LENGTH_LONG).show();
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }



          return true;
      }


  });
        holder.layout_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                int pos = holder.getAdapterPosition();
                Intent intent = new Intent(context, Ogrenci_Duzenle.class);
                String isim, soyisim, adres, velitel, veliad, tc;
                isim = data_arr.get(pos).getIsim();
                soyisim = data_arr.get(pos).getSoyisim();
                adres = data_arr.get(pos).getAdres();
                velitel = data_arr.get(pos).getVeli_tel();
                veliad = data_arr.get(pos).getVeli_isim();
                tc = data_arr.get(pos).getTc();


                intent.putExtra("ogrmodel_satir", new String[]{String.valueOf(data_arr.get(pos).getId()), isim, soyisim, tc, veliad, velitel, adres});
                //   Toast.makeText(view.getContext(),String.valueOf(pos)+"POSITIONNNNN",Toast.LENGTH_LONG).show();
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }
   public ItemTouchHelper.SimpleCallback simpleCallback=new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }




       @Override
       public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int direction) {
           final int pos4delete = viewHolder.getAdapterPosition();

           if (direction == ItemTouchHelper.LEFT) {
                    createAlert.eminMisiniz("Öğrenci bilgileri tamamen silinecek!")
                       .setCancelText("Hayır")
                       .setConfirmText("Evet")
                       .showCancelButton(true)
                       .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                           @Override
                           public void onClick(SweetAlertDialog sweetAlertDialog) {
                           // viewHolder.itemView.setBackgroundColor(Color.WHITE);
                               sweetAlertDialog.cancel();

                        notifyItemChanged(pos4delete);
                           }
                       })
                       .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                           @Override
                           public void onClick(SweetAlertDialog sDialog) {
                               Boolean sonuc = dbhelp.delete_student(data_arr.get(pos4delete).getId());
                               if (sonuc){
                                   sDialog.setTitleText("").setContentText("Başarıyla silindi.")
                                           .showCancelButton(false)
                                           .setConfirmText("Tamam")
                                           .setConfirmClickListener(null)
                                           .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);

                                   data_arr.remove(pos4delete);
                                   notifyItemRemoved(pos4delete);

                               }
                               else{
                                   sDialog.setContentText("Silinemedi. Sonra tekrar deneyin!")
                                           .showCancelButton(false)
                                           .setConfirmText("Tamam")
                                           .setConfirmClickListener(null)
                                           .changeAlertType(SweetAlertDialog.WARNING_TYPE);
                               }

                           }
                       }).show();
       }


        }

       @Override
       public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
           new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                   .addSwipeLeftBackgroundColor(ContextCompat.getColor(context,R.color.color_red))
                   .addSwipeLeftActionIcon(R.drawable.ic_delete_black_24dp)

                   .create()
                   .decorate();
           super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
       }
   };

    @Override
    public int getItemCount() {
        return data_arr.size();
    }




    public class tanimla extends RecyclerView.ViewHolder {
        TextView isim, soyisim,son_odev,saat;
        ImageView photo;
     // Button editle_btn, sil_btn;
        CardView layout_click;


        public tanimla(@NonNull View itemView) {
            super(itemView);



                isim = itemView.findViewById(R.id.isimex);
                son_odev=itemView.findViewById(R.id.son_odev_txt);
                photo=itemView.findViewById(R.id.photo_txt);
                soyisim = itemView.findViewById(R.id.soyisimex);
            //    editle_btn = itemView.findViewById(R.id.editbtn);
                layout_click=itemView.findViewById(R.id.layout_click);
              //  sil_btn = itemView.findViewById(R.id.deletebtn);
                saat=itemView.findViewById(R.id.saat_txt);


        }


    }
}
