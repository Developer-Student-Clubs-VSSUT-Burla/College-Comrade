package com.example.tt;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;

public class holiday extends AppCompatActivity {

    PDFView p1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_holiday);

        p1=(PDFView)findViewById(R.id.p1);

        p1.fromAsset("holiday.pdf").load();
    }
}
