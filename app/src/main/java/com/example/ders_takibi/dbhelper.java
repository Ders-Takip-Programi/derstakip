package com.example.ders_takibi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

// stdtable+id
public class dbhelper extends SQLiteOpenHelper {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    public static final String database_name = "allahuekber99";
    public static final String table_name = "student";
    public static final String col_isim = "isim";
    public static final String col_soyisim = "soyisim";
    public static final String col_tc = "tc";
    public static final String col_velitel = "veli_tel";
    public static final String col_veliad = "veli_isim";
    public static final String col_adres = "adres";
    //login
    public static final String user_table_name = "user";
    public static final String col_username = "username";
    public static final String col_pass = "password";
    public static final String col_id = "id";

    public dbhelper(Context context) {
        super(context, database_name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + table_name + " (id INTEGER primary key autoincrement ," + " " + col_isim + " text ," + " " +
                col_soyisim + " text ," + " " + col_tc + " text ," + " " + col_velitel + " text , " + col_veliad + " text , " + " " + col_adres + " text , saat text)";
        sqLiteDatabase.execSQL(query);

        String user_query = "CREATE TABLE " + user_table_name + " (" + col_id + " INTEGER primary key autoincrement, " + " " + col_username + " text, " + col_pass + " text); ";
        sqLiteDatabase.execSQL(user_query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists student");
        sqLiteDatabase.execSQL("drop table if exists user");
    }

    ////////////////////////////////////////////////////
    public Boolean kullanici_olustur(ogrenci_model sm) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues val = new ContentValues();
        val.put(col_isim, sm.getIsim());
        val.put(col_soyisim, sm.getSoyisim());
        val.put(col_adres, sm.getAdres());
        val.put(col_tc, sm.getTc());
        val.put(col_veliad, sm.getVeli_isim());
        val.put(col_velitel, sm.getVeli_tel());
        val.put("saat", sm.getSaat());
        String id = null;
        Boolean result = false;
        try {


            db.insert(table_name, null, val);
            id = id_finder();
            create_new_table(id);
            result = true;
        } catch (Exception e) {
            id = "Bir Problem oluştu";
            result = false;
            e.printStackTrace();
        }
        db.close();
        return result;


    }

    public String id_finder() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(table_name, new String[]{"id,isim"}, null, null, null, null, null);
        c.moveToLast();
        String id = " ";
        Log.d("id", String.valueOf(c.getInt(0)));
        System.out.println(String.valueOf(c.getInt(0)));
        id = String.valueOf(c.getInt(0));
        c.close();
        db.close();
        return id;
    }

