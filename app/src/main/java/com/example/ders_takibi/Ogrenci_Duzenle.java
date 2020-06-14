package com.example.ders_takibi;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

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

public class Ogrenci_Duzenle extends AppCompatActivity {
    Button anasayfabutton,duzenle_btn;
    EditText isim,soyisim,adres,velitel,veliad,tc;
    String name,surname,adress,kimlik,veli_phone,veli_name;
    int id;
    String[] data;
    dbhelper db;
    Calendar c;
    BottomNavigationView navbar;
    String selected_date;
    String saat;
    String selected_day_name;
    SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView tx=findViewById(R.id.actionbar_textview);
        tx.setText("Öğrenci Düzenle");
        setContentView(R.layout.ogrenci__duzenle);
        db=new dbhelper(this);
        tanımla();
        tıklama();
        Intent intent=getIntent();
         data=intent.getStringArrayExtra("ogrmodel_satir");
         doldur();


    }
    public void tanımla(){

        duzenle_btn=findViewById(R.id.kaydetduzenle);
        isim=findViewById(R.id.isimduzenle);
        soyisim=findViewById(R.id.soyisimduzenle);
        tc=findViewById(R.id.tcduzenle);
        adres=findViewById(R.id.adresduzenle);
        velitel=findViewById(R.id.velitelduzenle);
        veliad=findViewById(R.id.veliadduzenle);

        navbar=findViewById(R.id.bottomBar);
        navbar.setSelectedItemId(R.id.nav_ogrlistele);
        navbar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.nav_anasayfa:
                        Intent i=new Intent(getApplicationContext(),Anasayfa.class);
                        startActivity(i);
                        return true;
                    case R.id.nav_odevekle:
                        show_datedialog();
                        return true;
                    case R.id.nav_odevgoster:
                        Intent i3=new Intent(getApplicationContext(),Durum_Goruntule.class);
                        startActivity(i3);
                        return true;
                    case R.id.nav_ogrekle:
                        Intent i2=new Intent(getApplicationContext(),Ogrenci_Ekle.class);
                        startActivity(i2);
                        return true;
                    case R.id.nav_ogrlistele:

                        return true;
                }
                return false;
            }
        });



    }
    public void tıklama(){

        duzenle_btn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                name=isim.getText().toString();
                surname=soyisim.getText().toString();
                kimlik=tc.getText().toString();
                veli_phone=velitel.getText().toString();
                veli_name=veliad.getText().toString();
                adress=adres.getText().toString();
                /*
                String dk="";
                if (LocalDateTime.now().getMinute()<10){
                    dk="0"+String.valueOf(LocalDateTime.now().getMinute());
                }
                else{
                    dk=String.valueOf(LocalDateTime.now().getMinute());
                }
                saat= String.valueOf(LocalDateTime.now().getHour())+":"+dk;*/
                Calendar calendar=Calendar.getInstance();
                SimpleDateFormat formt=new SimpleDateFormat("HH:mm");
                saat=formt.format(calendar.getTime());
                final ogrenci_model om=new ogrenci_model(id,name,surname,kimlik,veli_name,veli_phone,adress,saat);
                if(name.equals("")||surname.equals("")){
                    new SweetAlertDialog(Ogrenci_Duzenle.this,SweetAlertDialog.WARNING_TYPE).setTitleText("Hata!").setContentText("İsim ve soyisim boş bırakılamaz!").show();
                }
                else{
                    new SweetAlertDialog(Ogrenci_Duzenle.this,SweetAlertDialog.WARNING_TYPE).setTitleText("Emin misiniz?").setContentText("Öğrenci bilgileri değişecek!")
                            .setCancelText("Hayır")
                            .setConfirmText("Evet")
                            .showCancelButton(true)
                            .setCancelClickListener(null)
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    Boolean res=db.update_student(om);

                                    if(res){
                                        sDialog.setTitleText("").setContentText("Başarıyla güncellendi.")
                                                .showCancelButton(false)
                                                .setConfirmText("Tamam")
                                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                    @Override
                                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                        Intent i=new Intent(getApplicationContext(),Ogrenci_Listesi.class);
                                                        startActivity(i);
                                                    }
                                                }).changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                    }
                                    else{
                                        sDialog.setTitleText("Hata!").setContentText("Güncellenemedi.Tekrar deneyin!")
                                                .showCancelButton(false)
                                                .setConfirmText("Tamam")
                                                .setConfirmClickListener(null)
                                                .changeAlertType(SweetAlertDialog.WARNING_TYPE);

                                    }
                                }
                            }).show();
                }

            }
        });
    }
    public void doldur(){

        id=Integer.parseInt(data[0]);
        name=data[1];
        surname=data[2];
        kimlik=data[3];
        veli_name=data[4];
        veli_phone=data[5];
        adress=data[6];
        isim.setText(name);
        soyisim.setText(surname);
        tc.setText(kimlik);
        veliad.setText(veli_name);
        velitel.setText(veli_phone);
        adres.setText(adress);


    }
    private void show_datedialog(){
        c= Calendar.getInstance();
        DatePickerDialog dialogg=new DatePickerDialog(Ogrenci_Duzenle.this, new DatePickerDialog.OnDateSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                i1++; //month starts zero
                String tarih=String.valueOf(i)+"-"+String.valueOf(i1)+"-"+String.valueOf(i2);

                System.out.println("TARİHHHHHH"+tarih);
                try {
                    //SECİLEN TARİHİ DATE OBJESİNE ATIYORUZ
                    c.setTime(format.parse(tarih));
                } catch (ParseException e) {
                    e.printStackTrace();
                }



                //Toast.makeText(getApplicationContext(),format.format(c.getTime()),Toast.LENGTH_LONG).show();
                selected_date=format.format(c.getTime());
                selected_day_name=getDayOfWeek(c.get(Calendar.DAY_OF_WEEK));
                Intent intent=new Intent(getApplicationContext(),Odev_Ekle.class);
                intent.putExtra("date",selected_date);
                intent.putExtra("day",selected_day_name);
                startActivity(intent);

            }
        },
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        dialogg.show();
        navbar.setSelectedItemId(R.id.nav_ogrlistele);
    }
    private String getDayOfWeek(int value) {
        String day = "";
        switch (value) {
            case 1:
                day = "Pazar";
                break;
            case 2:
                day = "Pazartesi";
                break;
            case 3:
                day = "Salı";
                break;
            case 4:
                day = "Çarşamba";
                break;
            case 5:
                day = "Perşembe";
                break;
            case 6:
                day = "Cuma";
                break;
            case 7:
                day = "Cumartesi";
                break;
        }
        return day;
    }
}
