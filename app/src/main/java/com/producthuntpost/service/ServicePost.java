package com.producthuntpost.service;

import android.content.Context;

import com.producthuntpost.api.ApiService;
import com.producthuntpost.model.posts.PostsDTO;
import com.producthuntpost.model.posts.details.DetailsPostDTO;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;


public class ServicePost{
    private static int PER_PAGE  = 20;

   public static void getPost(Context mContext, int idCollectionSelect, final Callback<PostsDTO> callback){
        ApiService.IPosts service = ApiService.getApi(mContext);
        final Call<PostsDTO> call = service.getPost(idCollectionSelect);
        call.enqueue(new Callback<PostsDTO>() {
            @Override
            public void onResponse(Response<PostsDTO> response, Retrofit retrofit) {
                callback.onResponse(response, retrofit);
            }

            @Override
            public void onFailure(Throwable t) {
                callback.onFailure(t);
            }
        });
    }


    public static void getPostAll(Context mContext, int page, final Callback<PostsDTO> callback){
        ApiService.IPosts service = ApiService.getApi(mContext);
        final Call<PostsDTO> call = service.getPostAll(PER_PAGE, page);
        call.enqueue(new Callback<PostsDTO>() {
            @Override
            public void onResponse(Response<PostsDTO> response, Retrofit retrofit) {
                callback.onResponse(response, retrofit);
            }

            @Override
            public void onFailure(Throwable t) {
                callback.onFailure(t);
            }
        });
    }


    public static void getPostDetails(Context mContext, int idPostSelect, final Callback<DetailsPostDTO> callback){
        ApiService.IPosts service = ApiService.getApi(mContext);
        final Call<DetailsPostDTO> call = service.getPostDetails(idPostSelect);
        call.enqueue(new Callback<DetailsPostDTO>() {
            @Override
            public void onResponse(Response<DetailsPostDTO> response, Retrofit retrofit) {
                callback.onResponse(response, retrofit);
            }

            @Override
            public void onFailure(Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    public static void getPostDay(Context mContext, String day, final Callback<PostsDTO> callback){
        ApiService.IPosts service = ApiService.getApi(mContext);
        final Call<PostsDTO> call = service.getPostDay(day);
        call.enqueue(new Callback<PostsDTO>() {
            @Override
            public void onResponse(Response<PostsDTO> response, Retrofit retrofit) {
                callback.onResponse(response, retrofit);
            }

            @Override
            public void onFailure(Throwable t) {
                callback.onFailure(t);
            }
        });
    }



}
