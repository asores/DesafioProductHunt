package com.producthuntpost.service;

import android.content.Context;

import com.producthuntpost.api.ApiService;
import com.producthuntpost.model.collections.CollectionsPostModel;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;


public class ServiceCollection {
    private static int PER_PAGE  = 20;

    public static void getTodayCollection(Context mContext, final Callback<CollectionsPostModel> callback){
        ApiService.IPosts service = ApiService.getApi(mContext);
        final Call<CollectionsPostModel> call = service.getCollection();
        call.enqueue(new Callback<CollectionsPostModel>() {
            @Override
            public void onResponse(Response<CollectionsPostModel> response, Retrofit retrofit) {
                callback.onResponse(response, retrofit);
            }

            @Override
            public void onFailure(Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    public static void getAllCollection(Context mContext, int page, final Callback<CollectionsPostModel> callback){
        ApiService.IPosts service = ApiService.getApi(mContext);
        final Call<CollectionsPostModel> call = service.getCollectionAll(PER_PAGE, page);
        call.enqueue(new Callback<CollectionsPostModel>() {
            @Override
            public void onResponse(Response<CollectionsPostModel> response, Retrofit retrofit) {
                callback.onResponse(response, retrofit);
            }

            @Override
            public void onFailure(Throwable t) {
                callback.onFailure(t);
            }
        });
    }


}
