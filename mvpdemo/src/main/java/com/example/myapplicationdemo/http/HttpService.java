package com.example.myapplicationdemo.http;


import com.example.myapplicationdemo.HttpConstants;
import com.example.myapplicationdemo.bean.HomeBean;
import com.example.myapplicationdemo.bean.LoginBean;
import com.example.myapplicationdemo.bean.UserBean;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
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

    @GET(HttpConstants.HOME)
    Call<HomeBean> home();

    //GET请求
    @GET(HttpConstants.LOGIN)
    Call<LoginBean> getLogin(@Query("uname") String phone,@Query("pwd") String pwd);

    //POST请求 传入全url
    @FormUrlEncoded
    @POST
    Call<String> login(@Url String url,@FieldMap Map<String, String> params);

    //POST请求
    @FormUrlEncoded
    @POST(HttpConstants.LOGIN)
    Call<UserBean> login(@FieldMap Map<String, String> params);

    //上传图片
    @Multipart
    @POST(HttpConstants.SAVE_AVATAR)
    Call<String> uploadImg(@Part("email") RequestBody email, @Part("pwd") RequestBody pwd, @Part MultipartBody.Part file);

    //上传图片
    @Multipart
    @POST(HttpConstants.SAVE_AVATAR)
    Call<UserBean> uploadImg_(@Part("user_id") RequestBody userId,
                                  @Part("fields") RequestBody fields,
                                  @Part MultipartBody.Part file);
    //下载
    @GET
    @Streaming
    Call<ResponseBody> downloadFileWithDynamicUrlAsync(@Url String fileUrl);

}
