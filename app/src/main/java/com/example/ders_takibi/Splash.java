package com.example.ders_takibi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.widget.ImageView;

public class Splash extends AppCompatActivity {
    ImageView clover;
    Animation anim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getSupportActionBar().hide();
        setContentView(R.layout.activity_splash);
       // bgapp=findViewById(R.id.bgapp);
//        clover=findViewById(R.id.clover);
//
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i=new Intent(getApplicationContext(),Anasayfa.class);
                startActivity(i);
                finish();
            }
        },3000);
    }
}
