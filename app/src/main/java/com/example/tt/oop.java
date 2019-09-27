package com.example.tt;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;

public class oop extends AppCompatActivity {
    PDFView oop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oop);
        oop=(PDFView)findViewById(R.id.o);
        oop.fromAsset("oop.pdf").load();
    }
}
