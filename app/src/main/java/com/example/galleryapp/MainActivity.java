package com.example.galleryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import com.example.galleryapp.Adapter.MyRecyclerAdapter;
import com.example.galleryapp.Model.ImagesModel;
import com.example.galleryapp.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int REQ_PER_WRITE_EXTERNAL_STORAGE= 2;
    private ActivityMainBinding binding;

    ArrayList<ImagesModel> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED)
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQ_PER_WRITE_EXTERNAL_STORAGE);
        }
        else
        {
            new MyAsyncTask().execute();
        }


    }



    class MyAsyncTask extends AsyncTask<Void,Void,Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            binding.mainPb.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            getAllImages();
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            binding.mainPb.setVisibility(View.GONE);
            fetchImagesIntoRecycler();

        }
    }


    private void fetchImagesIntoRecycler(){
        MyRecyclerAdapter adapter = new MyRecyclerAdapter(MainActivity.this,data);
        RecyclerView.LayoutManager lm = new GridLayoutManager(MainActivity.this,2);
        binding.mainRv.setLayoutManager(lm);
        binding.mainRv.setHasFixedSize(true);
        binding.mainRv.setAdapter(adapter);
        Toast.makeText(this, ""+data.size(), Toast.LENGTH_SHORT).show();
    }

    private void getAllImages(){
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = new String[]{
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.SIZE,
                MediaStore.Images.Media.DESCRIPTION
        };
        String sortOrder = MediaStore.Images.Media.DATE_ADDED;

        Cursor cursor = getContentResolver().query(uri,projection,null,null,sortOrder);

        if (cursor.moveToFirst())
        {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String path = cursor.getString(2);
                String size = cursor.getString(3);
                String des = cursor.getString(4);

                ImagesModel imagesModel = new ImagesModel(String.valueOf(id),name,path,size,des);
                data.add(imagesModel);
            }while (cursor.moveToNext());
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQ_PER_WRITE_EXTERNAL_STORAGE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                new MyAsyncTask().execute();
            }else {
                Toast.makeText(this, "PERMISSION DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }
}