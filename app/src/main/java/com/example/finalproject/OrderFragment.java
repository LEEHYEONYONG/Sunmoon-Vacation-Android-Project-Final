package com.example.finalproject;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.finalproject.RemoteService.BASE_URL_FINALPRODUCT;

public class OrderFragment extends Fragment {
    Retrofit retrofit;
    RemoteService remoteService;

    RecyclerView list;
    EditText edtOrderSearch;

    String strOrder="code";
    String strQuery="";

    //ArrayList<HashMap<String,String>> array;
    List<OrderVO> arrayOrder = new ArrayList<>();
    //OrderAdapter orderAdapter = new OrderAdapter();
    OrderAdapter orderAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        // Inflate the layout for this fragment
        edtOrderSearch = view.findViewById(R.id.edtOrderSearch);

        retrofit = new Retrofit.Builder().baseUrl(BASE_URL_FINALPRODUCT).addConverterFactory(GsonConverterFactory.create()).build();
        remoteService = retrofit.create(RemoteService.class);

        CallData(strOrder,strQuery);

        /*
        list = view.findViewById(R.id.recyclerOrder);
        list.setAdapter(OrderAdapter);
        */

        list = getActivity().findViewById(R.id.recyclerOrder);
        //list.setLayoutManager(new GridLayoutManager(getActivity(),2));
        list.setLayoutManager(new LinearLayoutManager(getActivity()));
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
                orderAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<OrderVO>> call, Throwable t) {

            }
        });
    }

    class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder>{

        @NonNull
        @Override
        public OrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order,parent,false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull OrderAdapter.ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return arrayOrder.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView txtOrderName,txtOrderPrice;
            ImageView txtOrderImage;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                txtOrderImage = itemView.findViewById(R.id.txtOrderImage);
                txtOrderName = itemView.findViewById(R.id.txtOrderName);
                txtOrderPrice =itemView.findViewById(R.id.txtOrderPrice);

            }
        }
    }
}