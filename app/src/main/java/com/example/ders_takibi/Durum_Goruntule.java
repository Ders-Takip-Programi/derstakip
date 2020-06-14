package com.example.ders_takibi;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Durum_Goruntule extends AppCompatActivity {
    Button anasayfabutton;
    RecyclerView recview;
    dbhelper db;
    TextView mtv3;
    RecyclerView.LayoutManager layoutManager;
    Calendar c;

    String selected_date;
    String selected_day_name;
    SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView tx=findViewById(R.id.actionbar_textview);
        tx.setText("Yapılan Ödevler");
        setContentView(R.layout.durum__goruntule);
        tanımla();
        tıklama();
        ArrayList<ogrenci_model> arrlist=db.all_students();
        liste4odev_goruntule_adapter adapter=new liste4odev_goruntule_adapter(getApplicationContext(),arrlist);
        DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.divider));
        recview.addItemDecoration(dividerItemDecoration);
        recview.setAdapter(adapter);
        recview.setNestedScrollingEnabled(true);
    }
    public void tanımla(){

        recview=findViewById(R.id.recview3);
        db=new dbhelper(getApplicationContext());
        layoutManager=new LinearLayoutManager(getApplicationContext());
        recview.setLayoutManager(layoutManager);
        BottomNavigationView navbar;
        mtv3=findViewById(R.id.mtv3);
        mtv3.setSelected(true);
        navbar=findViewById(R.id.bottomBar);
        navbar.setSelectedItemId(R.id.nav_odevgoster);
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

                        return true;
                    case R.id.nav_ogrekle:
                        Intent i3=new Intent(getApplicationContext(),Ogrenci_Ekle.class);
                        startActivity(i3);
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

    }
    private void show_datedialog(){
        c=Calendar.getInstance();
        DatePickerDialog dialogg=new DatePickerDialog(Durum_Goruntule.this, new DatePickerDialog.OnDateSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                i1++; //month starts zero
                String tarih=String.valueOf(i)+"-"+String.valueOf(i1)+"-"+String.valueOf(i2);
             //   Toast.makeText(getApplicationContext(),tarih,Toast.LENGTH_LONG).show();
                if(tarih != null && !tarih .isEmpty()) {
                    try {
                        //SECİLEN TARİHİ DATE OBJESİNE ATIYORUZ
                        c.setTime(format.parse(tarih.substring(0,9)));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }
                else{
                    new SweetAlertDialog(Durum_Goruntule.this,SweetAlertDialog.ERROR_TYPE).setTitleText("Hata!").setContentText("Tarih seçin!").show();
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
