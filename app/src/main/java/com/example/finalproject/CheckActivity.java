package com.example.finalproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

import kr.co.bootpay.Bootpay;
import kr.co.bootpay.BootpayAnalytics;
import kr.co.bootpay.enums.Method;
import kr.co.bootpay.enums.PG;
import kr.co.bootpay.enums.UX;
import kr.co.bootpay.listener.CancelListener;
import kr.co.bootpay.listener.CloseListener;
import kr.co.bootpay.listener.ConfirmListener;
import kr.co.bootpay.listener.DoneListener;
import kr.co.bootpay.listener.ErrorListener;
import kr.co.bootpay.listener.ReadyListener;
import kr.co.bootpay.model.BootExtra;
import kr.co.bootpay.model.BootUser;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.finalproject.RemoteService.BASE_URL_FINALORDER;

public class CheckActivity extends AppCompatActivity {
    RemoteService remoteService;
    Retrofit retrofit;

    ImageView imgCheck;
    TextView txtNameCheck, txtPriceCheck, txtCountCheck, txtSumPriceCheck;
    Button btnMinus,btnPlus,btnOrderFinal;

    String strName,strImg;
    int intCode,intPrice;

    private int stuck = 10;
    String PhoneNum;

    public static String emailput;
    private final int REQUEST_READ_PHONE_STATE=1;

    Date dt = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String currentTime = sdf.format(dt);




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        getSupportActionBar().setTitle("주문갯수확인");
        Intent intent = getIntent();


        retrofit=new Retrofit.Builder().baseUrl(BASE_URL_FINALORDER).addConverterFactory(GsonConverterFactory.create()).build();
        remoteService = retrofit.create(RemoteService.class);


        intCode =  intent.getIntExtra("productcode",0);
        strName = intent.getStringExtra("productname");
        intPrice = intent.getIntExtra("price",0);
        strImg = intent.getStringExtra("image");
        emailput = intent.getStringExtra("email");

        //System.out.println("상품코드: "+intent.getStringExtra("productcode"));
        System.out.println("상품코드: "+intent.getIntExtra("productcode",0));
        System.out.println("상품이름: "+intent.getStringExtra("productname"));
        //System.out.println("상품가격: "+intent.getStringExtra("price"));
        System.out.println("상품가격: " +intent.getIntExtra("price",0));
        System.out.println("이미지파일: "+intent.getStringExtra("image"));
        System.out.println("이메일:" + intent.getStringExtra("email"));

        imgCheck = findViewById(R.id.imgCheck);
        txtNameCheck = findViewById(R.id.txtNameCheck);
        txtPriceCheck = findViewById(R.id.txtPriceCheck);
        txtCountCheck = findViewById(R.id.txtCountCheck);
        txtSumPriceCheck = findViewById(R.id.txtSumPriceCheck);

        btnMinus = findViewById(R.id.btnMinus);
        btnPlus = findViewById(R.id.btnPlus);
        btnOrderFinal = findViewById(R.id.btnOrderFinal);

