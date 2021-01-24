package com.example.coffeebakeryadmin.List_Post;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;

import com.example.coffeebakeryadmin.AdminActivity;
import com.example.coffeebakeryadmin.ListCustomer.Customer;
import com.example.coffeebakeryadmin.ListCustomer.CustomerAdapter;
import com.example.coffeebakeryadmin.ListCustomer.ListCustomerActivity;
import com.example.coffeebakeryadmin.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListPostActivity extends AppCompatActivity {

    ImageView img_backdsbv, img_adddsbv;
    RecyclerView listpost;
    PostAdapter adapter;
    private DatabaseReference mData;
    ArrayList<Post> arrayList;
    SearchView timkiem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_post);

        AnhXa();

        mData = FirebaseDatabase.getInstance().getReference();
        listpost.setLayoutManager(new LinearLayoutManager(this));
        arrayList = new ArrayList<Post>();

        mData.child("Post").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap : snapshot.getChildren()){
                    Post p = snap.getValue(Post.class);
                    arrayList.add(p);
                }
                adapter = new PostAdapter(arrayList, ListPostActivity.this);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ListPostActivity.this);
                listpost.setAdapter(adapter);
                listpost.setLayoutManager(linearLayoutManager);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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