package com.example.user.advanceapp;

import java.util.List;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;

/**
 * Created by User on 15/06/2016.
 */
public interface TransactionApi {
    @GET("/expenses")
    Call<Transactions> getTrans();

    @POST("/expenses")
    Call<Transaction> addTrans(@Body Transaction transaction);
}
