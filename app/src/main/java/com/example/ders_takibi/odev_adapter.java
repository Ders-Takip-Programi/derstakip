package com.example.ders_takibi;

import android.content.Context;
import android.os.Build;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;


import java.time.LocalDateTime;
import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class odev_adapter extends RecyclerView.Adapter<odev_adapter.tanimla> {
    Context context;
    dbhelper helper;
    String tarih,gun;
    CreateAlert createAlert;
    String errorMessage="";


    ArrayList<ogrenci_model> data_arr;

    public odev_adapter (Context context,ArrayList<ogrenci_model> data_arr,String tarih_str,String tarihgun){
        tarih=tarih_str;
        gun=tarihgun;
        this.context=context;
        this.data_arr=data_arr;
        helper=new dbhelper(context);
    }

    @NonNull
    @Override
    public odev_adapter.tanimla onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.example_item3,parent,false);

        context = parent.getContext();
        createAlert=new CreateAlert(context);
        return new tanimla(view);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull final tanimla holder, final int position) {
        holder.artıeksi.setInputType(InputType.TYPE_CLASS_TEXT);
        holder.not.setInputType(InputType.TYPE_CLASS_TEXT);
        holder.yapilan.setInputType(InputType.TYPE_CLASS_TEXT);
        holder.degerlendir.setInputType(InputType.TYPE_CLASS_TEXT);
        holder.yapilan.setInputType(InputType.TYPE_CLASS_TEXT);
        holder.isim.setText(data_arr.get(position).getIsim().toUpperCase());
        holder.soyisim.setText(data_arr.get(position).getSoyisim().toUpperCase());

        String saat=" ";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            saat = String.valueOf(LocalDateTime.now().getHour())+":"+String.valueOf(LocalDateTime.now().getMinute());
        }
        //HEPSİ BOS CONDITION
        final String finalSaat = saat;
        holder.yapti_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean changed) {
                if (changed){
                    holder.artıeksi.setInputType(InputType.TYPE_CLASS_TEXT);
                    holder.not.setInputType(InputType.TYPE_CLASS_TEXT);
                    holder.yapilan.setInputType(InputType.TYPE_CLASS_TEXT);
                    holder.degerlendir.setInputType(InputType.TYPE_CLASS_TEXT);
                    holder.yapilan.setInputType(InputType.TYPE_CLASS_TEXT);

                }
                else {
                    holder.artıeksi.setInputType(InputType.TYPE_NULL);
                    holder.not.setInputType(InputType.TYPE_NULL);
                    holder.yapilan.setInputType(InputType.TYPE_NULL);
                    holder.degerlendir.setInputType(InputType.TYPE_NULL);
                    holder.yapilan.setInputType(InputType.TYPE_NULL);
                }
            }



        });

        holder.kaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!holder.yapti_switch.isChecked()){
                    createAlert.warningAlert("Ders Vermeyen Öğrenciye Not Giremezsiniz").show();
                }
                else {
                String ders_yaptimi="1";
                if(  holder.yapilan.getText().toString().trim().equals("") && holder.toplam.getText().toString().trim().equals("") &&
                        holder.not.getText().toString().trim().equals("") && holder.artıeksi.getText().toString().trim().equals("")){
                    ders_yaptimi="0";
                }
                ogrenci_kisisel_model kisisel_model=new ogrenci_kisisel_model(data_arr.get(position).getId(),tarih,gun,
                        holder.yapilan.getText().toString(),holder.toplam.getText().toString(),holder.not.getText().toString(),holder.artıeksi.getText().toString(),
                        holder.degerlendir.getText().toString(), finalSaat,ders_yaptimi);
                String sonuc=helper.odev_kaydet(kisisel_model);
                if (sonuc.equals("Başarıyla Eklendi")){
                    new SweetAlertDialog(context,SweetAlertDialog.SUCCESS_TYPE).setConfirmText("Tamam").setContentText(data_arr.get(position).getIsim()+
                            " Öğrencisine "+sonuc).show();
                    holder.yapilan.setText("");
                    holder.artıeksi.setText("");
                    holder.degerlendir.setText("");
                    holder.toplam.setText("");
                    holder.not.setText("");

                }
                else if (sonuc.equals("Bir Hata Oldu Tekrar Deneyin")){
                    errorMessage=data_arr.get(position).getIsim().substring(0,1).toUpperCase()+data_arr.get(position).getIsim().substring(1)+
                            " Öğrencisine "+sonuc;
                    createAlert.errorAlert(errorMessage).show();


                }
                else{

                    errorMessage=data_arr.get(position).getIsim().substring(0,1).toUpperCase()+data_arr.get(position).getIsim().substring(1)+
                            " Öğrencisine "+sonuc+" Ödev Görüntüle Ekranından Değiştirebilirsiniz. ";

                    createAlert.errorAlert(errorMessage).show();
                    holder.yapilan.setText("");
                    holder.artıeksi.setText("");
                    holder.degerlendir.setText("");
                    holder.toplam.setText("");
                    holder.not.setText("");
                }


            }}
        });



    }



    @Override
    public int getItemCount() {
        return data_arr.size();
    }

    public class tanimla extends RecyclerView.ViewHolder{
        TextView isim,soyisim;
        Button kaydet;
        Switch yapti_switch;
        EditText not,yapilan,toplam,artıeksi,degerlendir;
        public tanimla(View itemview){
            super(itemview);
            isim=itemview.findViewById(R.id.isimex3);
            kaydet=itemview.findViewById(R.id.odevkayit);
            soyisim=itemview.findViewById(R.id.soyisimex3);
            not=itemview.findViewById(R.id.not);
            yapilan=itemview.findViewById(R.id.yapilansayfa);
            toplam=itemview.findViewById(R.id.toplamsayfa);
            artıeksi=itemview.findViewById(R.id.artieksi);
            yapti_switch=itemview.findViewById(R.id.ekle_switch);
            degerlendir=itemview.findViewById(R.id.degerlendirme);
        }
    }
}
