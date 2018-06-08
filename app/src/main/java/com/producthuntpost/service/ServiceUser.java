package com.producthuntpost.service;

import android.content.Context;

import com.producthuntpost.api.ApiService;
import com.producthuntpost.model.users.UserDetailsDTO;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class ServiceUser {

    public static void getUserDetails(Context mContext, int idUser, final Callback<UserDetailsDTO> callback){
        ApiService.IPosts service = ApiService.getApi(mContext);
        final Call<UserDetailsDTO> call = service.getUserDetails(idUser);
        call.enqueue(new Callback<UserDetailsDTO>() {
            @Override
            public void onResponse(Response<UserDetailsDTO> response, Retrofit retrofit) {
                callback.onResponse(response, retrofit);
            }

            @Override
            public void onFailure(Throwable t) {
                callback.onFailure(t);
            }
        });
    }
}
