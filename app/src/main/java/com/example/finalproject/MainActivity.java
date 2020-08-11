package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
                startActivityForResult(intent,1);
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

    public void loginUser(final String email, String password){
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(MainActivity.this,"로그인성공",Toast.LENGTH_SHORT).show();
                    FirebaseUser user = mAuth.getCurrentUser();
                    Intent intent = new Intent(MainActivity.this,OrderActivity.class);
                    intent.putExtra("email",email);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(MainActivity.this,"로그인실패",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==1 && resultCode==RESULT_OK){
            Toast.makeText(this,"계정등록완료",Toast.LENGTH_SHORT).show();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}