package com.example.moments.http.cache;

import retrofit2.Call;
import retrofit2.Response;


public interface CacheCallback<T> {

    void onResponse(Call<T> call, Response<T> response);

    void onFailure(Call<T> call, Throwable t);

    void onCache(T t);
}
