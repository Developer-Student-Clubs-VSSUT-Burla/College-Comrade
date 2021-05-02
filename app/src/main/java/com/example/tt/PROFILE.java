package com.example.tt;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.example.tt.todo.TodoRV.TodoListActivity;
import com.google.firebase.auth.FirebaseAuth;

public class PROFILE extends AppCompatActivity {


    // private Button btncontact;
    check c;
    private int mode = 0;
    private String Filename = "sdfile";
    private String Data = "b";
    private Button btntt;
    private Button btnhol;
    private Button btnfac;
    private Button btnsb;
    //  private Button btnln;


    public static final String Student_name = "studentname";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        // Cardview btncontact = (Cardview)findViewById(R.id.btncontact);
        CardView card1 = (CardView) findViewById(R.id.btncontact);
        // btnln=(Button)findViewById(R.id.ln);
        CardView card2 = (CardView) findViewById(R.id.ln);

        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), contact.class);
                startActivity(intent);
            }
        });
        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Lecturenotes.class);
                startActivity(intent);
            }
        });

        // btntt=(Button)findViewById(R.id.tt);
        CardView card3 = (CardView) findViewById(R.id.tt);
        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), timetable.class);
                startActivity(intent);
            }
        });

        //  btnfac=(Button)findViewById(R.id.fac);
        CardView card4 = (CardView) findViewById(R.id.fac);
        card4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), faculty.class);
                startActivity(intent);
            }
        });

        // btnhol=(Button)findViewById(R.id.ho);
        CardView card5 = (CardView) findViewById(R.id.ho);
        card5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), holiday.class);
                startActivity(intent);
            }
        });

        //  btnsb=(Button)findViewById(R.id.sb);
        CardView card6 = (CardView) findViewById(R.id.sb);
        card6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), syllabus.class);
                startActivity(intent);
            }
        });
        CardView card7 = (CardView) findViewById(R.id.logout);
        card7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                SharedPreferences preferences = getSharedPreferences(Filename, mode);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean(Data, false);
                editor.commit();
                FirebaseAuth.getInstance().signOut();
                startActivity(intent);
                finish();
            }
        });
        CardView card8 = (CardView) findViewById(R.id.btnTodo);

        card8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TodoListActivity.class);
                startActivity(intent);
            }
        });
        CardView card9 = (CardView) findViewById(R.id.btnPdfReader);

        card8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TodoListActivity.class);
                startActivity(intent);
            }
        });
    }
    int backButtonCount = 0;
    @Override
    public void onBackPressed() {
        if (backButtonCount >= 1) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Press the back button once again to close the application.", Toast.LENGTH_SHORT).show();
            backButtonCount++;
        }


    }


}