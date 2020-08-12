package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.finalproject.RemoteService.BASE_URL_FINALACCOUNT;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText edtRegisterEmail, edtRegisterPassword, edtRegisterName;//등록할 이메일,패스워드,이름
    EditText edtRegisterTel1,edtRegisterTel2,edtRegisterTel3;//ex)010-0000-0000,등록할 전화번호
    Button btnRegister2;
    String strEmail, strPassword, strName, strTel1, strTel2, strTel3;

    Retrofit retrofit;
    RemoteService remoteService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setTitle("회원가입");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edtRegisterEmail = (EditText)findViewById(R.id.edtRegisterEmail);
        edtRegisterPassword = (EditText)findViewById(R.id.edtRegisterPassword);
        edtRegisterName = (EditText)findViewById(R.id.edtRegisterName);
        edtRegisterTel1 = (EditText)findViewById(R.id.edtRegisterTel1);
        edtRegisterTel2 = (EditText)findViewById(R.id.edtRegisterTel2);
        edtRegisterTel3 = (EditText)findViewById(R.id.edtRegisterTel3);
        btnRegister2 = (Button)findViewById(R.id.btnRegister2);


        retrofit = new Retrofit.Builder().baseUrl(BASE_URL_FINALACCOUNT).addConverterFactory(GsonConverterFactory.create()).build();
        remoteService=retrofit.create(RemoteService.class);//웹과 mysql관련

        mAuth= FirebaseAuth.getInstance();//firebase관련

        btnRegister2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strEmail=edtRegisterEmail.getText().toString();
                strPassword=edtRegisterPassword.getText().toString();
                strName=edtRegisterName.getText().toString();
                strTel1=edtRegisterTel1.getText().toString();
                strTel2=edtRegisterTel2.getText().toString();
                strTel3=edtRegisterTel3.getText().toString();
                if(strEmail.equals("") || strPassword.equals("") || strName.equals("") || strTel1.equals("") || strTel2.equals("") || strTel3.equals("")){
                    Toast.makeText(RegisterActivity.this,"입력하지 않은 빈칸이 있습니다",Toast.LENGTH_SHORT).show();
                }else{
                    registerUser(strEmail,strPassword);
                }

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
                    //Toast.makeText(RegisterActivity.this,"등록성공",Toast.LENGTH_SHORT).show();

                    //mysql도 동시에 등록
                    OrderVO orderVO = new OrderVO();
                    orderVO.setEmail(edtRegisterEmail.getText().toString());
                    orderVO.setName(edtRegisterName.getText().toString());
                    String strtel = edtRegisterTel1.getText().toString() + "-" + edtRegisterTel2.getText().toString() + "-" + edtRegisterTel3.getText().toString();
                    orderVO.setTel(strtel);

                    Call<Void> call = remoteService.insertAccount(orderVO.getEmail(),orderVO.getName(),orderVO.getTel());
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            setResult(RESULT_OK);
                            finish();
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {

                        }
                    });


                }else{
                    Toast.makeText(RegisterActivity.this,"등록실패",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}