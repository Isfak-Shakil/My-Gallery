package com.example.mygallery.View.UI;

import androidx.annotation.NonNull;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.mygallery.R;
import com.example.mygallery.Service.Model.ImageModel;
import com.example.mygallery.Service.Model.SearchModel;
import com.example.mygallery.Service.Network.RetrofitInstance;
import com.example.mygallery.View.Adapter.ImageAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
   private GridLayoutManager layoutManager;
   private ArrayList<ImageModel> list;
    private ImageAdapter imageAdapter;
    private ProgressDialog progressDialog;
    private  int page=1;
    private  int pageSize=30;
    private boolean isLoading;
    private boolean isLastPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));

        recyclerView=findViewById(R.id.recyclerView);
        list=new ArrayList<>();
       imageAdapter=new ImageAdapter(this,list);
        layoutManager=new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(imageAdapter);


        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();


        getData();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItem=layoutManager.getChildCount();
                int totalItem=layoutManager.getItemCount();
                int firstVisibleItemPosition=layoutManager.findFirstVisibleItemPosition();
                if (!isLoading && !isLastPage){
                    if ((visibleItem +firstVisibleItemPosition >=totalItem)
                            && firstVisibleItemPosition >= 0
                            && totalItem >=pageSize){
                        page++;
                        getData();
                    }
                }
            }
        });
    }

    private void getData() {
        isLoading=true;
        RetrofitInstance.getApiService().getImages(page,60).enqueue(new Callback<List<ImageModel>>() {
            @Override
            public void onResponse(Call<List<ImageModel>> call, Response<List<ImageModel>> response) {
                if (response.body()!=null){
                    list.addAll(response.body());
                    imageAdapter.notifyDataSetChanged();

                }
                isLoading=false;
                progressDialog.dismiss();
                if (list.size() > 0){
                    isLastPage=list.size()<pageSize;
                }else isLastPage=true;

            }

            @Override
            public void onFailure(Call<List<ImageModel>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this,"Error: "+t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu,menu);
        MenuItem search=menu.findItem(R.id.searchId);
   SearchView searchView= (SearchView) search.getActionView();
   searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
       @Override
       public boolean onQueryTextSubmit(String s) {
           progressDialog.show();
           searchData(s);
           return true;
       }

       @Override
       public boolean onQueryTextChange(String s) {
           return false;
       }
   });
        return true;
    }

    private void searchData(String s) {
        RetrofitInstance.getApiService().searchImages(s).enqueue(new Callback<SearchModel>() {
            @Override
            public void onResponse(Call<SearchModel> call, Response<SearchModel> response) {
                list.clear();
                list.addAll(response.body().getResults());
                imageAdapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<SearchModel> call, Throwable t) {

            }
        });
    }
}