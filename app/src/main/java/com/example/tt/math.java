package com.example.tt;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;

public class math extends AppCompatActivity {
    PDFView math;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math);
        math=(PDFView)findViewById(R.id.m);
        math.fromAsset("math.pdf").load();
    }
}
