package com.example.retrofitrxcaceh;


import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by Administrator on 2017/2/20.
 */

public interface HttpService {


    //下载
    @GET
    @Streaming
    Call<ResponseBody> downloadFileWithDynamicUrlAsync(@Url String fileUrl);

    Observable getAppMsg(String version);
//
//    @GET("book/search")
//    Observable getAppMsg(String version);
//
//    @GET("book/search")
//    Observable<String> getSearchBook(@Query("q") String name,
//                                   @Query("tag") String tag, @Query("start") int start,
//                                   @Query("count") int count);

}
