package com.example.user.cameracapture;


import org.json.JSONObject;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by Administrator on 2016-05-24.
 */
public interface Retrofit {

    public static final String ServerIP ="http://113.198.80.212:80"; // 서버 아이피http://113.198.80.233:80 http://203.252.166.241:8080

//    @POST("/PS/test")
//    public void sendString(@Query("string") String string, Callback<String> result);


    @POST("/PS/tae")
    public void sendString(@Query("string") String string, Callback<String> result);

    @GET("/PS/results")
    public void sendResult(@Query("string") String string, Callback<String> result);

//    @POST("/PS/OCR")
//    public void sendJSON(@Body JSONObject jsonObject, Callback<JSONObject> result);
//
//    @GET("/hangang/test.hangang")
//    public void sendImage(@Query("string") String image, Callback<String> result);

//    JsonObject 로 보낼떄는 Query를 Body로  @Body JsonObject info.
}