    public void create_new_table(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        if (id != null) {


            String idd = "stdtable" + id;
            String queryy = "CREATE TABLE IF NOT EXISTS " + idd + " (id INTEGER primary key autoincrement , tarih text , gun text, yapilan text, ssayisi text, puan text , artieksi text, aciklama text , saat text , bool_yapti text  )";
            try {
                db.execSQL(queryy);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        db.close();

    }

    // HER OGRENCİ TABLOSUNA EKLEME parametre olarak id alır
    public String add_each_student(ogrenci_kisisel_model om) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues val = new ContentValues();
        String idd = "stdtable" + String.valueOf(om.getId());
        val.put("tarih", om.getTarih());
        val.put("puan", om.getPuan());
        val.put("gun", om.getGun());
        val.put("yapilan", om.getYapilan());
        val.put("ssayisi", om.getSsayisi());
        val.put("artieksi", om.getArtieksi());
        val.put("aciklama", om.getAciklama());
        val.put("saat", om.getSaat());
        val.put("bool_yapti", om.getBool_yapti());

        long res = -5;
        try {
            res = db.insert(idd, null, val);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String tes = "Eklenemedi";
        if (res > 0) {
            tes = "Basariyla eklendi";
            System.out.println("EKLENDİİ");
        }
        db.close();
        return tes;

    }

    /* HERHANGİ ÖĞRENCİNİN KENDİ TABLOSUNDAKİ TÜM KAYITLARI GÖSTERİR
    public void show_first_student(String id){
        String idd="stdtable"+id;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor c=db.query(idd,null,null,null,null,null,null);
        c.moveToFirst();
        int i=0;
        while(i<c.getCount()){
            System.out.println("STUDENT SHOWWWWW");
            System.out.println(String.valueOf(c.getInt(0))+c.getString(1));
            i++;
        }
        c.close();
        db.close();

    }*/
    public ArrayList<ogrenci_model> all_students() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<ogrenci_model> arrlist = new ArrayList<>();
        Cursor c = db.query(table_name, new String[]{"id", col_isim, col_soyisim, col_tc, col_velitel, col_veliad, col_adres, "saat"}, null, null, null, null, null);
        c.moveToFirst();
        int i = 0;
        while (i < c.getCount()) {

            ogrenci_model om = new ogrenci_model(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getString(5), c.getString(4), c.getString(6), c.getString(7));

            arrlist.add(om);

            i++;
            c.moveToNext();
        }
        c.close();
        db.close();
        Collections.sort(arrlist, new CustomComparatorLetter());
        return arrlist;
    }

    public Boolean delete_student(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String table = table_name;
        String whereClause = "id = ? ";
        String[] whereArgs = new String[]{String.valueOf(id)};
        db.execSQL("DROP TABLE IF EXISTS " + "stdtable" + String.valueOf(id));
        long res = db.delete(table, whereClause, whereArgs);
        return res > 0;
    }

    public Boolean update_student(ogrenci_model om) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues val = new ContentValues();
        val.put(col_isim, om.getIsim());
        val.put(col_soyisim, om.getSoyisim());
        val.put(col_tc, om.getTc());
        val.put(col_veliad, om.getVeli_isim());
        val.put(col_velitel, om.getVeli_tel());
        val.put(col_adres, om.getAdres());
        val.put("saat", om.getSaat());
        long res = db.update(table_name, val, "id = ?", new String[]{String.valueOf(om.getId())});
        return res > 0;

    }

    ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////
    ///////////////////////ODEV KAYIT/////////////////////////////////////////////////
    //        String queryy="CREATE TABLE IF NOT EXISTS "+ idd+" (id INTEGER primary key autoincrement
    //        , tarih text , gun text, yapilan text, ssayisi text, puan text , artieksi text, aciklama text  )";
    public String odev_kaydet(ogrenci_kisisel_model om) {
        String tabloadi = "stdtable" + String.valueOf(om.getId());

        long res = -1;
        ContentValues val = new ContentValues();
        // odev girilmediyse ife girer
        if (!ogun_odev_girildimi(tabloadi, om.getTarih())) {
            val.put("tarih", om.getTarih());
            val.put("gun", om.getGun());
            val.put("yapilan", om.getYapilan());
            val.put("ssayisi", om.getSsayisi());
            val.put("puan", om.getPuan());
            val.put("artieksi", om.getArtieksi());
            val.put("aciklama", om.getAciklama());
            val.put("saat", om.getSaat());
            val.put("bool_yapti", om.getBool_yapti());
            try {
                SQLiteDatabase db = this.getWritableDatabase();

                res = db.insert(tabloadi, null, val);
                db.close();
            } catch (Exception e) {
                System.out.println("HATAA" + e);
            }


            if (res > 0) {
                return "Başarıyla Eklendi";
            } else {
                return "Bir Hata Oldu Tekrar Deneyin";
            }
        }

        return "Birgünde 2 Kayıt Ekleyemezsiniz.";
    }


    ////////////// EĞER O GÜNÜN TARİHİNDE ÖDEV GİRİLDİYSE KAYDA İZİN VERME. TRUE ISE OGUN GİRİLMİŞ///////////////
    public Boolean ogun_odev_girildimi(String tabloadi, String tarih) {
        SQLiteDatabase db = this.getReadableDatabase();
        //Cursor c=db.query(tabloadi,null,"tarih = ?",new String[]{tarih},null,null,null,null);
        Cursor c = db.rawQuery("SELECT * FROM " + tabloadi + " WHERE tarih = ?", new String[]{tarih});
        Boolean ret = false;
        if (c.getCount() > 0) {
            ret = true;
            if (db != null && db.isOpen()) {
                db.close();
            }
            c.close();
            return ret;
        }

        if (db != null && db.isOpen()) {
            db.close();
        }
        //db.close();
        if (db != null && db.isOpen()) {
            db.close();
        }
        c.close();
        System.out.println("ODEV GIRILDI MI NEGATIF");
        return ret;

    }

