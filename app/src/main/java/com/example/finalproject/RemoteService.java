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

    /*finalOrder*/

    /*finalProduct*/
    @GET("list.jsp")
    Call<List<OrderVO>> listProduct(@Query("order") String order, @Query("query") String query);



}
