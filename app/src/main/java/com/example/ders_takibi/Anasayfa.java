package com.example.ders_takibi;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

//stdtable+id
public class Anasayfa extends AppCompatActivity {
    CardView odevekle;
    CardView ogrenciekle;
    CardView goruntule;
    CardView ogrenciliste;
    Calendar c;
    String selected_date;
    String selected_day_name;
    SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");

    private static long exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      this.getSupportActionBar().hide();

        setContentView(R.layout.anasayfanew);

        tanimla();
        tiklama();
    }

    @Override
    public void onBackPressed() {
        if (exit+2000>System.currentTimeMillis()){
            Intent i=new Intent(Intent.ACTION_MAIN);
            i.addCategory(Intent.CATEGORY_HOME);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
        }
        else{
            Toast.makeText(this,"Kapatmak için tekrar geri tuşuna basınız.",Toast.LENGTH_LONG).show();
        }
        exit=System.currentTimeMillis();
    }

    public void tanimla(){
        odevekle=(CardView) findViewById(R.id.odevekle);
        ogrenciekle=(CardView) findViewById(R.id.ogrenciekle);
        goruntule=(CardView) findViewById(R.id.goruntule);
        ogrenciliste=(CardView) findViewById(R.id.ogrenciliste);
        c=Calendar.getInstance();

    }
    public void tiklama(){
        odevekle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                show_datedialog();
            }
        });

        ogrenciekle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),Ogrenci_Ekle.class);
                startActivity(i);
            }
        });

        goruntule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(getApplicationContext(),Durum_Goruntule.class);
                startActivity(i);
            }
        });

        ogrenciliste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),Ogrenci_Listesi.class);
                startActivity(i);
            }
        });
    }
    private void show_datedialog(){
        DatePickerDialog dialogg=new DatePickerDialog(Anasayfa.this, new DatePickerDialog.OnDateSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                i1++; //month starts zero
                String tarih=String.valueOf(i)+"-"+String.valueOf(i1)+"-"+String.valueOf(i2);


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
