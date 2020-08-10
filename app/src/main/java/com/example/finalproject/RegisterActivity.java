package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText edtRegisterEmail, edtRegisterPassword, edtRegisterName;//등록할 이메일,패스워드,이름
    EditText edtRegisterTel1,edtRegisterTel2,edtRegisterTel3;//ex)010-0000-0000,등록할 전화번호
    Button btnRegister2;
    String strEmail, strPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setTitle("회원가입");
        edtRegisterEmail = (EditText)findViewById(R.id.edtRegisterEmail);
        edtRegisterPassword = (EditText)findViewById(R.id.edtRegisterPassword);
        edtRegisterName = (EditText)findViewById(R.id.edtRegisterName);
        edtRegisterTel1 = (EditText)findViewById(R.id.edtRegisterTel1);
        edtRegisterTel2 = (EditText)findViewById(R.id.edtRegisterTel2);
        edtRegisterTel3 = (EditText)findViewById(R.id.edtRegisterTel3);
        btnRegister2 = (Button)findViewById(R.id.btnRegister2);


        mAuth= FirebaseAuth.getInstance();

        btnRegister2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strEmail=edtRegisterEmail.getText().toString();
                strPassword=edtRegisterPassword.getText().toString();
                registerUser(strEmail,strPassword);
            }
        });

    }

    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    public void registerUser(String strEmail,String strPassword){
        mAuth.createUserWithEmailAndPassword(strEmail,strPassword).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = mAuth.getCurrentUser();
                    Toast.makeText(RegisterActivity.this,"등록성공",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(RegisterActivity.this,"등록실패",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}