package com.example.coffeebakeryadmin.List_Receipt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.coffeebakeryadmin.AdminActivity;
import com.example.coffeebakeryadmin.R;
import java.util.ArrayList;
import static com.example.coffeebakeryadmin.AdminActivity.mData;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import com.google.firebase.database.ValueEventListener;

public class ListReceiptActivity extends AppCompatActivity {
    ImageView back;
    RecyclerView recyclerView;
    RecieptAdapter adapter;
    ArrayList<Receipt> listReceipt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_receipt);
        AnhXa();
        listReceipt = new ArrayList<Receipt>();
        mData.child("DonHang").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data : snapshot.getChildren()){
                    String temp_nguoidung = data.child("nguoidung").getValue().toString();
                    String temp_madon = data.child("madon").getValue().toString();
                    String temp_ngaydat = data.child("ngaydat").getValue().toString();
                    String temp_tongtien = data.child("tongtien").getValue().toString();
                    String temp_trangthai = data.child("trangthai").getValue().toString();
                    listReceipt.add(new Receipt(temp_madon,temp_ngaydat,temp_tongtien,temp_trangthai,temp_nguoidung));
                }
                adapter = new RecieptAdapter(listReceipt,ListReceiptActivity.this);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ListReceiptActivity.this);

                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(linearLayoutManager);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListReceiptActivity.this, AdminActivity.class);
                startActivity(intent);
            }
        });

    }

    private void AnhXa() {
        back = (ImageView) findViewById(R.id.btn_BackReceipt);
        recyclerView = (RecyclerView) findViewById(R.id.list_receipt);
    }
}