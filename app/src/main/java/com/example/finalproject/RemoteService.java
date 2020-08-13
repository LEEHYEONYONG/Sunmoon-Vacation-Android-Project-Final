package com.example.finalproject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RemoteService {
    public static final String BASE_URL_FINALACCOUNT = "http://192.168.0.15:8088/finalAccount/";
    public static final String BASE_URL_FINALORDER = "http://192.168.0.15:8088/finalOrder/";
    public static final String BASE_URL_FINALPRODUCT = "http://192.168.0.15:8088/finalProduct/";

    /*finalAccount*/

    @POST("insert.jsp")
    Call<Void> insertAccount(@Query("email") String email, @Query("name") String name,
                             @Query("tel") String tel);

    @GET("select.jsp")
    Call<List<OrderVO>> selectAccount(@Query("email") String email);

    /*finalOrder*/

    @POST("insert.jsp")
    Call<Void> insertOrder(@Query("ordertime") String ordertime,
                           @Query("count") int count,
                           @Query("email") String email,
                           @Query("productcode") int productcode);

    /*finalProduct*/
    @GET("list.jsp")
    Call<List<OrderVO>> listProduct(@Query("order") String order, @Query("query") String query);


    /*기타*/
    @GET("select.jsp")
    Call<List<OrderVO>> listOrder();



}
