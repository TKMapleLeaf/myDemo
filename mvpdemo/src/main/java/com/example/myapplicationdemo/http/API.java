package com.example.myapplicationdemo.http;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by TK on 2017/9/12.
 */

public interface API {
    @GET("article_detail.php?id=3873")
    Call<ResponseBody> getNew();
}