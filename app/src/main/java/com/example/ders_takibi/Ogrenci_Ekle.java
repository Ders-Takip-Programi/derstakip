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
    SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView tx=findViewById(R.id.actionbar_textview);
        tx.setText("Öğrenci Ekle");
        /*
        ActionBar actionbar = getSupportActionBar();
        TextView textview = new TextView(Ogrenci_Ekle.this);
        RelativeLayout.LayoutParams layoutparams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT );
        textview.setLayoutParams(layoutparams);
        textview.setText("Öğrenci Ekle");
        textview.setTextColor(Color.WHITE);
        textview.setTextSize(20);
        layoutparams.setMargins(265,30,180,30);
        actionbar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionbar.setCustomView(textview);
        actionbar.setDisplayShowHomeEnabled(true);
        actionbar.setDisplayUseLogoEnabled(true);
        actionbar.setDisplayHomeAsUpEnabled(true);
*/
        setContentView(R.layout.ogrenci__ekle);
        db=new dbhelper(this);
        tanımla();
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


        BottomNavigationView navbar;
        navbar=findViewById(R.id.bottomBar);
        navbar.setSelectedItemId(R.id.nav_ogrekle);
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

                        return true;
                    case R.id.nav_ogrlistele:
                        Intent i2=new Intent(getApplicationContext(),Ogrenci_Listesi.class);
                        startActivity(i2);
                        return true;
                }
                return false;
            }
        });
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
            /*
            String dk="";
            if (LocalDateTime.now().getMinute()<10){
                dk="0"+String.valueOf(LocalDateTime.now().getMinute());
            }
            else{
                dk=String.valueOf(LocalDateTime.now().getMinute());
            }

             saat= String.valueOf(LocalDateTime.now().getHour())+":"+dk;
            Log.d("Saat", String.valueOf(LocalDateTime.now().getHour()));

*/
            Calendar calendar=Calendar.getInstance();
            SimpleDateFormat formt=new SimpleDateFormat("HH:mm");
            saat=formt.format(calendar.getTime());

      if (isim.trim().equals("")|soyisim.trim().equals("")){

              new SweetAlertDialog(Ogrenci_Ekle.this,SweetAlertDialog.ERROR_TYPE).setTitleText("Hata!").setContentText("İsim Soyisim Alanları Boş Bırakılamaz").show();
           }
           else{
                ogrenci_model om=new ogrenci_model(1,isim,soyisim,tc,veli_isim,velitel,adres,saat);
                Boolean result=db.kullanici_olustur(om);
                if(result.equals(true)){
                    new SweetAlertDialog(Ogrenci_Ekle.this,SweetAlertDialog.SUCCESS_TYPE).setContentText("Başarıyla eklendi.").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            Intent i=new Intent(getApplicationContext(),Anasayfa.class);
                            startActivity(i);
                        }
                    }).show();

                }
                else{
                    new SweetAlertDialog(Ogrenci_Ekle.this,SweetAlertDialog.ERROR_TYPE).setTitleText("Hata!").setContentText("Kayıt eklenemedi tekrar deneyin!").show();
                }


            }
       }
    });
    }
    private void show_datedialog(){
        c=Calendar.getInstance();
        DatePickerDialog dialogg=new DatePickerDialog(Ogrenci_Ekle.this, new DatePickerDialog.OnDateSetListener() {
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
