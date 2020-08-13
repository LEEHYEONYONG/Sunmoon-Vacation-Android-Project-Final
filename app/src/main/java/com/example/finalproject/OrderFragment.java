package com.example.finalproject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Url;

import static com.example.finalproject.RemoteService.BASE_URL_FINALPRODUCT;
import java.net.*;

public class OrderFragment extends Fragment {

    Retrofit retrofit;
    RemoteService remoteService;

    RecyclerView list;
    EditText edtOrderSearch;

    String strOrder="productname";
    String strQuery="";

    //ArrayList<HashMap<String,String>> array;
    List<OrderVO> arrayOrder = new ArrayList<>();
    //OrderAdapter orderAdapter = new OrderAdapter();
    OrderAdapter orderAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        //getSupportActionBar().setTitle("주문");
        //((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("주문");
        // Inflate the layout for this fragment
        /*
        URL url = null;
        try {
            url = new URL("http://192.168.0.15:8088/finalProduct/list.jsp");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        conn.setConnectTimeout(200000);
        */

        edtOrderSearch = view.findViewById(R.id.edtOrderSearch);

        retrofit = new Retrofit.Builder().baseUrl(BASE_URL_FINALPRODUCT).addConverterFactory(GsonConverterFactory.create()).build();
        remoteService = retrofit.create(RemoteService.class);

        CallData(strOrder,strQuery);

        /*
        list = view.findViewById(R.id.recyclerOrder);
        list.setAdapter(OrderAdapter);
        */
        //System.out.println("수:" + arrayOrder.size());
        list = view.findViewById(R.id.recyclerOrder);
        //list.setLayoutManager(new GridLayoutManager(getActivity(),2));
        //list.setLayoutManager(new LinearLayoutManager(getActivity()));
        list.setLayoutManager(new GridLayoutManager(getActivity(),2));
        //orderAdapter = new OrderAdapter(getActivity(),arrayOrder);
        orderAdapter = new OrderAdapter();
        list.setAdapter(orderAdapter);



        return view;

    }

    public void CallData(String order, String query){


        Call<List<OrderVO>> call = remoteService.listProduct(order, query);
        call.enqueue(new Callback<List<OrderVO>>() {
            @Override
            public void onResponse(Call<List<OrderVO>> call, Response<List<OrderVO>> response) {
                arrayOrder= response.body();
                System.out.println("수: " + arrayOrder.size());
                orderAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<OrderVO>> call, Throwable t) {
                System.out.println("대실패");
                t.printStackTrace();
            }
        });
    }


    class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder>{
        /*
        private Context context;
        private List<OrderVO> arrayOrder = new ArrayList<>();

        public OrderAdapter(Context context ,List<OrderVO> arrayOrder){
            this.context = context;
            this.arrayOrder = arrayOrder;
        }
        */


        @NonNull
        @Override
        public OrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order,parent,false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull OrderAdapter.ViewHolder holder, int position) {
            //OrderVO orderVO = new OrderVO();
            holder.txtOrderName.setText(arrayOrder.get(position).getProductname());
            holder.txtOrderPrice.setText(arrayOrder.get(position).getPrice() + "원");
            Picasso.with(getActivity())
                    .load("http://192.168.0.15:8088/finalProduct/image/" + arrayOrder.get(position).getImage()).into(holder.txtOrderImage);
            System.out.println(arrayOrder.get(position).getImage());
            //System.out.println("확인:" + orderVO);
        }

        @Override
        public int getItemCount() {
            return arrayOrder.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView txtOrderName,txtOrderPrice;
            ImageView txtOrderImage;
            LinearLayout LayoutOrder;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                txtOrderImage = itemView.findViewById(R.id.txtOrderImage);
                txtOrderName = itemView.findViewById(R.id.txtOrderName);
                txtOrderPrice = itemView.findViewById(R.id.txtOrderPrice);
                LayoutOrder = itemView.findViewById(R.id.LayoutOrder);
                System.out.println("수:" + arrayOrder.size());

                LayoutOrder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder box = new AlertDialog.Builder(getActivity());
                        box.setTitle("주문확인");
                        box.setMessage("주문하시겠습니까?");
                        box.setPositiveButton("예", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(getActivity(),CheckActivity.class);
                                intent.putExtra("productcode",arrayOrder.get(getAdapterPosition()).getProductcode());
                                intent.putExtra("productname",arrayOrder.get(getAdapterPosition()).getProductname());
                                intent.putExtra("price",arrayOrder.get(getAdapterPosition()).getPrice());
                                intent.putExtra("image",arrayOrder.get(getAdapterPosition()).getImage());
                                intent.putExtra("email",OrderActivity.email);
                                //startActivityForResult(intent,3);
                                startActivity(intent);
                            }
                        });
                        box.setNegativeButton("아니오",null);
                        box.show();
                    }
                });

            }
        }
    }
}