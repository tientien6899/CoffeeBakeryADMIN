package com.example.coffeebakeryadmin.Banner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;

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

public class ListBannerActivity extends AppCompatActivity {
    RecyclerView danhsach;
    BannerAdapter adapter;
    private DatabaseReference mData;
    ArrayList<Banner> arrayList;
    SearchView timkiem;
    ImageView addposter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_banner);
        AnhXa();
        mData = FirebaseDatabase.getInstance().getReference();
        danhsach.setLayoutManager(new LinearLayoutManager(this));
        arrayList = new ArrayList<Banner>();

        mData.child("Poster").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap : snapshot.getChildren()){
                    Banner bn = snap.getValue(Banner.class);
                    arrayList.add(bn);
                }
                adapter = new BannerAdapter(arrayList, ListBannerActivity.this);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ListBannerActivity.this);
                danhsach.setAdapter(adapter);
                danhsach.setLayoutManager(linearLayoutManager);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        if(timkiem != null){
            timkiem.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    search(s);
                    return true;
                }
            });
        }

        addposter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),BannerActivity.class);
                startActivity(intent);
            }
        });

    }

    private void AnhXa() {
        addposter = (ImageView) findViewById(R.id.btn_AddPoster);
        danhsach = (RecyclerView) findViewById(R.id.rv_ListPoster);
        timkiem = (SearchView) findViewById(R.id.searchViewPoster);
    }

    private void search(String s){
        ArrayList<Banner> list = new ArrayList<>();
        for(Banner obj : arrayList){
            if(obj.getTen().toLowerCase().contains(s.toLowerCase())){
                list.add(obj);
            }
        }
        BannerAdapter adapter1 = new BannerAdapter(list,ListBannerActivity.this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ListBannerActivity.this);
        danhsach.setAdapter(adapter1);
        danhsach.setLayoutManager(linearLayoutManager);
    }


}