package com.example.tt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tt.todo.TodoRV.TodoListActivity;

public class PROFILE extends AppCompatActivity {


        private Button btncontact;
        private Button btntt;
        private Button btnhol;
        private  Button btnfac;
        private  Button btnsb;
        private Button btnln;
        private Button btnTodoList;
        check c;




        public static final String Student_name="studentname";
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_profile);




            btncontact = (Button)findViewById(R.id.contact);
            btnln=(Button)findViewById(R.id.ln);

            btncontact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(getApplicationContext(),contact.class);

                    startActivity(intent);
                }
            });
            btnln.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(getApplicationContext(),Lecturenotes.class);

                    startActivity(intent);
                }
            });

            btntt=(Button)findViewById(R.id.tt);
            btntt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent=new Intent(getApplicationContext(),timetable.class);

                    startActivity(intent);
                }
            });

            btnfac=(Button)findViewById(R.id.fac);
            btnfac.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent=new Intent(getApplicationContext(),faculty.class);

                    startActivity(intent);
                }
            });

            btnhol=(Button)findViewById(R.id.ho);
            btnhol.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent=new Intent(getApplicationContext(),holiday.class);

                    startActivity(intent);
                }
            });

            btnsb=(Button)findViewById(R.id.sb);
            btnsb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent=new Intent(getApplicationContext(),syllabus.class);

                    startActivity(intent);
                }
            });

            btnTodoList=(Button)findViewById(R.id.btnTodoList);
            btnTodoList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent=new Intent(getApplicationContext(), TodoListActivity.class);

                    startActivity(intent);
                }
            });


        }
    int backButtonCount=0;
    @Override
    public void onBackPressed()
    {
        if(backButtonCount >= 1)
        {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(this, "Press the back button once again to close the application.", Toast.LENGTH_SHORT).show();
            backButtonCount++;
        }
    }


}
