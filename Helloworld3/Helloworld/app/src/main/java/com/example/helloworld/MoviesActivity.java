package com.example.helloworld;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helloworld.adapters.MovieAdapter;
import com.example.helloworld.objects.Movie;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class MoviesActivity extends AppCompatActivity {
    private ArrayList<Movie> arrayList;
    private MovieAdapter movieAdapter;
    private RecyclerView recyclerView;

    private FloatingActionButton btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_movies);
        prepareMovieData();//can phai goi neu lam viec voi networking.
        //su dung recyclerview de hien thi du lieu
        //do du lieu len adapter
        movieAdapter = new MovieAdapter(this, arrayList, new MovieAdapter.ClickListeners() {
            @Override
            public void onItemClick(int position, View v) {
                Intent intent = new Intent(getBaseContext(),LoginActivity.class);
                String title = arrayList.get(position).getTitle();
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(int position, View v) {
                //code here
                String title = arrayList.get(position).getTitle();
                Toast.makeText(getBaseContext(),title,Toast.LENGTH_SHORT).show();
                btnAdd.setVisibility(View.VISIBLE);
            }
        });
        //set du lieu cho recyclerview
        recyclerView = findViewById(R.id.rcListMovies);
        recyclerView.setAdapter(movieAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //
        btnAdd = findViewById(R.id.floatingActionButton);
        btnAdd.setOnClickListener(v -> {
            Movie movie = new Movie("Huong vi tinh than", "Tinh cam", "2021");
            arrayList.add(movie);
            //thong bao giao dien thay doi
            movieAdapter.notifyDataSetChanged();
        });
//        btnAdd.setVisibility(View.INVISIBLE);
    }

    private void prepareMovieData() {
        arrayList = new ArrayList<>();
        Movie movie = new Movie("Mad Max: Fury Road", "Action & Adventure", "2015");
        arrayList.add(movie);

        movie = new Movie("Inside Out", "Animation, Kids & Family", "2015");
        arrayList.add(movie);

        movie = new Movie("Star Wars: Episode VII - The Force Awakens", "Action", "2015");
        arrayList.add(movie);
//
//        movie = new Movie("Shaun the Sheep", "Animation", "2015");
//        arrayList.add(movie);
//
//        movie = new Movie("The Martian", "Science Fiction & Fantasy", "2015");
//        arrayList.add(movie);
//
//        movie = new Movie("Mission: Impossible Rogue Nation", "Action", "2015");
//        arrayList.add(movie);
    }
}