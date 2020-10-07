package com.example.yu_map.Activity;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.yu_map.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.ServerTimestamp;

public class LoginActivity extends AppCompatActivity {

    public String GlobalEmail;
    private EditText mEmail, mPassword;
    private Button mLoginButton, mRegisterButton;
    FirebaseAuth mAuth;

    private static final String TAG = "LoginActivity";

    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        mEmail = findViewById(R.id.Login_email);
        mPassword = findViewById(R.id.Login_password);
        mLoginButton = findViewById(R.id.Login_email_sign_in_button);
        mRegisterButton = findViewById(R.id.Login_link_register);

        mAuth = FirebaseAuth.getInstance();

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login (mEmail.getText().toString(), mPassword.getText().toString());
                ManageConnection();

            }
        });

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));

            }
        });
    }

    private void Login (String email, String password) {
        GlobalEmail = email;
        context = this;
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "SignWithEmail:onComplete" + task.isSuccessful());
                        if(task.isSuccessful()){
                            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                            Toast.makeText(LoginActivity.this, "Authentication Success", Toast.LENGTH_LONG).show();
                        }
                        else{
                            Log.d(TAG, "SignInWithEmail:failed", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void ManageConnection(){

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        String TempName = GlobalEmail;
        int idx = TempName.indexOf("@");

        final String NickName = GlobalEmail.substring(0, idx);


        final DatabaseReference connectRegerence = db.getReference().child("connection");
        final DatabaseReference lastConnected = db.getReference().child("lastConndected");
        final DatabaseReference infoConnected = db.getReference().child(".info/connected");

        infoConnected.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean connected = dataSnapshot.getValue(boolean.class);

                if(connected){
                    DatabaseReference connect = connectRegerence.child(NickName);
                    connect.setValue(true);
                    connect.onDisconnect().setValue(false);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}