package com.test.helloworld.test4android.network;

import com.test.helloworld.test4android.bean.Province;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface RetrofitRequestsInter {


//    @GET("api/china")
//    Call<Province[]> getCall();
//
//    @GET("api/china")
//    Observable<Province[]> getRxCall();


    @GET("users.json")
    Call<ResponseBody> getCall();

    @GET("users.json")
    Observable<ResponseBody> getRxCall();
//    @POST("/form")
//    @FormUrlEncoded
//    public Call<ResponseBody> getPost(@Field("id") String id, @Field("name") String name);


}
