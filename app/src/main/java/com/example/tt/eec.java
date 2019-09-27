package com.example.tt;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;

public class eec extends AppCompatActivity {
    PDFView eec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eec);
        eec=(PDFView)findViewById(R.id.ee);
        eec.fromAsset("eec.pdf").load();
    }
}
