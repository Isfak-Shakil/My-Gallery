package com.example.mygallery.Service.Network;

import com.example.mygallery.Service.Model.ImageModel;
import com.example.mygallery.Service.Model.SearchModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;

import static com.example.mygallery.Service.Network.RetrofitInstance.API_KEY;

  public interface ApiService {
    @Headers("Authorization: Client-ID "+API_KEY)
    @GET("/photos")
    Call<List<ImageModel>> getImages(@Query("page")int page,@Query("per_page")int per_Page);

    @Headers("Authorization: Client-ID "+API_KEY)
    @GET("/search/photos")
    Call<SearchModel> searchImages(@Query("query")String query);
}
