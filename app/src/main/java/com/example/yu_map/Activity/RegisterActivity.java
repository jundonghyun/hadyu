package com.example.yu_map.Activity;



import androidx.annotation.NonNull;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.yu_map.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegisterActivity extends Activity {

    private static final String TAG = "RegisterActivity";

    //widgets
    private EditText mEmail, mPassword, mSchoolnum, mConfirmPassword;
    private Button mRegisterBtn;
    private ProgressBar mProgressBar;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mEmail = findViewById(R.id.input_email);
        mPassword = findViewById(R.id.input_password);
        mConfirmPassword = findViewById(R.id.input_confirm_password);
        mSchoolnum = findViewById(R.id.shcoolnum);
        mRegisterBtn = findViewById(R.id.btn_register);
        mProgressBar = findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();


        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAccount(mEmail.getText().toString(), mPassword.getText().toString(), mSchoolnum.getText().toString());
            }
        });

    }

    /* 계정 생성하는 메소드 */
    private void createAccount(String email, String password, String schoolnum){

        Map<String, String> User = new HashMap<>();

        User.put("Email", email);
        User.put("Password", password);
        User.put("FriendRequest", "false");
        User.put("학번", schoolnum);


        DocumentReference newUserRef = db
                .collection("User")
                .document(email);


        newUserRef.set(User).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });

        if(!isValidEmail(email)){
            Toast.makeText(RegisterActivity.this, "올바른 이메일 형식이 아닙니다",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if(!isValidPasswd(password)){
            Toast.makeText(RegisterActivity.this, "비밀번호는 영문자와 숫자만 사용가능합니다",
                    Toast.LENGTH_SHORT).show();

            return;
        }
        mProgressBar.setVisibility(ProgressBar.VISIBLE);

        /* firebase와 계정 연동하는곳 */
        mAuth.createUserWithEmailAndPassword(mEmail.getText().toString(), mPassword.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(!task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this, "서버와 인증 오류",
                                    Toast.LENGTH_SHORT).show();
                        }
                        mProgressBar.setVisibility(ProgressBar.INVISIBLE);
                    }

                });


        Toast.makeText(RegisterActivity.this, "계정을 생성했습니다", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
    }


    /* 유효성 검사 */
    private boolean isValidPasswd(String target){
        Pattern p = Pattern.compile("(^.*(?=.{6,100})(?=.*[0-9])(?=.*[a-zA-Z]).*$)");

        Matcher m = p.matcher(target);
        if (m.find() && !target.matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*")){
            return true;
        }else{
            return false;
        }
    }

    /* 이메일 유효성 검사 */
    private boolean isValidEmail(String target) {
        if (target == null || TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }





}