package com.example.coffeebakeryadmin.Drivers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.coffeebakeryadmin.List_Receipt.ListReceiptActivity;
import com.example.coffeebakeryadmin.List_Receipt.Receipt;
import com.example.coffeebakeryadmin.List_Receipt.RecieptAdapter;
import com.example.coffeebakeryadmin.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.coffeebakeryadmin.AdminActivity.mData;

public class DriverActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DriverAdapter adapter;
    ArrayList<Driver> listDriver;
    Button themmoi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);
        AnhXa();
        listDriver = new ArrayList<Driver>();
        mData.child("Tài xế").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot data : snapshot.getChildren()){
                        Driver dr = data.getValue(Driver.class);
                        listDriver.add(dr);
                    }
                    adapter = new DriverAdapter(listDriver, DriverActivity.this);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DriverActivity.this);

                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(linearLayoutManager);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        themmoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), AddDriverActivity.class));
            }
        });
    }

    private void AnhXa() {
        recyclerView = findViewById(R.id.list_driver);
        themmoi = findViewById(R.id.btn_themmoitaixe);
    }
}