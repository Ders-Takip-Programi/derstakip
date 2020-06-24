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
    TextView kayanYazi;
    RecyclerView.LayoutManager layoutManager;
    Calendar c;
    String selected_date;
    String selected_day_name;
    SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CreateActionBar createActionBar=new CreateActionBar(this,getSupportActionBar(),"Öğrenci Listesi");
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
        kayanYazi=findViewById(R.id.mtv2);
        kayanYazi.setSelected(true);
        bottom_bar_call();

    }
    private void bottom_bar_call(){
        BottomBarActions bottomBarActions=new BottomBarActions(getApplicationContext(),R.id.nav_ogrlistele,Ogrenci_Listesi.class,this);
        bottomBarActions.select_navbar();
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
