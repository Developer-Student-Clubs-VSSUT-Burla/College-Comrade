package com.example.tt;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    private FirebaseAuth mAuth;
    private Button btncontact;
    private ProgressDialog progressDialog;
    private EditText name;
    private EditText mobno;
    private EditText roll;

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
        progressDialog = new ProgressDialog(MainActivity.this);
        email = (EditText) findViewById(R.id.email);
        name = (EditText) findViewById(R.id.name);
        password = (EditText) findViewById(R.id.password);
        btnSubmit = (Button) findViewById(R.id.login);

        mobno = (EditText) findViewById(R.id.no);
        roll = (EditText) findViewById(R.id.regd1);
        btnsignup = (Button) findViewById(R.id.signup);
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


        btnsignup.setOnClickListener(new View.OnClickListener() {
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


        btncontact = (Button) findViewById(R.id.contact);

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
