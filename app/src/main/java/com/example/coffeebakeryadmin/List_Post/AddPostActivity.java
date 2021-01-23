package com.example.coffeebakeryadmin.List_Post;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.coffeebakeryadmin.R;

public class AddPostActivity extends AppCompatActivity {

    ImageView img_backdsbv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        AnhXa();

        img_backdsbv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddPostActivity.this, ListPostActivity.class);
                startActivity(intent);
            }
        });
    }

    private void AnhXa() {
        img_backdsbv = findViewById(R.id.img_BackDSBV);
    }
}