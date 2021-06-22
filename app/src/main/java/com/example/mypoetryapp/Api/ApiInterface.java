package com.example.mypoetryapp.Api;

import com.example.mypoetryapp.Response.DeletePoetryResponse;
import com.example.mypoetryapp.Response.GetPoetryResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {

    @GET("getMyPoetry.php")
    Call<GetPoetryResponse> getMyPoetry();

    @FormUrlEncoded
    @POST("deleteMyPoetry.php")
    Call<DeletePoetryResponse> deleteMyPoetry(@Field("id") String poetry_id);

    @FormUrlEncoded
    @POST("addToPoetry.php")
    Call<DeletePoetryResponse> addMyPoetry(@Field("poet_name") String poetName, @Field("poetry_data") String poetryData);

    @FormUrlEncoded
    @POST("updateMyPoetry.php")
    Call<DeletePoetryResponse> updateMyPoetry(@Field("poetry_data") String poetryData, @Field("id") String poetryId);
}
