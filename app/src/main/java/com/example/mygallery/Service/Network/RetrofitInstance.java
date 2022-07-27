package com.example.mygallery.Service.Network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

    public static final String  BASE_URL="https://api.unsplash.com";
    public static final String  API_KEY="GwVJ91bLsphvj3WRietyZ7w_19c7czQb8M0KfVRdO7Y";


    public static Retrofit retrofit=null;

    public static ApiService getApiService(){
        if (retrofit==null){
            retrofit=new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit.create(ApiService.class);
    }
}
