package com.example.ders_takibi;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.whiteelephant.monthpicker.MonthPickerDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

public class Ogrenci_Durumu extends AppCompatActivity {
    public static Button geriduruma,yapilanbtn,yapilmayanbtn,tumbtn;
    public static String table_name;

    dbhelper helper;
    RecyclerView recview;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<ogrenci_kisisel_model> arrlist;
    SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
    String ay,yil;
    int year;
    int i=0;
    int month;
    TextView mtv5;

    Calendar c;
    ArrayList <ogrenci_kisisel_model> filtre_arr;
    odev_goruntule_adapter adapter;
    String selected_date;
    String isimsoy;
    String selected_day_name;
   public static ArrayList <ogrenci_kisisel_model> yapilanarr;
  public static ArrayList <ogrenci_kisisel_model> yapilmayanarr;



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.home_action,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.tarih_ikon){
            MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(Ogrenci_Durumu.this, new MonthPickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(int selectedMonth, int selectedYear) {
                    //Toast.makeText(getApplicationContext(), "AYY" + String.valueOf(selectedMonth+1)+" "+String.valueOf(selectedYear), Toast.LENGTH_LONG).show();
                    if (selectedMonth<10){
                        selectedMonth++;
                        ay="0"+String.valueOf(selectedMonth);
                        yil=String.valueOf(selectedYear);
                    }
                    else{
                        ay=String.valueOf(selectedMonth+1);
                        yil=String.valueOf(selectedYear);

                    }
                    arr_olustur();
                }

            }, year, month);
            builder.build().show();

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras=getIntent().getExtras();
        String isim=extras.getString("isim");
        int fotocolor=extras.getInt("fotocolor");
        String foto=isim.substring(0,1);
        TextDrawable drawable=TextDrawable.builder()
                .beginConfig()
                .width(115)
                .height(115)
                .endConfig()
                .buildRound(foto,fotocolor);

        ActionBar actionbar = getSupportActionBar();

        actionbar.setLogo(drawable);
        TextView textview = new TextView(Ogrenci_Durumu.this);
        RelativeLayout.LayoutParams layoutparams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT );
        textview.setLayoutParams(layoutparams);


        textview.setText(isim);
        textview.setTextColor(Color.WHITE);
        textview.setTextSize(18);
        textview.setPadding(30,0,0,0);
      //  textview.setGravity(Gravity.CENTER|Gravity.TOP);



        //layoutparams.setMarginStart(140);
        //layoutparams.setMargins(230,30,150,30);
        actionbar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionbar.setCustomView(textview);
        actionbar.setDisplayShowHomeEnabled(true);
        actionbar.setDisplayUseLogoEnabled(true);
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setSubtitle("sss");
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        setContentView(R.layout.ogrenci__durumu);
        tanımla();
        tıklama();
         arrlist=helper.odev_al(table_name);
        Collections.sort(arrlist,new CustomComparatorLetter());
        // Collections.sort(arrlist,new CustomComparatorDate() );
         adapter=new odev_goruntule_adapter(this,arrlist,table_name);
        yap_array(arrlist);

        ItemTouchHelper itemTouchHelper=new ItemTouchHelper(adapter.simpleCallback);
        itemTouchHelper.attachToRecyclerView(recview);
        recview.setAdapter(adapter);
        btn_text(yapilanarr_size(yapilanarr),yapilmayanarr.size());
        tum_click(getWindow().getDecorView().getRootView());
        //////////////////////////////////////////////

    }
    public static int yapilanarr_size(ArrayList <ogrenci_kisisel_model> arr){
        if (arr.size()==0){
            return 0;
        }
        int counter=0;
        for(int i=0;i<arr.size();i++){
            if (arr.get(i).getSsayisi().contains("-") || arr.get(i).getSsayisi().contains("/")){
                counter++;
            }
        }
        return arr.size()+counter;
    }
    /*
    private class CustomComparatorDate implements Comparator<odev_adapter> {


        @Override
        public int compare(odev_adapter t0, odev_adapter t1) {
            return t0.tarih.compareTo(t1.tarih);
        }

        @Override
        public Comparator<odev_adapter> reversed() {
            return null;
        }
    }
}*/
    public void tanımla(){

        Bundle extras=getIntent().getExtras();
        table_name="stdtable"+String.valueOf(extras.getInt("id"));
        isimsoy=extras.getString("isim");
        helper=new dbhelper(getApplicationContext());
        recview=findViewById(R.id.recview4);
        layoutManager=new LinearLayoutManager(getApplicationContext());
        recview.setLayoutManager(layoutManager);
        year=  Calendar.getInstance().get(Calendar.YEAR);
         month= Calendar.getInstance().get(Calendar.MONTH);
        BottomNavigationView navbar;
        navbar=findViewById(R.id.bottomBar);
        tumbtn=findViewById(R.id.tumbtn);


        navbar.setSelectedItemId(R.id.nav_odevgoster);
        yapilanbtn=findViewById(R.id.yapilanbtn);
        mtv5=findViewById(R.id.mtv5);
        mtv5.setSelected(true);
        yapilmayanbtn=findViewById(R.id.yapilmayanbtn);



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
                        Intent i2=new Intent(getApplicationContext(),Ogrenci_Ekle.class);
                        startActivity(i2);
                        return true;
                    case R.id.nav_ogrlistele:
                        Intent i3=new Intent(getApplicationContext(),Ogrenci_Listesi.class);
                        startActivity(i3);
                        return true;
                }
                return false;
            }
        });



    }
