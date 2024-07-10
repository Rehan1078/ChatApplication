package com.example.mylastchatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mylastchatapp.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.FirebaseDatabaseKtxRegistrar;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {
    EditText email,username,password,reenteredpassword;
    Button SIGNUP;
    DatabaseReference databaseReference;
    String emailentered,usernameentered,passwordentered,Confirmpassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        email=findViewById(R.id.enter_email_id);
        username=findViewById(R.id.enter_username_id);
        password=findViewById(R.id.enter_password_id);
        reenteredpassword=findViewById(R.id.enter_reenterpassword_id);
        databaseReference= FirebaseDatabase.getInstance().getReference("users");
        SIGNUP=findViewById(R.id.button_next1);
        SIGNUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Signup();
            }
        });


    }

    void Signup(){
        emailentered=email.getText().toString();
        usernameentered=username.getText().toString();
        passwordentered=password.getText().toString();
        Confirmpassword=reenteredpassword.getText().toString();
        if (!passwordentered.equals(Confirmpassword)) {
            reenteredpassword.setError("Passwords should match");
            return;
        }


        FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(emailentered.trim(),passwordentered)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(usernameentered).build();
                        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                        firebaseUser.updateProfile(userProfileChangeRequest);
                        UserModel userModel=new UserModel(emailentered,usernameentered,passwordentered,FirebaseAuth.getInstance().getUid());
//                        userModel.setEmail(emailentered);
//                        userModel.setUsername(usernameentered);
//                        userModel.setPassword(passwordentered);
//                        userModel.setReenteredpassword(Confirmpassword);
                        databaseReference.child(FirebaseAuth.getInstance().getUid())
                                .setValue(userModel);
                        startActivity(new Intent(SignupActivity.this, MainActivity.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle failure, log error, or display a toast message
                        Log.e("SignupActivity", "Error creating user: " + e.getMessage());
                        Toast.makeText(SignupActivity.this,  e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

    }

