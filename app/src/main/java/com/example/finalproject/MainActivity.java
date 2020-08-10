package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    EditText edtEmail,edtPassword;
    Button btnRegister,btnLogin;
    String strEmail,strPassword;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("메인화면");

        mAuth= FirebaseAuth.getInstance();
        edtEmail = (EditText)findViewById(R.id.edtEmail);
        edtPassword = (EditText)findViewById(R.id.edtPassword);
        btnRegister = (Button)findViewById(R.id.btnRegister);
        btnLogin = (Button)findViewById(R.id.btnLogin);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strEmail = edtEmail.getText().toString();
                strPassword = edtPassword.getText().toString();
                if(strEmail.equals("")){
                    Toast.makeText(MainActivity.this,"아이디를 입력하세요",Toast.LENGTH_SHORT).show();
                }
                else if(strPassword.equals("")){
                    Toast.makeText(MainActivity.this,"비밀번호를 입력하세요",Toast.LENGTH_SHORT).show();
                }
                else {
                    loginUser(strEmail, strPassword);
                }
            }
        });



    }

    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    public void loginUser(String email,String password){
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(MainActivity.this,"로그인성공",Toast.LENGTH_SHORT).show();
                    FirebaseUser user = mAuth.getCurrentUser();
                }
                else{
                    Toast.makeText(MainActivity.this,"로그인실패",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}