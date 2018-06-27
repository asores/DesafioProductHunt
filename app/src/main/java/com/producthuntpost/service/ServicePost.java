package com.producthuntpost.service;

import android.content.Context;

import com.producthuntpost.api.ApiService;
import com.producthuntpost.model.posts.PostsModel;
import com.producthuntpost.model.posts.details.DetailsPostModel;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;


public class ServicePost{
    private static int PER_PAGE  = 20;

   public static void getPost(Context mContext, int idCollectionSelect, final Callback<PostsModel> callback){
        ApiService.IPosts service = ApiService.getApi(mContext);
        final Call<PostsModel> call = service.getPost(idCollectionSelect);
        call.enqueue(new Callback<PostsModel>() {
            @Override
            public void onResponse(Response<PostsModel> response, Retrofit retrofit) {
                callback.onResponse(response, retrofit);
            }

            @Override
            public void onFailure(Throwable t) {
                callback.onFailure(t);
            }
        });
    }


    public static void getPostAll(Context mContext, int page, final Callback<PostsModel> callback){
        ApiService.IPosts service = ApiService.getApi(mContext);
        final Call<PostsModel> call = service.getPostAll(PER_PAGE, page);
        call.enqueue(new Callback<PostsModel>() {
            @Override
            public void onResponse(Response<PostsModel> response, Retrofit retrofit) {
                callback.onResponse(response, retrofit);
            }

            @Override
            public void onFailure(Throwable t) {
                callback.onFailure(t);
            }
        });
    }


    public static void getPostDetails(Context mContext, int idPostSelect, final Callback<DetailsPostModel> callback){
        ApiService.IPosts service = ApiService.getApi(mContext);
        final Call<DetailsPostModel> call = service.getPostDetails(idPostSelect);
        call.enqueue(new Callback<DetailsPostModel>() {
            @Override
            public void onResponse(Response<DetailsPostModel> response, Retrofit retrofit) {
                callback.onResponse(response, retrofit);
            }

            @Override
            public void onFailure(Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    public static void getPostDay(Context mContext, String day, final Callback<PostsModel> callback){
        ApiService.IPosts service = ApiService.getApi(mContext);
        final Call<PostsModel> call = service.getPostDay(day);
        call.enqueue(new Callback<PostsModel>() {
            @Override
            public void onResponse(Response<PostsModel> response, Retrofit retrofit) {
                callback.onResponse(response, retrofit);
            }

            @Override
            public void onFailure(Throwable t) {
                callback.onFailure(t);
            }
        });
    }



}
