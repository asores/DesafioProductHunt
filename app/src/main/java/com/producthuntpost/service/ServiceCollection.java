package com.producthuntpost.service;

import android.content.Context;

import com.producthuntpost.api.ApiService;
import com.producthuntpost.model.collections.CollectionsPostDTO;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;


public class ServiceCollection {
    private static int PER_PAGE  = 20;

    public static void getTodayCollection(Context mContext, final Callback<CollectionsPostDTO> callback){
        ApiService.IPosts service = ApiService.getApi(mContext);
        final Call<CollectionsPostDTO> call = service.getCollection();
        call.enqueue(new Callback<CollectionsPostDTO>() {
            @Override
            public void onResponse(Response<CollectionsPostDTO> response, Retrofit retrofit) {
                callback.onResponse(response, retrofit);
            }

            @Override
            public void onFailure(Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    public static void getAllCollection(Context mContext, int page, final Callback<CollectionsPostDTO> callback){
        ApiService.IPosts service = ApiService.getApi(mContext);
        final Call<CollectionsPostDTO> call = service.getCollectionAll(PER_PAGE, page);
        call.enqueue(new Callback<CollectionsPostDTO>() {
            @Override
            public void onResponse(Response<CollectionsPostDTO> response, Retrofit retrofit) {
                callback.onResponse(response, retrofit);
            }

            @Override
            public void onFailure(Throwable t) {
                callback.onFailure(t);
            }
        });
    }


}
