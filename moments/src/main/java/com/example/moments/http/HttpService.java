package com.example.moments.http;


import com.example.moments.bean.DynamicBean;
import com.example.moments.constants.HttpConstants;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Administrator on 2017/2/20.
 */

public interface HttpService {

    //动态GET请求
    @GET(HttpConstants.DYNAMIC)
    Call<DynamicBean> dynamic();

    //动态
    @FormUrlEncoded
    @POST(HttpConstants.DYNAMIC)
    Call<DynamicBean> getDynamic(@FieldMap Map<String, String> params);


}
