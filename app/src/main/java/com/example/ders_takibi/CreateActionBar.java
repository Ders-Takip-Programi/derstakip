package com.example.ders_takibi;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import org.w3c.dom.Text;

public class CreateActionBar {
    androidx.appcompat.app.ActionBar actionBar;
    String title;
    Context context;
    Activity activity;
    public CreateActionBar(Activity activity,ActionBar actionBar, String title){
        this.actionBar=actionBar;
        this.title=title;
        this.activity=activity;
        actionBar.setDisplayOptions(androidx.appcompat.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.action_bar);
        actionBar.setDisplayHomeAsUpEnabled(true);

        AppCompatTextView tx=activity.findViewById(R.id.actionbar_textview);

        tx.setText(title);

    }




}
