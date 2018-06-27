package com.producthuntpost.service;

import android.content.Context;

import com.producthuntpost.api.ApiService;
import com.producthuntpost.model.users.UserDetailsModel;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class ServiceUser {

    public static void getUserDetails(Context mContext, int idUser, final Callback<UserDetailsModel> callback){
        ApiService.IPosts service = ApiService.getApi(mContext);
        final Call<UserDetailsModel> call = service.getUserDetails(idUser);
        call.enqueue(new Callback<UserDetailsModel>() {
            @Override
            public void onResponse(Response<UserDetailsModel> response, Retrofit retrofit) {
                callback.onResponse(response, retrofit);
            }

            @Override
            public void onFailure(Throwable t) {
                callback.onFailure(t);
            }
        });
    }
}
