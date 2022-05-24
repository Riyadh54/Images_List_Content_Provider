package com.example.galleryapp.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.galleryapp.Model.ImagesModel;
import com.example.galleryapp.databinding.CustomItemsBinding;

import java.util.ArrayList;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<ImagesModel> data;

    public MyRecyclerAdapter(Context context, ArrayList<ImagesModel> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CustomItemsBinding binding = CustomItemsBinding.inflate(LayoutInflater.from(context),parent,false);

        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ImagesModel imagesModel = data.get(position);


       holder.binding.customItemsImg.setImageURI(Uri.parse(imagesModel.getImagePath()));

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
            CustomItemsBinding binding;

        public MyViewHolder(@NonNull CustomItemsBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;


        }
    }
}
