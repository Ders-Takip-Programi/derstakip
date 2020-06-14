package com.example.ders_takibi;

import android.content.Context;
import android.graphics.Canvas;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDateTime;
import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class odev_goruntule_adapter extends RecyclerView.Adapter<odev_goruntule_adapter.tanimla> {
    Context context;
    ArrayList<ogrenci_kisisel_model> data_arr;
    dbhelper dbhelp;
    String tablo_name;

    public odev_goruntule_adapter(Context context, ArrayList<ogrenci_kisisel_model> data_arr, String tablo_adi) {
        this.context = context;
        this.data_arr = data_arr;
        dbhelp = new dbhelper(context);
        tablo_name = tablo_adi;

    }

    @NonNull
    @Override
    public odev_goruntule_adapter.tanimla onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.example_item4, parent, false);
        context = parent.getContext();
        return new odev_goruntule_adapter.tanimla(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final odev_goruntule_adapter.tanimla holder, final int position) {
        kilitle(holder);

        //holder.kaydet.setText("Kilidi ");
        final int pos = holder.getAdapterPosition();
        holder.tarih.setText(data_arr.get(position).getTarih() + "    " + data_arr.get(position).getGun());
        // holder.tarih4gun.setText(data_arr.get(position).getTarih()+"\n"+data_arr.get(position).getGun());
        holder.artieksi.setText(data_arr.get(position).getArtieksi());
        holder.yapti_switch.setChecked(Boolean.parseBoolean(data_arr.get(position).getBool_yapti()));
        holder.degerlendir.setText(data_arr.get(position).getAciklama());
        holder.yapilan.setText(data_arr.get(position).getYapilan());
        holder.ssayisi.setText(data_arr.get(position).getSsayisi());
        holder.not.setText(data_arr.get(position).getPuan());
        holder.kaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.kaydet.setText("Değişiklikleri Kaydet");
                if (holder.artieksi.getInputType() == InputType.TYPE_NULL) {
                    kilit_ac(holder);
                } else {
                    String not, ss, yapilan, arti, degerlendir_str;
                    not = holder.not.getText().toString();
                    ss = holder.ssayisi.getText().toString();
                    yapilan = holder.yapilan.getText().toString();
                    arti = holder.artieksi.getText().toString();
                    degerlendir_str = holder.degerlendir.getText().toString();
                    String saat=" ";
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        saat = String.valueOf(LocalDateTime.now().getHour())+":"+String.valueOf(LocalDateTime.now().getMinute());
                    }
                    //HEPSİ BOS CONDITION
                    final String finalSaat = saat;
                    String ders_yaptimi="1";
                    if(  holder.yapilan.getText().toString().trim().equals("") && holder.ssayisi.getText().toString().trim().equals("") && holder.not.getText().toString().trim().equals("") && holder.artieksi.getText().toString().trim().equals("")){
                        ders_yaptimi="0";
                    }
                    final ogrenci_kisisel_model om = new ogrenci_kisisel_model(data_arr.get(pos).getId(), data_arr.get(pos).getTarih(),
                            data_arr.get(pos).getGun(), yapilan, ss, not, arti, degerlendir_str,finalSaat,ders_yaptimi);

                    new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE).setTitleText("Emin misiniz?").setContentText("Ödev bilgileri değiştirilecek!")
                            .setCancelText("Hayır")
                            .setConfirmText("Evet")
                            .setCancelText("Hayır")
                            .setConfirmText("Evet")
                            .showCancelButton(true)
                            .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    // viewHolder.itemView.setBackgroundColor(Color.WHITE);
                                    sweetAlertDialog.cancel();

                                    notifyItemChanged(pos);
                                    data_arr.set(pos,om);
                                    Ogrenci_Durumu.yap_array(data_arr);
                                }
                            })
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    Boolean guncel = dbhelp.odev_guncelle(om, tablo_name);
                                    if (guncel) {
                                        sDialog.setTitleText("").setContentText("Başarıyla güncellendi.")
                                                .showCancelButton(false)
                                                .setConfirmText("Tamam")
                                                .setConfirmClickListener(null)
                                                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);

                                        try {
                                         data_arr.set(pos,om);
                                            Ogrenci_Durumu.yap_array(data_arr);
                                            holder.kaydet.setText("Kilidi Aç");
                                            kilitle(holder);
                                        } catch (Exception e) {
                                            sDialog.setTitleText("").setContentText("Bir hata oldu!")
                                                    .showCancelButton(false)
                                                    .setConfirmText("Tamam")
                                                    .setConfirmClickListener(null)
                                                    .changeAlertType(SweetAlertDialog.WARNING_TYPE);
                                        }
                                    } else {
                                        sDialog.setTitleText("").setContentText("Kayıt güncellenemedi!Tekrar deneyin.")
                                                .showCancelButton(false)
                                                .setConfirmText("Tamam")
                                                .setConfirmClickListener(null)
                                                .changeAlertType(SweetAlertDialog.WARNING_TYPE);
                                        try {
                                            kilitle(holder);
                                        } catch (Exception e) {
                                            sDialog.setTitleText("").setContentText("Kayıt güncellenemedi!Tekrar deneyin.")
                                                    .showCancelButton(false)
                                                    .setConfirmText("Tamam")
                                                    .setConfirmClickListener(null)
                                                    .changeAlertType(SweetAlertDialog.WARNING_TYPE);
                                        }

                                    }
                                }
                            }).show();

                }
            }
        });


    }

    public void kilitle(@NonNull odev_goruntule_adapter.tanimla holder) {
        holder.artieksi.setInputType(InputType.TYPE_NULL);
        holder.not.setInputType(InputType.TYPE_NULL);
        holder.ssayisi.setInputType(InputType.TYPE_NULL);
        holder.degerlendir.setInputType(InputType.TYPE_NULL);
        holder.yapilan.setInputType(InputType.TYPE_NULL);
        holder.yapti_switch.setInputType(InputType.TYPE_NULL);
    }

    public void kilit_ac(@NonNull odev_goruntule_adapter.tanimla holder) {
        holder.artieksi.setInputType(InputType.TYPE_CLASS_TEXT);
        holder.not.setInputType(InputType.TYPE_CLASS_TEXT);
        holder.ssayisi.setInputType(InputType.TYPE_CLASS_TEXT);
        holder.degerlendir.setInputType(InputType.TYPE_CLASS_TEXT);
        holder.yapilan.setInputType(InputType.TYPE_CLASS_TEXT);
        holder.yapti_switch.setInputType(InputType.TYPE_CLASS_TEXT);
    }

    public ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }


        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            // emin misiniz?
            final int pos = viewHolder.getAdapterPosition();

            new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE).setTitleText("Emin misiniz?").setContentText("Ödev kayıtları silinecek!")
                    .setCancelText("Hayır")
                    .setConfirmText("Evet")
                    .showCancelButton(true)
                    .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            // viewHolder.itemView.setBackgroundColor(Color.WHITE);
                            sweetAlertDialog.cancel();

                            notifyItemChanged(pos);
                            //data_arr.remove(pos);

                            Ogrenci_Durumu.yap_array(dbhelp.odev_al(tablo_name));
                        }
                    })
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            Boolean sil_sonuc = dbhelp.odev_sil(tablo_name, data_arr.get(pos).getId());
                            if (sil_sonuc) {
                                sDialog.setContentText("Silindi.")
                                        .showCancelButton(false)
                                        .setConfirmText("Tamam")
                                        .setConfirmClickListener(null)
                                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                data_arr.remove(pos);
                                notifyItemRemoved(pos);

                                Ogrenci_Durumu.yap_array(dbhelp.odev_al(tablo_name));
                            } else {
                                sDialog.setContentText("Silinemedi! Tekrar deneyin.")
                                        .showCancelButton(false)
                                        .setConfirmText("Tamam")
                                        .setConfirmClickListener(null)
                                        .changeAlertType(SweetAlertDialog.WARNING_TYPE);
                            }
                        }
                    }).show();

        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(context, R.color.color_red))
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
        TextView tarih, tarih4gun;
        EditText yapilan, ssayisi, artieksi, not, degerlendir;
        Button sil, kaydet;
        Switch yapti_switch;


        LinearLayout layout_click;

        public tanimla(@NonNull View itemView) {
            super(itemView);
            tarih = itemView.findViewById(R.id.tarih);
            yapti_switch=itemView.findViewById(R.id.duzenle_switch);
            yapilan = itemView.findViewById(R.id.yapilangoster);
            // tarih4gun=itemView.findViewById(R.id.tarih_ex4gun);
            ssayisi = itemView.findViewById(R.id.toplamgoster);

            artieksi = itemView.findViewById(R.id.artieksigoster);
            not = itemView.findViewById(R.id.notgoster);
            degerlendir = itemView.findViewById(R.id.degerlendirmegoster);
            //sil = itemView.findViewById(R.id.sil_goster);
            kaydet = itemView.findViewById(R.id.kaydet_goster);


        }
    }
}
