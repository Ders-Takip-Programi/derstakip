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



import cn.pedant.SweetAlert.SweetAlertDialog;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
public class Durum_Goruntule extends AppCompatActivity {
    Button anasayfabutton;
    RecyclerView recview;
    dbhelper db;
    TextView mtv3;
    RecyclerView.LayoutManager layoutManager;
    Calendar c;
    Date2Day date2Day;
    CreateAlert createAlert;
    String selected_date;
    String selected_day_name;
    SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CreateActionBar createActionBar=new CreateActionBar(this,getSupportActionBar(),"Ödev Görüntüle");

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
        date2Day=new Date2Day();
        createAlert=new CreateAlert(getApplicationContext());
      bottom_bar_call();

    }
    private void bottom_bar_call(){
        BottomBarActions bottomBarActions=new BottomBarActions(getApplicationContext(),R.id.nav_odevgoster,Durum_Goruntule.class,this);
        bottomBarActions.select_navbar();
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

                if(tarih != null && !tarih .isEmpty()) {
                    try {
                        //SECİLEN TARİHİ DATE OBJESİNE ATIYORUZ
                        c.setTime(format.parse(tarih.substring(0,9)));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }
                else{
                    createAlert.errorAlert("Tarih seçin!").show();
                }

                selected_date=format.format(c.getTime());
                selected_day_name=date2Day.getDayOfWeek(c.get(Calendar.DAY_OF_WEEK));
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

}
