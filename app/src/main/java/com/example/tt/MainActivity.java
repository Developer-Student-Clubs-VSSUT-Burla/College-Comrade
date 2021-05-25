package com.example.tt;

import android.app.ActionBar;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {

    public static final String Student_name = "studentname";

    private EditText email;
    private EditText password;
    private Button btnSubmit;
    private Button btnsignup;
    private Button next_button;
    private Button back_button;
    private TextView btnSubmit;
    private TextView btnsignup;
    private FirebaseAuth mAuth;
    private TextView btncontact;
    private ProgressDialog progressDialog;
    private EditText name;
    private EditText mobno;
    private EditText roll;

    private LinearLayout page1;
    private LinearLayout page2;


    DatabaseReference databasestudents;

    @Override
    protected void onStart() {
        super.onStart();

        check c = new check(getApplicationContext());
        c.firsttime();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        progressDialog = new ProgressDialog(MainActivity.this);
        email = findViewById(R.id.email);
        name = findViewById(R.id.name);
        password = findViewById(R.id.password);
        btnSubmit = findViewById(R.id.login);

        mobno = findViewById(R.id.no);
        roll = findViewById(R.id.regd1);
        btnsignup = findViewById(R.id.signup);

        next_button = findViewById(R.id.next);
        back_button = findViewById(R.id.back);

        page1 = findViewById(R.id.page1);
        page2 = findViewById(R.id.page2);

        databasestudents = FirebaseDatabase.getInstance().getReference("students");
        mAuth = FirebaseAuth.getInstance();

        databasestudents = FirebaseDatabase.getInstance().getReference("students");
        mAuth = FirebaseAuth.getInstance();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel =
                    new NotificationChannel("MyNotifications", "MyNotifications", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        FirebaseMessaging.getInstance().subscribeToTopic("general")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Successful";
                        if (!task.isSuccessful()) {
                            msg = "Failed";
                        }
                        // Log.d(TAG, msg);
                        // Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page1.setVisibility(View.VISIBLE);
                page2.setVisibility(View.GONE);
                btnsignup.setEnabled(false);
            }
        });

        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(name.getText().toString().equals("") || roll.getText().toString().equals("") || mobno.getText().toString().equals("")){
                    Toast.makeText(MainActivity.this, "Fill in all fields", Toast.LENGTH_SHORT).show();
                }

                else {
                    page1.setVisibility(View.GONE);
                    page2.setVisibility(View.VISIBLE);
                    btnsignup.setEnabled(true);
                }
            }
        });

        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String e = email.getText().toString();
                String p = password.getText().toString();
                String m = mobno.getText().toString();
                if (TextUtils.isEmpty(p)) {
                    password.setError(" enter a  Password");
                }
                if (TextUtils.isEmpty(e)) {
                    Toast.makeText(MainActivity.this, "please enter email", Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(p)) {
                    Toast.makeText(MainActivity.this, "please enter password", Toast.LENGTH_SHORT).show();
                }

                if (!TextUtils.isEmpty(e) && !TextUtils.isEmpty(p)) {
                    progressDialog.show();
                    progressDialog.setMessage("Registering user");

                mAuth.createUserWithEmailAndPassword(e, p).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            String mssg = task.getException().getMessage();
                            Toast.makeText(MainActivity.this, mssg, Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        } else {
                            String N = name.getText().toString();
                            String E = email.getText().toString();
                            String M = mobno.getText().toString();
                            String R = roll.getText().toString();
                            String id = databasestudents.push().getKey();

                            studentdatabase studentdatabase = new studentdatabase(id, N, R, E, M);

                                databasestudents.child(id).setValue(studentdatabase);


                                progressDialog.setMessage("Registering user");
                                Toast.makeText(MainActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();


                            }

                        }
                    });
                }
            }
        });


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String e = email.getText().toString();
                String p = password.getText().toString();

                if (TextUtils.isEmpty(e)) {
                    Toast.makeText(MainActivity.this, "please enter email", Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(p)) {
                    Toast.makeText(MainActivity.this, "please enter password", Toast.LENGTH_SHORT).show();
                }

                if (!TextUtils.isEmpty(e) && !TextUtils.isEmpty(p)) {
                    progressDialog.show();

                            check c = new check(getApplicationContext());
                            c.secondtime();
                            String N = name.getText().toString();
                            Toast.makeText(MainActivity.this, "logged in", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), PROFILE.class);
                    mAuth.signInWithEmailAndPassword(e, p).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "Invalid email or password/Register first", Toast.LENGTH_SHORT).show();
                            } else {

                                check c = new check(getApplicationContext());
                                c.secondtime();
                                String N = name.getText().toString();
                                Toast.makeText(MainActivity.this, "logged in", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), PROFILE.class);

                                intent.putExtra(Student_name, N);
                                startActivity(intent);


                            }
                            progressDialog.dismiss();
                        }
                    });
                }
            }
        });


        btncontact = (TextView) findViewById(R.id.contact);

        btncontact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String N = name.getText().toString();
                Intent intent = new Intent(getApplicationContext(), contact.class);

                intent.putExtra(Student_name, N);
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