    public String son_odev(String table_name) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM " + table_name, null);
        String exp = "Ödev Bulunamadı  ";
        String ress = "-";
        c.moveToLast();
        if (c.getCount() <= 0) {
            c.close();
            db.close();
            return exp;
        } else {
            String day = c.getString(2);
            ress = c.getString(1);
            String[] parts = ress.split("-");

            c.close();
            db.close();
            ress = parts[2] + "." + parts[1] + "." + parts[0];
            return "Son Ödev " + ress + " " + day;
        }

    }

    public ArrayList<ogrenci_kisisel_model> son_odev2(String table_name) {
        ArrayList<ogrenci_kisisel_model> tarihler = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * from " + table_name, null);
        c.moveToFirst();

        while (c.moveToLast()) {
            ogrenci_kisisel_model okm=new ogrenci_kisisel_model(c.getInt(0),c.getString(1),c.getString(2),c.getString(3),c.getString(4),c.getString(5),c.getString(6),c.getString(7),c.getString(8),c.getString(9));
            tarihler.add(okm);
            c.moveToNext();
        }
        c.close();
        db.close();
//        if (tarihler.size()!= 0){
//            Collections.sort(tarihler,new CustomComparatorhist());
//            return tarihler;
//        }

        return tarihler;
    }


    private class CustomComparatorhist implements Comparator<ogrenci_kisisel_model> {

        @Override
        public int compare(ogrenci_kisisel_model o1, ogrenci_kisisel_model o2) {
            int res = o1.getTarih().compareToIgnoreCase(o2.getTarih());
            if (res != 0)
                return res;
            return o1.getTarih().compareToIgnoreCase(o2.getTarih());
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public String son_saat(String table_name) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM " + table_name, null);
        int i = 0;
        int hour = LocalDateTime.now().getHour();
        int dk = LocalDateTime.now().getMinute();

        String exp = String.valueOf(hour) + ":" + String.valueOf(dk);
        String ress = "-";
        c.moveToLast();
        if (c.getCount() <= 0) {
            c.close();
            db.close();
            return exp;
        } else {

            exp = c.getString(8);
            c.close();
            db.close();

            return exp;
        }

    }


    ///////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////
    //ODEV GORUNTULE/////////
    public ArrayList<ogrenci_kisisel_model> odev_al(String table_name) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<ogrenci_kisisel_model> arrayList = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT * FROM " + table_name, null);
        int i = 0;
        c.moveToFirst();
        while (i < c.getCount()) {
            ogrenci_kisisel_model om = new ogrenci_kisisel_model(c.getInt(0), c.getString(1), c.getString(2),
                    c.getString(3), c.getString(4), c.getString(5), c.getString(6), c.getString(7), c.getString(8), c.getString(9));
            c.moveToNext();
            arrayList.add(om);
            i++;
        }
        c.close();
        db.close();
        return arrayList;
    }

    ////////ODEV GUNCELLE////////
    public Boolean odev_guncelle(ogrenci_kisisel_model om, String tablo_adi) {
        ContentValues val = new ContentValues();
        val.put("tarih", om.getTarih());
        val.put("gun", om.getGun());
        val.put("yapilan", om.getYapilan());
        val.put("ssayisi", om.getSsayisi());
        val.put("puan", om.getPuan());
        val.put("artieksi", om.getArtieksi());
        val.put("aciklama", om.getAciklama());
        val.put("bool_yapti", om.getBool_yapti());
        SQLiteDatabase db = this.getWritableDatabase();
        long res = db.update(tablo_adi, val, "id = ?", new String[]{String.valueOf(om.getId())});
        db.close();
        return res > 0;
    }

    public Boolean odev_sil(String tablo_adi, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = "id = ? ";
        String[] whereArgs = new String[]{String.valueOf(id)};
        long res = db.delete(tablo_adi, whereClause, whereArgs);
        return res > 0;
    }


    ////////////////////////////////////////////////////////////////////////////////////
    //login
    public Boolean login(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from " + user_table_name + " where " + col_username + " =? and " + col_pass + " =? ", new String[]{username, password});
        if (cursor.getCount() > 0) {
            cursor.close();
            db.close();
            return true;
        }
        cursor.close();
        db.close();
        return false;
    }

    public Boolean register(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(col_username, username);
        values.put(col_pass, password);
        Boolean res = false;
        try {
            db.insert(user_table_name, null, values);
            res = true;
        } catch (Exception e) {
            res = false;
            e.printStackTrace();
        }
        db.close();
        return res;
    }

    public void useregister() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(user_table_name, null, null, null, null, null, null);
        if (cursor.getCount() > 0) {
            db.close();
            cursor.close();
            return;
        } else {
            register("admin", "admin");
            db.close();
            cursor.close();
        }
    }

    public Boolean changepass(String username, String pass, String newpass) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(col_pass, newpass);
        Cursor cursor = db.rawQuery("Select * from " + user_table_name + " where " + col_username + " =? and " + col_pass + " =? ", new String[]{username, pass});
        Boolean log = false;
        if (cursor.getCount() > 0) {
            cursor.close();
            log = true;
        }

        int id = 1;
        if (log) {
            try {
                db.update(user_table_name, values, " id = ? ", new String[]{String.valueOf(id)});
                db.close();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                db.close();
                return false;
            }

        }
        db.close();
        return false;

    }

    private class CustomComparatorLetter implements Comparator<ogrenci_model> {

        @Override
        public int compare(ogrenci_model o1, ogrenci_model o2) {
            int res = o1.getIsim().compareToIgnoreCase(o2.getIsim());
            if (res != 0)
                return res;
            return o1.getSoyisim().compareToIgnoreCase(o2.getSoyisim());
        }
    }
}

