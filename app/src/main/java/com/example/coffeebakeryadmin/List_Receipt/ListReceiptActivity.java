package com.example.coffeebakeryadmin.List_Receipt;

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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import me.pushy.sdk.Pushy;

import static com.example.coffeebakeryadmin.AdminActivity.mData;

public class ListReceiptActivity extends AppCompatActivity {
    ImageView back;
    RecyclerView recyclerView;
    RecieptAdapter adapter;
    ArrayList<Receipt> listReceipt;
    SearchView timkiem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_receipt);
        Pushy.listen(this);
        AnhXa();
        listReceipt = new ArrayList<Receipt>();
        mData.child("Đơn hàng").child("Thông tin").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data : snapshot.getChildren()){
                    Receipt re = data.getValue(Receipt.class);
                    listReceipt.add(re);
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

    private void search(String s){
        ArrayList<Receipt> list = new ArrayList<>();
        for(Receipt obj : listReceipt){
            if(obj.getTrangthai().toLowerCase().contains(s.toLowerCase()) ||
            obj.getNguoidung().toLowerCase().contains(s.toLowerCase()) || obj.getMadon().contains(s)
            || obj.getNgaydat().contains(s) || obj.getTongtien().contains(s)){
                list.add(obj);
            }
        }
        RecieptAdapter adapter1 = new RecieptAdapter(list, ListReceiptActivity.this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ListReceiptActivity.this);
        recyclerView.setAdapter(adapter1);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void AnhXa() {
        back = (ImageView) findViewById(R.id.btn_BackReceipt);
        recyclerView = (RecyclerView) findViewById(R.id.list_receipt);
        timkiem = findViewById(R.id.searchViewDonHang);
    }
}