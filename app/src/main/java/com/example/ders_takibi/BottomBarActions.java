package com.example.ders_takibi;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class BottomBarActions {
    private int resId;
    private Class aClass;
    Date2Day date2Day;
    Calendar c;
    RelativeLayout rel;
    String selected_date;
    String saat;
    String selected_day_name;
    SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
    Context context;
    Activity activity;
    MenuItem menuItem;
    public BottomBarActions(Context context, int resId, Class className, Activity activity)
    {
        date2Day=new Date2Day();
        this.aClass=className;
        this.resId=resId;
        this.context=context;
//        Activity activity1=(Activity) context;
        this.activity=activity;
    }
    public void select_navbar(){
        BottomNavigationView navbar;
        navbar=activity.findViewById(R.id.bottomBar);
        navbar.setSelectedItemId(resId);


        navbar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId()==resId){ //IF CLICKED, DO NOTHING
                    return true;
                }
                else{


                switch (menuItem.getItemId()){
                    case R.id.nav_anasayfa:
                        Intent i=new Intent(context,Anasayfa.class);
                        activity.startActivity(i);
                        return true;
                    case R.id.nav_odevekle:
                        show_datedialog();

                        //dt.show_date_dialog();
                        return true;
                    case R.id.nav_odevgoster:
                        Intent i3=new Intent(context,Durum_Goruntule.class);
                        activity.startActivity(i3);
                        return true;
                    case R.id.nav_ogrekle:
                        Intent i10=new Intent(context,Ogrenci_Ekle.class);
                        activity.startActivity(i10);
                        return true;
                    case R.id.nav_ogrlistele:
                        Intent i2=new Intent(context,Ogrenci_Listesi.class);
                        activity.startActivity(i2);
                        return true;
                }
                return false;
            }
            }
        });
    }
    private void show_datedialog(){
        c= Calendar.getInstance();
        DatePickerDialog dialogg=new DatePickerDialog(this.activity, new DatePickerDialog.OnDateSetListener() {
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



                selected_date=format.format(c.getTime());
                selected_day_name=date2Day.getDayOfWeek(c.get(Calendar.DAY_OF_WEEK));
                Intent intent=new Intent(context,Odev_Ekle.class);
                intent.putExtra("date",selected_date);
                intent.putExtra("day",selected_day_name);
                activity.startActivity(intent);

            }
        },
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        dialogg.show();
    }


}
