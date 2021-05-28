package com.example.tt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.example.tt.adapter.PdfListAdapter;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;

import java.io.File;

public class PdfViewerActivity extends AppCompatActivity {
    PDFView pdfView;
    File pdf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_viewer);
        pdfView=findViewById(R.id.viewPdf);
        Intent intent = getIntent();
        int position = intent.getIntExtra("position",-1);
        pdf= PdfListAdapter.list.get(position);

        readPdf();
    }

    private void readPdf() {
        pdfView.fromFile(pdf)
                .enableSwipe(true)
                .enableAnnotationRendering(true)
                .scrollHandle(new DefaultScrollHandle(this))
                .load();

    }
}