public static void btn_text(int yapilan, int yapilmayan){
       // dbhelper help=new dbhelper(this.getApplicationContext());

    yapilanbtn.setText("YAPILAN ("+String.valueOf(yapilan)+")");
    yapilmayanbtn.setText("YAPILMAYAN ("+String.valueOf(yapilmayan)+")");
    tumbtn.setText("TÜMÜ ("+String.valueOf(yapilan+yapilmayan)+")");
}
    public void tıklama(){

    }
    public String[] week_start(){
        Calendar cal = Calendar.getInstance();

        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());


        String x=format.format(cal.getTime());
        Calendar cal2 = Calendar.getInstance();
        cal2.set(Calendar.HOUR_OF_DAY, 0);
        cal2.clear(Calendar.MINUTE);
        cal2.clear(Calendar.SECOND);
        cal2.clear(Calendar.MILLISECOND);
        cal2.set(Calendar.DAY_OF_MONTH,1);
        String y=format.format(cal2.getTime());

        String[] res=new String[2];
        res[0]=x;
        res[1]=y;
        return res;
    }
    public void filtrele(View view){
        i++;
        MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(Ogrenci_Durumu.this, new MonthPickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(int selectedMonth, int selectedYear) {
                //Toast.makeText(getApplicationContext(), "AYY" + String.valueOf(selectedMonth+1)+" "+String.valueOf(selectedYear), Toast.LENGTH_LONG).show();
                   if (selectedMonth<10){
                      selectedMonth++;
                      ay="0"+String.valueOf(selectedMonth);
                      yil=String.valueOf(selectedYear);
                   }
                   else{
                       ay=String.valueOf(selectedMonth+1);
                       yil=String.valueOf(selectedYear);

                   }
                arr_olustur();
            }

        }, year, month);
        builder.build().show();



    }
