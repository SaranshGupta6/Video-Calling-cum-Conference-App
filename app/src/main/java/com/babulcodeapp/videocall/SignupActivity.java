package com.babulcodeapp.videocall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignupActivity extends AppCompatActivity {
    FirebaseAuth auth;
    EditText emailBox, passwordBox , namebox;
    Button loginBtn, signupBtn;

    FirebaseFirestore database;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getSupportActionBar().hide();

        database = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(SignupActivity.this);
        progressDialog.setTitle("Creating Account");
        progressDialog.setMessage("We are creating your account");

        namebox=findViewById(R.id.namebox);
        emailBox = findViewById(R.id.emailBox);
        passwordBox = findViewById(R.id.passwordBox);

        loginBtn = findViewById(R.id.loginBtn);
        signupBtn = findViewById(R.id.createBtn);

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                String email,pass,name;
                email=emailBox.getText().toString();
                pass=passwordBox.getText().toString();
                name=namebox.getText().toString();

                User user = new User();
                user.setEmail(email);
                user.setPass(pass);
                user.setName(name);


                auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()){
                            database.collection("Users")
                                    .document().set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                                }
                            });
                            Toast.makeText(SignupActivity.this, "Account is Created", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(SignupActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
            }
        });

    }
}