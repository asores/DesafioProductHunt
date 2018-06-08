package com.producthuntpost.api;

import android.content.Context;

import com.producthuntpost.model.collections.CollectionsPostDTO;
import com.producthuntpost.model.posts.PostsDTO;
import com.producthuntpost.model.posts.details.DetailsPostDTO;
import com.producthuntpost.model.users.UserDetailsDTO;
import com.producthuntpost.util.Constants;
import com.producthuntpost.util.Utils;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

public class ApiService {
    private static IPosts iPosts;


    public static IPosts getApi(final Context context) {
        int cacheSize = 10 * 1024 * 1024; // 10 MB
        File httpCacheDirectory = new File(context.getCacheDir(), "responses");
        Cache cache = new Cache(httpCacheDirectory, cacheSize);

        OkHttpClient okClient = new OkHttpClient();
        okClient.setWriteTimeout(120, TimeUnit.SECONDS);
        okClient.setConnectTimeout(120, TimeUnit.SECONDS);
        okClient.setReadTimeout(120, TimeUnit.SECONDS);
        okClient.setCache(cache);
        okClient.interceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                String cacheHeaderValue = Utils.checkInternetConnection(context)
                        ? "public, max-age=2419200"
                        : "public, only-if-cached, max-stale=2419200" ;
                Request original = chain.request();
                Request.Builder requestBuilder = chain.request().newBuilder();
                requestBuilder.header("Authorization", Constants.TOKEN_AUTH);
                requestBuilder.header("Content-Type", "application/json");
                requestBuilder.header("Accept", "application/json");
                requestBuilder.method(original.method(), original.body());

                Response response = chain.proceed(requestBuilder.build());
                return response.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", cacheHeaderValue)
                        .build();
            }
        });

        okClient.networkInterceptors().add(
                new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request originalRequest = chain.request();
                        String cacheHeaderValue = Utils.checkInternetConnection(context)
                                ? "public, max-age=2419200"
                                : "public, only-if-cached, max-stale=2419200" ;
                        Request request = originalRequest.newBuilder().build();
                        Response response = chain.proceed(request);
                        return response.newBuilder()
                                .removeHeader("Pragma")
                                .removeHeader("Cache-Control")
                                .header("Cache-Control", cacheHeaderValue)
                                .build();
                    }
                }
        );

        Retrofit client = new Retrofit.Builder()
                .baseUrl(Constants.URL_BASE)
                .client(okClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        iPosts = client.create(IPosts.class);
        return iPosts;
    }



    public interface IPosts {
        @GET("/v1/collections")
        Call<CollectionsPostDTO> getCollection();

        @GET("v1/collections")
        Call<CollectionsPostDTO> getCollectionAll(
                @Query("per_page") int perPage,
                @Query("page") int page
        );

        @GET("v1/collections/{idCollectionSelect}")
        Call<PostsDTO> getPost( @Path("idCollectionSelect") int idCollectionSelect );

        @GET("v1/posts/all")
        Call<PostsDTO> getPostAll(
                @Query("per_page") int perPage,
                @Query("page") int page
        );

        @GET("v1/posts/{idPostSelect}")
        Call<DetailsPostDTO> getPostDetails(@Path("idPostSelect") int idPostSelect );

        @GET("v1/posts")
        Call<PostsDTO> getPostDay(@Query("day") String day);

        @GET("v1/users/{idUser}")
        Call<UserDetailsDTO> getUserDetails(@Path("idUser") int idUser);

    }

}
