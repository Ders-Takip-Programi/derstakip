package com.example.ders_takibi;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Ogrenci_Listesi extends AppCompatActivity {
    Button anasayfabutton;
    RecyclerView recyclerView;
    ImageView notfoundimg;
    Button notfoundbtn;
    dbhelper db;
    TextView mt2;
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
        tx.setText("Öğrenci Listesi");
        setContentView(R.layout.ogrenci__listesi);
        db=new dbhelper(this);
        tanımla();
        tıklama();
        doldur();
    }
    public void tanımla(){
        anasayfabutton=(Button)findViewById(R.id.anasayfabutton);
        recyclerView=findViewById(R.id.recyclerview);
        layoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        BottomNavigationView navbar;
        mt2=findViewById(R.id.mtv2);
        mt2.setSelected(true);
      //  notfoundbtn=findViewById(R.id.not_found_btn);
       // notfoundimg=findViewById(R.id.not_found_img);
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

    }
    public void doldur(){
        ArrayList<ogrenci_model> arrlist=db.all_students();
        ogrenci_adapter adapter=new ogrenci_adapter(getApplicationContext(),arrlist);
        ItemTouchHelper itemTouchHelper=new ItemTouchHelper(adapter.simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        recyclerView.setNestedScrollingEnabled(true);
        DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.divider));
        recyclerView.addItemDecoration(dividerItemDecoration);

            recyclerView.setAdapter(adapter);


        for(int i=0;i<arrlist.size();i++){
            System.out.println(arrlist.get(0).getSoyisim()+"soyaddd");
        }
    }
    private void show_datedialog(){
        c=Calendar.getInstance();
        DatePickerDialog dialogg=new DatePickerDialog(Ogrenci_Listesi.this, new DatePickerDialog.OnDateSetListener() {
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
