package com.example.mylastchatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mylastchatapp.adapter.UserAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.ktx.Firebase;

public class MainActivity extends AppCompatActivity {

    EditText email,password;
    TextView signupmove;
    Button LOGIN;
    String emailentered;
    String passwordentered;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        UserAdapter userAdapter = new UserAdapter(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        signupmove=findViewById(R.id.signupmove);
        email=findViewById(R.id.enter_email_id);
        password=findViewById(R.id.enter_password_id);
        LOGIN=findViewById(R.id.button_next1);
//        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
//            startActivity(new Intent(MainActivity.this, ChatActivity.class));
//            finish();
//        }


        LOGIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        signupmove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
    }

    void login(){
        emailentered= email.getText().toString();
        passwordentered= password.getText().toString();
        FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(emailentered.trim(),passwordentered)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        startActivity(new Intent(MainActivity.this, ChatActivity.class));
                    }
                });
    }
}