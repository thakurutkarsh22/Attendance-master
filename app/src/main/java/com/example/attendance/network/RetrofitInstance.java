package com.example.attendance.network;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class RetrofitInstance {
    private static Retrofit retrofit;
    private static final String BASE_URL = "https://webapiforcruddev.azurewebsites.net/";


     // Create an instance of Retrofit object


   /*OkHttpClient okHttpClient = new OkHttpClient().newBuilder().addInterceptor(new Interceptor() {
       @Override
       public Response intercept(Chain chain) throws IOException {
           Request.Builder  builder = chain.request().newBuilder();
           String auth =
           return null;
       }
   }).build();*/

    public static Retrofit getRetrofitInstance() {

        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
