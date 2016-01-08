package com.edu.android_day1229_01_imagehttp.dao;

import com.edu.android_day1229_01_imagehttp.entity.Item;
import com.edu.android_day1229_01_imagehttp.entity.ItemShit;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by Ming on 2015/12/29.
 */
public interface ShitService {
    /**
     *
     * @param type
     * @param page @Query("page")  是 get  @Field 是 post
     * @return
     */
    @GET("article/list/{type}")
    Call<List<Item>> getList(@Path("type") String type, @Query("page") int page);

    @GET("article/list/{type}")
    Call<ItemShit> getListResponse(@Path("type") String type, @Query("page") int page);
}
