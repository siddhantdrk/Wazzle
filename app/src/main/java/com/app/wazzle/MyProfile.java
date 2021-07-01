package com.app.wazzle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class MyProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);


        RecyclerView rv_songs_list = findViewById(R.id.rv_songs_list);
        MySongItemAdataper mySongItemAdataper = new MySongItemAdataper(this);
        rv_songs_list.setLayoutManager(new LinearLayoutManager(this));
        rv_songs_list.setAdapter(mySongItemAdataper);
    }
}