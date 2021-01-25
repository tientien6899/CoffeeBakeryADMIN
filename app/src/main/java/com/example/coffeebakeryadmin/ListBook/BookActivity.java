package com.example.coffeebakeryadmin.ListBook;

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

public class BookActivity extends AppCompatActivity {

    ImageView addbook, back;
    RecyclerView danhsach;
    BookAdapter adapter;
    ArrayList<Book> arrayList;
    SearchView timkiem;
    private DatabaseReference mData = FirebaseDatabase.getInstance().getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        AnhXa();
        danhsach.setLayoutManager(new LinearLayoutManager(this));
        arrayList = new ArrayList<Book>();

        mData.child("ChinhSach").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap : snapshot.getChildren()){
                    Book b = snap.getValue(Book.class);
                    arrayList.add(b);
                }
                adapter = new BookAdapter(arrayList, BookActivity.this);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(BookActivity.this);
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

        addbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AddBookActivity.class);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AdminActivity.class);
                startActivity(intent);
            }
        });
    }

    private void AnhXa() {
        addbook = findViewById(R.id.img_addbook);
        danhsach = findViewById(R.id.list_book);
        timkiem = findViewById(R.id.searchviewBook);
        back = findViewById(R.id.img_Back_to_admin);
    }

    private void search(String s){
        ArrayList<Book> list = new ArrayList<>();
        for(Book obj : arrayList){
            if(obj.getTieude().toLowerCase().contains(s.toLowerCase())){
                list.add(obj);
            }
        }
        BookAdapter adapter1 = new BookAdapter(list, BookActivity.this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(BookActivity.this);
        danhsach.setAdapter(adapter1);
        danhsach.setLayoutManager(linearLayoutManager);
    }
}