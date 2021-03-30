package com.example.tt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

public class Login extends AppCompatActivity {

    public static final String Student_name = "studentname";

    private FirebaseAuth mAuth;
    private Button login,fb,google;
    DatabaseReference databasestudents;
    EditText email,password;
    private ProgressDialog progressDialog;

    @Override
    protected void onStart() {
        super.onStart();

        check c = new check(getApplicationContext());
        c.firsttime();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        databasestudents = FirebaseDatabase.getInstance().getReference("students");
        mAuth = FirebaseAuth.getInstance();

        email = findViewById(R.id._name);
        password = findViewById(R.id._password);
        login = findViewById(R.id.login_btn);
        fb = findViewById(R.id.facebook_login);
        google = findViewById(R.id.google_login);


        progressDialog = new ProgressDialog(Login.this);

        FirebaseMessaging.getInstance().subscribeToTopic("general")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Successful";
                        if (!task.isSuccessful()) {
                            msg = "Failed";
                        }
                    }
                });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginCheck();
            }
        });

    }

    private void loginCheck() {


        String e = email.getText().toString();
        String p = password.getText().toString();

        if (TextUtils.isEmpty(e)) {
            Toast.makeText(Login.this, "please enter email", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(p)) {
            Toast.makeText(Login.this, "please enter password", Toast.LENGTH_SHORT).show();
        }

        if (!TextUtils.isEmpty(e) && !TextUtils.isEmpty(p)) {
            progressDialog.show();
            progressDialog.setMessage("User Login");

            mAuth.signInWithEmailAndPassword(e, p).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        Toast.makeText(Login.this, "Invalid email or password/Register first", Toast.LENGTH_SHORT).show();
                    } else {

                        check c = new check(getApplicationContext());
                        c.secondtime();
                        String E = email.getText().toString();
                        Toast.makeText(Login.this, "logged in", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), PROFILE.class);

                        intent.putExtra(Student_name, E);
                        startActivity(intent);


                    }
                    progressDialog.dismiss();
                }
            });
        }
    }
}