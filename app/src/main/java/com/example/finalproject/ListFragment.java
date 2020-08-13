package com.example.finalproject;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.finalproject.RemoteService.BASE_URL_FINALORDER;


public class ListFragment extends Fragment {
    Retrofit retrofit;
    RemoteService remoteService;
    List<OrderVO> arrayOrderList = new ArrayList<>();
    ListOrderAdapter listOrderAdapter = new ListOrderAdapter();
    ListView list;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        //((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("주문내역");
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL_FINALORDER).addConverterFactory(GsonConverterFactory.create()).build();
        remoteService = retrofit.create(RemoteService.class);
        callData();

        System.out.println("아악: " + arrayOrderList);
        list = view.findViewById(R.id.listOrder);
        list.setAdapter(listOrderAdapter);
        // Inflate the layout for this fragment
        return view;
    }

    public void callData(){
        Call<List<OrderVO>> call = remoteService.listOrder();
        call.enqueue(new Callback<List<OrderVO>>() {
            @Override
            public void onResponse(Call<List<OrderVO>> call, Response<List<OrderVO>> response) {
                arrayOrderList = response.body();
                listOrderAdapter.notifyDataSetChanged();
                System.out.println("으악: " + arrayOrderList);
            }

            @Override
            public void onFailure(Call<List<OrderVO>> call, Throwable t) {

            }
        });
    }


    class ListOrderAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return arrayOrderList.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.item_list,viewGroup,false);

            ImageView imgList = view.findViewById(R.id.imgList);//이미지
            TextView txtListOrderCode = view.findViewById(R.id.txtListOrderCode);//주문코드
            TextView txtListOrdertime = view.findViewById(R.id.txtListOrdertime);//주문시간
            TextView txtListEmail = view.findViewById(R.id.txtListEmail);//주문이메일
            TextView txtListProductCode = view.findViewById(R.id.txtListProductCode);//상품코드
            TextView txtListProductName = view.findViewById(R.id.txtListProductName);//상품이름
            TextView txtListProductPrice = view.findViewById(R.id.txtListProductPrice);//상품가격
            TextView txtListSumPrice = view.findViewById(R.id.txtListSumPrice);//합계금액


            return view;
        }
    }
}