package com.example.coffeebakeryadmin.List_Post;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.coffeebakeryadmin.AdminActivity;
import com.example.coffeebakeryadmin.R;

public class ListPostActivity extends AppCompatActivity {

    ImageView img_backdsbv, img_adddsbv;
    RecyclerView listpost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_post);

        AnhXa();

        img_backdsbv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListPostActivity.this, AdminActivity.class);
                startActivity(intent);
            }
        });

        img_adddsbv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListPostActivity.this, AddPostActivity.class);
                startActivity(intent);
            }
        });
    }

    private void AnhXa() {
        img_backdsbv = findViewById(R.id.img_BackDSBV);
        img_adddsbv = findViewById(R.id.img_AddDSBV);
        listpost = findViewById(R.id.list_post);
    }
}