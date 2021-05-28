package com.example.tt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.MotionEvent;

import android.widget.Toast;

import com.example.tt.adapter.PdfListAdapter;
import com.skydoves.androidveil.VeilRecyclerFrameView;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.Executor;

public class PdfReaderActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE=200;
//    private static ArrayList<PDFInfo> pdfInfoList=new ArrayList<>();
    public static ArrayList<File> pdfFileList=new ArrayList<>();
    VeilRecyclerFrameView rvPdfList;
    SearchView itemSearch;
    PdfListAdapter adapter= new PdfListAdapter(PdfReaderActivity.this);;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_reader);
        itemSearch=findViewById(R.id.itemSearch);
        rvPdfList=findViewById(R.id.rvPdfList);
        rvPdfList.setLayoutManager(new LinearLayoutManager(PdfReaderActivity.this));
        rvPdfList.addVeiledItems(15);
        if(!checkPermission()){
            requestPermission();
        }else {
            File dir = new File(Environment.getExternalStorageDirectory().toString());
            System.out.println(dir);
            AsyncTaskRunner runner = new AsyncTaskRunner();
            runner.execute(dir);
            rvPdfList.setAdapter(adapter);
            System.out.println(pdfFileList.size());
        }
            itemSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    //todo
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                 //   Toast.makeText(PdfReaderActivity.this, newText, Toast.LENGTH_SHORT).show();
                    if (!newText.isEmpty()) {
                        adapter.getFilter().filter(newText);
                    }
                    return false;
                }
            });
    }

    private class AsyncTaskRunner extends AsyncTask<File, String, ArrayList<File>> {
        @Override
        protected ArrayList<File> doInBackground(File... params) {
            pdfFileList.clear();
            pdfFileList.addAll(getfile(params[0]));
            return pdfFileList;
        }


        @Override
        protected void onPostExecute(ArrayList<File> files) {
            super.onPostExecute(files);
            adapter.updateList(files);
            rvPdfList.unVeil();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           rvPdfList.veil();
        }


        @Override
        protected void onProgressUpdate(String... text) {


        }
    }
    private ArrayList<File> getfile(File dir) {
        File listFile[]=dir.listFiles();
        File f = new File(String.valueOf(dir));
        System.out.println(f.isDirectory());
        if (listFile != null && listFile.length > 0) {
            for (int i = 0; i < listFile.length; i++) {

                if (listFile[i].isDirectory()) {
                    // pdfFileList.add(listFile[i]);
                    getfile(listFile[i]);

                } else {
                    if (listFile[i].getName().endsWith(".pdf"))
                    {
                        pdfFileList.add(listFile[i]);
                    }
                }
            }
        }
        return pdfFileList;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{READ_EXTERNAL_STORAGE,WRITE_EXTERNAL_STORAGE},
                PERMISSION_REQUEST_CODE);
    }

    private boolean checkPermission() {
        int writePermission= ContextCompat.checkSelfPermission(getApplicationContext(),WRITE_EXTERNAL_STORAGE);
        int readPermission= ContextCompat.checkSelfPermission(getApplicationContext(),READ_EXTERNAL_STORAGE);
        return writePermission== PackageManager.PERMISSION_GRANTED && readPermission==PackageManager.PERMISSION_GRANTED;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==PERMISSION_REQUEST_CODE){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
                Toast.makeText(this.getApplicationContext(),"Permission granted.",Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this.getApplicationContext(),"Permission denied.",Toast.LENGTH_SHORT).show();
        }
    }
}