        Picasso.with(this).load("http://192.168.0.15:8088/finalProduct/image/"+ strImg ).into(imgCheck);
        txtNameCheck.setText("상품이름: " + strName);
        txtPriceCheck.setText("상품가격: " +Integer.toString(intPrice)+" 원");
        txtSumPriceCheck.setText(Integer.toString(intPrice*Integer.parseInt(txtCountCheck.getText().toString())));

        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int intCountCheck = Integer.parseInt(txtCountCheck.getText().toString());
                if(intCountCheck>=1){
                    intCountCheck++;
                    txtCountCheck.setText(Integer.toString(intCountCheck));
                    txtSumPriceCheck.setText(Integer.toString(intPrice*Integer.parseInt(txtCountCheck.getText().toString())));
                }else{
                    Toast.makeText(CheckActivity.this,"수량은 1개이상이어야 합니다.",Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int intCountCheck = Integer.parseInt(txtCountCheck.getText().toString());
                if(intCountCheck>=2){
                    intCountCheck--;
                    txtCountCheck.setText(Integer.toString(intCountCheck));
                    txtSumPriceCheck.setText(Integer.toString(intPrice*Integer.parseInt(txtCountCheck.getText().toString())));
                }else{
                    Toast.makeText(CheckActivity.this,"수량은 1개이상이어야 합니다.",Toast.LENGTH_SHORT).show();
                }
            }
        });

        /*결제*/
        BootpayAnalytics.init(this, Apikey.APPLICATION_ID);

        btnOrderFinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                //onClick_request(view);
                AlertDialog.Builder box = new AlertDialog.Builder(CheckActivity.this);
                box.setTitle("결제");
                box.setMessage("결제하시겠습니까?");
                box.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        onClick_request(view);
                    }
                });
                box.setNegativeButton("아니오",null);
                box.show();
            }
        });
    }


    public void onClick_request(View v) {
        // 결제호출
        BootUser bootUser = new BootUser().setPhone(PhoneNum/*"010-9954-4256"*/);
        BootExtra bootExtra = new BootExtra().setQuotas(new int[] {0,2,3});

        Bootpay.init(getFragmentManager())
                .setApplicationId(Apikey.APPLICATION_ID/*[ Android SDK용 Application ID ]*/) // 해당 프로젝트(안드로이드)의 application id 값
                .setPG(PG.KAKAO) // 결제할 PG 사
                .setMethod(Method.EASY) // 결제수단
                .setContext(this)
                .setBootUser(bootUser)
                .setBootExtra(bootExtra)
                .setUX(UX.PG_DIALOG)
                //.setUserPhone("010-9954-4256") // 구매자 전화번호
                .setName(strName) // 결제할 상품명
                .setOrderId("1234") // 결제 고유번호expire_month
                .setPrice(Integer.parseInt(txtSumPriceCheck.getText().toString())) // 결제할 금액
                .addItem("마우's 스", 1, "ITEM_CODE_MOUSE", 100) // 주문정보에 담길 상품정보, 통계를 위해 사용
                .addItem("키보드", 1, "ITEM_CODE_KEYBOARD", 200, "패션", "여성상의", "블라우스") // 주문정보에 담길 상품정보, 통계를 위해 사용
                .onConfirm(new ConfirmListener() { // 결제가 진행되기 바로 직전 호출되는 함수로, 주로 재고처리 등의 로직이 수행
                    @Override
                    public void onConfirm(@Nullable String message) {

                        if (0 < stuck) Bootpay.confirm(message); // 재고가 있을 경우.
                        else Bootpay.removePaymentWindow(); // 재고가 없어 중간에 결제창을 닫고 싶을 경우
                        Log.d("confirm", message);
                    }
                })
                .onDone(new DoneListener() { // 결제완료시 호출, 아이템 지급 등 데이터 동기화 로직을 수행합니다
                    @Override
                    public void onDone(@Nullable String message) {
                        Log.d("done", message);
                        Toast.makeText(CheckActivity.this,"결제를 완료",Toast.LENGTH_SHORT).show();
                        ///////orderdata에 저장///////

                        OrderVO orderVO = new OrderVO();
                        orderVO.setOrdertime(currentTime);
                        orderVO.setCount(Integer.parseInt(txtCountCheck.getText().toString()));
                        //orderVO.setEmail(getIntent().getStringExtra("email"));
                        //orderVO.setEmail(emailput);
                        orderVO.setEmail(OrderActivity.email);
                        orderVO.setProductcode(intCode);

                        System.out.println(currentTime);
                        System.out.println(Integer.parseInt(txtCountCheck.getText().toString()));
                        System.out.println(getIntent().getStringExtra("email"));
                        System.out.println(OrderActivity.email);
                        System.out.println(intCode);

                        Call<Void> call = remoteService.insertOrder(orderVO.getOrdertime(),orderVO.getCount(),orderVO.getEmail(),orderVO.getProductcode());
                        call.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                /*
                                //Toast.makeText(AddActivity.this,"저장완료",Toast.LENGTH_SHORT).show();
                                finish();
                                Intent intent = new Intent(AddActivity.this,MainActivity.class);
                                startActivity(intent);
                                */
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {

                            }
                        });
                        ///////////////////////////////
                        finish();/*삭제할수도있음*/
                        Intent intent  = new Intent(CheckActivity.this,OrderActivity.class);
                        intent.putExtra("email",emailput);
                        startActivity(intent);
                    }
                })
                .onReady(new ReadyListener() { // 가상계좌 입금 계좌번호가 발급되면 호출되는 함수입니다.
                    @Override
                    public void onReady(@Nullable String message) {
                        Log.d("ready", message);
                    }
                })
                .onCancel(new CancelListener() { // 결제 취소시 호출
                    @Override
                    public void onCancel(@Nullable String message) {

                        Log.d("cancel", message);
                        Toast.makeText(CheckActivity.this,"결제를 취소",Toast.LENGTH_SHORT).show();
                    }
                })
                .onError(new ErrorListener() { // 에러가 났을때 호출되는 부분
                    @Override
                    public void onError(@Nullable String message) {
                        Log.d("error", message);
                    }
                })
                .onClose(
                        new CloseListener() { //결제창이 닫힐때 실행되는 부분
                            @Override
                            public void onClose(String message) {
                                Log.d("close", "close");

                            }
                        })
                .request();
    }
}