package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class CheckActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        getSupportActionBar().setTitle("주문갯수확인");
        Intent intent = getIntent();
        //System.out.println("상품코드: "+intent.getStringExtra("productcode"));
        System.out.println("상품코드: "+intent.getIntExtra("productcode",0));
        System.out.println("상품이름: "+intent.getStringExtra("productname"));
        //System.out.println("상품가격: "+intent.getStringExtra("price"));
        System.out.println("상품가격: " +intent.getIntExtra("price",0));
        System.out.println("이미지파일: "+intent.getStringExtra("image"));


    }
}