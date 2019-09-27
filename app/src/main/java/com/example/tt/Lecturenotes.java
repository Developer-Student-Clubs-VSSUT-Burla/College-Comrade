package com.example.tt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;



public class Lecturenotes extends AppCompatActivity {
    private Button btnmath,btnoop,btndsa,btndec,btneec;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecturenotes);
        btndec=(Button)findViewById(R.id.dec);
        btndec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Refer class notes",Toast.LENGTH_LONG).show();
            }
        });
        btndsa=(Button)findViewById(R.id.dsa);
        btndsa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),dsa.class);

                startActivity(intent);
            }
        });
        btnmath=(Button)findViewById(R.id.math);
        btnmath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),math.class);

                startActivity(intent);
            }
        });
        btnoop=(Button)findViewById(R.id.oop);
        btnoop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),oop.class);

                startActivity(intent);
            }
        });
        btneec=(Button)findViewById(R.id.eco);
        btneec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),eec.class);

                startActivity(intent);
            }
        });
    }
}
