package com.example.tt;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;

public class dsa extends AppCompatActivity {
    PDFView dsa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dsa);
        dsa=(PDFView)findViewById(R.id.ds);
        dsa.fromAsset("dsa.pdf").load();
    }
}
