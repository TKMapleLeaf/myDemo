package com.example.moments.http;


import com.example.moments.bean.DynamicBean;
import com.example.moments.bean.QiniuToken;
import com.example.moments.constants.HttpConstants;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

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

    //获取七牛token
    @FormUrlEncoded
    @POST(HttpConstants.TOKEN)
    Call<QiniuToken> getToken(@FieldMap Map<String, String> params);


    //下载
    @GET
    @Streaming
    Call<ResponseBody> downloadFileWithDynamicUrlAsync(@Url String fileUrl);


}
