package com.example.coffeebakeryadmin.ListCustomer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeebakeryadmin.AdminActivity;
import com.example.coffeebakeryadmin.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListCustomerActivity extends AppCompatActivity {
    RecyclerView danhsach;
    CustomerAdapter adapter;
    private DatabaseReference mData;
    ArrayList<Customer> arrayList;
    SearchView timkiem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_customer);
        AnhXa();
        mData = FirebaseDatabase.getInstance().getReference();
        danhsach.setLayoutManager(new LinearLayoutManager(this));
        arrayList = new ArrayList<Customer>();

        mData.child("Khách hàng").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap : snapshot.getChildren()){
                    Customer cu = snap.getValue(Customer.class);
                    arrayList.add(cu);
                }
                adapter = new CustomerAdapter(arrayList, ListCustomerActivity.this);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ListCustomerActivity.this);
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

    }
    private void AnhXa() {
        danhsach = (RecyclerView) findViewById(R.id.list_customer);
        timkiem = (SearchView) findViewById(R.id.searchViewCustomer);
    }

    private void search(String s){
        ArrayList<Customer> list = new ArrayList<>();
        for(Customer obj : arrayList){
            if(obj.getHoten().toLowerCase().contains(s.toLowerCase())
            || obj.getSdt().toLowerCase().contains(s.toLowerCase())){
                list.add(obj);
            }
        }
        CustomerAdapter adapter1 = new CustomerAdapter(list,ListCustomerActivity.this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ListCustomerActivity.this);
        danhsach.setAdapter(adapter1);
        danhsach.setLayoutManager(linearLayoutManager);
    }

}