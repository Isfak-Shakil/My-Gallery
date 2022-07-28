package com.example.mygallery.View.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mygallery.R;
import com.example.mygallery.Service.Model.ImageModel;
import com.example.mygallery.View.UI.FullImageActivity;

import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<ImageModel> imageModelArrayList;

    public ImageAdapter(Context context, ArrayList<ImageModel> imageModelArrayList) {
        this.context = context;
        this.imageModelArrayList = imageModelArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=  LayoutInflater.from(context).inflate(R.layout.image_sample_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(context).load(imageModelArrayList.get(position).getUrls().getRegular()).into(holder.imageView);
        holder.imageView.setOnClickListener(view ->{
            Intent intent=new Intent(context, FullImageActivity.class);
            intent.putExtra("image",imageModelArrayList.get(position).getUrls().getRegular());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return imageModelArrayList.size();
    }

    public  class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.imageView_Id);
        }
    }
}