public void tum_click(View view){
        yap_array(helper.odev_al(table_name));
    if (i==0){

        yapilmayanbtn.setBackgroundTintList(getApplicationContext().getResources().getColorStateList(R.color.transpr));
        yapilmayanbtn.setTextColor(Color.BLACK);
        tumbtn.setBackgroundTintList(getApplicationContext().getResources().getColorStateList(R.color.colorAccent));
        tumbtn.setTextColor(Color.WHITE);
        yapilanbtn.setBackgroundTintList(getApplicationContext().getResources().getColorStateList(R.color.transpr));
        yapilanbtn.setTextColor(Color.BLACK);
     //  btn_text(yapilanarr_size(yapilanarr),yapilmayanarr.size());
        Ogrenci_Durumu.btn_text(yapilanarr_size(yapilanarr),yapilmayanarr.size());
        //    yapilanbtn.setTextColor(Color.WHITE);


        adapter.data_arr=helper.odev_al(table_name);
        recview.setAdapter(adapter);
    }
    yapilmayanbtn.setBackgroundTintList(getApplicationContext().getResources().getColorStateList(R.color.transpr));
    yapilmayanbtn.setTextColor(Color.BLACK);
    tumbtn.setBackgroundTintList(getApplicationContext().getResources().getColorStateList(R.color.colorAccent));
    tumbtn.setTextColor(Color.WHITE);
    yapilanbtn.setBackgroundTintList(getApplicationContext().getResources().getColorStateList(R.color.transpr));
    yapilanbtn.setTextColor(Color.BLACK);

    btn_text(yapilanarr_size(yapilanarr),yapilmayanarr.size());
    //    yapilanbtn.setTextColor(Color.WHITE);


    adapter.data_arr=helper.odev_al(table_name);
    recview.setAdapter(adapter);


}
    public void yapilan_click(View view){
        yap_array(helper.odev_al(table_name));
        if (i==0){
        //    ArrayList <ogrenci_kisisel_model> arr=adapter.data_arr;
         //   yap_array(arr);
            yapilmayanbtn.setBackgroundTintList(getApplicationContext().getResources().getColorStateList(R.color.transpr));
            yapilmayanbtn.setTextColor(Color.BLACK);
            tumbtn.setBackgroundTintList(getApplicationContext().getResources().getColorStateList(R.color.transpr));
            tumbtn.setTextColor(Color.BLACK);
            yapilanbtn.setBackgroundTintList(getApplicationContext().getResources().getColorStateList(R.color.colorAccent));
            yapilanbtn.setTextColor(Color.WHITE);
            btn_text(yapilanarr_size(yapilanarr),yapilmayanarr.size());
            //    yapilanbtn.setTextColor(Color.WHITE);


            adapter.data_arr=yapilanarr;
            recview.setAdapter(adapter);
        }
        yapilmayanbtn.setBackgroundTintList(getApplicationContext().getResources().getColorStateList(R.color.transpr));
        yapilmayanbtn.setTextColor(Color.BLACK);
        tumbtn.setBackgroundTintList(getApplicationContext().getResources().getColorStateList(R.color.transpr));
        tumbtn.setTextColor(Color.BLACK);
        yapilanbtn.setBackgroundTintList(getApplicationContext().getResources().getColorStateList(R.color.colorAccent));
        yapilanbtn.setTextColor(Color.WHITE);
        btn_text(yapilanarr_size(yapilanarr),yapilmayanarr.size());
        //    yapilanbtn.setTextColor(Color.WHITE);


        adapter.data_arr=yapilanarr;
        recview.setAdapter(adapter);
    }
    public void yapilmayan_click(View view){
        yap_array(helper.odev_al(table_name));
        if (i==0){
         //   ArrayList <ogrenci_kisisel_model> arr=adapter.data_arr;
          //  yap_array(arr);
            yapilanbtn.setBackgroundTintList(getApplicationContext().getResources().getColorStateList(R.color.transpr));
            yapilanbtn.setTextColor(Color.BLACK);
            tumbtn.setBackgroundTintList(getApplicationContext().getResources().getColorStateList(R.color.transpr));
            tumbtn.setTextColor(Color.BLACK);
            yapilmayanbtn.setBackgroundTintList(getApplicationContext().getResources().getColorStateList(R.color.colorAccent));
            yapilmayanbtn.setTextColor(Color.WHITE);
            btn_text(yapilanarr_size(yapilanarr),yapilmayanarr.size());

            adapter.data_arr=yapilmayanarr;
            recview.setAdapter(adapter);

        }
        yapilanbtn.setBackgroundTintList(getApplicationContext().getResources().getColorStateList(R.color.transpr));
        yapilanbtn.setTextColor(Color.BLACK);
        tumbtn.setBackgroundTintList(getApplicationContext().getResources().getColorStateList(R.color.transpr));
        tumbtn.setTextColor(Color.BLACK);
        yapilmayanbtn.setBackgroundTintList(getApplicationContext().getResources().getColorStateList(R.color.colorAccent));
        yapilmayanbtn.setTextColor(Color.WHITE);
        btn_text(yapilanarr_size(yapilanarr),yapilmayanarr.size());

        adapter.data_arr=yapilmayanarr;
        recview.setAdapter(adapter);

    }
public static void yap_array (ArrayList<ogrenci_kisisel_model> arrayList){
        ArrayList <ogrenci_kisisel_model> icicearr=new ArrayList<>();
    yapilanarr=new ArrayList<>();
    yapilmayanarr=new ArrayList<>();
        for(int i=0;i<arrayList.size();i++){
            if(arrayList.get(i).getBool_yapti().equals("1")){
                yapilanarr.add(arrayList.get(i));
               // Toast.makeText(getApplicationContext(),arrayList.get(i).getTarih(),Toast.LENGTH_LONG).show();
            }
            else{
                yapilmayanarr.add(arrayList.get(i));
            }
            Ogrenci_Durumu.btn_text(yapilanarr_size(yapilanarr),yapilmayanarr.size());
        }


}

    public int kayit_varmi(ArrayList<ogrenci_kisisel_model> arr,String tarih){
        for(int i=0;i<arr.size();i++){
            if(arr.get(i).getTarih().equals(tarih)){
                return i;
            }

        }
        return -10;
    }

    public void arr_olustur(){
        String selected_tarih=yil+"-"+ay;
        ArrayList<ogrenci_kisisel_model> om=new ArrayList<>();


        for(int i=0;i<arrlist.size();i++){
            if(selected_tarih.equals(arrlist.get(i).getTarih().substring(0,7))){

                om.add(arrlist.get(i));
            }
        }
        Collections.sort(om,new CustomComparatorLetter());
      //  odev_goruntule_adapter adapter2=new odev_goruntule_adapter(getApplicationContext(),om,table_name);

        adapter.data_arr=om;
        filtre_arr=om;
        yap_array(filtre_arr);
        recview.setAdapter(adapter);
       // try{




       // }
       // catch (Exception e){
       //   e.printStackTrace();
        //}





    }
    private void show_datedialog(){
        c= Calendar.getInstance();
        DatePickerDialog dialogg=new DatePickerDialog(Ogrenci_Durumu.this, new DatePickerDialog.OnDateSetListener() {
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
    private class CustomComparatorLetter implements Comparator<ogrenci_kisisel_model> {


        @Override
        public int compare(ogrenci_kisisel_model t0, ogrenci_kisisel_model t1) {
            return t0.getTarih().compareTo(t1.getTarih());
        }
    }
}
