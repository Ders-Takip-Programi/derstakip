package com.example.ders_takibi;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import cn.pedant.SweetAlert.SweetAlertDialog;

//stdtable+id
public class Ogrenci_Ekle extends AppCompatActivity {
    Button anasayfabutton,eklebuton;
    String isim,soyisim,tc,velitel,veli_isim,adres;
    EditText isim_edx,soyisim_edx,tc_edx,velitel_edx,veliad_edx,adres_edx;
    dbhelper db;
    Calendar c;
    RelativeLayout rel;
    String selected_date;
    String saat;
    String selected_day_name;
    CreateAlert createAlert;
    SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CreateActionBar createActionBar=new CreateActionBar(this,getSupportActionBar(),"Öğrenci Ekle");
        setContentView(R.layout.ogrenci__ekle);
        db=new dbhelper(this);
        tanımla();
        navbar_selection();
        tıklama();
    }

    public void tanımla(){

        eklebuton=findViewById(R.id.ogrencikaydet);
        isim_edx=findViewById(R.id.isimgiris);
        soyisim_edx=findViewById(R.id.soyisimgiris);
        tc_edx=findViewById(R.id.tcgiris);
        velitel_edx=findViewById(R.id.velitelgiris);
        veliad_edx=findViewById(R.id.veliadgiris);
        adres_edx=findViewById(R.id.adresgiris);
        createAlert=new CreateAlert(getApplicationContext());


    }
    private void navbar_selection(){
        BottomBarActions bottomBarActions=new BottomBarActions(getApplicationContext(),R.id.nav_ogrekle,Ogrenci_Ekle.class,this);
        bottomBarActions.select_navbar();
    }
    public void tıklama(){


    eklebuton.setOnClickListener(new View.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onClick(View view) {

            isim=isim_edx.getText().toString();
            soyisim=soyisim_edx.getText().toString();
            tc=tc_edx.getText().toString();
            veli_isim=veliad_edx.getText().toString();
            velitel=velitel_edx.getText().toString();
            adres=adres_edx.getText().toString();
            Calendar calendar=Calendar.getInstance();
            SimpleDateFormat formt=new SimpleDateFormat("HH:mm");
            saat=formt.format(calendar.getTime());

      if (isim.trim().equals("")|soyisim.trim().equals("")){
            createAlert.errorAlert("İsim Soyisim Alanları Boş Bırakılamaz").show();

           }
           else{
                ogrenci_model om=new ogrenci_model(1,isim,soyisim,tc,veli_isim,velitel,adres,saat);
                Boolean result=db.kullanici_olustur(om);
                if(result.equals(true)){
                    createAlert.succesful("Başarıyla Eklendi").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            Intent i=new Intent(getApplicationContext(),Anasayfa.class);
                            startActivity(i);
                        }
                    }).show();

                }
                else{
                    createAlert.errorAlert("Kayıt eklenemedi tekrar deneyin!").show();
                }


            }
       }
    });
    }

}
