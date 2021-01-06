package com.example.coffeebakeryadmin;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

public class ListProductActivity extends AppCompatActivity {

    ImageButton back, add;
    Intent intent;
    RecyclerView danhsach;
    ProductAdapter adapter;
    EditText timkiem;
    private DatabaseReference danhsachRef, mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_product);

        AnhXa();
        mData = FirebaseDatabase.getInstance().getReference();
        danhsach.setLayoutManager(new LinearLayoutManager(this));

        danhsachRef = mData.child("SanPham");

        FirebaseRecyclerOptions<Product> options =
                new FirebaseRecyclerOptions.Builder<Product>()
                        .setQuery(danhsachRef, new SnapshotParser<Product>() {
                            @NonNull
                            @Override
                            public Product parseSnapshot(@NonNull DataSnapshot snapshot) {
                                return new Product(snapshot.child("tensp").getValue().toString(),
                                        snapshot.child("danhmuc").getValue().toString(),
                                        snapshot.child("link").getValue().toString(),
                                        snapshot.child("masp").getValue().toString(),
                                        snapshot.child("giaS").getValue().toString(),
                                        snapshot.child("giaM").getValue().toString(),
                                        snapshot.child("giaL").getValue().toString(),
                                        snapshot.child("giaKM").getValue().toString(),
                                        snapshot.child("mota").getValue().toString());
                            }
                        })
                        .build();
        adapter = new ProductAdapter(options);
        danhsach.setAdapter(adapter);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(ListProductActivity.this, AdminActivity.class);
                startActivity(intent);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(ListProductActivity.this, AddProductActivity.class);
                startActivity(intent);
            }
        });
    }

    private void AnhXa() {
        back = (ImageButton) findViewById(R.id.btn_BackHome);
        add = (ImageButton) findViewById(R.id.btn_AddProduct);
        timkiem = (EditText) findViewById(R.id.timkiem_sanpham);
        danhsach = (RecyclerView) findViewById(R.id.rv_ListProduct);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

}