package com.app.wazzle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MyProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        findViewById(R.id.iv_edit_profile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyProfile.this, EditProfile.class));
            }
        });

        RecyclerView rv_songs_list = findViewById(R.id.rv_songs_list);
        MySongItemAdataper mySongItemAdataper = new MySongItemAdataper(this);
        rv_songs_list.setLayoutManager(new LinearLayoutManager(this));
        rv_songs_list.setAdapter(mySongItemAdataper);
    }
}