package com.example.finalproject;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.finalproject.OrderActivity.email;
import static com.example.finalproject.RemoteService.BASE_URL_FINALACCOUNT;
import static com.example.finalproject.RemoteService.BASE_URL_FINALPRODUCT;

public class InformationFragment extends Fragment {
    EditText edtEmailEx,edtNameEx,edtTelEx;

    Retrofit retrofit;
    RemoteService remoteService;

    List<OrderVO> arrayOrder = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_information, container, false);

        //((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("정보");

        // Inflate the layout for this fragment
        edtEmailEx = view.findViewById(R.id.edtEmailEx);
        edtNameEx = view.findViewById(R.id.edtNameEx);
        edtTelEx = view.findViewById(R.id.edtTelEx);

        retrofit = new Retrofit.Builder().baseUrl(BASE_URL_FINALACCOUNT).addConverterFactory(GsonConverterFactory.create()).build();
        remoteService = retrofit.create(RemoteService.class);

        Call<List<OrderVO>> call = remoteService.selectAccount(email);
        call.enqueue(new Callback<List<OrderVO>>() {
            @Override
            public void onResponse(Call<List<OrderVO>> call, Response<List<OrderVO>> response) {
                arrayOrder= response.body();
                //OrderVO vo = new OrderVO();
                edtEmailEx.setText(email);
                edtNameEx.setText(arrayOrder.get(0).getName());
                edtTelEx.setText(arrayOrder.get(0).getTel());
            }

            @Override
            public void onFailure(Call<List<OrderVO>> call, Throwable t) {

            }
        });
        System.out.println("이메일확인:" + email);

        return view;
    }
}