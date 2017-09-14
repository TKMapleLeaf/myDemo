package com.example.retrofitcache.http;


import com.example.retrofitcache.bean.HomeBean;
import com.example.retrofitcache.bean.LoginBean;
import com.example.retrofitcache.bean.Task;
import com.example.retrofitcache.constants.HttpConstants;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by Administrator on 2017/2/20.
 */

public interface HttpService {


    //GET请求 传入全url
    @GET
    Call<String> getbankList(@Url String url);

//    @Headers("Cache-Control: public, max-age=60")
//    @Cache(time = 20,timeUnit = TimeUnit.MINUTES)
    @GET(HttpConstants.HOME)
    Call<HomeBean> home();

    @GET(HttpConstants.HOME)
    Call<ResponseBody> getHome();

    //GET请求
    @GET(HttpConstants.LOGIN)
    Call<LoginBean> login(@Query("username") String phone, @Query("password") String pwd);

    //POST请求 传入全url
    @FormUrlEncoded
    @POST
    Call<String> login(@Url String url, @FieldMap Map<String, String> params);

    //POST请求
    @FormUrlEncoded
    @POST(HttpConstants.LOGIN)
    Call<LoginBean> login(@FieldMap Map<String, String> params);

    @POST("/tasks")
    Call<Task> createTask(@Body Task task);

    //上传图片
    @Multipart
    @POST(HttpConstants.SAVE_AVATAR)
    Call<String> uploadImg(@Part("email") RequestBody email, @Part("pwd") RequestBody pwd, @Part MultipartBody.Part file);

    //上传图片
    @Multipart
    @POST(HttpConstants.SAVE_AVATAR)
    Call<String> uploadImg_(@Part("user_id") RequestBody userId,
                            @Part("fields") RequestBody fields,
                            @Part MultipartBody.Part file);
    //下载
    @GET
    @Streaming
    Call<ResponseBody> downloadFileWithDynamicUrlAsync(@Url String fileUrl);

//
//    @GET("book/search")
//    Observable getAppMsg(String version);
//
//    @GET("book/search")
//    Observable<String> getSearchBook(@Query("q") String name,
//                                   @Query("tag") String tag, @Query("start") int start,
//                                   @Query("count") int count);

}
