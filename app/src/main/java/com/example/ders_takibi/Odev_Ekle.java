package com.example.ders_takibi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Odev_Ekle extends AppCompatActivity {
    Button anasayfabutton;
    RecyclerView recview;
    dbhelper helper;
    TextView mtv;
    String tarih,tarihgun;
    RecyclerView.LayoutManager manager;
    SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
    Calendar c;
    //action

    TextView textview;
    RelativeLayout.LayoutParams layoutparams;

    String selected_date;
    String selected_day_name;
    ActionBar actionBar ;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
getSupportActionBar().setCustomView(R.layout.action_bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView tx=findViewById(R.id.actionbar_textview);
        tx.setText("Ödev Ekle");
//ActionBar actionbar = getSupportActionBar();
/*
TextView textview = new TextView(Odev_Ekle.this);
RelativeLayout.LayoutParams layoutparams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT );
textview.setLayoutParams(layoutparams);
textview.setText("Ders Kayıt Ekle");
textview.setTextColor(Color.WHITE);
textview.setTextSize(20);
//layoutparams.setMarginStart(140);
layoutparams.setMargins(240,30,150,30);
actionbar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
actionbar.setCustomView(textview);
actionbar.setDisplayShowHomeEnabled(true);
actionbar.setDisplayUseLogoEnabled(true);
actionbar.setDisplayHomeAsUpEnabled(true);
*/


        setContentView(R.layout.odev__ekle);
        helper = new dbhelper(this);

        tanımla();
        //tıklama();
        doldur();
    }


    public void tanımla() {

        recview=findViewById(R.id.recyclerview2);
        manager=new LinearLayoutManager(getApplicationContext());
        recview.setLayoutManager(manager);
        Bundle extras = getIntent().getExtras();
        tarih=extras.getString("date");
        mtv=findViewById(R.id.mtv);
        mtv.setSelected(true);
        tarihgun=extras.getString("day");
        BottomNavigationView navbar;
        navbar=findViewById(R.id.bottomBar);
        navbar.setSelectedItemId(R.id.nav_odevekle);
        navbar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.nav_anasayfa:
                        Intent i=new Intent(getApplicationContext(),Anasayfa.class);
                        startActivity(i);
                        return true;
                    case R.id.nav_odevekle:

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
                        Intent i5=new Intent(getApplicationContext(),Ogrenci_Listesi.class);
                        startActivity(i5);
                        return true;
                }
                return false;
            }
        });

    }

    public void doldur(){
        ArrayList<ogrenci_model> araylist=helper.all_students();
        odev_adapter odevadapter=new odev_adapter(getApplicationContext(),araylist,tarih,tarihgun);
        recview.setAdapter(odevadapter);

    }


}
